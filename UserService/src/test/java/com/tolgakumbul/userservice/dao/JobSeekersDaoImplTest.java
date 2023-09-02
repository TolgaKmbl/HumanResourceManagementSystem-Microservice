package com.tolgakumbul.userservice.dao;

import com.tolgakumbul.userservice.dao.impl.JobSeekersDaoImpl;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.dao.model.Pageable;
import com.tolgakumbul.userservice.entity.JobSeekersEntity;
import com.tolgakumbul.userservice.helper.HazelcastCacheHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobSeekersDaoImplTest {


    @InjectMocks
    private JobSeekersDaoImpl jobSeekersDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private HazelcastCacheHelper hazelcastCacheHelper;

    private JobSeekersEntity jobSeekersEntity;
    private ListRequest listRequest;

    private LocalDateTime localDateTimeNow = LocalDateTime.now();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        jobSeekersEntity = new JobSeekersEntity();
        jobSeekersEntity.setUserId(123L);
        jobSeekersEntity.setCvId(456L);
        jobSeekersEntity.setFirstName("Tolga");
        jobSeekersEntity.setLastName("Test");
        jobSeekersEntity.setNationalId("123456789");
        jobSeekersEntity.setBirthDate(LocalDateTime.of(1992, 11, 1, 0, 0, 0, 0));
        jobSeekersEntity.setIsApproved("Y");
        jobSeekersEntity.setApprovalDate(localDateTimeNow);
        jobSeekersEntity.setIsDeleted("N");

        listRequest = new ListRequest();
        Pageable pageable = new Pageable();
        pageable.setPageNumber(3L);
        pageable.setPageSize(35L);
        pageable.setSortColumn("FIRST_NAME");
        pageable.setSortType("ASC");
        listRequest.setPageable(pageable);

        jobSeekersDao = new JobSeekersDaoImpl(jdbcTemplate, hazelcastCacheHelper);

    }

    @Test
    public void getAllJobSeekersTest() {
        String query = "SELECT * FROM JOB_SEEKERS ORDER BY FIRST_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), any(Object[].class))).thenReturn(Collections.singletonList(jobSeekersEntity));

        List<JobSeekersEntity> allJobSeekers = jobSeekersDao.getAllJobSeekers(listRequest);

        Assert.assertNotNull(allJobSeekers);
        Assert.assertEquals(1, allJobSeekers.size());
        Assert.assertEquals("Tolga", allJobSeekers.get(0).getFirstName());
    }

    @Test
    public void getAllJobSeekersEmptyResultDataAccessExceptionTest() {
        String query = "SELECT * FROM JOB_SEEKERS ORDER BY FIRST_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), any(Object[].class))).thenThrow(EmptyResultDataAccessException.class);

        List<JobSeekersEntity> allJobSeekers = jobSeekersDao.getAllJobSeekers(listRequest);

        Assert.assertNotNull(allJobSeekers);
        Assert.assertEquals(0, allJobSeekers.size());
    }

    @Test(expected = RuntimeException.class)
    public void getAllJobSeekersExceptionTest() {
        String query = "SELECT * FROM JOB_SEEKERS ORDER BY FIRST_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), any(Object[].class))).thenThrow(RuntimeException.class);

        jobSeekersDao.getAllJobSeekers(listRequest);
    }

    @Test
    public void getJobSeekerByIdTest() {
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM JOB_SEEKERS WHERE USER_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(Long.class))).thenReturn(jobSeekersEntity);

        JobSeekersEntity jobSeekerById = jobSeekersDao.getJobSeekerById(123L);

        Assert.assertNotNull(jobSeekerById);
        Assert.assertEquals("Tolga", jobSeekerById.getFirstName());
    }

    @Test(expected = RuntimeException.class)
    public void getJobSeekerByIdExceptionTest() {
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM JOB_SEEKERS WHERE USER_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(Long.class))).thenThrow(RuntimeException.class);

        jobSeekersDao.getJobSeekerById(123L);
    }

    @Test
    public void getJobSeekerByIdEmptyResultDataAccessExceptionTest() {
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM JOB_SEEKERS WHERE USER_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(Long.class))).thenThrow(EmptyResultDataAccessException.class);

        JobSeekersEntity jobSeekerById = jobSeekersDao.getJobSeekerById(123L);

        Assert.assertNotNull(jobSeekerById);
        Assert.assertNull(jobSeekerById.getFirstName());
    }

    @Test
    public void getJobSeekerByNationalIdTest() {
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM JOB_SEEKERS WHERE NATIONAL_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(String.class))).thenReturn(jobSeekersEntity);

        JobSeekersEntity jobSeekerByNationalId = jobSeekersDao.getJobSeekerByNationalId("123123");

        Assert.assertNotNull(jobSeekerByNationalId);
        Assert.assertEquals("Tolga", jobSeekerByNationalId.getFirstName());
    }

    @Test(expected = RuntimeException.class)
    public void getJobSeekerByNationalIdExceptionTest() {
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM JOB_SEEKERS WHERE NATIONAL_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(String.class))).thenThrow(RuntimeException.class);

        jobSeekersDao.getJobSeekerByNationalId("123123");
    }

    @Test
    public void getJobSeekerByNationalIdEmptyResultDataAccessExceptionTest() {
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM JOB_SEEKERS WHERE NATIONAL_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(String.class))).thenThrow(EmptyResultDataAccessException.class);

        JobSeekersEntity jobSeekerByNationalId = jobSeekersDao.getJobSeekerByNationalId("123123");

        Assert.assertNotNull(jobSeekerByNationalId);
        Assert.assertNull(jobSeekerByNationalId.getFirstName());
    }


    @Test
    public void getJobSeekerByNameTest() {
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM JOB_SEEKERS WHERE FIRST_NAME = ? AND LAST_NAME = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(String.class), any(String.class))).thenReturn(jobSeekersEntity);

        JobSeekersEntity jobSeekerByName = jobSeekersDao.getJobSeekerByName("Tolga", "Test");

        Assert.assertNotNull(jobSeekerByName);
        Assert.assertEquals("Tolga", jobSeekerByName.getFirstName());

    }

    @Test(expected = RuntimeException.class)
    public void getJobSeekerByNameExceptionTest() {
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM JOB_SEEKERS WHERE FIRST_NAME = ? AND LAST_NAME = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(String.class), any(String.class))).thenThrow(RuntimeException.class);

        jobSeekersDao.getJobSeekerByName("Tolga", "Test");
    }

    @Test
    public void getJobSeekerByNameEmptyResultDataAccessExceptionTest() {
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM JOB_SEEKERS WHERE FIRST_NAME = ? AND LAST_NAME = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(String.class), any(String.class))).thenThrow(EmptyResultDataAccessException.class);

        JobSeekersEntity jobSeekerByName = jobSeekersDao.getJobSeekerByName("Tolga", "Test");

        Assert.assertNotNull(jobSeekerByName);
        Assert.assertNull(jobSeekerByName.getFirstName());
    }

    @Test
    public void insertJobSeekerTest() {
        String query = "INSERT INTO JOB_SEEKERS(USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT, IS_DELETED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        when(jdbcTemplate.update(eq(query), any(Object[].class))).thenReturn(1);

        Integer integer = jobSeekersDao.insertJobSeeker(jobSeekersEntity);

        Assert.assertNotNull(integer);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void insertJobSeekerExceptionTest() {
        String query = "INSERT INTO JOB_SEEKERS(USER_ID, FIRST_NAME, LAST_NAME, NATIONAL_ID, BIRTH_DATE, CV_ID, IS_APPROVED, APPROVAL_DATE, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT, IS_DELETED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        when(jdbcTemplate.update(eq(query), any(Object[].class))).thenThrow(RuntimeException.class);

        jobSeekersDao.insertJobSeeker(jobSeekersEntity);
    }

    @Test
    public void updateJobSeekerTest() {
        String query = "UPDATE JOB_SEEKERS SET FIRST_NAME = ?, LAST_NAME = ?, NATIONAL_ID = ?, BIRTH_DATE = ?, CV_ID = ?, IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ?  WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), any(Object[].class))).thenReturn(1);

        Integer integer = jobSeekersDao.updateJobSeeker(jobSeekersEntity);

        Assert.assertNotNull(integer);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void updateJobSeekerExceptionTest() {
        String query = "UPDATE JOB_SEEKERS SET FIRST_NAME = ?, LAST_NAME = ?, NATIONAL_ID = ?, BIRTH_DATE = ?, CV_ID = ?, IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ?  WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), any(Object[].class))).thenThrow(RuntimeException.class);

        jobSeekersDao.updateJobSeeker(jobSeekersEntity);
    }

    @Test
    public void deleteJobSeekerTest() {
        String query = "UPDATE JOB_SEEKERS SET IS_DELETED = ? WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), any(Object[].class))).thenReturn(1);

        Integer integer = jobSeekersDao.deleteJobSeeker(123L);

        Assert.assertNotNull(integer);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void deleteJobSeekerExceptionTest() {
        String query = "UPDATE JOB_SEEKERS SET IS_DELETED = ? WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), any(Object[].class))).thenThrow(RuntimeException.class);

        jobSeekersDao.deleteJobSeeker(123L);
    }

    @Test
    public void approveJobSeekerTest() {
        String query = "UPDATE JOB_SEEKERS SET IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ?  WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), any(Object[].class))).thenReturn(1);

        Integer integer = jobSeekersDao.approveJobSeeker(jobSeekersEntity);

        Assert.assertNotNull(integer);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void approveJobSeekerExceptionTest() {
        String query = "UPDATE JOB_SEEKERS SET IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ?  WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), any(Object[].class))).thenThrow(RuntimeException.class);

        jobSeekersDao.approveJobSeeker(jobSeekersEntity);
    }

    @Test
    public void getLatestUserIdTest() {
        String query = "SELECT\n" +
                "    MAX(USER_ID)\n" +
                "FROM\n" +
                "    JOB_SEEKERS";

        when(jdbcTemplate.queryForObject(eq(query), any(Class.class))).thenReturn(1123L);

        Long latestUserId = jobSeekersDao.getLatestUserId();

        Assert.assertNotNull(latestUserId);
        Assert.assertEquals(1123L, latestUserId.longValue());
    }

    @Test
    public void getLatestUserIdExceptionTest() {
        String query = "SELECT\n" +
                "    MAX(USER_ID)\n" +
                "FROM\n" +
                "    JOB_SEEKERS";

        when(jdbcTemplate.queryForObject(eq(query), any(Class.class))).thenThrow(RuntimeException.class);

        Long latestUserId = jobSeekersDao.getLatestUserId();

        Assert.assertNotNull(latestUserId);
        Assert.assertEquals(0L, latestUserId.longValue());
    }

}
