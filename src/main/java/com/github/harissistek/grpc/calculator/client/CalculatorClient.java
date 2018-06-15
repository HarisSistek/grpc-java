package com.github.harissistek.grpc.calculator.client;

import com.proto.calc.CalcServiceGrpc;
import com.proto.calc.PrimeRequest;
import com.proto.calc.SumRequest;
import com.proto.calc.SumResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {

    private ManagedChannel channel;

    private void run() {
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // forces ssl to be deactived during development, remove in production
                .build();

        doSum(channel);
        doPrime(channel);

        channel.shutdown();
    }

    private void doSum(ManagedChannel channel) {
        CalcServiceGrpc.CalcServiceBlockingStub client = CalcServiceGrpc.newBlockingStub(channel);

        SumRequest request = SumRequest.newBuilder()
                .setValue1(10)
                .setValue2(3)
                .build();

        SumResponse response = client.sum(request);

        System.out.print(String.format("%d + %d = %d", 10, 3, response.getResult()));
    }

    private void doPrime(ManagedChannel channel) {
        CalcServiceGrpc.CalcServiceBlockingStub client = CalcServiceGrpc.newBlockingStub(channel);

        PrimeRequest request = PrimeRequest.newBuilder()
                .setPrime(120)
                .build();

        // send the request and get a response stream back
        client.prime(request)
                .forEachRemaining(response -> {
                    System.out.println("Result: " + response.getResult());
                });
    }

    public static void main(String[] args) {
        CalculatorClient calculatorClient = new CalculatorClient();
        calculatorClient.run();
    }
}
