package chat;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class ChatServer {
    private Server grpcServer;
    private static final int PORT = 9090; // Define the port as a constant

    // Constructor to initialize and start the server
    public ChatServer() throws IOException {
        initializeServer();
        startServer();
    }

    // Initialize the gRPC server
    private void initializeServer() {
        grpcServer = ServerBuilder.forPort(PORT)
                                  .addService(new ChatServiceImpl())
                                  .build();
    }

    // Start the server and add a shutdown hook
    private void startServer() throws IOException {
        grpcServer.start();
        System.out.println("Server started, listening on port: " + PORT);
        addShutdownHook();
    }

    // Add a shutdown hook to gracefully shut down the server
    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** Shutting down gRPC server since JVM is shutting down");
            if (grpcServer != null) {
                grpcServer.shutdown();
            }
            System.err.println("*** Server shut down");
        }));
    }

    // Main method to start and keep the server running
    public static void main(String[] args) throws IOException, InterruptedException {
        ChatServer server = new ChatServer();
        server.blockUntilShutdown();
    }

    // Block main thread until server shuts down
    private void blockUntilShutdown() throws InterruptedException {
        if (grpcServer != null) {
            grpcServer.awaitTermination();
        }
    }
}
