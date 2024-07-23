package com.SumPortfolio.Service;

import com.SumPortfolio.Model.PlanType;
import com.SumPortfolio.Model.Subscription;
import com.SumPortfolio.Model.User;

public interface ISubscriptionService {

    Subscription createSubscription(User user) throws Exception;
    Subscription getUserSubscription(Long userId) throws Exception;
    Subscription upgradeSubscription(Long userId, PlanType type) throws Exception;
    boolean isValid(Subscription subscription) throws Exception;
}
