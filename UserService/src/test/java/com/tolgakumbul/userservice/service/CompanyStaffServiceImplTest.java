package com.tolgakumbul.userservice.service;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.dao.model.Pageable;
import com.tolgakumbul.userservice.entity.CompanyStaffEntity;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.common.PageableDTO;
import com.tolgakumbul.userservice.model.companystaff.*;
import com.tolgakumbul.userservice.service.impl.CompanyStaffServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

public class CompanyStaffServiceImplTest {

    @Mock
    private CompanyStaffDao companyStaffDao;

    @InjectMocks
    private CompanyStaffServiceImpl companyStaffService;

    private AutoCloseable autoCloseable;

    @Before
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        companyStaffService = new CompanyStaffServiceImpl(companyStaffDao);
    }

    @After
    public void after() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void getAllCompanyStaffTest() {
        Mockito.when(companyStaffDao.getAllCompanyStaff(getListRequest())).thenReturn(Collections.singletonList(getCompanyStaffEntity()));
        CompanyStaffListResponseDTO allCompanyStaff = companyStaffService.getAllCompanyStaff(getAllCompanyStaffRequestDTO());
        Assert.assertNotNull(allCompanyStaff);
        Assert.assertEquals(123L, allCompanyStaff.getCompanyStaffList().get(0).getUserId().longValue());
        Assert.assertEquals("TestName", allCompanyStaff.getCompanyStaffList().get(0).getFirstName());
        Assert.assertEquals("TestLastName", allCompanyStaff.getCompanyStaffList().get(0).getLastName());
        Assert.assertEquals(IsApprovedEnum.ACTIVE, allCompanyStaff.getCompanyStaffList().get(0).getIsApproved());
    }

    @Test
    public void getAllCompanyStaffEmptyDaoReturnTest() {
        Mockito.when(companyStaffDao.getAllCompanyStaff(getListRequest())).thenReturn(new ArrayList<>());
        CompanyStaffListResponseDTO allCompanyStaff = companyStaffService.getAllCompanyStaff(getAllCompanyStaffRequestDTO());
        Assert.assertNotNull(allCompanyStaff);
        Assert.assertEquals(0, allCompanyStaff.getCompanyStaffList().size());
    }

    @Test(expected = UsersException.class)
    public void getAllCompanyStaffDaoExceptionTest() {
        Mockito.when(companyStaffDao.getAllCompanyStaff(getListRequest())).thenThrow(new RuntimeException());
        companyStaffService.getAllCompanyStaff(getAllCompanyStaffRequestDTO());
    }

    @Test(expected = UsersException.class)
    public void getAllCompanyStaffExceptionTest() {
        Mockito.when(companyStaffDao.getAllCompanyStaff(getListRequest())).thenReturn(Collections.singletonList(getCompanyStaffEntityForException()));
        companyStaffService.getAllCompanyStaff(getAllCompanyStaffRequestDTO());
    }

    @Test
    public void getCompanyStaffByIdTest() {
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenReturn(getCompanyStaffEntity());
        CompanyStaffGeneralResponseDTO companyStaffById = companyStaffService.getCompanyStaffById(999L);
        Assert.assertNotNull(companyStaffById);
        Assert.assertEquals(123L, companyStaffById.getCompanyStaffData().getUserId().longValue());
        Assert.assertEquals("TestName", companyStaffById.getCompanyStaffData().getFirstName());
        Assert.assertEquals("TestLastName", companyStaffById.getCompanyStaffData().getLastName());
        Assert.assertEquals(IsApprovedEnum.ACTIVE, companyStaffById.getCompanyStaffData().getIsApproved());
    }

    @Test
    public void getCompanyStaffByIdDaoNullReturnTest() {
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenReturn(null);
        CompanyStaffGeneralResponseDTO companyStaffById = companyStaffService.getCompanyStaffById(999L);
        Assert.assertNotNull(companyStaffById);
    }

    @Test
    public void getCompanyStaffByIdDaoEmptyReturnTest() {
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenReturn(new CompanyStaffEntity());
        CompanyStaffGeneralResponseDTO companyStaffById = companyStaffService.getCompanyStaffById(999L);
        Assert.assertNotNull(companyStaffById);
    }

    @Test(expected = UsersException.class)
    public void getCompanyStaffByIdDaoExceptionTest() {
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenThrow(new RuntimeException());
        companyStaffService.getCompanyStaffById(999L);
    }

    @Test(expected = UsersException.class)
    public void getCompanyStaffByIdExceptionTest() {
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenReturn(getCompanyStaffEntityForException());
        companyStaffService.getCompanyStaffById(999L);
    }

    @Test
    public void getCompanyStaffByNameTest() {
        Mockito.when(companyStaffDao.getCompanyStaffByName(any(), any())).thenReturn(getCompanyStaffEntity());
        CompanyStaffGeneralResponseDTO companyStaffByName = companyStaffService.getCompanyStaffByName("TestName", "TestLastName");
        Assert.assertNotNull(companyStaffByName);
        Assert.assertEquals(123L, companyStaffByName.getCompanyStaffData().getUserId().longValue());
        Assert.assertEquals("TestName", companyStaffByName.getCompanyStaffData().getFirstName());
        Assert.assertEquals("TestLastName", companyStaffByName.getCompanyStaffData().getLastName());
        Assert.assertEquals(IsApprovedEnum.ACTIVE, companyStaffByName.getCompanyStaffData().getIsApproved());
    }

    @Test
    public void getCompanyStaffByNameDaoNullReturnTest() {
        Mockito.when(companyStaffDao.getCompanyStaffByName(any(), any())).thenReturn(null);
        CompanyStaffGeneralResponseDTO companyStaffByName = companyStaffService.getCompanyStaffByName("TestName", "TestLastName");
        Assert.assertNotNull(companyStaffByName);
    }

    @Test
    public void getCompanyStaffByNameDaoEmptyReturnTest() {
        Mockito.when(companyStaffDao.getCompanyStaffByName(any(), any())).thenReturn(new CompanyStaffEntity());
        CompanyStaffGeneralResponseDTO companyStaffByName = companyStaffService.getCompanyStaffByName("TestName", "TestLastName");
        Assert.assertNotNull(companyStaffByName);
    }

    @Test(expected = UsersException.class)
    public void getCompanyStaffByNameDaoExceptionTest() {
        Mockito.when(companyStaffDao.getCompanyStaffByName(any(), any())).thenThrow(new RuntimeException());
        companyStaffService.getCompanyStaffByName("TestName", "TestLastName");
    }

    @Test(expected = UsersException.class)
    public void getCompanyStaffByNameExceptionTest() {
        Mockito.when(companyStaffDao.getCompanyStaffByName(any(), any())).thenReturn(getCompanyStaffEntityForException());
        companyStaffService.getCompanyStaffByName("TestName", "TestLastName");
    }

    @Test
    public void insertCompanyStaffTest() {
        Mockito.when(companyStaffDao.insertCompanyStaff(any())).thenReturn(1);
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenReturn(getCompanyStaffEntity());
        CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = companyStaffService.insertCompanyStaff(getCompanyStaffDTO());
        Assert.assertNotNull(companyStaffGeneralResponseDTO);
        Assert.assertEquals(123L, companyStaffGeneralResponseDTO.getCompanyStaffData().getUserId().longValue());
        Assert.assertEquals("TestName", companyStaffGeneralResponseDTO.getCompanyStaffData().getFirstName());
        Assert.assertEquals("TestLastName", companyStaffGeneralResponseDTO.getCompanyStaffData().getLastName());
        Assert.assertEquals(IsApprovedEnum.ACTIVE, companyStaffGeneralResponseDTO.getCompanyStaffData().getIsApproved());
    }

    @Test(expected = UsersException.class)
    public void insertCompanyStaffDaoExceptionTest() {
        Mockito.when(companyStaffDao.insertCompanyStaff(any())).thenThrow(new RuntimeException());
        companyStaffService.insertCompanyStaff(getCompanyStaffDTO());
    }

    @Test(expected = UsersException.class)
    public void insertCompanyStaffGetByIdDaoExceptionTest() {
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenThrow(new RuntimeException());
        companyStaffService.insertCompanyStaff(getCompanyStaffDTO());
    }

    @Test(expected = UsersException.class)
    public void insertCompanyStaffGetByIdExceptionTest() {
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenReturn(getCompanyStaffEntityForException());
        companyStaffService.insertCompanyStaff(getCompanyStaffDTO());
    }

    @Test
    public void updateCompanyStaffTest() {
        Mockito.when(companyStaffDao.updateCompanyStaff(any())).thenReturn(1);
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenReturn(getCompanyStaffEntity());
        CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = companyStaffService.updateCompanyStaff(getCompanyStaffDTO());
        Assert.assertNotNull(companyStaffGeneralResponseDTO);
        Assert.assertEquals(123L, companyStaffGeneralResponseDTO.getCompanyStaffData().getUserId().longValue());
        Assert.assertEquals("TestName", companyStaffGeneralResponseDTO.getCompanyStaffData().getFirstName());
        Assert.assertEquals("TestLastName", companyStaffGeneralResponseDTO.getCompanyStaffData().getLastName());
        Assert.assertEquals(IsApprovedEnum.ACTIVE, companyStaffGeneralResponseDTO.getCompanyStaffData().getIsApproved());
    }

    @Test(expected = UsersException.class)
    public void updateCompanyStaffDaoInsertionExceptionTest() {
        Mockito.when(companyStaffDao.updateCompanyStaff(any())).thenThrow(new RuntimeException());
        companyStaffService.updateCompanyStaff(getCompanyStaffDTO());
    }

    @Test(expected = UsersException.class)
    public void updateCompanyStaffDaoGetByIdExceptionTest() {
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenThrow(new RuntimeException());
        companyStaffService.updateCompanyStaff(getCompanyStaffDTO());
    }

    @Test
    public void deleteCompanyStaffTest() {
        Mockito.when(companyStaffDao.deleteCompanyStaff(any())).thenReturn(1);
        CommonResponseDTO commonResponseDTO = companyStaffService.deleteCompanyStaff(999L);
        Assert.assertNotNull(commonResponseDTO);
        Assert.assertEquals(Constants.OK, commonResponseDTO.getReasonCode());
    }

    @Test(expected = UsersException.class)
    public void deleteCompanyStaffExceptionTest() {
        Mockito.when(companyStaffDao.deleteCompanyStaff(any())).thenThrow(new RuntimeException());
        companyStaffService.deleteCompanyStaff(999L);
    }


    @Test
    public void approveCompanyStaffTest() {
        Mockito.when(companyStaffDao.approveCompanyStaff(any())).thenReturn(1);
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenReturn(getCompanyStaffEntity());
        CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = companyStaffService.approveCompanyStaff(999L);
        Assert.assertNotNull(companyStaffGeneralResponseDTO);
        Assert.assertEquals(123L, companyStaffGeneralResponseDTO.getCompanyStaffData().getUserId().longValue());
        Assert.assertEquals("TestName", companyStaffGeneralResponseDTO.getCompanyStaffData().getFirstName());
        Assert.assertEquals("TestLastName", companyStaffGeneralResponseDTO.getCompanyStaffData().getLastName());
        Assert.assertEquals(IsApprovedEnum.ACTIVE, companyStaffGeneralResponseDTO.getCompanyStaffData().getIsApproved());
    }

    @Test(expected = UsersException.class)
    public void approveCompanyStaffExceptionTest() {
        Mockito.when(companyStaffDao.approveCompanyStaff(any())).thenThrow(new RuntimeException());
        companyStaffService.approveCompanyStaff(999L);
    }

    @Test(expected = UsersException.class)
    public void approveCompanyStaffGetByIdExceptionTest() {
        Mockito.when(companyStaffDao.getCompanyStaffById(any())).thenThrow(new RuntimeException());
        companyStaffService.approveCompanyStaff(999L);
    }

    private CompanyStaffDTO getCompanyStaffDTO() {
        CompanyStaffDTO companyStaffDTO = new CompanyStaffDTO();
        companyStaffDTO.setUserId(123L);
        companyStaffDTO.setIsApproved(IsApprovedEnum.PASSIVE);
        ;
        companyStaffDTO.setApprovalDate(LocalDateTime.now());
        companyStaffDTO.setFirstName("TestName");
        companyStaffDTO.setLastName("TestLastName");
        return companyStaffDTO;
    }

    private CompanyStaffEntity getCompanyStaffEntity() {
        CompanyStaffEntity companyStaffEntity = new CompanyStaffEntity();
        companyStaffEntity.setUserId(123L);
        companyStaffEntity.setFirstName("TestName");
        companyStaffEntity.setLastName("TestLastName");
        companyStaffEntity.setIsApproved("A");
        companyStaffEntity.setApprovalDate(LocalDateTime.now());
        return companyStaffEntity;
    }

    private CompanyStaffEntity getCompanyStaffEntityForException() {
        CompanyStaffEntity companyStaffEntity = new CompanyStaffEntity();
        companyStaffEntity.setUserId(123L);
        companyStaffEntity.setFirstName("TestName");
        companyStaffEntity.setLastName("TestLastName");
        companyStaffEntity.setIsApproved("UNKNOWN_ENUM");
        companyStaffEntity.setApprovalDate(LocalDateTime.now());
        return companyStaffEntity;
    }

    private GetAllCompanyStaffRequestDTO getAllCompanyStaffRequestDTO() {
        GetAllCompanyStaffRequestDTO getAllCompanyStaffRequestDTO = new GetAllCompanyStaffRequestDTO();
        PageableDTO pageableDTO = new PageableDTO();
        pageableDTO.setPageNumber(1L);
        pageableDTO.setPageSize(30L);
        pageableDTO.setSortColumn("");
        pageableDTO.setSortType("");
        getAllCompanyStaffRequestDTO.setPageable(pageableDTO);
        return getAllCompanyStaffRequestDTO;
    }

    private ListRequest getListRequest() {
        ListRequest listRequest = new ListRequest();
        Pageable pageableDTO = new Pageable();
        pageableDTO.setPageNumber(1L);
        pageableDTO.setPageSize(30L);
        pageableDTO.setSortColumn("");
        pageableDTO.setSortType("");
        listRequest.setPageable(pageableDTO);
        return listRequest;
    }


}
