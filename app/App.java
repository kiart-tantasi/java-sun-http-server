package app;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

// TODO: using Servlets container (Apache Tomcat)

public class App {
    public static void main(String[] args) throws IOException {
        final int port = 8080;
        final HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/healthz", new HealthzHandler());
        server.setExecutor(null); // Use the default executor
        server.start();
        System.out.println("Server is running on port " + port);
    }
}

class HealthzHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            final String response = "{\"message\": \"Some message sent !\"}";
            // needs to set heder before send header
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            final OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception ex) {
            System.out.println("HealthzHandler Error: " + ex.getMessage());
            throw ex;
        }
    }
}