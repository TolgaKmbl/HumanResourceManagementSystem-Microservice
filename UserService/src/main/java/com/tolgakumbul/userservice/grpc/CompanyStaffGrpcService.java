package com.tolgakumbul.userservice.grpc;

import com.google.protobuf.Empty;
import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.CompanyStaffProto.*;
import io.grpc.stub.StreamObserver;

public interface CompanyStaffGrpcService {

    void getAllCompanyStaff(Empty request, StreamObserver<GetAllCompanyStaffResponse> responseObserver);

    void getCompanyStaffById(CompanyStaffByIdRequest request, StreamObserver<CompanyStaffGeneralResponse> responseObserver);

    void getCompanyStaffByName(GetCompanyStaffByNameRequest request, StreamObserver<CompanyStaffGeneralResponse> responseObserver);

    void insertCompanyStaff(CompanyStaffData request, StreamObserver<CompanyStaffGeneralResponse> responseObserver);

    void updateCompanyStaff(CompanyStaffData request, StreamObserver<CompanyStaffGeneralResponse> responseObserver);

    void deleteCompanyStaff(CompanyStaffByIdRequest request, StreamObserver<CommonResponse> responseObserver);

    void approveCompanyStaff(CompanyStaffByIdRequest request, StreamObserver<CompanyStaffGeneralResponse> responseObserver);

}
