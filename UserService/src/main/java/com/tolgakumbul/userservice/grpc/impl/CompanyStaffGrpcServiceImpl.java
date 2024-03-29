package com.tolgakumbul.userservice.grpc.impl;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.CompanyStaffGrpcServiceGrpc;
import com.tolgakumbul.proto.CompanyStaffProto.*;
import com.tolgakumbul.userservice.grpc.CompanyStaffGrpcService;
import com.tolgakumbul.userservice.mapper.CompanyStaffMapper;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffGeneralResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffListResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.GetAllCompanyStaffRequestDTO;
import com.tolgakumbul.userservice.service.CompanyStaffService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CompanyStaffGrpcServiceImpl extends CompanyStaffGrpcServiceGrpc.CompanyStaffGrpcServiceImplBase implements CompanyStaffGrpcService {

    private final CompanyStaffMapper MAPPER = CompanyStaffMapper.INSTANCE;
    private final CompanyStaffService companyStaffService;

    public CompanyStaffGrpcServiceImpl(CompanyStaffService companyStaffService) {
        this.companyStaffService = companyStaffService;
    }

    @Override
    public void getAllCompanyStaff(GetAllCompanyStaffRequest request, StreamObserver<GetAllCompanyStaffResponse> responseObserver) {

        GetAllCompanyStaffRequestDTO getAllCompanyStaffRequestDTO = MAPPER.toGetAllCompanyStaffRequestDTO(request);

        CompanyStaffListResponseDTO allCompanyStaff = companyStaffService.getAllCompanyStaff(getAllCompanyStaffRequestDTO);

        GetAllCompanyStaffResponse grpcResponse = MAPPER.toGetAllCompanyStaffResponse(allCompanyStaff);

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();

    }

    @Override
    public void getCompanyStaffById(CompanyStaffByIdRequest request, StreamObserver<CompanyStaffGeneralResponse> responseObserver) {
        CompanyStaffGeneralResponseDTO companyStaffById = companyStaffService.getCompanyStaffById(request.getUserId());

        CompanyStaffGeneralResponse companyStaffGeneralResponse = MAPPER.toCompanyStaffGeneralResponse(companyStaffById);

        responseObserver.onNext(companyStaffGeneralResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getCompanyStaffByName(GetCompanyStaffByNameRequest request, StreamObserver<CompanyStaffGeneralResponse> responseObserver) {
        CompanyStaffGeneralResponseDTO companyStaffByName = companyStaffService.getCompanyStaffByName(request.getFirstName(), request.getLastName());

        CompanyStaffGeneralResponse companyStaffGeneralResponse = MAPPER.toCompanyStaffGeneralResponse(companyStaffByName);

        responseObserver.onNext(companyStaffGeneralResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void insertCompanyStaff(CompanyStaffData request, StreamObserver<CompanyStaffGeneralResponse> responseObserver) {
        CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = companyStaffService.insertCompanyStaff(MAPPER.toCompanyStaffDTO(request));

        responseObserver.onNext(MAPPER.toCompanyStaffGeneralResponse(companyStaffGeneralResponseDTO));
        responseObserver.onCompleted();
    }

    @Override
    public void updateCompanyStaff(CompanyStaffData request, StreamObserver<CompanyStaffGeneralResponse> responseObserver) {
        CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = companyStaffService.updateCompanyStaff(MAPPER.toCompanyStaffDTO(request));

        responseObserver.onNext(MAPPER.toCompanyStaffGeneralResponse(companyStaffGeneralResponseDTO));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteCompanyStaff(CompanyStaffByIdRequest request, StreamObserver<CommonResponse> responseObserver) {
        CommonResponseDTO commonResponseDTO = companyStaffService.deleteCompanyStaff(request.getUserId());

        responseObserver.onNext(MAPPER.toCommonResponse(commonResponseDTO));
        responseObserver.onCompleted();
    }

    @Override
    public void approveCompanyStaff(CompanyStaffByIdRequest request, StreamObserver<CompanyStaffGeneralResponse> responseObserver) {
        CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = companyStaffService.approveCompanyStaff(request.getUserId());

        responseObserver.onNext(MAPPER.toCompanyStaffGeneralResponse(companyStaffGeneralResponseDTO));
        responseObserver.onCompleted();
    }
}
