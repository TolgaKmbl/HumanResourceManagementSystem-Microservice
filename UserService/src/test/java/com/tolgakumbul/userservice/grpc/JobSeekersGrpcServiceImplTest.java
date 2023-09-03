package com.tolgakumbul.userservice.grpc;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.JobSeekersGrpcServiceGrpc;
import com.tolgakumbul.proto.JobSeekersGrpcServiceGrpc.JobSeekersGrpcServiceBlockingStub;
import com.tolgakumbul.proto.JobSeekersProto.*;
import com.tolgakumbul.proto.PageableProto.Pageable;
import com.tolgakumbul.userservice.grpc.impl.JobSeekersGrpcServiceImpl;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerGeneralResponseDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerListResponseDTO;
import com.tolgakumbul.userservice.service.JobSeekersService;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobSeekersGrpcServiceImplTest {

    @Rule
    public GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

    private JobSeekersGrpcServiceBlockingStub blockingStub;

    private JobSeekersGrpcServiceImpl jobSeekersGrpcService;

    @Mock
    private JobSeekersService jobSeekersService;

    @Before
    public void setUp() throws IOException {
        String serverName = InProcessServerBuilder.generateName();

        jobSeekersGrpcService = new JobSeekersGrpcServiceImpl(jobSeekersService);

        grpcCleanupRule.register(InProcessServerBuilder.forName(serverName).directExecutor().addService(jobSeekersGrpcService).build().start());

        blockingStub = JobSeekersGrpcServiceGrpc.newBlockingStub(
                grpcCleanupRule.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));
    }

    @Test
    public void getAllJobSeekersTest() {
        JobSeekerListGeneralRequest jobSeekerListGeneralRequest = JobSeekerListGeneralRequest.newBuilder()
                .setPageable(Pageable.newBuilder().setPageNumber(3L).setPageSize(15L).setSortColumn("Column").setSortType("ASC").build())
                .build();

        JobSeekerListResponseDTO jobSeekerListResponseDTO = new JobSeekerListResponseDTO();
        jobSeekerListResponseDTO.setJobSeekerList(Collections.singletonList(getJobSeekerDto()));

        when(jobSeekersService.getAllJobSeekers(any())).thenReturn(jobSeekerListResponseDTO);

        JobSeekerListGeneralResponse allJobSeekers = blockingStub.getAllJobSeekers(jobSeekerListGeneralRequest);

        Assert.assertNotNull(allJobSeekers);
        Assert.assertEquals(1, allJobSeekers.getJobSeekerListList().size());
        Assert.assertEquals("TOLGA", allJobSeekers.getJobSeekerListList().get(0).getFirstName());
    }

    @Test
    public void getJobSeekerByIdTest() {
        JobSeekerByIdRequest jobSeekerByIdRequest = JobSeekerByIdRequest.newBuilder()
                .setUserId(12312L)
                .build();

        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO();
        jobSeekerGeneralResponseDTO.setJobSeeker(getJobSeekerDto());

        when(jobSeekersService.getJobSeekerById(any())).thenReturn(jobSeekerGeneralResponseDTO);

        JobSeekerGeneralResponse jobSeekerById = blockingStub.getJobSeekerById(jobSeekerByIdRequest);

        Assert.assertNotNull(jobSeekerById);
        Assert.assertEquals("TOLGA", jobSeekerById.getJobSeeker().getFirstName());
    }

    @Test
    public void getJobSeekerByNationalIdTest() {
        JobSeekerByNationalIdRequest jobSeekerByNationalIdRequest = JobSeekerByNationalIdRequest.newBuilder()
                .setNationalId("123123")
                .build();

        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO();
        jobSeekerGeneralResponseDTO.setJobSeeker(getJobSeekerDto());

        when(jobSeekersService.getJobSeekerByNationalId(any())).thenReturn(jobSeekerGeneralResponseDTO);

        JobSeekerGeneralResponse jobSeekerByNationalId = blockingStub.getJobSeekerByNationalId(jobSeekerByNationalIdRequest);

        Assert.assertNotNull(jobSeekerByNationalId);
        Assert.assertEquals("TOLGA", jobSeekerByNationalId.getJobSeeker().getFirstName());
    }

    @Test
    public void getJobSeekerByNameTest() {
        JobSeekerByNameRequest jobSeekerByNameRequest = JobSeekerByNameRequest.newBuilder()
                .setFirstName("Tolga")
                .setLastName("Tolga")
                .build();

        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO();
        jobSeekerGeneralResponseDTO.setJobSeeker(getJobSeekerDto());

        when(jobSeekersService.getJobSeekerByName(any(), any())).thenReturn(jobSeekerGeneralResponseDTO);

        JobSeekerGeneralResponse jobSeekerByNationalId = blockingStub.getJobSeekerByName(jobSeekerByNameRequest);

        Assert.assertNotNull(jobSeekerByNationalId);
        Assert.assertEquals("TOLGA", jobSeekerByNationalId.getJobSeeker().getFirstName());
    }

    @Test
    public void updateJobSeekerTest() {
        JobSeeker jobSeeker = JobSeeker.newBuilder()
                .setFirstName("Tolga")
                .setLastName("Tolga")
                .build();

        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO();
        jobSeekerGeneralResponseDTO.setJobSeeker(getJobSeekerDto());

        when(jobSeekersService.updateJobSeeker(any())).thenReturn(jobSeekerGeneralResponseDTO);

        JobSeekerGeneralResponse jobSeekerGeneralResponse = blockingStub.updateJobSeeker(jobSeeker);

        Assert.assertNotNull(jobSeekerGeneralResponse);
        Assert.assertEquals("TOLGA", jobSeekerGeneralResponse.getJobSeeker().getFirstName());
    }

    @Test
    public void insertJobSeekerTest() {
        JobSeeker jobSeeker = JobSeeker.newBuilder()
                .setFirstName("Tolga")
                .setLastName("Tolga")
                .build();

        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO();
        jobSeekerGeneralResponseDTO.setJobSeeker(getJobSeekerDto());

        when(jobSeekersService.insertJobSeeker(any())).thenReturn(jobSeekerGeneralResponseDTO);

        JobSeekerGeneralResponse jobSeekerGeneralResponse = blockingStub.insertJobSeeker(jobSeeker);

        Assert.assertNotNull(jobSeekerGeneralResponse);
        Assert.assertEquals("TOLGA", jobSeekerGeneralResponse.getJobSeeker().getFirstName());
    }

    @Test
    public void deleteJobSeekerTest() {
        JobSeekerByIdRequest jobSeekerByIdRequest = JobSeekerByIdRequest.newBuilder()
                .setUserId(123123L)
                .build();

        CommonResponseDTO commonResponseDTO = new CommonResponseDTO();
        commonResponseDTO.setReasonCode("OK");
        commonResponseDTO.setReturnCode(200);

        when(jobSeekersService.deleteJobSeeker(any())).thenReturn(commonResponseDTO);

        CommonResponse commonResponse = blockingStub.deleteJobSeeker(jobSeekerByIdRequest);

        Assert.assertNotNull(commonResponse);
        Assert.assertEquals("OK", commonResponse.getReasonCode());
    }

    @Test
    public void approveJobSeekerTest() {
        JobSeekerByIdRequest jobSeekerByIdRequest = JobSeekerByIdRequest.newBuilder()
                .setUserId(123123L)
                .build();

        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO();
        jobSeekerGeneralResponseDTO.setJobSeeker(getJobSeekerDto());

        when(jobSeekersService.approveJobSeeker(any())).thenReturn(jobSeekerGeneralResponseDTO);

        JobSeekerGeneralResponse jobSeekerGeneralResponse = blockingStub.approveJobSeeker(jobSeekerByIdRequest);

        Assert.assertNotNull(jobSeekerGeneralResponse);
        Assert.assertEquals("TOLGA", jobSeekerGeneralResponse.getJobSeeker().getFirstName());
    }

    private JobSeekerDTO getJobSeekerDto() {
        JobSeekerDTO jobSeekerDTO = new JobSeekerDTO();
        jobSeekerDTO.setUserId(123L);
        jobSeekerDTO.setFirstName("TOLGA");
        jobSeekerDTO.setLastName("TEST");
        return jobSeekerDTO;
    }


}
