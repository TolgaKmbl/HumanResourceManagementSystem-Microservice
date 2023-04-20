package com.tolgakumbul.userservice.grpc;

import com.google.protobuf.Empty;
import com.tolgakumbul.proto.CompanyStaffProto.CompanyStaffData;
import com.tolgakumbul.proto.CompanyStaffProto.GetAllCompanyStaffResponse;
import com.tolgakumbul.proto.CompanyStaffProto.GetCompanyStaffByIdRequest;
import com.tolgakumbul.proto.CompanyStaffProto.GetCompanyStaffByNameRequest;
import io.grpc.stub.StreamObserver;

public interface CompanyStaffGrpcService {

    void getAllCompanyStaff(Empty request, StreamObserver<GetAllCompanyStaffResponse> responseObserver);

    void getCompanyStaffById(GetCompanyStaffByIdRequest request, StreamObserver<CompanyStaffData> responseObserver);

    void getCompanyStaffByName(GetCompanyStaffByNameRequest request, StreamObserver<CompanyStaffData> responseObserver);

    void insertCompanyStaff(CompanyStaffData request, StreamObserver<CompanyStaffData> responseObserver);

}
