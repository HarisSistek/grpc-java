package com.github.harissistek.grpc.greeting.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greet.Greet;
import com.proto.greet.Greeting;
import com.proto.greet.GreetingRequest;
import com.proto.greet.GreetingResponse;
import com.proto.greet.GreetingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {

    public static void main(String[] args) {
        System.out.println("Starting GreetingClient...");

        // create a GRPC channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // forces ssl to be deactived during development, remove in production
                .build();

        // create the greeting service client
        GreetingServiceGrpc.GreetingServiceBlockingStub client = GreetingServiceGrpc.newBlockingStub(channel);

        // create the greeting payload
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Haris")
                .setLastName("Sistek")
                .build();

        // create the greeting request
        GreetingRequest greetingRequest = GreetingRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        // send the request and get a response
        GreetingResponse greetingResponse = client.greet(greetingRequest);

        System.out.println("Result: " + greetingResponse.getResult());

        System.out.println("Stopping GreetingClient..");
        channel.shutdown();

    }
}
