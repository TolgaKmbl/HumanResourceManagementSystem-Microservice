package com.tolgakumbul.userservice.grpc.impl;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.EmployersGrpcServiceGrpc;
import com.tolgakumbul.proto.EmployersProto.*;
import com.tolgakumbul.userservice.grpc.EmployersGrpcService;
import com.tolgakumbul.userservice.mapper.EmployersMapper;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersByCompanyNameRequestDTO;
import com.tolgakumbul.userservice.model.employers.EmployersGeneralResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersListResponseDTO;
import com.tolgakumbul.userservice.model.employers.GetAllEmployersRequestDTO;
import com.tolgakumbul.userservice.service.EmployersService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class EmployersGrpcServiceImpl extends EmployersGrpcServiceGrpc.EmployersGrpcServiceImplBase implements EmployersGrpcService {

    private final EmployersMapper MAPPER = EmployersMapper.INSTANCE;
    private final EmployersService employersService;

    public EmployersGrpcServiceImpl(EmployersService employersService) {
        this.employersService = employersService;
    }


    @Override
    public void getAllEmployers(EmployerListGeneralRequest request, StreamObserver<EmployerListGeneralResponse> responseObserver) {

        GetAllEmployersRequestDTO getAllEmployersRequestDTO = MAPPER.toGetAllEmployersRequestDTO(request);

        EmployersListResponseDTO allEmployers = employersService.getAllEmployers(getAllEmployersRequestDTO);

        EmployerListGeneralResponse grpcResponse = MAPPER.toEmployerListGeneralResponse(allEmployers);

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getEmployerById(EmployerByIdRequest request, StreamObserver<EmployerGeneralResponse> responseObserver) {
        EmployersGeneralResponseDTO employerById = employersService.getEmployerById(request.getUserId());

        EmployerGeneralResponse employerGeneralResponse = EmployerGeneralResponse.newBuilder()
                .setEmployer(MAPPER.toEmployer(employerById.getEmployer()))
                .build();

        responseObserver.onNext(employerGeneralResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getEmployersByCompanyName(EmployersByCompanyNameRequest request, StreamObserver<EmployerListGeneralResponse> responseObserver) {
        EmployersByCompanyNameRequestDTO employersByCompanyNameRequestDTO = MAPPER.toEmployersByCompanyNameRequestDTO(request);
        EmployersListResponseDTO employersByCompanyName = employersService.getEmployersByCompanyName(employersByCompanyNameRequestDTO);

        EmployerListGeneralResponse grpcResponse = MAPPER.toEmployerListGeneralResponse(employersByCompanyName);

        responseObserver.onNext(grpcResponse);
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
