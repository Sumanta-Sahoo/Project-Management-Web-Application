package com.SumPortfolio.Service;

import com.SumPortfolio.Model.Issue;
import com.SumPortfolio.Model.Project;
import com.SumPortfolio.Model.User;
import com.SumPortfolio.Repository.IssueRepository;
import com.SumPortfolio.Request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IIssueService{

    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IProjectService projectService;


    @Override
    public Issue getIssueById(Long userId) throws Exception {
        Optional<Issue> issue = issueRepository.findById(userId);
        if(issue.isPresent())
            return issue.get();
        throw  new Exception("Issue Not Found With userId : " + userId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectId());
        Issue issue = new Issue();
        issue.setIssueTitle(issueRequest.getIssueTitle());
        issue.setIssueDescription(issueRequest.getIssueDescription());
        issue.setIssueStatus(issueRequest.getIssueStatus());
        issue.setProjectId(issueRequest.getProjectId());
        issue.setDueDate(issueRequest.getDueDate());
        issue.setIssuePriority(issueRequest.getIssuePriority());

        issue.setProject(project);
        return issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        getIssueById(issueId);
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(userId);

        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue = getIssueById(issueId);
        issue.setIssueStatus(status);
        return issueRepository.save(issue);
    }
}
