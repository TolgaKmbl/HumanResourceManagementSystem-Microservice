package com.tolgakumbul.emailservice.grpc;

import com.tolgakumbul.proto.EmailProto.ActivateEmailGrpcRequest;
import com.tolgakumbul.proto.EmailProto.ActivateEmailGrpcResponse;
import com.tolgakumbul.proto.EmailProto.SendEmailGrpcRequest;
import com.tolgakumbul.proto.EmailProto.SendEmailGrpcResponse;
import io.grpc.stub.StreamObserver;

public interface EmailGrpcService {

    void sendEmail(SendEmailGrpcRequest request, StreamObserver<SendEmailGrpcResponse> responseObserver);

    void activateEmail(ActivateEmailGrpcRequest request, StreamObserver<ActivateEmailGrpcResponse> responseObserver);

}
