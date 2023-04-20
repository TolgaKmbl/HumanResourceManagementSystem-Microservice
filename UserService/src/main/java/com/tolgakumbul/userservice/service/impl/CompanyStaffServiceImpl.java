package com.tolgakumbul.userservice.service.impl;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.entity.CompanyStaff;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.mapper.CompanyStaffMapper;
import com.tolgakumbul.userservice.model.CommonResponseDTO;
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
        try {
            CompanyStaff companyStaffById = companyStaffDao.getCompanyStaffById(companyStaffId);
            return MAPPER.toCompanyStaffDTO(companyStaffById);
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getCompanyStaffById : {}", e.getMessage());
            throw new UsersException("ERRMSGCMPNY002");
        }
    }

    @Override
    public CompanyStaffDTO getCompanyStaffByName(String firstName, String lastName) {
        try {
            CompanyStaff companyStaffByName = companyStaffDao.getCompanyStaffByName(firstName, lastName);
            return MAPPER.toCompanyStaffDTO(companyStaffByName);
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getCompanyStaffByName : {}", e.getMessage());
            throw new UsersException("ERRMSGCMPNY003");
        }
    }

    @Override
    public CommonResponseDTO insertCompanyStaff(CompanyStaffDTO companyStaffDTO) {
        try {
            CommonResponseDTO commonResponseDTO = companyStaffDao.insertCompanyStaff(MAPPER.toCompanyStaff(companyStaffDTO));
            return commonResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.insertCompanyStaff : {}", e.getMessage());
            throw new UsersException("ERRMSGCMPNY004");
        }
    }
}
