package com.tolgakumbul.userservice.service.impl;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.constants.ErrorCode;
import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.CompanyStaffEntity;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.helper.aspect.KafkaHelper;
import com.tolgakumbul.userservice.mapper.CompanyStaffMapper;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.common.PaginationMetadataDTO;
import com.tolgakumbul.userservice.model.companystaff.*;
import com.tolgakumbul.userservice.service.CompanyStaffService;
import com.tolgakumbul.userservice.util.PaginationMetadataUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyStaffServiceImpl implements CompanyStaffService {

    private static final Logger LOGGER = LogManager.getLogger(CompanyStaffServiceImpl.class);

    private final CompanyStaffMapper MAPPER = CompanyStaffMapper.INSTANCE;

    private final CompanyStaffDao companyStaffDao;

    public CompanyStaffServiceImpl(CompanyStaffDao companyStaffDao) {
        this.companyStaffDao = companyStaffDao;
    }

    @Override
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.COMPANYSTAFF_KAFKA, operationName = Constants.GET_ALL)
    public CompanyStaffListResponseDTO getAllCompanyStaff(GetAllCompanyStaffRequestDTO requestDTO) {
        try {
            ListRequest listRequest = MAPPER.toListRequest(requestDTO);
            List<CompanyStaffEntity> allCompanyStaffEntity = companyStaffDao.getAllCompanyStaff(listRequest);
            List<CompanyStaffDTO> companyStaffDTOList = allCompanyStaffEntity.stream().map(MAPPER::toCompanyStaffDTO).collect(Collectors.toList());
            PaginationMetadataDTO paginationMetadata = PaginationMetadataUtil.getPaginationMetadata(getTotalRowCount(), requestDTO.getPageable());
            CompanyStaffListResponseDTO companyStaffListResponseDTO = new CompanyStaffListResponseDTO(companyStaffDTOList, paginationMetadata);

            return companyStaffListResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getAllCompanyStaff : {}", e.getMessage());
            throw new UsersException(ErrorCode.ALL_COMPANY_STAFF_FETCH_ERROR, e.getMessage());
        }

    }

    @Override
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.COMPANYSTAFF_KAFKA, operationName = Constants.GET_BY_ID)
    public CompanyStaffGeneralResponseDTO getCompanyStaffById(Long companyStaffId) {
        try {
            CompanyStaffEntity companyStaffEntityById = companyStaffDao.getCompanyStaffById(companyStaffId);
            CompanyStaffDTO companyStaffData = MAPPER.toCompanyStaffDTO(companyStaffEntityById);
            CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = new CompanyStaffGeneralResponseDTO(companyStaffData);

            return companyStaffGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getCompanyStaffById : {}", e.getMessage());
            throw new UsersException(ErrorCode.COMPANY_STAFF_BY_ID_FETCH_ERROR, e.getMessage());
        }
    }

    @Override
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.COMPANYSTAFF_KAFKA, operationName = Constants.GET_BY_NAME)
    public CompanyStaffGeneralResponseDTO getCompanyStaffByName(String firstName, String lastName) {
        try {
            CompanyStaffEntity companyStaffEntityByName = companyStaffDao.getCompanyStaffByName(firstName, lastName);
            CompanyStaffDTO companyStaffData = MAPPER.toCompanyStaffDTO(companyStaffEntityByName);
            CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = new CompanyStaffGeneralResponseDTO(companyStaffData);

            return companyStaffGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getCompanyStaffByName : {}", e.getMessage());
            throw new UsersException(ErrorCode.COMPANY_STAFF_BY_NAME_FETCH_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.COMPANYSTAFF_KAFKA, operationName = Constants.INSERT)
    public CompanyStaffGeneralResponseDTO insertCompanyStaff(CompanyStaffDTO companyStaffDTO) {
        try {
            Long latestUserId = companyStaffDao.getLatestUserId();
            companyStaffDTO.setUserId(++latestUserId);
            companyStaffDao.insertCompanyStaff(MAPPER.toCompanyStaff(companyStaffDTO));

            CompanyStaffDTO companyStaffByIdDTO = MAPPER.toCompanyStaffDTO(
                    companyStaffDao.getCompanyStaffById(companyStaffDTO.getUserId()));

            CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = new CompanyStaffGeneralResponseDTO(companyStaffByIdDTO);

            return companyStaffGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.insertCompanyStaff : {}", e.getMessage());
            throw new UsersException(ErrorCode.COMPANY_STAFF_INSERT_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.COMPANYSTAFF_KAFKA, operationName = Constants.UPDATE)
    public CompanyStaffGeneralResponseDTO updateCompanyStaff(CompanyStaffDTO companyStaffDTO) {
        try {
            companyStaffDao.updateCompanyStaff(MAPPER.toCompanyStaff(companyStaffDTO));
            CompanyStaffDTO companyStaffByIdDTO = MAPPER.toCompanyStaffDTO(
                    companyStaffDao.getCompanyStaffById(companyStaffDTO.getUserId()));

            CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = new CompanyStaffGeneralResponseDTO(companyStaffByIdDTO);

            return companyStaffGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.updateCompanyStaff : {}", e.getMessage());
            throw new UsersException(ErrorCode.COMPANY_STAFF_UPDATE_ERROR, companyStaffDTO.getUserId(), e.getMessage());
        }
    }

    @Override
    @Transactional
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.COMPANYSTAFF_KAFKA, operationName = Constants.DELETE)
    public CommonResponseDTO deleteCompanyStaff(Long companyStaffId) {
        try {
            companyStaffDao.deleteCompanyStaff(companyStaffId);
            //TODO: Return response like -> 1 row has been deleted / No rows has been deleted
            return new CommonResponseDTO(Constants.STATUS_OK, Constants.OK);
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.deleteCompanyStaff : {}", e.getMessage());
            throw new UsersException(ErrorCode.COMPANY_STAFF_DELETE_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.COMPANYSTAFF_KAFKA, operationName = Constants.APPROVE)
    public CompanyStaffGeneralResponseDTO approveCompanyStaff(Long companyStaffId) {
        try {
            CompanyStaffEntity companyStaffEntityById = companyStaffDao.getCompanyStaffById(companyStaffId);
            companyStaffEntityById.setIsApproved(IsApprovedEnum.ACTIVE.getTextType());
            companyStaffDao.approveCompanyStaff(companyStaffEntityById);

            CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = new CompanyStaffGeneralResponseDTO(MAPPER.toCompanyStaffDTO(companyStaffEntityById));

            return companyStaffGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.approveCompanyStaff : {}", e.getMessage());
            throw new UsersException(ErrorCode.COMPANY_STAFF_APPROVE_ERROR, e.getMessage());
        }
    }

    private Long getTotalRowCount(){
        return companyStaffDao.getTotalRowCount();
    }

}
