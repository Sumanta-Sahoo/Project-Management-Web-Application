package com.SumPortfolio.Service;

import com.SumPortfolio.Model.User;
import com.SumPortfolio.Repository.UserRepository;
import com.SumPortfolio.SecurityConfig.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepo;
    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String email = JWTProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        if(user==null)
            throw new Exception("User Not Found With Email : " + email);
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty())
            throw new Exception("User Not Found With UserID : " + userId);
        return user.get();
    }

    @Override
    public User updateUserProjectSize(User user, Integer projectSize) throws Exception {
        user.setProjectSize(user.getProjectSize()+projectSize);
        return userRepo.save(user);
    }
}
