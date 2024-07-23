package com.SumPortfolio.Controller;

import com.SumPortfolio.Model.PlanType;
import com.SumPortfolio.Model.Subscription;
import com.SumPortfolio.Model.User;
import com.SumPortfolio.Service.ISubscriptionService;
import com.SumPortfolio.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subs")
public class SubscriptionController {
    @Autowired
    private ISubscriptionService subscriptionService;
    @Autowired
    private IUserService userService;

    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserByJwt(jwt);
        Subscription subscription = subscriptionService.getUserSubscription(user.getId());

        return  new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubscription(
            @RequestHeader("Authorization") String jwt,
            @RequestParam PlanType type
    ) throws Exception{
        User user = userService.findUserByJwt(jwt);
        Subscription subscription = subscriptionService.upgradeSubscription(user.getId(), type);

        return  new ResponseEntity<>(subscription, HttpStatus.OK);
    }
}
