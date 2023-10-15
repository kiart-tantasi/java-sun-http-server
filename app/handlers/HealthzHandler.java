package app.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HealthzHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            final var response = "{\"message\": \"okay !\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            final var os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception ex) {
            System.out.println("Handler error: " + ex.getMessage());
        }
    }
}