package com.tolgakumbul.userservice.grpc.impl;

import com.google.protobuf.Empty;
import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.EmployersGrpcServiceGrpc;
import com.tolgakumbul.proto.EmployersProto.*;
import com.tolgakumbul.userservice.grpc.EmployersGrpcService;
import com.tolgakumbul.userservice.mapper.EmployersMapper;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersGeneralResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersListResponseDTO;
import com.tolgakumbul.userservice.service.EmployersService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class EmployersGrpcServiceImpl extends EmployersGrpcServiceGrpc.EmployersGrpcServiceImplBase implements EmployersGrpcService {

    private final EmployersMapper MAPPER = EmployersMapper.INSTANCE;
    private final EmployersService employersService;

    public EmployersGrpcServiceImpl(EmployersService employersService) {
        this.employersService = employersService;
    }


    @Override
    public void getAllEmployers(Empty request, StreamObserver<EmployerListGeneralResponse> responseObserver) {
        EmployersListResponseDTO allEmployers = employersService.getAllEmployers();

        List<Employer> employerList = allEmployers.getEmployerList().stream()
                .map(MAPPER::toEmployer)
                .collect(Collectors.toList());

        EmployerListGeneralResponse getAllEmployersResponse = EmployerListGeneralResponse.newBuilder()
                .addAllEmployerList(employerList)
                .setCommonResponse(MAPPER.toCommonResponse(allEmployers.getCommonResponse()))
                .build();

        responseObserver.onNext(getAllEmployersResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getEmployerById(EmployerByIdRequest request, StreamObserver<EmployerGeneralResponse> responseObserver) {
        EmployersGeneralResponseDTO employerById = employersService.getEmployerById(request.getUserId());

        EmployerGeneralResponse employerGeneralResponse = EmployerGeneralResponse.newBuilder()
                .setCommonResponse(MAPPER.toCommonResponse(employerById.getCommonResponse()))
                .setEmployer(MAPPER.toEmployer(employerById.getEmployer()))
                .build();

        responseObserver.onNext(employerGeneralResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getEmployersByCompanyName(EmployersByCompanyNameRequest request, StreamObserver<EmployerListGeneralResponse> responseObserver) {
        EmployersListResponseDTO employersByCompanyName = employersService.getEmployersByCompanyName(request.getCompanyName());

        EmployerListGeneralResponse employerGeneralResponse = EmployerListGeneralResponse.newBuilder()
                .setCommonResponse(MAPPER.toCommonResponse(employersByCompanyName.getCommonResponse()))
                .addAllEmployerList(employersByCompanyName.getEmployerList().stream()
                        .map(MAPPER::toEmployer)
                        .collect(Collectors.toList()))
                .build();

        responseObserver.onNext(employerGeneralResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void updateEmployer(Employer request, StreamObserver<EmployerGeneralResponse> responseObserver) {
        EmployersGeneralResponseDTO employersGeneralResponseDTO = employersService.updateEmployer(MAPPER.toEmployersDTO(request));

        responseObserver.onNext(MAPPER.toEmployerGeneralResponse(employersGeneralResponseDTO));
        responseObserver.onCompleted();
    }

    @Override
    public void insertEmployer(Employer request, StreamObserver<EmployerGeneralResponse> responseObserver) {
        EmployersGeneralResponseDTO employersGeneralResponseDTO = employersService.insertEmployer(MAPPER.toEmployersDTO(request));

        responseObserver.onNext(MAPPER.toEmployerGeneralResponse(employersGeneralResponseDTO));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteEmployer(EmployerByIdRequest request, StreamObserver<CommonResponse> responseObserver) {
        CommonResponseDTO commonResponseDTO = employersService.deleteEmployer(request.getUserId());

        responseObserver.onNext(MAPPER.toCommonResponse(commonResponseDTO));
        responseObserver.onCompleted();
    }
}
