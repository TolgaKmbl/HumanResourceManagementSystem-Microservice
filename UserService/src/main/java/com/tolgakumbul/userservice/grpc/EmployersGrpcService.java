package com.tolgakumbul.userservice.grpc;

import com.google.protobuf.Empty;
import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.EmployersProto.*;
import io.grpc.stub.StreamObserver;

public interface EmployersGrpcService {

    void getAllEmployers(Empty request, StreamObserver<EmployerListGeneralResponse> responseObserver);

    void getEmployerById(EmployerByIdRequest request, StreamObserver<EmployerGeneralResponse> responseObserver);

    void getEmployersByCompanyName(EmployersByCompanyNameRequest request, StreamObserver<EmployerListGeneralResponse> responseObserver);

    void updateEmployer(Employer request, StreamObserver<EmployerGeneralResponse> responseObserver);

    void insertEmployer(Employer request, StreamObserver<EmployerGeneralResponse> responseObserver);

    void deleteEmployer(EmployerByIdRequest request, StreamObserver<CommonResponse> responseObserver);

}
