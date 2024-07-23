package com.SumPortfolio.Service;

import com.SumPortfolio.Model.PlanType;
import com.SumPortfolio.Model.Subscription;
import com.SumPortfolio.Model.User;
import com.SumPortfolio.Repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService{
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private IUserService userService;
    @Override
    public Subscription createSubscription(User user) throws Exception {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setPlanType(PlanType.FREE);
        subscription.setValid(true);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if(!isValid(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType type) throws Exception {
        Subscription subscription = getUserSubscription(userId);
        subscription.setPlanType(type);
        subscription.setSubscriptionStartDate(LocalDate.now());
        if(type.equals(PlanType.MONTHLY)){
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }else{
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }

        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) throws Exception {
        if(subscription.getPlanType().equals(PlanType.FREE)){
            return  true;
        }
        LocalDate endDate = subscription.getGetSubscriptionEndDate();
        LocalDate currentDate = LocalDate.now();
        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
