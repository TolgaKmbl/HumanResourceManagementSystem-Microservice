package com.tolgakumbul.userservice.service.impl;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.entity.CompanyStaffEntity;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.helper.KafkaProducerHelper;
import com.tolgakumbul.userservice.helper.model.kafka.KafkaProducerModel;
import com.tolgakumbul.userservice.mapper.CompanyStaffMapper;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffGeneralResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffListResponseDTO;
import com.tolgakumbul.userservice.service.CompanyStaffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyStaffServiceImpl implements CompanyStaffService {

    private static final Logger LOGGER = LogManager.getLogger(CompanyStaffServiceImpl.class);
    private static final String entityNameForKafka = "COMPANYSTAFF";

    private final CompanyStaffMapper MAPPER = CompanyStaffMapper.INSTANCE;

    private final CompanyStaffDao companyStaffDao;
    private final KafkaProducerHelper kafkaProducerHelper;

    @Value("${kafka.topic.userservicegeneral.name}")
    private String companyStaffGeneralResponseTopic;

    public CompanyStaffServiceImpl(CompanyStaffDao companyStaffDao, KafkaProducerHelper kafkaProducerHelper) {
        this.companyStaffDao = companyStaffDao;
        this.kafkaProducerHelper = kafkaProducerHelper;
    }

    @Override
    public CompanyStaffListResponseDTO getAllCompanyStaff() {
        try {
            List<CompanyStaffEntity> allCompanyStaffEntity = companyStaffDao.getAllCompanyStaff();
            List<CompanyStaffDTO> companyStaffDTOList = allCompanyStaffEntity.stream().map(MAPPER::toCompanyStaffDTO).collect(Collectors.toList());
            final CommonResponseDTO commonResponseDTO = getCommonResponseDTO(companyStaffDTOList);
            CompanyStaffListResponseDTO companyStaffListResponseDTO = new CompanyStaffListResponseDTO(companyStaffDTOList, commonResponseDTO);

            sendKafkaTopic(null, companyStaffListResponseDTO, "GETALL");

            return companyStaffListResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getAllCompanyStaff : {}", e.getMessage());
            sendKafkaTopicForError(null, "GETALL", e.getMessage());
            throw new UsersException("ERRMSGCMPNY001");
        }

    }

    @Override
    public CompanyStaffGeneralResponseDTO getCompanyStaffById(Long companyStaffId) {
        try {
            CompanyStaffEntity companyStaffEntityById = companyStaffDao.getCompanyStaffById(companyStaffId);
            final CommonResponseDTO commonResponseDTO = getCommonResponseDTO(companyStaffEntityById);
            CompanyStaffDTO companyStaffData = MAPPER.toCompanyStaffDTO(companyStaffEntityById);
            CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = new CompanyStaffGeneralResponseDTO(companyStaffData, commonResponseDTO);

            sendKafkaTopic(companyStaffId, companyStaffGeneralResponseDTO, "GETBYID");

            return companyStaffGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getCompanyStaffById : {}", e.getMessage());
            sendKafkaTopicForError(companyStaffId, "GETBYID", e.getMessage());
            throw new UsersException("ERRMSGCMPNY002");
        }
    }

    @Override
    public CompanyStaffGeneralResponseDTO getCompanyStaffByName(String firstName, String lastName) {
        try {
            CompanyStaffEntity companyStaffEntityByName = companyStaffDao.getCompanyStaffByName(firstName, lastName);
            final CommonResponseDTO commonResponseDTO = getCommonResponseDTO(companyStaffEntityByName);
            CompanyStaffDTO companyStaffData = MAPPER.toCompanyStaffDTO(companyStaffEntityByName);
            CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = new CompanyStaffGeneralResponseDTO(companyStaffData, commonResponseDTO);

            sendKafkaTopic(firstName+" "+lastName, companyStaffGeneralResponseDTO, "GETBYNAME");

            return companyStaffGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.getCompanyStaffByName : {}", e.getMessage());
            sendKafkaTopicForError(firstName+" "+lastName, "GETBYNAME", e.getMessage());
            throw new UsersException("ERRMSGCMPNY003");
        }
    }

    @Override
    @Transactional
    public CompanyStaffGeneralResponseDTO insertCompanyStaff(CompanyStaffDTO companyStaffDTO) {
        try {
            Integer affectedRowCount = companyStaffDao.insertCompanyStaff(MAPPER.toCompanyStaff(companyStaffDTO));
            final CommonResponseDTO commonResponseDTO;
            CompanyStaffDTO companyStaffByIdDTO = new CompanyStaffDTO();
            if (affectedRowCount == 1) {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_OK, Constants.OK);
                CompanyStaffEntity companyStaffEntityById = companyStaffDao.getCompanyStaffById(companyStaffDTO.getUserId());
                companyStaffByIdDTO = MAPPER.toCompanyStaffDTO(companyStaffEntityById);
            } else {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_INTERNAL_ERROR, "Could not insert data!");
            }

            CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = new CompanyStaffGeneralResponseDTO(companyStaffByIdDTO, commonResponseDTO);

            sendKafkaTopic(companyStaffDTO, companyStaffGeneralResponseDTO, "INSERT");

            return companyStaffGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.insertCompanyStaff : {}", e.getMessage());
            sendKafkaTopicForError(companyStaffDTO, "INSERT", e.getMessage());
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

            sendKafkaTopic(companyStaffId, commonResponseDTO, "DELETE");

            return commonResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.deleteCompanyStaff : {}", e.getMessage());
            sendKafkaTopicForError(companyStaffId, "DELETE", e.getMessage());
            throw new UsersException("ERRMSGCMPNY005");
        }
    }

    @Override
    @Transactional
    public CompanyStaffGeneralResponseDTO approveCompanyStaff(Long companyStaffId) {
        try {
            Integer affectedRowCount = companyStaffDao.approveCompanyStaff(companyStaffId);
            final CommonResponseDTO commonResponseDTO;
            CompanyStaffDTO companyStaffByIdDTO = new CompanyStaffDTO();

            if (affectedRowCount == 1) {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_OK, Constants.OK);
                CompanyStaffEntity companyStaffEntityById = companyStaffDao.getCompanyStaffById(companyStaffId);
                companyStaffByIdDTO = MAPPER.toCompanyStaffDTO(companyStaffEntityById);
            } else {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_INTERNAL_ERROR, "Could not update company staff!");
            }

            CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO = new CompanyStaffGeneralResponseDTO(companyStaffByIdDTO, commonResponseDTO);

            sendKafkaTopic(companyStaffId, companyStaffGeneralResponseDTO, "APPROVE");

            return companyStaffGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in CompanyStaffServiceImpl.approveCompanyStaff : {}", e.getMessage());
            sendKafkaTopicForError(companyStaffId, "APPROVE", e.getMessage());
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


    private void sendKafkaTopic(Object request, Object response, String operationName) {
        KafkaProducerModel kafkaProducerModel = kafkaProducerHelper.generateKafkaProducerModel(companyStaffGeneralResponseTopic,
                entityNameForKafka+"-"+operationName,
                request,
                response,
                operationName,
                entityNameForKafka,
                "");
        kafkaProducerHelper.send(kafkaProducerModel);
    }

    private void sendKafkaTopicForError(Object request, String operationName, String errorMessage) {
        KafkaProducerModel kafkaProducerModel = kafkaProducerHelper.generateKafkaProducerModel(companyStaffGeneralResponseTopic,
                entityNameForKafka+"-"+operationName,
                request,
                null,
                operationName,
                entityNameForKafka,
                errorMessage);
        kafkaProducerHelper.send(kafkaProducerModel);
    }

}
