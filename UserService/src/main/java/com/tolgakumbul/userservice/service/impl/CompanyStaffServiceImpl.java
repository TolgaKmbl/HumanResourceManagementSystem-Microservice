package com.tolgakumbul.userservice.service.impl;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.entity.CompanyStaffEntity;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.mapper.CompanyStaffMapper;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffGeneralResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffListResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.IsApprovedEnum;
import com.tolgakumbul.userservice.service.CompanyStaffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

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
    public CompanyStaffListResponseDTO getAllCompanyStaff() {
        try {
            List<CompanyStaffEntity> allCompanyStaffEntity = companyStaffDao.getAllCompanyStaff();
            List<CompanyStaffDTO> companyStaffDTOList = allCompanyStaffEntity.stream().map(MAPPER::toCompanyStaffDTO).collect(Collectors.toList());
            final CommonResponseDTO commonResponseDTO = getCommonResponseDTO(companyStaffDTOList);
            return new CompanyStaffListResponseDTO(companyStaffDTOList, commonResponseDTO);
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getAllCompanyStaff : {}", e.getMessage());
            throw new UsersException("ERRMSGCMPNY001");
        }

    }

    @Override
    public CompanyStaffGeneralResponseDTO getCompanyStaffById(Long companyStaffId) {
        try {
            CompanyStaffEntity companyStaffEntityById = companyStaffDao.getCompanyStaffById(companyStaffId);
            final CommonResponseDTO commonResponseDTO = getCommonResponseDTO(companyStaffEntityById);
            CompanyStaffDTO companyStaffData = new CompanyStaffDTO(0L, "", "", IsApprovedEnum.PASSIVE, null);
            if(companyStaffEntityById != null){
                companyStaffData = MAPPER.toCompanyStaffDTO(companyStaffEntityById);
            }
            return new CompanyStaffGeneralResponseDTO(companyStaffData, commonResponseDTO);
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getCompanyStaffById : {}", e.getMessage());
            throw new UsersException("ERRMSGCMPNY002");
        }
    }

    @Override
    public CompanyStaffGeneralResponseDTO getCompanyStaffByName(String firstName, String lastName) {
        try {
            CompanyStaffEntity companyStaffEntityByName = companyStaffDao.getCompanyStaffByName(firstName, lastName);
            final CommonResponseDTO commonResponseDTO = getCommonResponseDTO(companyStaffEntityByName);
            CompanyStaffDTO companyStaffData = new CompanyStaffDTO(0L, "", "", IsApprovedEnum.PASSIVE, null);
            if(companyStaffEntityByName != null){
                companyStaffData = MAPPER.toCompanyStaffDTO(companyStaffEntityByName);
            }
            return new CompanyStaffGeneralResponseDTO(companyStaffData, commonResponseDTO);
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getCompanyStaffByName : {}", e.getMessage());
            throw new UsersException("ERRMSGCMPNY003");
        }
    }

    @Override
    @Transactional
    public CompanyStaffGeneralResponseDTO insertCompanyStaff(CompanyStaffDTO companyStaffDTO) {
        try {
            Integer affectedRowCount = companyStaffDao.insertCompanyStaff(MAPPER.toCompanyStaff(companyStaffDTO));
            final CommonResponseDTO commonResponseDTO;
            CompanyStaffDTO companyStaffByIdDTO = new CompanyStaffDTO(0L, "", "", IsApprovedEnum.PASSIVE, null);

            if (affectedRowCount == 1) {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_OK, Constants.OK);
                CompanyStaffEntity companyStaffEntityById = companyStaffDao.getCompanyStaffById(companyStaffDTO.getUserId());
                if (companyStaffEntityById != null) {
                    companyStaffByIdDTO = MAPPER.toCompanyStaffDTO(companyStaffEntityById);
                }
            } else {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_INTERNAL_ERROR, "Could not insert data!");
            }

            return new CompanyStaffGeneralResponseDTO(companyStaffByIdDTO, commonResponseDTO);
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.insertCompanyStaff : {}", e.getMessage());
            throw new UsersException("ERRMSGCMPNY004");
        }
    }

    @Override
    @Transactional
    public CommonResponseDTO deleteCompanyStaff(Long companyStaffId) {
        try {
            Integer affectedRowCount = companyStaffDao.deleteCompanyStaff(companyStaffId);
            final CommonResponseDTO commonResponseDTO;
            if (affectedRowCount == 1) {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_OK, Constants.OK);
            } else {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_INTERNAL_ERROR, "Could not delete data!");
            }
            return commonResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.deleteCompanyStaff : {}", e.getMessage());
            throw new UsersException("ERRMSGCMPNY005");
        }
    }

    @Override
    @Transactional
    public CompanyStaffGeneralResponseDTO approveCompanyStaff(Long companyStaffId) {
        try {
            Integer affectedRowCount = companyStaffDao.approveCompanyStaff(companyStaffId);
            final CommonResponseDTO commonResponseDTO;
            CompanyStaffDTO companyStaffByIdDTO = new CompanyStaffDTO(0L, "", "", IsApprovedEnum.PASSIVE, null);

            if (affectedRowCount == 1) {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_OK, Constants.OK);
                CompanyStaffEntity companyStaffEntityById = companyStaffDao.getCompanyStaffById(companyStaffId);
                if (companyStaffEntityById != null) {
                    companyStaffByIdDTO = MAPPER.toCompanyStaffDTO(companyStaffEntityById);
                }
            } else {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_INTERNAL_ERROR, "Could not update company staff!");
            }

            return new CompanyStaffGeneralResponseDTO(companyStaffByIdDTO, commonResponseDTO);
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.approveCompanyStaff : {}", e.getMessage());
            throw new UsersException("ERRMSGCMPNY006");
        }
    }


    private CommonResponseDTO getCommonResponseDTO(Object companyStaff) {
        boolean isList = companyStaff instanceof List;
        boolean isEmpty = isList ? CollectionUtils.isEmpty((List<?>) companyStaff) : ObjectUtils.isEmpty(companyStaff);

        int statusCode = isEmpty ? Constants.STATUS_INTERNAL_ERROR : Constants.STATUS_OK;
        String message = isEmpty ? (isList ? "List is empty" : "Could not fetch data by name!") : Constants.OK;

        return new CommonResponseDTO(statusCode, message);
    }

}
