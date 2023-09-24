package com.tolgakumbul.emailservice.grpc;

import com.tolgakumbul.emailservice.mapper.EmailMapper;
import com.tolgakumbul.emailservice.service.EmailService;
import com.tolgakumbul.proto.EmailGrpcServiceGrpc;
import com.tolgakumbul.proto.EmailProto.ActivateEmailGrpcRequest;
import com.tolgakumbul.proto.EmailProto.ActivateEmailGrpcResponse;
import com.tolgakumbul.proto.EmailProto.SendEmailGrpcRequest;
import com.tolgakumbul.proto.EmailProto.SendEmailGrpcResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class EmailGrpcServiceImpl extends EmailGrpcServiceGrpc.EmailGrpcServiceImplBase implements EmailGrpcService {

    private final EmailMapper MAPPER = EmailMapper.INSTANCE;

    private final EmailService emailService;

    public EmailGrpcServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void sendEmail(SendEmailGrpcRequest request, StreamObserver<SendEmailGrpcResponse> responseObserver) {
        super.sendEmail(request, responseObserver);
    }

    @Override
    public void activateEmail(ActivateEmailGrpcRequest request, StreamObserver<ActivateEmailGrpcResponse> responseObserver) {
        super.activateEmail(request, responseObserver);
    }
}
