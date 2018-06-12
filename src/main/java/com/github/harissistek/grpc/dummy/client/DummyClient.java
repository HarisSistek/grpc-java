package com.github.harissistek.grpc.dummy.client;

import com.proto.dummy.DummyServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DummyClient {

    public static void main(String[] args) {
        System.out.println("Starting GreetingClient...");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // forces ssl to be deactived during development, remove in production
                .build();

        DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);

        // DummyServiceGrpc.DummyServiceFutureStub asyncClient = DummyServiceGrpc.newFutureStub(channel);

        // do some api requests

        System.out.println("Stopping GreetingClient..");
        channel.shutdown();

    }
}
