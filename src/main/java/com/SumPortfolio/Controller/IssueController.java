package com.SumPortfolio.Controller;

import com.SumPortfolio.DTO.IssueDTO;
import com.SumPortfolio.Model.Issue;
import com.SumPortfolio.Model.User;
import com.SumPortfolio.Request.IssueRequest;
import com.SumPortfolio.Response.MessageResponse;
import com.SumPortfolio.Service.IIssueService;
import com.SumPortfolio.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IIssueService issueService;
    @Autowired
    private IUserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue>getIssueById(
            @PathVariable(required = false) Long issueId
    ) throws Exception{
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssuesByProjectId(
            @PathVariable(required = false) Long projectId
    ) throws Exception{
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(
            @RequestParam IssueRequest issueRequest,
            @RequestHeader("Authorization") String token
            ) throws Exception{

        User tokenUser = userService.findUserByJwt(token);
        User user = userService.findUserById(tokenUser.getId());

        Issue createdIssue = issueService.createIssue(issueRequest, tokenUser);
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setIssueDescription(createdIssue.getIssueDescription());
        issueDTO.setDueDate(createdIssue.getDueDate());
        issueDTO.setIssuePriority(createdIssue.getIssuePriority());
        issueDTO.setIssueTitle(createdIssue.getIssueTitle());
        issueDTO.setId(createdIssue.getId());
        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setProjectId(createdIssue.getProjectId());
        issueDTO.setAssignee(createdIssue.getAssignee());
        issueDTO.setTags(createdIssue.getTags());
        issueDTO.setIssueStatus(createdIssue.getIssueStatus());

        return ResponseEntity.ok(issueDTO);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable(required = false) Long issueId,
            @RequestHeader("Authorization") String token
    ) throws Exception{
        User tokenUser = userService.findUserByJwt(token);
        issueService.deleteIssue(issueId, tokenUser.getId());

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Issue Deleted Successfullly with issueId : " + issueId);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/{issueId}/asignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
            @PathVariable(required = false) Long issueId,
            @PathVariable(required = false) Long userId
    ) throws Exception{
        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
            @PathVariable(required = false) Long issueId,
            @PathVariable(required = false) String status
    ) throws Exception{
        Issue issue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }
}
