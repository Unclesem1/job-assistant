package com.jobassistant;

import java.util.List;

public class ResumeInfo {
    private String title;
    private String updated;
    private int views;
    private int responses;
    private int invites;
    private List<String> skills;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUpdated() { return updated; }
    public void setUpdated(String updated) { this.updated = updated; }

    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    public int getResponses() { return responses; }
    public void setResponses(int responses) { this.responses = responses; }

    public int getInvites() { return invites; }
    public void setInvites(int invites) { this.invites = invites; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
}
