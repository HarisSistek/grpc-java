package com.github.harissistek.grpc.greeting.server;

import com.proto.greet.Greeting;
import com.proto.greet.GreetingRequest;
import com.proto.greet.GreetingResponse;
import com.proto.greet.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

    @Override
    public void greet(GreetingRequest request, StreamObserver<GreetingResponse> responseStreamObserver) {

        // extract the request data
        Greeting greeting = request.getGreeting();

        // build response
        String result = "Hello " + greeting.getFirstName() + " " + greeting.getLastName();

        System.out.println(result);

        // Create the response
        GreetingResponse response = GreetingResponse.newBuilder()
                .setResult(result)
                .build();

        // send the response
        responseStreamObserver.onNext(response);

        // complete the RPC call
        responseStreamObserver.onCompleted();
    }

    @Override
    public void greetManyTimes(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        // extract the request data
        Greeting greeting = request.getGreeting();


        try {
            for (int i = 0; i < 10; i++) {
                // build response
                String result = "Hello " + greeting.getFirstName() + " " + greeting.getLastName() + " response id: " + i;

                System.out.println(result);

                // Create the response
                GreetingResponse response = GreetingResponse.newBuilder()
                        .setResult(result)
                        .build();

                responseObserver.onNext(response);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public StreamObserver<GreetingRequest> longGreet(StreamObserver<GreetingResponse> responseObserver) {

        return new StreamObserver<GreetingRequest>() {

            String result = "";

            @Override
            public void onNext(GreetingRequest value) {
                // client sends a message
                result += "Hello " + value.getGreeting().getFirstName() + " " + value.getGreeting().getLastName() + "! ";
            }

            @Override
            public void onError(Throwable t) {
                // client sends an error
            }

            @Override
            public void onCompleted() {
                // client is done sending, this where we want to return the response
                responseObserver.onNext(GreetingResponse.newBuilder().setResult(result).build());
                responseObserver.onCompleted();
            }
        };
    }
}
