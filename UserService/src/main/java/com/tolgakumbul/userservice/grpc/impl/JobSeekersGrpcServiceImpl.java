package com.tolgakumbul.userservice.grpc.impl;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.JobSeekersGrpcServiceGrpc;
import com.tolgakumbul.proto.JobSeekersProto.*;
import com.tolgakumbul.userservice.grpc.JobSeekersGrpcService;
import com.tolgakumbul.userservice.mapper.JobSeekerMapper;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.jobseekers.GetAllJobSeekersRequestDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerGeneralResponseDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerListResponseDTO;
import com.tolgakumbul.userservice.service.JobSeekersService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class JobSeekersGrpcServiceImpl extends JobSeekersGrpcServiceGrpc.JobSeekersGrpcServiceImplBase implements JobSeekersGrpcService {

    private final JobSeekerMapper MAPPER = JobSeekerMapper.INSTANCE;
    private final JobSeekersService jobSeekersService;

    public JobSeekersGrpcServiceImpl(JobSeekersService jobSeekersService) {
        this.jobSeekersService = jobSeekersService;
    }

    @Override
    public void getAllJobSeekers(JobSeekerListGeneralRequest request, StreamObserver<JobSeekerListGeneralResponse> responseObserver) {
        GetAllJobSeekersRequestDTO getAllJobSeekersRequestDTO = MAPPER.toGetAllJobSeekersRequestDTO(request);

        JobSeekerListResponseDTO allJobSeekers = jobSeekersService.getAllJobSeekers(getAllJobSeekersRequestDTO);

        List<JobSeeker> jobSeekerList = allJobSeekers.getJobSeekerList().stream()
                .map(MAPPER::toJobSeeker)
                .collect(Collectors.toList());

        JobSeekerListGeneralResponse jobSeekerListGeneralResponse = JobSeekerListGeneralResponse.newBuilder()
                .addAllJobSeekerList(jobSeekerList)
                .build();

        responseObserver.onNext(jobSeekerListGeneralResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getJobSeekerById(JobSeekerByIdRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        JobSeekerGeneralResponseDTO jobSeekerById = jobSeekersService.getJobSeekerById(request.getUserId());

        JobSeekerGeneralResponse jobSeekerGeneralResponse = JobSeekerGeneralResponse.newBuilder()
                .setJobSeeker(MAPPER.toJobSeeker(jobSeekerById.getJobSeeker()))
                .build();

        responseObserver.onNext(jobSeekerGeneralResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getJobSeekerByNationalId(JobSeekerByNationalIdRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        JobSeekerGeneralResponseDTO jobSeekerByNationalId = jobSeekersService.getJobSeekerByNationalId(request.getNationalId());

        JobSeekerGeneralResponse jobSeekerGeneralResponse = JobSeekerGeneralResponse.newBuilder()
                .setJobSeeker(MAPPER.toJobSeeker(jobSeekerByNationalId.getJobSeeker()))
                .build();

        responseObserver.onNext(jobSeekerGeneralResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getJobSeekerByName(JobSeekerByNameRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        JobSeekerGeneralResponseDTO jobSeekerByName = jobSeekersService.getJobSeekerByName(request.getFirstName(), request.getLastName());

        JobSeekerGeneralResponse jobSeekerGeneralResponse = JobSeekerGeneralResponse.newBuilder()
                .setJobSeeker(MAPPER.toJobSeeker(jobSeekerByName.getJobSeeker()))
                .build();

        responseObserver.onNext(jobSeekerGeneralResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void updateJobSeeker(JobSeeker request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = jobSeekersService.updateJobSeeker(MAPPER.toJobSeekerDTO(request));

        responseObserver.onNext(MAPPER.toJobSeekerGeneralResponse(jobSeekerGeneralResponseDTO));
        responseObserver.onCompleted();
    }

    @Override
    public void insertJobSeeker(JobSeeker request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = jobSeekersService.insertJobSeeker(MAPPER.toJobSeekerDTO(request));

        responseObserver.onNext(MAPPER.toJobSeekerGeneralResponse(jobSeekerGeneralResponseDTO));
        responseObserver.onCompleted();
    }

    @Override
    public void approveJobSeeker(JobSeekerByIdRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = jobSeekersService.approveJobSeeker(MAPPER.toJobSeekerDTO(request));

        responseObserver.onNext(MAPPER.toJobSeekerGeneralResponse(jobSeekerGeneralResponseDTO));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteJobSeeker(JobSeekerByIdRequest request, StreamObserver<CommonResponse> responseObserver) {
        CommonResponseDTO commonResponseDTO = jobSeekersService.deleteJobSeeker(request.getUserId());

        responseObserver.onNext(MAPPER.toCommonResponse(commonResponseDTO));
        responseObserver.onCompleted();
    }
}
