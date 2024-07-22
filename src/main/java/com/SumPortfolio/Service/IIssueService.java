package com.SumPortfolio.Service;

import com.SumPortfolio.Model.Issue;
import com.SumPortfolio.Model.User;
import com.SumPortfolio.Request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IIssueService {

    Issue getIssueById(Long userId) throws Exception;
    List<Issue> getIssueByProjectId(Long projectId) throws Exception;
    Issue createIssue(IssueRequest issueRequest, User user) throws Exception;
    void deleteIssue(Long issueId, Long userId) throws Exception;
    Issue addUserToIssue(Long issueId, Long userId) throws Exception;
    Issue updateStatus(Long issueId, String status) throws Exception;

}
