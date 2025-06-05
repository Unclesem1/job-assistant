package com.jobassistant.ui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ResumeController {

    public void readResumeFromFile() {
        File file = new File("resume.txt");

        // Используем try-with-resources — автоматически закроет Scanner
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println("Резюме: " + line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
        }
    }
}
