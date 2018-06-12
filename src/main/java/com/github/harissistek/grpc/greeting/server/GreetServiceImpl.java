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
}
