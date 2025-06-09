package backend.hh;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HhAuthManager {
    private static final String CLIENT_ID = "ВАШ_CLIENT_ID";
    private static final String CLIENT_SECRET = "ВАШ_CLIENT_SECRET";
    private static final String REDIRECT_URI = "http://localhost:8080/callback";

    private static String accessToken = "";
    private static String refreshToken = "";

    public static String getAuthorizationUrl() {
        return "https://hh.ru/oauth/authorize?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI;
    }

    public static void exchangeCodeForToken(String code) throws IOException {
        URL url = new URL("https://hh.ru/oauth/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String data = "grant_type=authorization_code&client_id=" + CLIENT_ID +
                      "&client_secret=" + CLIENT_SECRET +
                      "&code=" + code +
                      "&redirect_uri=" + REDIRECT_URI;

        try (OutputStream os = conn.getOutputStream()) {
            os.write(data.getBytes(StandardCharsets.UTF_8));
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String json = in.readLine();
        JSONObject obj = new JSONObject(json);
        accessToken = obj.getString("access_token");
        refreshToken = obj.getString("refresh_token");
        System.out.println("✅ Авторизация прошла успешно. AccessToken получен.");
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }
}
