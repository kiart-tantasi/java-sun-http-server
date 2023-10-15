package app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class App {
    public static void main(String[] args) throws IOException {
        final var port = 8080;
        final var server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/healthz", new HealthzHandler());
        server.createContext("/user", new UserHandler());
        server.setExecutor(null); // Use the default executor
        server.start();
        System.out.println("Server is running on port " + port);
    }
}

class UserHandler implements HttpHandler {
    private Connection connection;
    private int counter = 15;

    public UserHandler() {
        System.out.println("getting connection to SQL database...");
        try {
            this.connection = this.getConnection();
            System.out.println("database connection is successful");
        } catch (SQLException ex) {
            System.out.println("Constructor error " + ex.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        // NOTE: in prod, should keep these as secrets instead of hard-coding them here
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "password");
    }

    private int insertUser() throws SQLException {
        final var sql = String.format("INSERT INTO test (name, age) VALUES ('Hello World', %d)", this.counter);
        final var statement = this.connection.prepareStatement(sql);
        final var rowsAffected = statement.executeUpdate();
        System.out.println("counter: " + this.counter);
        this.counter++;
        return rowsAffected;
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            final var rowsAffected = insertUser();
            if (rowsAffected != 1) {
                throw new Exception("inserting user failed !");
            }
            final var response = "{\"message\": \"User is created !\"}";
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

class HealthzHandler implements HttpHandler {
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
