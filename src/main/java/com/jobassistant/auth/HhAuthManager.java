package com.jobassistant.auth;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

public class HhAuthManager {
    private static final String CLIENT_ID = "ВАШ_CLIENT_ID";
    private static final String CLIENT_SECRET = "ВАШ_CLIENT_SECRET";
    private static final String REDIRECT_URI = "http://localhost:8080/callback";

    private static String accessToken = null;
    private static String refreshToken = null;

    public static String getAuthorizationUrl() throws UnsupportedEncodingException {
        return "https://hh.ru/oauth/authorize?response_type=code&client_id=" + URLEncoder.encode(CLIENT_ID, "UTF-8") +
               "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8");
    }

    public static void exchangeCodeForToken(String code) throws IOException {
        URL url = new URL("https://hh.ru/oauth/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        String body = "grant_type=authorization_code" +
                      "&client_id=" + URLEncoder.encode(CLIENT_ID, "UTF-8") +
                      "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, "UTF-8") +
                      "&code=" + URLEncoder.encode(code, "UTF-8") +
                      "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes());
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            String json = new BufferedReader(new InputStreamReader(conn.getInputStream())).lines()
                .reduce("", (acc, line) -> acc + line);
            JSONObject obj = new JSONObject(json);
            accessToken = obj.getString("access_token");
            refreshToken = obj.getString("refresh_token");
        } else {
            throw new IOException("Ошибка авторизации: HTTP " + responseCode);
        }
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }
}
