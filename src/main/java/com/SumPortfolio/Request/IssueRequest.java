package com.SumPortfolio.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequest {
    private Long id;
    private String issueTitle;
    private String issueDescription;
    private String issueStatus;
    private Long projectId;
    private String issuePriority;
    private LocalDate dueDate;
}
