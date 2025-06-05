package com.jobassistant.auth;

import com.jobassistant.HhAuthManager;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.Executors;

public class CallbackServer {
    public static void start(HhAuthManager manager, Runnable onSuccess) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/callback", new CallbackHandler(manager, server, onSuccess));
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();
    }

    static class CallbackHandler implements HttpHandler {
        private final HhAuthManager manager;
        private final HttpServer server;
        private final Runnable onSuccess;

        public CallbackHandler(HhAuthManager manager, HttpServer server, Runnable onSuccess) {
            this.manager = manager;
            this.server = server;
            this.onSuccess = onSuccess;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI requestURI = exchange.getRequestURI();
            String query = requestURI.getQuery();
            if (query != null && query.contains("code=")) {
                String code = query.split("code=")[1];
                manager.exchangeCodeForToken(code);
                onSuccess.run(); // <-- запуск контроллера
            }

            String response = "✅ Авторизация прошла успешно. Можете закрыть окно.";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }

            server.stop(0);
        }
    }
}
