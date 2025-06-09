package backend.hh;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class CallbackServer {

    public static void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/callback", new CallbackHandler());
        server.setExecutor(null);
        server.start();
    }

    static class CallbackHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            if (query != null && query.contains("code=")) {
                String code = query.split("code=")[1];
                try {
                    HhAuthManager.exchangeCodeForToken(code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String response = "Авторизация завершена. Можете закрыть окно.";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
