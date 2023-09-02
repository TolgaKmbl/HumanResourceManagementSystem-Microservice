package com.tolgakumbul.userservice.service;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.dao.JobSeekersDao;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.dao.model.Pageable;
import com.tolgakumbul.userservice.entity.JobSeekersEntity;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.common.PageableDTO;
import com.tolgakumbul.userservice.model.jobseekers.GetAllJobSeekersRequestDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerGeneralResponseDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerListResponseDTO;
import com.tolgakumbul.userservice.service.impl.JobSeekersServiceImpl;
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

public class JobSeekersServiceImplTest {

    @Mock
    private JobSeekersDao jobSeekersDao;

    @InjectMocks
    private JobSeekersServiceImpl jobSeekersService;

    private AutoCloseable autoCloseable;

    @Before
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        jobSeekersService = new JobSeekersServiceImpl(jobSeekersDao);
    }

    @After
    public void after() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void getAllJobSeekersTest() {
        Mockito.when(jobSeekersDao.getAllJobSeekers(getListRequest())).thenReturn(Collections.singletonList(getJobSeekersEntity()));
        JobSeekerListResponseDTO allJobSeekers = jobSeekersService.getAllJobSeekers(getAllJobSeekersRequestDTO());
        Assert.assertNotNull(allJobSeekers);
        Assert.assertEquals(123L, allJobSeekers.getJobSeekerList().get(0).getUserId().longValue());
        Assert.assertEquals("TOLGA", allJobSeekers.getJobSeekerList().get(0).getFirstName());
        Assert.assertEquals("TEST", allJobSeekers.getJobSeekerList().get(0).getLastName());
    }

    @Test(expected = UsersException.class)
    public void getAllJobSeekersExceptionTest() {
        Mockito.when(jobSeekersDao.getAllJobSeekers(getListRequest())).thenThrow(new RuntimeException());
        jobSeekersService.getAllJobSeekers(getAllJobSeekersRequestDTO());
    }

    @Test
    public void getJobSeekerByIdTest() {
        Mockito.when(jobSeekersDao.getJobSeekerById(any())).thenReturn(getJobSeekersEntity());
        JobSeekerGeneralResponseDTO jobSeekerById = jobSeekersService.getJobSeekerById(999L);
        Assert.assertNotNull(jobSeekerById);
        Assert.assertEquals(123L, jobSeekerById.getJobSeeker().getUserId().longValue());
        Assert.assertEquals("TOLGA", jobSeekerById.getJobSeeker().getFirstName());
        Assert.assertEquals("TEST", jobSeekerById.getJobSeeker().getLastName());
    }

    @Test(expected = UsersException.class)
    public void getJobSeekerByIdExceptionTest() {
        Mockito.when(jobSeekersDao.getJobSeekerById(any())).thenThrow(new RuntimeException());
        jobSeekersService.getJobSeekerById(999L);
    }

    @Test
    public void getJobSeekerByNationalIdTest() {
        Mockito.when(jobSeekersDao.getJobSeekerByNationalId(any())).thenReturn(getJobSeekersEntity());
        JobSeekerGeneralResponseDTO jobSeekerByNationalId = jobSeekersService.getJobSeekerByNationalId("123123123");
        Assert.assertNotNull(jobSeekerByNationalId);
        Assert.assertEquals(123L, jobSeekerByNationalId.getJobSeeker().getUserId().longValue());
        Assert.assertEquals("TOLGA", jobSeekerByNationalId.getJobSeeker().getFirstName());
        Assert.assertEquals("TEST", jobSeekerByNationalId.getJobSeeker().getLastName());
    }

    @Test(expected = UsersException.class)
    public void getJobSeekerByNationalIdExceptionTest() {
        Mockito.when(jobSeekersDao.getJobSeekerByNationalId(any())).thenThrow(new RuntimeException());
        jobSeekersService.getJobSeekerByNationalId("123123123");
    }

    @Test
    public void getJobSeekerByNameTest() {
        Mockito.when(jobSeekersDao.getJobSeekerByName(any(), any())).thenReturn(getJobSeekersEntity());
        JobSeekerGeneralResponseDTO jobSeekerByName = jobSeekersService.getJobSeekerByName("Tolga", "Test");
        Assert.assertNotNull(jobSeekerByName);
        Assert.assertEquals(123L, jobSeekerByName.getJobSeeker().getUserId().longValue());
        Assert.assertEquals("TOLGA", jobSeekerByName.getJobSeeker().getFirstName());
        Assert.assertEquals("TEST", jobSeekerByName.getJobSeeker().getLastName());
    }

    @Test(expected = UsersException.class)
    public void getJobSeekerByNameExceptionTest() {
        Mockito.when(jobSeekersDao.getJobSeekerByName(any(), any())).thenThrow(new RuntimeException());
        jobSeekersService.getJobSeekerByName("Tolga", "Test");
    }

    @Test
    public void insertJobSeekerTest() {
        Mockito.when(jobSeekersDao.insertJobSeeker(any())).thenReturn(1);
        Mockito.when(jobSeekersDao.getJobSeekerById(any())).thenReturn(getJobSeekersEntity());
        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = jobSeekersService.insertJobSeeker(getJobSeekerDto());
        Assert.assertNotNull(jobSeekerGeneralResponseDTO);
        Assert.assertEquals(123L, jobSeekerGeneralResponseDTO.getJobSeeker().getUserId().longValue());
        Assert.assertEquals("TOLGA", jobSeekerGeneralResponseDTO.getJobSeeker().getFirstName());
        Assert.assertEquals("TEST", jobSeekerGeneralResponseDTO.getJobSeeker().getLastName());
    }

    @Test(expected = UsersException.class)
    public void insertJobSeekerDaoInsertionExceptionTest() {
        Mockito.when(jobSeekersDao.insertJobSeeker(any())).thenThrow(new RuntimeException());
        jobSeekersService.insertJobSeeker(getJobSeekerDto());
    }

    @Test(expected = UsersException.class)
    public void insertJobSeekerDaoGetByIdExceptionTest() {
        Mockito.when(jobSeekersDao.getJobSeekerById(any())).thenThrow(new RuntimeException());
        jobSeekersService.insertJobSeeker(getJobSeekerDto());
    }

    @Test
    public void updateJobSeekerTest() {
        Mockito.when(jobSeekersDao.updateJobSeeker(any())).thenReturn(1);
        Mockito.when(jobSeekersDao.getJobSeekerById(any())).thenReturn(getJobSeekersEntity());
        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = jobSeekersService.updateJobSeeker(getJobSeekerDto());
        Assert.assertNotNull(jobSeekerGeneralResponseDTO);
        Assert.assertEquals(123L, jobSeekerGeneralResponseDTO.getJobSeeker().getUserId().longValue());
        Assert.assertEquals("TOLGA", jobSeekerGeneralResponseDTO.getJobSeeker().getFirstName());
        Assert.assertEquals("TEST", jobSeekerGeneralResponseDTO.getJobSeeker().getLastName());
    }

    @Test(expected = UsersException.class)
    public void updateJobSeekerDaoInsertionExceptionTest() {
        Mockito.when(jobSeekersDao.updateJobSeeker(any())).thenThrow(new RuntimeException());
        jobSeekersService.updateJobSeeker(getJobSeekerDto());
    }

    @Test(expected = UsersException.class)
    public void updateJobSeekerDaoGetByIdExceptionTest() {
        Mockito.when(jobSeekersDao.getJobSeekerById(any())).thenThrow(new RuntimeException());
        jobSeekersService.updateJobSeeker(getJobSeekerDto());
    }

    @Test
    public void deleteJobSeekerTest() {
        Mockito.when(jobSeekersDao.deleteJobSeeker(any())).thenReturn(1);
        CommonResponseDTO commonResponseDTO = jobSeekersService.deleteJobSeeker(999L);
        Assert.assertNotNull(commonResponseDTO);
        Assert.assertEquals(Constants.OK, commonResponseDTO.getReasonCode());
    }

    @Test(expected = UsersException.class)
    public void deleteJobSeekerExceptionTest() {
        Mockito.when(jobSeekersDao.deleteJobSeeker(any())).thenThrow(new RuntimeException());
        jobSeekersService.deleteJobSeeker(999L);
    }

    @Test
    public void approveJobSeekerTest() {
        Mockito.when(jobSeekersDao.approveJobSeeker(any())).thenReturn(1);
        Mockito.when(jobSeekersDao.getJobSeekerById(any())).thenReturn(getJobSeekersEntity());
        JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = jobSeekersService.approveJobSeeker(getJobSeekerDto());
        Assert.assertNotNull(jobSeekerGeneralResponseDTO);
        Assert.assertEquals(123L, jobSeekerGeneralResponseDTO.getJobSeeker().getUserId().longValue());
        Assert.assertEquals("TOLGA", jobSeekerGeneralResponseDTO.getJobSeeker().getFirstName());
        Assert.assertEquals("TEST", jobSeekerGeneralResponseDTO.getJobSeeker().getLastName());
    }

    @Test(expected = UsersException.class)
    public void approveJobSeekerExceptionTest() {
        Mockito.when(jobSeekersDao.approveJobSeeker(any())).thenThrow(new RuntimeException());
        jobSeekersService.approveJobSeeker(getJobSeekerDto());
    }

    @Test(expected = UsersException.class)
    public void approveJobSeekerGetByIdExceptionTest() {
        Mockito.when(jobSeekersDao.getJobSeekerById(any())).thenThrow(new RuntimeException());
        jobSeekersService.approveJobSeeker(getJobSeekerDto());
    }

    private JobSeekersEntity getJobSeekersEntity() {
        JobSeekersEntity jobSeekersEntity = new JobSeekersEntity();
        jobSeekersEntity.setUserId(123L);
        jobSeekersEntity.setFirstName("TOLGA");
        jobSeekersEntity.setLastName("TEST");
        return jobSeekersEntity;
    }

    private JobSeekerDTO getJobSeekerDto() {
        JobSeekerDTO jobSeekerDTO = new JobSeekerDTO();
        jobSeekerDTO.setUserId(123L);
        jobSeekerDTO.setFirstName("TOLGA");
        jobSeekerDTO.setLastName("TEST");
        return jobSeekerDTO;
    }

    private GetAllJobSeekersRequestDTO getAllJobSeekersRequestDTO() {
        GetAllJobSeekersRequestDTO getAllJobSeekersRequestDTO = new GetAllJobSeekersRequestDTO();
        PageableDTO pageableDTO = new PageableDTO();
        pageableDTO.setPageNumber(1L);
        pageableDTO.setPageSize(30L);
        pageableDTO.setSortColumn("");
        pageableDTO.setSortType("");
        getAllJobSeekersRequestDTO.setPageable(pageableDTO);
        return getAllJobSeekersRequestDTO;
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
