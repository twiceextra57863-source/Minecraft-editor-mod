package com.videoeditor.manager;

import com.videoeditor.project.Clip;
import com.videoeditor.project.Project;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectManager {
    private final List<Project> projects;
    private Project currentProject;
    
    public ProjectManager() {
        this.projects = new ArrayList<>();
        this.currentProject = null;
    }
    
    public Project createProject(String name, String description, int quality, int fps) {
        Project project = new Project(name, description, quality, fps);
        projects.add(project);
        currentProject = project;
        return project;
    }
    
    public void deleteProject(Project project) {
        projects.remove(project);
        if (currentProject == project) {
            currentProject = null;
        }
    }
    
    public List<Project> getProjects() {
        return new ArrayList<>(projects);
    }
    
    public Optional<Project> getProjectById(String id) {
        return projects.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst();
    }
    
    public Project getCurrentProject() {
        return currentProject;
    }
    
    public void setCurrentProject(Project project) {
        this.currentProject = project;
    }
    
    public boolean hasProjects() {
        return !projects.isEmpty();
    }
    
    public void addClipToCurrentProject(Clip clip) {
        if (currentProject != null) {
            currentProject.addClip(clip);
        }
    }
}
