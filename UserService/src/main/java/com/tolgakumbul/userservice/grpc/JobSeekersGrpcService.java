package com.tolgakumbul.userservice.grpc;

import com.google.protobuf.Empty;
import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.JobSeekersProto.*;
import io.grpc.stub.StreamObserver;

public interface JobSeekersGrpcService {

    void getAllJobSeekers(Empty request, StreamObserver<JobSeekerListGeneralResponse> responseObserver);

    void getJobSeekerById(JobSeekerByIdRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver);

    void getJobSeekerByNationalId(JobSeekerByNationalIdRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver);

    void getJobSeekerByName(JobSeekerByNameRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver);

    void insertJobSeeker(JobSeeker request, StreamObserver<JobSeekerGeneralResponse> responseObserver);

    void updateJobSeeker(JobSeeker request, StreamObserver<JobSeekerGeneralResponse> responseObserver);

    void deleteJobSeeker(JobSeekerByIdRequest request, StreamObserver<CommonResponse> responseObserver);

    void approveJobSeeker(JobSeekerByIdRequest request, StreamObserver<JobSeekerGeneralResponse> responseObserver);

}
