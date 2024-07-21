package com.SumPortfolio.Controller;

import com.SumPortfolio.Model.User;
import com.SumPortfolio.Repository.UserRepository;
import com.SumPortfolio.Request.LoginRequest;
import com.SumPortfolio.Response.AuthResponse;
import com.SumPortfolio.SecurityConfig.JWTProvider;
import com.SumPortfolio.Service.CustomUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customUserDetails;

    @PostMapping("/signUp")
    public ResponseEntity<User>createUserHandler(@RequestBody User user) throws Exception{

        User isUserExist = userRepo.findByEmail(user.getEmail());
        if(isUserExist!=null){
            throw new Exception("User Already Exist : " + user.getEmail());
        }
        User createdUser = new User();
        createdUser.setFullName(user.getFullName());
        createdUser.setEmail(user.getEmail());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepo.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JWTProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("signUp success");
        authResponse.setJwt(jwt);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse>singInUsingEmail(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password  = loginRequest.getPassword();

        Authentication authentication = authentication(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JWTProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("singIn success");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authentication(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username : " + username);
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
