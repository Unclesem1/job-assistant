package com.jobassistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobassistant.ResumeInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ResumeService {
    public static ResumeInfo fetchResume(String token) {
        try {
            URL listUrl = new URL("https://api.hh.ru/resumes/mine");
            HttpURLConnection conn = (HttpURLConnection) listUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("User-Agent", "JobAssistant");

            String resumeId = extractFirstResumeId(conn);
            return fetchResumeDetails(token, resumeId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ResumeInfo fetchResumeDetails(String token, String resumeId) throws Exception {
        URL url = new URL("https://api.hh.ru/resumes/" + resumeId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("User-Agent", "JobAssistant");

        try (InputStream is = conn.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(is);

            ResumeInfo info = new ResumeInfo();
            info.setTitle(root.path("title").asText());
            info.setUpdated(root.path("updated_at").asText());
            info.setViews(root.path("views").asInt());
            info.setResponses(root.path("responses").asInt());
            info.setInvites(root.path("invites").asInt());

            List<String> skills = new ArrayList<>();
            for (JsonNode skill : root.path("skills")) {
                skills.add(skill.asText());
            }
            info.setSkills(skills);
            return info;
        }
    }

    private static String extractFirstResumeId(HttpURLConnection conn) throws Exception {
        try (InputStream is = conn.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(is);
            return root.path("items").get(0).path("id").asText();
        }
    }
}
