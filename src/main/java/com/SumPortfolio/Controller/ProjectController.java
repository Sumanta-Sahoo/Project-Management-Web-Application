package com.SumPortfolio.Controller;

import com.SumPortfolio.Model.Chat;
import com.SumPortfolio.Model.Invitation;
import com.SumPortfolio.Model.Project;
import com.SumPortfolio.Model.User;
import com.SumPortfolio.Request.InviteRequest;
import com.SumPortfolio.Response.MessageResponse;
import com.SumPortfolio.Service.IInvitationService;
import com.SumPortfolio.Service.IProjectService;
import com.SumPortfolio.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private IProjectService projectService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IInvitationService invitationService;
    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        List<Project> projects = projectService.getProjectListByTeam(user, category, tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Project createdproject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdproject, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Project updatedProject = projectService.updateProject(project, projectId);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        MessageResponse messageResponse = new MessageResponse("Project Deleted Successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        List<Project> projects = projectService.searchProject(keyword, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId/chat}")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Chat chats = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestHeader("Authorization") String jwt,
            @RequestBody InviteRequest request,
            @RequestBody Project project
    ) throws Exception{
        User user = userService.findUserByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);
        invitationService.sendInvitation(request.getEmail(), request.getProjectId());
        MessageResponse messageResponse = new MessageResponse("User Invitation Send");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInvitationProject(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String token,
            @RequestBody Project project
    ) throws Exception{
        User user = userService.findUserByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());
        return new ResponseEntity<>(invitation, HttpStatus.OK);
    }
}
