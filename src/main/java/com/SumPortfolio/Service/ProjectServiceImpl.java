package com.SumPortfolio.Service;

import com.SumPortfolio.Model.Chat;
import com.SumPortfolio.Model.Project;
import com.SumPortfolio.Model.User;
import com.SumPortfolio.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements IProjectService{

    @Autowired
    private ProjectRepository projectRepo;
    @Autowired
    private IUserService userService;
    @Autowired
    private IChatService chatService;
    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createProject = new Project();
        createProject.setOwner(user);
        createProject.setTags(project.getTags());
        createProject.setProjectName(project.getProjectName());
        createProject.setProjectCategory(project.getProjectCategory());
        createProject.setProjectDescription(project.getProjectDescription());
        createProject.getTeam().add(user);

        Project savedProject = projectRepo.save(createProject);

        Chat chat = new Chat();
        chat.setProject(savedProject);

        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectListByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepo.findByTeamContainingAndOwner(user, user);
        if(category!=null)
            projects.stream().filter(project -> project.getProjectCategory().equals(category)).collect(Collectors.toList());

        if(tag!=null)
            projects.stream().filter(project -> project.getTags().contains(tag)).collect(Collectors.toList());


        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepo.findById(projectId);
        if(optionalProject.isEmpty())
            throw new Exception("Project Not Found With PorjectID : " + projectId);

        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        getProjectById(projectId);
        projectRepo.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);

        project.setProjectName(updatedProject.getProjectName());
        project.setProjectDescription(updatedProject.getProjectDescription());
        project.setProjectCategory(updatedProject.getProjectCategory());
        project.setTags(updatedProject.getTags());

        return projectRepo.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(!project.getTeam().contains(user)){
            project.getChat().getUserList().add(user);
            project.getTeam().add(user);
        }
        projectRepo.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(project.getTeam().contains(user)){
            project.getChat().getUserList().remove(user);
            project.getTeam().remove(user);
        }
        projectRepo.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception{
        Project project = getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProject(String keyword, User user) throws Exception {

        return projectRepo.findNameContainingAndTeamContains(keyword, user);
    }
}
