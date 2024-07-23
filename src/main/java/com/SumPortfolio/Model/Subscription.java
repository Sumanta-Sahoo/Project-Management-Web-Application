package com.SumPortfolio.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subId;
    private LocalDate subscriptionStartDate;
    private LocalDate getSubscriptionEndDate;

    @OneToOne
    private User user;
    private PlanType planType;
    private boolean isValid;
}
