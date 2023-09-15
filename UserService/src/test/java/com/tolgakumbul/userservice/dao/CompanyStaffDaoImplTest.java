package com.tolgakumbul.userservice.dao;

import com.tolgakumbul.userservice.dao.impl.CompanyStaffDaoImpl;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.dao.model.Pageable;
import com.tolgakumbul.userservice.entity.CompanyStaffEntity;
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
public class CompanyStaffDaoImplTest {

    @InjectMocks
    private CompanyStaffDaoImpl companyStaffDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private HazelcastCacheHelper hazelcastCacheHelper;

    private CompanyStaffEntity companyStaffEntity;
    private ListRequest listRequest;

    private LocalDateTime localDateTimeNow = LocalDateTime.now();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        companyStaffEntity = new CompanyStaffEntity();
        companyStaffEntity.setUserId(123L);
        companyStaffEntity.setFirstName("Tolga");
        companyStaffEntity.setLastName("Test");
        companyStaffEntity.setIsApproved("Y");
        companyStaffEntity.setApprovalDate(localDateTimeNow);
        companyStaffEntity.setIsDeleted("N");

        listRequest = new ListRequest();
        Pageable pageable = new Pageable();
        pageable.setPageNumber(3L);
        pageable.setPageSize(35L);
        pageable.setSortColumn("FIRST_NAME");
        pageable.setSortType("ASC");
        listRequest.setPageable(pageable);

