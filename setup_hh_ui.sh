#!/bin/bash

echo "🔁 Удаляем старые файлы FXML и CSS..."
rm -rf src/main/resources/com/jobassistant/ui/*.fxml
rm -rf src/main/resources/com/jobassistant/ui/*.css

echo "📁 Создаём директории..."
mkdir -p src/main/resources/com/jobassistant/ui/view
mkdir -p src/main/java/com/jobassistant/ui/controller

FXML_DIR="src/main/resources/com/jobassistant/ui/view"
JAVA_DIR="src/main/java/com/jobassistant/ui/controller"
STYLE_FILE="$FXML_DIR/styles.css"

echo "🎨 Создаём стили..."
cat > "$STYLE_FILE" <<EOF
.root {
    -fx-background-color: #ffffff;
    -fx-font-family: "Segoe UI", sans-serif;
}

.header {
    -fx-font-size: 24px;
    -fx-font-weight: bold;
    -fx-padding: 20 0 10 20;
}

.card {
    -fx-background-color: #f8f9fa;
    -fx-border-color: #e0e0e0;
    -fx-border-radius: 10;
    -fx-padding: 15;
    -fx-background-radius: 10;
    -fx-spacing: 10;
}

.button {
    -fx-background-color: #0041c4;
    -fx-text-fill: white;
    -fx-font-weight: bold;
    -fx-padding: 6 12;
    -fx-background-radius: 5;
}
EOF

echo "🧩 Создаём FXML-дизайн дашборда..."
cat > "$FXML_DIR/dashboard.fxml" <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.*?>
<?import javafx.scene.control.*?>
<?import javafx.geometry.*?>
<BorderPane fx:controller="com.jobassistant.ui.controller.DashboardController"
            xmlns:fx="http://javafx.com/fxml"
            stylesheets="@styles.css"
            prefWidth="1024" prefHeight="768">
    <top>
        <VBox styleClass="header">
            <Label text="Мои резюме"/>
        </VBox>
    </top>
    <center>
        <VBox spacing="20" alignment="CENTER" padding="20">
            <HBox spacing="15">
                <VBox styleClass="card" alignment="TOP_LEFT" prefWidth="300" prefHeight="150">
                    <Label text="Системный аналитик"/>
                    <Label text="Просмотров: 458"/>
                    <Button text="Поднять в поиске"/>
                </VBox>
                <VBox styleClass="card" alignment="TOP_LEFT" prefWidth="300" prefHeight="150">
                    <Label text="Резюме на hh.ru"/>
                    <Button text="Редактировать"/>
                    <Button text="Удалить"/>
                </VBox>
            </HBox>
        </VBox>
    </center>
</BorderPane>
EOF

echo "👨‍💻 Добавляем контроллер DashboardController.java..."
cat > "$JAVA_DIR/DashboardController.java" <<EOF
package com.jobassistant.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DashboardController {
    @FXML private Button resumeButton;

    @FXML
    private void initialize() {
        System.out.println("Загрузка Dashboard");
    }
}
EOF

echo "✅ Шаблон HH UI установлен!"
