package com.jobassistant;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class HhAuthManager {

    // ✅ Вставь свои данные приложения с https://dev.hh.ru
    private static final String CLIENT_ID ="KA2013FOJ7UU70FP53UQGLSCFU2O1367EHU6R5NU7LIEDM3JRH04UBNAFGQTJVU1";
    private static final String CLIENT_SECRET = "U7FK881IHG474ABK882QD8GK2CHA47TEO07P3RAMBUR74CCPFO3F2GDO7IH9J3RD";
    private static final String REDIRECT_URI = "http://localhost:8080/callback";

    private static final String AUTH_URL = "https://hh.ru/oauth/authorize";
    private static final String TOKEN_URL = "https://hh.ru/oauth/token";
    private static final String RESUME_URL = "https://api.hh.ru/resumes/mine";

    private static String accessToken;

    // Открывает браузер для авторизации
    public static void authenticate() throws IOException {
        String url = AUTH_URL +
                "?response_type=code" +
                "&client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8);

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI.create(url));
        } else {
            System.out.println("Открой вручную: " + url);
        }
    }

    // Получает access_token по коду из redirect
    public static void getToken(String code) throws IOException {
        URL url = new URL(TOKEN_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        String body = "grant_type=authorization_code" +
                "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8) +
                "&client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8) +
                "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8);

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        try (InputStream is = conn.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(is);
            accessToken = json.get("access_token").asText();
        }
    }

    // Делает запрос к HH API
    public static String getResumes() throws IOException {
        URL url = new URL(RESUME_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        try (InputStream is = conn.getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }


    public void exchangeCodeForToken(String code) {
        System.out.println("TODO: обмен кода на токен: " + code);
        // Здесь будет логика запроса POST https://hh.ru/oauth/token
    }
}