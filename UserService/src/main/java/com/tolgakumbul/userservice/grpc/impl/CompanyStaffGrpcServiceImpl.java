package com.tolgakumbul.userservice.grpc.impl;

import com.google.protobuf.Empty;
import com.tolgakumbul.proto.CompanyStaffGrpcServiceGrpc;
import com.tolgakumbul.proto.CompanyStaffProto.CompanyStaffData;
import com.tolgakumbul.proto.CompanyStaffProto.GetAllCompanyStaffResponse;
import com.tolgakumbul.proto.CompanyStaffProto.GetCompanyStaffByIdRequest;
import com.tolgakumbul.proto.CompanyStaffProto.GetCompanyStaffByNameRequest;
import com.tolgakumbul.userservice.grpc.CompanyStaffGrpcService;
import com.tolgakumbul.userservice.mapper.CompanyStaffMapper;
import com.tolgakumbul.userservice.model.CompanyStaffDTO;
import com.tolgakumbul.userservice.service.CompanyStaffService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class CompanyStaffGrpcServiceImpl extends CompanyStaffGrpcServiceGrpc.CompanyStaffGrpcServiceImplBase implements CompanyStaffGrpcService {

    private CompanyStaffMapper MAPPER = CompanyStaffMapper.INSTANCE;
    private final CompanyStaffService companyStaffService;

    public CompanyStaffGrpcServiceImpl(CompanyStaffService companyStaffService) {
        this.companyStaffService = companyStaffService;
    }

    @Override
    public void getAllCompanyStaff(Empty request, StreamObserver<GetAllCompanyStaffResponse> responseObserver) {

        List<CompanyStaffDTO> allCompanyStaff = companyStaffService.getAllCompanyStaff();

        List<CompanyStaffData> getCompanyStaffResponses = allCompanyStaff.stream()
                .map(MAPPER::toGetCompanyStaffResponse)
                .collect(Collectors.toList());

        GetAllCompanyStaffResponse getAllCompanyStaffResponse = GetAllCompanyStaffResponse.newBuilder()
                .addAllCompanyStaffList(getCompanyStaffResponses)
                .build();

        responseObserver.onNext(getAllCompanyStaffResponse);
        responseObserver.onCompleted();

    }

    @Override
    public void getCompanyStaffById(GetCompanyStaffByIdRequest request, StreamObserver<CompanyStaffData> responseObserver) {
        CompanyStaffDTO companyStaffById = companyStaffService.getCompanyStaffById(request.getUserId());
        CompanyStaffData companyStaffData = MAPPER.toGetCompanyStaffResponse(companyStaffById);
        responseObserver.onNext(companyStaffData);
        responseObserver.onCompleted();
    }

    @Override
    public void getCompanyStaffByName(GetCompanyStaffByNameRequest request, StreamObserver<CompanyStaffData> responseObserver) {
        CompanyStaffDTO companyStaffByName = companyStaffService.getCompanyStaffByName(request.getFirstName(), request.getLastName());
        CompanyStaffData companyStaffData = MAPPER.toGetCompanyStaffResponse(companyStaffByName);
        responseObserver.onNext(companyStaffData);
        responseObserver.onCompleted();
    }

    @Override
    public void insertCompanyStaff(CompanyStaffData request, StreamObserver<CompanyStaffData> responseObserver) {
        CompanyStaffDTO companyStaffByName = companyStaffService.insertCompanyStaff(MAPPER.toCompanyStaffDTO(request));
        CompanyStaffData companyStaffData = MAPPER.toGetCompanyStaffResponse(companyStaffByName);
        responseObserver.onNext(companyStaffData);
        responseObserver.onCompleted();
    }
}
