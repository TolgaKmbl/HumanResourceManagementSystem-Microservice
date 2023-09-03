package com.tolgakumbul.userservice.grpc;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.EmployersGrpcServiceGrpc;
import com.tolgakumbul.proto.EmployersGrpcServiceGrpc.EmployersGrpcServiceBlockingStub;
import com.tolgakumbul.proto.EmployersProto.*;
import com.tolgakumbul.proto.PageableProto.Pageable;
import com.tolgakumbul.userservice.grpc.impl.EmployersGrpcServiceImpl;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersDTO;
import com.tolgakumbul.userservice.model.employers.EmployersGeneralResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersListResponseDTO;
import com.tolgakumbul.userservice.service.EmployersService;
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
public class EmployersGrpcServiceImplTest {

    @Rule
    public GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

    private EmployersGrpcServiceBlockingStub blockingStub;

    private EmployersGrpcServiceImpl employersGrpcService;

    @Mock
    private EmployersService employersService;

    @Before
    public void setUp() throws IOException {
        String serverName = InProcessServerBuilder.generateName();

        employersGrpcService = new EmployersGrpcServiceImpl(employersService);

        grpcCleanupRule.register(InProcessServerBuilder.forName(serverName).directExecutor().addService(employersGrpcService).build().start());

        blockingStub = EmployersGrpcServiceGrpc.newBlockingStub(
                grpcCleanupRule.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));
    }

    @Test
    public void getAllEmployersTest() {
        EmployerListGeneralRequest employerListGeneralRequest = EmployerListGeneralRequest.newBuilder()
                .setPageable(Pageable.newBuilder().setPageNumber(3L).setPageSize(15L).setSortColumn("Column").setSortType("ASC").build())
                .build();

        EmployersListResponseDTO employersListResponseDTO = new EmployersListResponseDTO();
        employersListResponseDTO.setEmployerList(Collections.singletonList(getEmployersDTO()));

        when(employersService.getAllEmployers(any())).thenReturn(employersListResponseDTO);

        EmployerListGeneralResponse allEmployers = blockingStub.getAllEmployers(employerListGeneralRequest);

        Assert.assertNotNull(allEmployers);
        Assert.assertEquals(1, allEmployers.getEmployerListList().size());
        Assert.assertEquals("TEST", allEmployers.getEmployerListList().get(0).getCompanyName());
    }

    @Test
    public void getEmployerByIdTest() {
        EmployerByIdRequest employerByIdRequest = EmployerByIdRequest.newBuilder()
                .setUserId(12312L)
                .build();

        EmployersGeneralResponseDTO employersGeneralResponseDTO = new EmployersGeneralResponseDTO();
        employersGeneralResponseDTO.setEmployer(getEmployersDTO());

        when(employersService.getEmployerById(any())).thenReturn(employersGeneralResponseDTO);

        EmployerGeneralResponse employerById = blockingStub.getEmployerById(employerByIdRequest);

        Assert.assertNotNull(employerById);
        Assert.assertEquals("TEST", employerById.getEmployer().getCompanyName());
    }

    @Test
    public void getEmployersByCompanyNameTest() {
        EmployersByCompanyNameRequest employersByCompanyNameRequest = EmployersByCompanyNameRequest.newBuilder()
                .setCompanyName("COMPANY")
                .build();

        EmployersListResponseDTO employersListResponseDTO = new EmployersListResponseDTO();
        employersListResponseDTO.setEmployerList(Collections.singletonList(getEmployersDTO()));


        when(employersService.getEmployersByCompanyName(any())).thenReturn(employersListResponseDTO);

        EmployerListGeneralResponse employersByCompanyName = blockingStub.getEmployersByCompanyName(employersByCompanyNameRequest);

        Assert.assertNotNull(employersByCompanyName);
        Assert.assertEquals(1, employersByCompanyName.getEmployerListList().size());
        Assert.assertEquals("TEST", employersByCompanyName.getEmployerListList().get(0).getCompanyName());
    }

    @Test
    public void updateEmployerTest() {
        Employer employer = Employer.newBuilder()
                .setCompanyName("Tolga")
                .setWebsite("Tolga")
                .build();

        EmployersGeneralResponseDTO employersGeneralResponseDTO = new EmployersGeneralResponseDTO();
        employersGeneralResponseDTO.setEmployer(getEmployersDTO());

        when(employersService.updateEmployer(any())).thenReturn(employersGeneralResponseDTO);

        EmployerGeneralResponse employerGeneralResponse = blockingStub.updateEmployer(employer);

        Assert.assertNotNull(employerGeneralResponse);
        Assert.assertEquals("TEST", employerGeneralResponse.getEmployer().getCompanyName());
    }

    @Test
    public void insertEmployerTest() {
        Employer employer = Employer.newBuilder()
                .setCompanyName("Tolga")
                .setWebsite("Tolga")
                .build();

        EmployersGeneralResponseDTO employersGeneralResponseDTO = new EmployersGeneralResponseDTO();
        employersGeneralResponseDTO.setEmployer(getEmployersDTO());

        when(employersService.insertEmployer(any())).thenReturn(employersGeneralResponseDTO);

        EmployerGeneralResponse employerGeneralResponse = blockingStub.insertEmployer(employer);

        Assert.assertNotNull(employerGeneralResponse);
        Assert.assertEquals("TEST", employerGeneralResponse.getEmployer().getCompanyName());
    }


    @Test
    public void deleteEmployerTest() {
        EmployerByIdRequest employerByIdRequest = EmployerByIdRequest.newBuilder()
                .setUserId(123123L)
                .build();

        CommonResponseDTO commonResponseDTO = new CommonResponseDTO();
        commonResponseDTO.setReasonCode("OK");
        commonResponseDTO.setReturnCode(200);

        when(employersService.deleteEmployer(any())).thenReturn(commonResponseDTO);

        CommonResponse commonResponse = blockingStub.deleteEmployer(employerByIdRequest);

        Assert.assertNotNull(commonResponse);
        Assert.assertEquals("OK", commonResponse.getReasonCode());
    }

    private EmployersDTO getEmployersDTO() {
        EmployersDTO employersDTO = new EmployersDTO();
        employersDTO.setCompanyName("TEST");
        employersDTO.setWebsite("TEST");
        return employersDTO;
    }


}
