package com.github.harissistek.grpc.dummy.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class DummyServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("GreetingServer starting...");

        Server server = ServerBuilder.forPort(50051)
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    System.out.println("Received Shutdown, stopping server...");
                    server.shutdown();
                    System.out.println("Server Closed.");
                }
        ));

        server.awaitTermination();
    }
}
