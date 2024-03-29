package com.tolgakumbul.userservice.grpc;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.CompanyStaffGrpcServiceGrpc;
import com.tolgakumbul.proto.CompanyStaffGrpcServiceGrpc.CompanyStaffGrpcServiceBlockingStub;
import com.tolgakumbul.proto.CompanyStaffProto.*;
import com.tolgakumbul.proto.PageableProto.Pageable;
import com.tolgakumbul.userservice.grpc.impl.CompanyStaffGrpcServiceImpl;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.common.PaginationMetadataDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffGeneralResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffListResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.IsApprovedEnum;
import com.tolgakumbul.userservice.service.CompanyStaffService;
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
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompanyStaffGrpcServiceImplTest {

    @Rule
    public GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

    private CompanyStaffGrpcServiceBlockingStub blockingStub;

    private CompanyStaffGrpcServiceImpl companyStaffGrpcService;

    @Mock
    private CompanyStaffService companyStaffService;

    @Before
    public void setUp() throws IOException {
        String serverName = InProcessServerBuilder.generateName();

        companyStaffGrpcService = new CompanyStaffGrpcServiceImpl(companyStaffService);

        grpcCleanupRule.register(InProcessServerBuilder.forName(serverName).directExecutor().addService(companyStaffGrpcService).build().start());

        blockingStub = CompanyStaffGrpcServiceGrpc.newBlockingStub(
                grpcCleanupRule.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));
    }

    @Test
    public void getAllCompanyStaffTest() {
        GetAllCompanyStaffRequest getAllCompanyStaffRequest = GetAllCompanyStaffRequest.newBuilder()
                .setPageable(Pageable.newBuilder().setPageNumber(3L).setPageSize(15L).setSortColumn("Column").setSortType("ASC").build())
                .build();

        CompanyStaffListResponseDTO companyStaffListResponseDTO = new CompanyStaffListResponseDTO();
        companyStaffListResponseDTO.setCompanyStaffList(Collections.singletonList(getCompanyStaffDTO()));
        PaginationMetadataDTO paginationMetadata = new PaginationMetadataDTO();
        paginationMetadata.setPageSize(15L);
        paginationMetadata.setCurrentPage(3L);
        companyStaffListResponseDTO.setPaginationMetadata(paginationMetadata);

        when(companyStaffService.getAllCompanyStaff(any())).thenReturn(companyStaffListResponseDTO);

        GetAllCompanyStaffResponse allCompanyStaff = blockingStub.getAllCompanyStaff(getAllCompanyStaffRequest);

        Assert.assertNotNull(allCompanyStaff);
        Assert.assertEquals(1, allCompanyStaff.getCompanyStaffListList().size());
        Assert.assertEquals("TestName", allCompanyStaff.getCompanyStaffListList().get(0).getFirstName());
        Assert.assertEquals(3L, allCompanyStaff.getPaginationMetadata().getCurrentPage());
        Assert.assertEquals(15L, allCompanyStaff.getPaginationMetadata().getPageSize());
    }

    @Test
    public void getCompanyStaffByIdTest() {
        CompanyStaffByIdRequest companyStaffByIdRequest = CompanyStaffByIdRequest.newBuilder()
                .setUserId(12312L)
                .build();

        CompanyStaffGeneralResponseDTO companyStaffGeneralResponse = new CompanyStaffGeneralResponseDTO();
        companyStaffGeneralResponse.setCompanyStaffData(getCompanyStaffDTO());

        when(companyStaffService.getCompanyStaffById(any())).thenReturn(companyStaffGeneralResponse);

        CompanyStaffGeneralResponse companyStaffById = blockingStub.getCompanyStaffById(companyStaffByIdRequest);

        Assert.assertNotNull(companyStaffById);
        Assert.assertEquals("TestName", companyStaffById.getCompanyStaffData().getFirstName());
    }

    @Test
    public void getCompanyStaffByNameTest() {
        GetCompanyStaffByNameRequest getCompanyStaffByNameRequest = GetCompanyStaffByNameRequest.newBuilder()
                .setFirstName("Tolga")
                .setLastName("Tolga")
                .build();

        CompanyStaffGeneralResponseDTO companyStaffGeneralResponse = new CompanyStaffGeneralResponseDTO();
        companyStaffGeneralResponse.setCompanyStaffData(getCompanyStaffDTO());

        when(companyStaffService.getCompanyStaffByName(any(), any())).thenReturn(companyStaffGeneralResponse);

        CompanyStaffGeneralResponse companyStaffById = blockingStub.getCompanyStaffByName(getCompanyStaffByNameRequest);

        Assert.assertNotNull(companyStaffById);
        Assert.assertEquals("TestName", companyStaffById.getCompanyStaffData().getFirstName());
    }

    @Test
    public void insertCompanyStaffTest() {
        CompanyStaffData companyStaffData = CompanyStaffData.newBuilder()
                .setFirstName("Tolga")
                .setLastName("Tolga")
                .build();

        CompanyStaffGeneralResponseDTO companyStaffGeneralResponse = new CompanyStaffGeneralResponseDTO();
        companyStaffGeneralResponse.setCompanyStaffData(getCompanyStaffDTO());

        when(companyStaffService.insertCompanyStaff(any())).thenReturn(companyStaffGeneralResponse);

        CompanyStaffGeneralResponse insertedCompanyStaff = blockingStub.insertCompanyStaff(companyStaffData);

        Assert.assertNotNull(insertedCompanyStaff);
        Assert.assertEquals("TestName", insertedCompanyStaff.getCompanyStaffData().getFirstName());
    }

    @Test
    public void updateCompanyStaffTest() {
        CompanyStaffData companyStaffData = CompanyStaffData.newBuilder()
                .setFirstName("Tolga")
                .setLastName("Tolga")
                .build();

        CompanyStaffGeneralResponseDTO companyStaffGeneralResponse = new CompanyStaffGeneralResponseDTO();
        companyStaffGeneralResponse.setCompanyStaffData(getCompanyStaffDTO());

        when(companyStaffService.updateCompanyStaff(any())).thenReturn(companyStaffGeneralResponse);

        CompanyStaffGeneralResponse insertedCompanyStaff = blockingStub.updateCompanyStaff(companyStaffData);

        Assert.assertNotNull(insertedCompanyStaff);
        Assert.assertEquals("TestName", insertedCompanyStaff.getCompanyStaffData().getFirstName());
    }

    @Test
    public void deleteCompanyStaffTest() {
        CompanyStaffByIdRequest companyStaffByIdRequest = CompanyStaffByIdRequest.newBuilder()
                .setUserId(123123L)
                .build();

        CommonResponseDTO commonResponseDTO = new CommonResponseDTO();
        commonResponseDTO.setReasonCode("OK");
        commonResponseDTO.setReturnCode(200);

        when(companyStaffService.deleteCompanyStaff(any())).thenReturn(commonResponseDTO);

        CommonResponse commonResponse = blockingStub.deleteCompanyStaff(companyStaffByIdRequest);

        Assert.assertNotNull(commonResponse);
        Assert.assertEquals("OK", commonResponse.getReasonCode());
    }

    @Test
    public void approveCompanyStaffTest() {
        CompanyStaffByIdRequest companyStaffByIdRequest = CompanyStaffByIdRequest.newBuilder()
                .setUserId(123123L)
                .build();

        CompanyStaffGeneralResponseDTO companyStaffGeneralResponse = new CompanyStaffGeneralResponseDTO();
        companyStaffGeneralResponse.setCompanyStaffData(getCompanyStaffDTO());

        when(companyStaffService.approveCompanyStaff(any())).thenReturn(companyStaffGeneralResponse);

        CompanyStaffGeneralResponse approvedCompanyStaff = blockingStub.approveCompanyStaff(companyStaffByIdRequest);

        Assert.assertNotNull(approvedCompanyStaff);
        Assert.assertEquals("TestName", approvedCompanyStaff.getCompanyStaffData().getFirstName());
    }

    private CompanyStaffDTO getCompanyStaffDTO() {
        CompanyStaffDTO companyStaffDTO = new CompanyStaffDTO();
        companyStaffDTO.setUserId(123L);
        companyStaffDTO.setIsApproved(IsApprovedEnum.PASSIVE);
        companyStaffDTO.setApprovalDate(LocalDateTime.now());
        companyStaffDTO.setFirstName("TestName");
        companyStaffDTO.setLastName("TestLastName");
        return companyStaffDTO;
    }


}
