package com.tolgakumbul.userservice.service;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.dao.EmployersDao;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.dao.model.Pageable;
import com.tolgakumbul.userservice.entity.EmployersEntity;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.common.PageableDTO;
import com.tolgakumbul.userservice.model.employers.*;
import com.tolgakumbul.userservice.service.impl.EmployersServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

public class EmployersServiceImplTest {

    @Mock
    private EmployersDao employersDao;

    @InjectMocks
    private EmployersServiceImpl employersService;

    private AutoCloseable autoCloseable;

    @Before
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        employersService = new EmployersServiceImpl(employersDao);
    }

    @After
    public void after() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void getAllEmployersTest() {
        Mockito.when(employersDao.getAllEmployers(getListRequest())).thenReturn(Collections.singletonList(getEmployersEntity()));
        EmployersListResponseDTO allEmployers = employersService.getAllEmployers(getAllEmployersRequestDTO());
        Assert.assertNotNull(allEmployers);
        Assert.assertEquals(123L, allEmployers.getEmployerList().get(0).getUserId().longValue());
        Assert.assertEquals("01231231223", allEmployers.getEmployerList().get(0).getPhoneNum());
        Assert.assertEquals("TESTCOMPNAME", allEmployers.getEmployerList().get(0).getCompanyName());
    }

    @Test(expected = UsersException.class)
    public void getAllEmployersExceptionTest() {
        Mockito.when(employersDao.getAllEmployers(getListRequest())).thenThrow(new RuntimeException());
        employersService.getAllEmployers(getAllEmployersRequestDTO());
    }

    @Test
    public void getEmployerByIdTest() {
        Mockito.when(employersDao.getEmployerById(any())).thenReturn(getEmployersEntity());
        EmployersGeneralResponseDTO employerById = employersService.getEmployerById(999L);
        Assert.assertNotNull(employerById);
        Assert.assertEquals(123L, employerById.getEmployer().getUserId().longValue());
        Assert.assertEquals("01231231223", employerById.getEmployer().getPhoneNum());
        Assert.assertEquals("TESTCOMPNAME", employerById.getEmployer().getCompanyName());
    }

    @Test(expected = UsersException.class)
    public void getEmployerByIdExceptionTest() {
        Mockito.when(employersDao.getEmployerById(any())).thenThrow(new RuntimeException());
        employersService.getEmployerById(999L);
    }

    @Test
    public void getEmployersByCompanyNameTest() {
        Mockito.when(employersDao.getEmployersByCompanyName(any())).thenReturn(Collections.singletonList(getEmployersEntity()));
        EmployersListResponseDTO employersByCompanyName = employersService.getEmployersByCompanyName(getEmployersByCompanyNameRequestDTO());
        Assert.assertNotNull(employersByCompanyName);
        Assert.assertEquals(123L, employersByCompanyName.getEmployerList().get(0).getUserId().longValue());
        Assert.assertEquals("01231231223", employersByCompanyName.getEmployerList().get(0).getPhoneNum());
        Assert.assertEquals("TESTCOMPNAME", employersByCompanyName.getEmployerList().get(0).getCompanyName());
    }

    @Test(expected = UsersException.class)
    public void getEmployersByCompanyNameExceptionTest() {
        Mockito.when(employersDao.getEmployersByCompanyName(any())).thenThrow(new RuntimeException());
        employersService.getEmployersByCompanyName(getEmployersByCompanyNameRequestDTO());
    }

    @Test
    public void insertEmployerTest() {
        Mockito.when(employersDao.insertEmployer(any())).thenReturn(1);
        Mockito.when(employersDao.getEmployerById(any())).thenReturn(getEmployersEntity());
        EmployersGeneralResponseDTO employersGeneralResponseDTO = employersService.insertEmployer(getEmployersDTO());
        Assert.assertNotNull(employersGeneralResponseDTO);
        Assert.assertEquals(123L, employersGeneralResponseDTO.getEmployer().getUserId().longValue());
        Assert.assertEquals("01231231223", employersGeneralResponseDTO.getEmployer().getPhoneNum());
        Assert.assertEquals("TESTCOMPNAME", employersGeneralResponseDTO.getEmployer().getCompanyName());
    }

    @Test(expected = UsersException.class)
    public void insertEmployerDaoInsertionExceptionTest() {
        Mockito.when(employersDao.insertEmployer(any())).thenThrow(new RuntimeException());
        employersService.insertEmployer(getEmployersDTO());
    }

    @Test(expected = UsersException.class)
    public void insertEmployerDaoGetByIdExceptionTest() {
        Mockito.when(employersDao.getEmployerById(any())).thenThrow(new RuntimeException());
        employersService.insertEmployer(getEmployersDTO());
    }

    @Test
    public void updateEmployerTest() {
        Mockito.when(employersDao.updateEmployer(any())).thenReturn(1);
        Mockito.when(employersDao.getEmployerById(any())).thenReturn(getEmployersEntity());
        EmployersGeneralResponseDTO employersGeneralResponseDTO = employersService.updateEmployer(getEmployersDTO());
        Assert.assertNotNull(employersGeneralResponseDTO);
        Assert.assertEquals(123L, employersGeneralResponseDTO.getEmployer().getUserId().longValue());
        Assert.assertEquals("01231231223", employersGeneralResponseDTO.getEmployer().getPhoneNum());
        Assert.assertEquals("TESTCOMPNAME", employersGeneralResponseDTO.getEmployer().getCompanyName());
    }

    @Test(expected = UsersException.class)
    public void updateEmployerDaoInsertionExceptionTest() {
        Mockito.when(employersDao.updateEmployer(any())).thenThrow(new RuntimeException());
        employersService.updateEmployer(getEmployersDTO());
    }

    @Test(expected = UsersException.class)
    public void updateEmployerDaoGetByIdExceptionTest() {
        Mockito.when(employersDao.getEmployerById(any())).thenThrow(new RuntimeException());
        employersService.updateEmployer(getEmployersDTO());
    }

    @Test
    public void deleteEmployerTest() {
        Mockito.when(employersDao.deleteEmployer(any())).thenReturn(1);
        CommonResponseDTO commonResponseDTO = employersService.deleteEmployer(999L);
        Assert.assertNotNull(commonResponseDTO);
        Assert.assertEquals(Constants.OK, commonResponseDTO.getReasonCode());
    }

    @Test(expected = UsersException.class)
    public void deleteEmployerExceptionTest() {
        Mockito.when(employersDao.deleteEmployer(any())).thenThrow(new RuntimeException());
        employersService.deleteEmployer(999L);
    }

    private EmployersEntity getEmployersEntity() {
        EmployersEntity employersEntity = new EmployersEntity();
        employersEntity.setUserId(123L);
        employersEntity.setPhoneNum("01231231223");
        employersEntity.setCompanyName("TESTCOMPNAME");
        return employersEntity;
    }

    private EmployersDTO getEmployersDTO() {
        EmployersDTO employersDTO = new EmployersDTO();
        employersDTO.setCompanyName("TEST");
        employersDTO.setWebsite("TEST");
        return employersDTO;
    }

    private GetAllEmployersRequestDTO getAllEmployersRequestDTO() {
        GetAllEmployersRequestDTO getAllEmployersRequestDTO = new GetAllEmployersRequestDTO();
        PageableDTO pageableDTO = new PageableDTO();
        pageableDTO.setPageNumber(1L);
        pageableDTO.setPageSize(30L);
        pageableDTO.setSortColumn("");
        pageableDTO.setSortType("");
        getAllEmployersRequestDTO.setPageable(pageableDTO);
        return getAllEmployersRequestDTO;
    }

    private EmployersByCompanyNameRequestDTO getEmployersByCompanyNameRequestDTO() {
        EmployersByCompanyNameRequestDTO employersByCompanyNameRequestDTO = new EmployersByCompanyNameRequestDTO();
        PageableDTO pageableDTO = new PageableDTO();
        pageableDTO.setPageNumber(1L);
        pageableDTO.setPageSize(30L);
        pageableDTO.setSortColumn("");
        pageableDTO.setSortType("");
        employersByCompanyNameRequestDTO.setPageable(pageableDTO);
        employersByCompanyNameRequestDTO.setCompanyName("TEST");
        return employersByCompanyNameRequestDTO;
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
