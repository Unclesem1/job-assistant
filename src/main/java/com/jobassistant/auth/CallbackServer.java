package com.jobassistant.auth;

import com.jobassistant.HhAuthManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CallbackServer {

    public static void start(HhAuthManager manager) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/callback", new CallbackHandler(manager));
        server.setExecutor(null);
        server.start();
        System.out.println(" Callback-сервер запущен на http://localhost:8080/callback");
    }

    static class CallbackHandler implements HttpHandler {
        private final HhAuthManager manager;

        public CallbackHandler(HhAuthManager manager) {
            this.manager = manager;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery(); // code=...
            Map<String, String> params = parseQuery(query);
            String code = params.get("code");

            String response = "✅ Авторизация прошла успешно. Можете закрыть окно.";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            if (code != null) {
                System.out.println(" Получен code: " + code);
                manager.exchangeCodeForToken(code);
            } else {
                System.out.println("⚠️ Code не найден в callback.");
            }
        }

        private Map<String, String> parseQuery(String query) {
            return query == null ? Map.of() :
                java.util.Arrays.stream(query.split("&"))
                    .map(s -> s.split("=", 2))
                    .collect(Collectors.toMap(
                        s -> s[0],
                        s -> s.length > 1 ? s[1] : ""
                    ));
        }
    }
}
