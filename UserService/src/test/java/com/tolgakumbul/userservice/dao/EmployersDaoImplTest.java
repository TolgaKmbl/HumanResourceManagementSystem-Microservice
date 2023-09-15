package com.tolgakumbul.userservice.dao;

import com.tolgakumbul.userservice.dao.impl.EmployersDaoImpl;
import com.tolgakumbul.userservice.dao.model.EmployersByCompanyNameRequest;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.dao.model.Pageable;
import com.tolgakumbul.userservice.entity.EmployersEntity;
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

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployersDaoImplTest {

    @InjectMocks
    private EmployersDaoImpl employersDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private HazelcastCacheHelper hazelcastCacheHelper;

    private EmployersEntity employersEntity;
    private ListRequest listRequest;
    private EmployersByCompanyNameRequest employersByCompanyNameRequest;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        employersEntity = new EmployersEntity();
        employersEntity.setUserId(123L);
        employersEntity.setCompanyName("CompanyName");
        employersEntity.setWebsite("Test");
        employersEntity.setPhoneNum("123123123");
        employersEntity.setCompanyImgUrl("url");
        employersEntity.setIsDeleted("N");

        listRequest = new ListRequest();
        Pageable pageable = new Pageable();
        pageable.setPageNumber(3L);
        pageable.setPageSize(35L);
        pageable.setSortColumn("COMPANY_NAME");
        pageable.setSortType("ASC");
        listRequest.setPageable(pageable);

        employersByCompanyNameRequest = new EmployersByCompanyNameRequest();
        employersByCompanyNameRequest.setPageable(pageable);
        employersByCompanyNameRequest.setCompanyName("CompName");

        employersDao = new EmployersDaoImpl(jdbcTemplate, hazelcastCacheHelper);

    }

    @Test
    public void getAllEmployersTest() {
        String query = "SELECT * FROM EMPLOYERS ORDER BY COMPANY_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), (Object) any())).thenReturn(Collections.singletonList(employersEntity));

        List<EmployersEntity> allEmployers = employersDao.getAllEmployers(listRequest);

        Assert.assertNotNull(allEmployers);
        Assert.assertEquals(1, allEmployers.size());
        Assert.assertEquals("CompanyName", allEmployers.get(0).getCompanyName());
    }

    @Test
    public void getAllEmployersEmptyResultDataAccessExceptionTest() {
        String query = "SELECT * FROM EMPLOYERS ORDER BY COMPANY_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), (Object) any())).thenThrow(EmptyResultDataAccessException.class);

        List<EmployersEntity> allEmployers = employersDao.getAllEmployers(listRequest);

        Assert.assertNotNull(allEmployers);
        Assert.assertEquals(0, allEmployers.size());
    }

    @Test(expected = RuntimeException.class)
    public void getAllEmployersExceptionTest() {
        String query = "SELECT * FROM EMPLOYERS ORDER BY COMPANY_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), (Object) any())).thenThrow(RuntimeException.class);

        employersDao.getAllEmployers(listRequest);
    }

    @Test
    public void getEmployerByIdTest() {
        String query = "SELECT USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL, IS_DELETED FROM EMPLOYERS WHERE USER_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(Long.class))).thenReturn(employersEntity);

        EmployersEntity employerById = employersDao.getEmployerById(123L);

        Assert.assertNotNull(employerById);
        Assert.assertEquals("CompanyName", employerById.getCompanyName());
    }

    @Test(expected = RuntimeException.class)
    public void getEmployerByIdExceptionTest() {
        String query = "SELECT USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL, IS_DELETED FROM EMPLOYERS WHERE USER_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(Long.class))).thenThrow(RuntimeException.class);

        employersDao.getEmployerById(123L);
    }

    @Test
    public void getEmployerByIdEmptyResultDataAccessExceptionTest() {
        String query = "SELECT USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL, IS_DELETED FROM EMPLOYERS WHERE USER_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(Long.class))).thenThrow(EmptyResultDataAccessException.class);

        EmployersEntity employerById = employersDao.getEmployerById(123L);

        Assert.assertNotNull(employerById);
        Assert.assertNull(employerById.getCompanyName());
    }

    @Test
    public void getEmployersByCompanyNameTest() {
        String query = "SELECT USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL, IS_DELETED FROM EMPLOYERS WHERE COMPANY_NAME = ? ORDER BY COMPANY_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), (Object) any())).thenReturn(Collections.singletonList(employersEntity));

        List<EmployersEntity> compName = employersDao.getEmployersByCompanyName(employersByCompanyNameRequest);

        Assert.assertNotNull(compName);
        Assert.assertEquals(1, compName.size());
        Assert.assertEquals("CompanyName", compName.get(0).getCompanyName());

    }

    @Test(expected = RuntimeException.class)
    public void getEmployersByCompanyNameExceptionTest() {
        String query = "SELECT USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL, IS_DELETED FROM EMPLOYERS WHERE COMPANY_NAME = ? ORDER BY COMPANY_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), (Object) any())).thenThrow(RuntimeException.class);

        employersDao.getEmployersByCompanyName(employersByCompanyNameRequest);
    }

    @Test
    public void getEmployersByCompanyNameEmptyResultDataAccessExceptionTest() {
        String query = "SELECT USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL, IS_DELETED FROM EMPLOYERS WHERE COMPANY_NAME = ? ORDER BY COMPANY_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), (Object) any())).thenThrow(EmptyResultDataAccessException.class);

        List<EmployersEntity> compName = employersDao.getEmployersByCompanyName(employersByCompanyNameRequest);

        Assert.assertNotNull(compName);
        Assert.assertEquals(0, compName.size());
    }

    @Test
    public void insertEmployerTest() {
        String query = "INSERT INTO EMPLOYERS(USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT, IS_DELETED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenReturn(1);

        Integer integer = employersDao.insertEmployer(employersEntity);

        Assert.assertNotNull(integer);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void insertEmployerExceptionTest() {
        String query = "INSERT INTO EMPLOYERS(USER_ID, COMPANY_NAME, WEBSITE, PHONE_NUM, COMPANY_IMG_URL, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT, IS_DELETED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenThrow(RuntimeException.class);

        employersDao.insertEmployer(employersEntity);
    }

    @Test
    public void updateEmployerTest() {
        String query = "UPDATE EMPLOYERS SET COMPANY_NAME = ?, WEBSITE = ?, PHONE_NUM = ?, COMPANY_IMG_URL = ?, UPDATED_BY = ?, UPDATED_AT = ? WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenReturn(1);

        Integer integer = employersDao.updateEmployer(employersEntity);

        Assert.assertNotNull(integer);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void updateEmployerExceptionTest() {
        String query = "UPDATE EMPLOYERS SET COMPANY_NAME = ?, WEBSITE = ?, PHONE_NUM = ?, COMPANY_IMG_URL = ?, UPDATED_BY = ?, UPDATED_AT = ? WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenThrow(RuntimeException.class);

        employersDao.updateEmployer(employersEntity);
    }

    @Test
    public void deleteEmployerTest() {
        String query = "UPDATE EMPLOYERS SET IS_DELETED = ? WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenReturn(1);

        Integer integer = employersDao.deleteEmployer(123L);

        Assert.assertNotNull(integer);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void deleteEmployerExceptionTest() {
        String query = "UPDATE EMPLOYERS SET IS_DELETED = ? WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenThrow(RuntimeException.class);

        employersDao.deleteEmployer(123L);
    }

    @Test
    public void getLatestUserIdTest() {
        String query = "SELECT\n" +
                "    MAX(USER_ID)\n" +
                "FROM\n" +
                "    EMPLOYERS";

        when(jdbcTemplate.queryForObject(eq(query), any(Class.class))).thenReturn(1123L);

        Long latestUserId = employersDao.getLatestUserId();

        Assert.assertNotNull(latestUserId);
        Assert.assertEquals(1123L, latestUserId.longValue());
    }

    @Test
    public void getLatestUserIdExceptionTest() {
        String query = "SELECT\n" +
                "    MAX(USER_ID)\n" +
                "FROM\n" +
                "    EMPLOYERS";

        when(jdbcTemplate.queryForObject(eq(query), any(Class.class))).thenThrow(RuntimeException.class);

        Long latestUserId = employersDao.getLatestUserId();

        Assert.assertNotNull(latestUserId);
        Assert.assertEquals(0L, latestUserId.longValue());
    }

    @Test
    public void getTotalRowCountTest(){
        when(jdbcTemplate.queryForObject(eq("SELECT COUNT(*) FROM EMPLOYERS"), eq(Long.class))).thenReturn(14L);

        Long totalRowCount = employersDao.getTotalRowCount();

        Assert.assertNotNull(totalRowCount);
        Assert.assertEquals(14L, totalRowCount.longValue());
    }

    @Test
    public void getTotalRowCountExceptionTest(){
        when(jdbcTemplate.queryForObject(eq("SELECT COUNT(*) FROM EMPLOYERS"), eq(Long.class))).thenThrow(RuntimeException.class);

        Long totalRowCount = employersDao.getTotalRowCount();

        Assert.assertNotNull(totalRowCount);
        Assert.assertEquals(0L, totalRowCount.longValue());
    }

}
