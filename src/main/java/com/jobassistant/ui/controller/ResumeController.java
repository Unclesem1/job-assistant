package com.jobassistant.ui.controller;

import com.jobassistant.ResumeInfo;
import com.jobassistant.service.ResumeService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;

public class ResumeController {
    @FXML private Label resumeTitle;
    @FXML private Label resumeUpdated;
    @FXML private Label viewsLabel;
    @FXML private Label responsesLabel;
    @FXML private Label invitesLabel;
    @FXML private FlowPane skillsPane;

    public void initialize() {
        String token = System.getenv("HH_TOKEN");
        ResumeInfo info = ResumeService.fetchResume(token);
        if (info == null) return;

        resumeTitle.setText(info.getTitle());
        resumeUpdated.setText("Обновлено: " + info.getUpdated());
        viewsLabel.setText("Показов: " + info.getViews());
        responsesLabel.setText("Откликов: " + info.getResponses());
        invitesLabel.setText("Приглашений: " + info.getInvites());

        skillsPane.getChildren().clear();
        for (String skill : info.getSkills()) {
            Label label = new Label(skill);
            label.setStyle("-fx-background-color: #eeeeee; -fx-padding: 5 10; -fx-border-radius: 4;");
            skillsPane.getChildren().add(label);
        }
    }
}
