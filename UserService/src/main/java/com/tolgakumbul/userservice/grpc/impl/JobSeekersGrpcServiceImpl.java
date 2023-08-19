package com.tolgakumbul.userservice.grpc.impl;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.JobSeekersGrpcServiceGrpc;
import com.tolgakumbul.proto.JobSeekersProto.*;
import com.tolgakumbul.userservice.grpc.JobSeekersGrpcService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class JobSeekersGrpcServiceImpl extends JobSeekersGrpcServiceGrpc.JobSeekersGrpcServiceImplBase implements JobSeekersGrpcService {

    @Override
    public void getAllJobSeekers(JobSeekerListGeneralRequest request, StreamObserver<JobSeekerListGeneralResponse> responseObserver) {
        /*TODO: Not implemented yet*/
    }

    @Override
    public void getJobSeekerById(JobSeekerByIdRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        /*TODO: Not implemented yet*/
    }

    @Override
    public void getJobSeekerByNationalId(JobSeekerByNationalIdRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        /*TODO: Not implemented yet*/
    }

    @Override
    public void getJobSeekerByName(JobSeekerByNameRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        /*TODO: Not implemented yet*/
    }

    @Override
    public void updateJobSeeker(JobSeeker request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        /*TODO: Not implemented yet*/
    }

    @Override
    public void insertJobSeeker(JobSeeker request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        /*TODO: Not implemented yet*/
    }

    @Override
    public void approveJobSeeker(JobSeekerByIdRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver) {
        /*TODO: Not implemented yet*/
    }

    @Override
    public void deleteJobSeeker(JobSeekerByIdRequest request, StreamObserver<CommonResponse> responseObserver) {
        /*TODO: Not implemented yet*/
    }
}
