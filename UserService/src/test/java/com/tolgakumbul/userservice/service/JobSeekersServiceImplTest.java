package com.tolgakumbul.userservice.service;

import com.tolgakumbul.userservice.dao.JobSeekersDao;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.dao.model.Pageable;
import com.tolgakumbul.userservice.entity.JobSeekersEntity;
import com.tolgakumbul.userservice.model.common.PageableDTO;
import com.tolgakumbul.userservice.model.jobseekers.GetAllJobSeekersRequestDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerDTO;
import com.tolgakumbul.userservice.service.impl.JobSeekersServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    private JobSeekersEntity getJobSeekersEntity() {
        JobSeekersEntity jobSeekersEntity = new JobSeekersEntity();
        jobSeekersEntity.setUserId(123L);
        jobSeekersEntity.setFirstName("TOLGA");
        jobSeekersEntity.setLastName("TEST");
        return jobSeekersEntity;
    }

    private JobSeekerDTO getEmployersDTO() {
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
