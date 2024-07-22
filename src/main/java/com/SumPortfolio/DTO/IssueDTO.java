package com.SumPortfolio.DTO;

import com.SumPortfolio.Model.Project;
import com.SumPortfolio.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    private Long id;
    private String issueTitle;
    private String issueDescription;
    private String issueStatus;
    private Long projectId;
    private String issuePriority;
    private LocalDate dueDate;
    private List<String> tags = new ArrayList<>();
    private Project project;
    private User assignee;
}
