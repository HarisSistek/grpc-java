package com.github.harissistek.grpc.calculator.server;

import com.proto.calc.*;
import io.grpc.stub.StreamObserver;

public class CalculatorImpl extends CalcServiceGrpc.CalcServiceImplBase {

    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
        int value1 = request.getValue1();
        int value2 = request.getValue2();

        int result = value1 + value2;

        System.out.println(String.format("%d + %d = %d", value1, value2, result));

        SumResponse sumResponse = SumResponse.newBuilder()
                .setResult(result)
                .build();

        responseObserver.onNext(sumResponse);

        responseObserver.onCompleted();
    }

    @Override
    public void prime(PrimeRequest request,
                      StreamObserver<PrimeResponse> responseObserver) {
        int k = 2;
        int N = request.getPrime();

        System.out.print("" + N + "=" + k + ", ");

        while (N > 1) {
            if (N % k == 0) {
                System.out.println(k + ", ");
                responseObserver.onNext(
                        PrimeResponse.newBuilder()
                                .setResult(k)
                                .build());
                N = N / k;
            } else {
                k += 1;
            }
        }

        responseObserver.onCompleted();
    }
}
