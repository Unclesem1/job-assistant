package com.jobassistant.auth;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class CallbackServer {

    public static void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/callback", new CallbackHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Сервер обратного вызова запущен на http://localhost:8080/callback");
    }

    static class CallbackHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI requestURI = exchange.getRequestURI();
            String query = requestURI.getQuery();
            String response;
            if (query != null && query.contains("code=")) {
                String code = query.split("code=")[1].split("&")[0];
                try {
                    HhAuthManager.exchangeCodeForToken(code);
                    response = "Авторизация прошла успешно. Можете закрыть окно.";
                    System.out.println("AccessToken: " + HhAuthManager.getAccessToken());
                } catch (IOException e) {
                    response = "Ошибка при получении токена: " + e.getMessage();
                }
            } else {
                response = "Код авторизации не найден.";
            }

            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