        companyStaffDao = new CompanyStaffDaoImpl(jdbcTemplate, hazelcastCacheHelper);

    }

    @Test
    public void getAllCompanyStaffTest() {
        String query = "SELECT * FROM COMPANY_STAFF ORDER BY FIRST_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), (Object) any())).thenReturn(Collections.singletonList(companyStaffEntity));

        List<CompanyStaffEntity> allCompanyStaff = companyStaffDao.getAllCompanyStaff(listRequest);

        Assert.assertNotNull(allCompanyStaff);
        Assert.assertEquals(1, allCompanyStaff.size());
        Assert.assertEquals("Tolga", allCompanyStaff.get(0).getFirstName());
    }

    @Test
    public void getAllCompanyStaffEmptyResultDataAccessExceptionTest(){
        String query = "SELECT * FROM COMPANY_STAFF ORDER BY FIRST_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), (Object) any())).thenThrow(EmptyResultDataAccessException.class);

        List<CompanyStaffEntity> allCompanyStaff = companyStaffDao.getAllCompanyStaff(listRequest);

        Assert.assertNotNull(allCompanyStaff);
        Assert.assertEquals(0, allCompanyStaff.size());
    }

    @Test(expected = RuntimeException.class)
    public void getAllCompanyStaffExceptionTest(){
        String query = "SELECT * FROM COMPANY_STAFF ORDER BY FIRST_NAME ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        when(jdbcTemplate.query(eq(query), any(RowMapper.class), (Object) any())).thenThrow(RuntimeException.class);

        companyStaffDao.getAllCompanyStaff(listRequest);
    }

    @Test
    public void getCompanyStaffByIdTest() {
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM COMPANY_STAFF WHERE USER_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(Long.class))).thenReturn(companyStaffEntity);

        CompanyStaffEntity companyStaffById = companyStaffDao.getCompanyStaffById(123L);

        Assert.assertNotNull(companyStaffById);
        Assert.assertEquals("Tolga", companyStaffById.getFirstName());
    }

    @Test(expected = RuntimeException.class)
    public void getCompanyStaffByIdExceptionTest(){
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM COMPANY_STAFF WHERE USER_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(Long.class))).thenThrow(RuntimeException.class);

        companyStaffDao.getCompanyStaffById(123L);
    }

    @Test
    public void getCompanyStaffByIdEmptyResultDataAccessExceptionTest(){
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM COMPANY_STAFF WHERE USER_ID = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(Long.class))).thenThrow(EmptyResultDataAccessException.class);

        CompanyStaffEntity companyStaffById = companyStaffDao.getCompanyStaffById(123L);

        Assert.assertNotNull(companyStaffById);
        Assert.assertNull(companyStaffById.getFirstName());
    }

    @Test
    public void getCompanyStaffByNameTest() {
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM COMPANY_STAFF WHERE FIRST_NAME = ? AND LAST_NAME = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(String.class), any(String.class))).thenReturn(companyStaffEntity);

        CompanyStaffEntity companyStaffByName = companyStaffDao.getCompanyStaffByName("Tolga", "Test");

        Assert.assertNotNull(companyStaffByName);
        Assert.assertEquals("Tolga", companyStaffByName.getFirstName());

    }

    @Test(expected = RuntimeException.class)
    public void getCompanyStaffByNameExceptionTest(){
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM COMPANY_STAFF WHERE FIRST_NAME = ? AND LAST_NAME = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(String.class), any(String.class))).thenThrow(RuntimeException.class);

        companyStaffDao.getCompanyStaffByName("Tolga", "Test");
    }

    @Test
    public void getCompanyStaffByNameEmptyResultDataAccessExceptionTest(){
        String query = "SELECT USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE, IS_DELETED FROM COMPANY_STAFF WHERE FIRST_NAME = ? AND LAST_NAME = ?";
        when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), any(String.class), any(String.class))).thenThrow(EmptyResultDataAccessException.class);

        CompanyStaffEntity companyStaffByName = companyStaffDao.getCompanyStaffByName("Tolga", "Test");

        Assert.assertNotNull(companyStaffByName);
        Assert.assertNull(companyStaffByName.getFirstName());
    }

    @Test
    public void insertCompanyStaffTest() {
        String query = "INSERT INTO COMPANY_STAFF(USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT, IS_DELETED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenReturn(1);

        Integer integer = companyStaffDao.insertCompanyStaff(companyStaffEntity);

        Assert.assertNotNull(integer);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void insertCompanyStaffExceptionTest(){
        String query = "INSERT INTO COMPANY_STAFF(USER_ID, FIRST_NAME, LAST_NAME, IS_APPROVED, APPROVAL_DATE, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT, IS_DELETED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenThrow(RuntimeException.class);

        companyStaffDao.insertCompanyStaff(companyStaffEntity);
    }

    @Test
    public void updateCompanyStaffTest() {
        String query = "UPDATE COMPANY_STAFF SET FIRST_NAME = ?, LAST_NAME = ?, IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ?WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenReturn(1);

        Integer integer = companyStaffDao.updateCompanyStaff(companyStaffEntity);

        Assert.assertNotNull(integer);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void updateCompanyStaffExceptionTest(){
        String query = "UPDATE COMPANY_STAFF SET FIRST_NAME = ?, LAST_NAME = ?, IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ?WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenThrow(RuntimeException.class);

        companyStaffDao.updateCompanyStaff(companyStaffEntity);
    }


    @Test
    public void deleteCompanyStaffTest() {
        String query = "UPDATE COMPANY_STAFF SET IS_DELETED = ? WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenReturn(1);

        Integer integer = companyStaffDao.deleteCompanyStaff(123L);

        Assert.assertNotNull(integer);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void deleteCompanyStaffExceptionTest(){
        String query = "UPDATE COMPANY_STAFF SET IS_DELETED = ? WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenThrow(RuntimeException.class);

        companyStaffDao.deleteCompanyStaff(123L);
    }

    @Test
    public void approveCompanyStaffTest() {
        String query = "UPDATE COMPANY_STAFF SET IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ? WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenReturn(1);

        Integer integer = companyStaffDao.approveCompanyStaff(companyStaffEntity);

        Assert.assertNotNull(integer);
        Assert.assertEquals(1, integer.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void approveCompanyStaffExceptionTest(){
        String query = "UPDATE COMPANY_STAFF SET IS_APPROVED = ?, APPROVAL_DATE = ?, UPDATED_BY = ?, UPDATED_AT = ? WHERE USER_ID = ?";
        when(jdbcTemplate.update(eq(query), (Object) any())).thenThrow(RuntimeException.class);

        companyStaffDao.approveCompanyStaff(companyStaffEntity);
    }

    @Test
    public void getLatestUserIdTest() {
        String query = "SELECT\n" +
                "    MAX(USER_ID)\n" +
                "FROM\n" +
                "    COMPANY_STAFF";

        when(jdbcTemplate.queryForObject(eq(query), any(Class.class))).thenReturn(1123L);

        Long latestUserId = companyStaffDao.getLatestUserId();

        Assert.assertNotNull(latestUserId);
        Assert.assertEquals(1123L, latestUserId.longValue());
    }

    @Test
    public void getLatestUserIdExceptionTest(){
        String query = "SELECT\n" +
                "    MAX(USER_ID)\n" +
                "FROM\n" +
                "    COMPANY_STAFF";

        when(jdbcTemplate.queryForObject(eq(query), any(Class.class))).thenThrow(RuntimeException.class);

        Long latestUserId = companyStaffDao.getLatestUserId();

        Assert.assertNotNull(latestUserId);
        Assert.assertEquals(0L, latestUserId.longValue());
    }

    @Test
    public void getTotalRowCountTest(){
        when(jdbcTemplate.queryForObject(eq("SELECT COUNT(*) FROM COMPANY_STAFF"), eq(Long.class))).thenReturn(14L);

        Long totalRowCount = companyStaffDao.getTotalRowCount();

        Assert.assertNotNull(totalRowCount);
        Assert.assertEquals(14L, totalRowCount.longValue());
    }

    @Test
    public void getTotalRowCountExceptionTest(){
        when(jdbcTemplate.queryForObject(eq("SELECT COUNT(*) FROM COMPANY_STAFF"), eq(Long.class))).thenThrow(RuntimeException.class);

        Long totalRowCount = companyStaffDao.getTotalRowCount();

        Assert.assertNotNull(totalRowCount);
        Assert.assertEquals(0L, totalRowCount.longValue());
    }
}