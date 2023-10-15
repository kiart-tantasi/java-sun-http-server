package app;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

import app.handlers.HealthzHandler;
import app.handlers.UserHandler;

public class App {
    public static void main(String[] args) throws IOException {
        final var port = 8080;
        final var server = HttpServer.create(new InetSocketAddress(port), 0);

        // endpoints
        server.createContext("/healthz", new HealthzHandler());
        server.createContext("/user", new UserHandler());

        server.setExecutor(null); // Use the default executor
        server.start();
        System.out.println("Server is running on port " + port);
    }
}
