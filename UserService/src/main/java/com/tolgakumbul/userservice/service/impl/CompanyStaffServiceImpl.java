package com.tolgakumbul.userservice.service.impl;

import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.entity.CompanyStaff;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.mapper.CompanyStaffMapper;
import com.tolgakumbul.userservice.model.CompanyStaffDTO;
import com.tolgakumbul.userservice.service.CompanyStaffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyStaffServiceImpl implements CompanyStaffService {

    private static Logger LOGGER = LogManager.getLogger(CompanyStaffServiceImpl.class);

    private CompanyStaffMapper MAPPER = CompanyStaffMapper.INSTANCE;

    private final CompanyStaffDao companyStaffDao;

    public CompanyStaffServiceImpl(CompanyStaffDao companyStaffDao) {
        this.companyStaffDao = companyStaffDao;
    }

    @Override
    public List<CompanyStaffDTO> getAllCompanyStaff() {
        try {
            List<CompanyStaff> allCompanyStaff = companyStaffDao.getAllCompanyStaff();
            List<CompanyStaffDTO> companyStaffDTOList = allCompanyStaff.stream().map(MAPPER::toCompanyStaffDTO).collect(Collectors.toList());
            return companyStaffDTOList;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getAllCompanyStaff : {}", e.getMessage());
            throw new UsersException("ERRMSGCMPNY001");
        }

    }

    @Override
    public CompanyStaffDTO getCompanyStaffById(Long companyStaffId) {
        System.out.println(companyStaffId);
        return null;
    }

    @Override
    public CompanyStaffDTO getCompanyStaffByName(String firstName, String lastName) {
        System.out.println(firstName + " " + lastName);
        return null;
    }

    @Override
    public CompanyStaffDTO insertCompanyStaff(CompanyStaffDTO companyStaffDTO) {
        System.out.println(companyStaffDTO);
        return null;
    }
}
