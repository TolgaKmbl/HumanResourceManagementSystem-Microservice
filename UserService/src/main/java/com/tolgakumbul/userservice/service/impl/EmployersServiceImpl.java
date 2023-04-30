package com.tolgakumbul.userservice.service.impl;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.dao.EmployersDao;
import com.tolgakumbul.userservice.entity.EmployersEntity;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.helper.KafkaProducerHelper;
import com.tolgakumbul.userservice.helper.model.kafka.KafkaProducerModel;
import com.tolgakumbul.userservice.mapper.EmployersMapper;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersDTO;
import com.tolgakumbul.userservice.model.employers.EmployersGeneralResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersListResponseDTO;
import com.tolgakumbul.userservice.service.EmployersService;
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
public class EmployersServiceImpl implements EmployersService {

    private static final Logger LOGGER = LogManager.getLogger(EmployersServiceImpl.class);
    private static final String entityNameForKafka = "EMPLOYERS";

    private final EmployersMapper MAPPER = EmployersMapper.INSTANCE;

    private final EmployersDao employersDao;
    private final KafkaProducerHelper kafkaProducerHelper;

    @Value("${kafka.topic.userservicegeneral.name}")
    private String userServiceTopic;

    public EmployersServiceImpl(EmployersDao employersDao, KafkaProducerHelper kafkaProducerHelper) {
        this.employersDao = employersDao;
        this.kafkaProducerHelper = kafkaProducerHelper;
    }

    @Override
    public EmployersListResponseDTO getAllEmployers() {
        try {
            List<EmployersEntity> allEmployers = employersDao.getAllEmployers();
            List<EmployersDTO> employersDTOList = allEmployers.stream().map(MAPPER::toEmployersDTO).collect(Collectors.toList());
            final CommonResponseDTO commonResponseDTO = getCommonResponseDTO(employersDTOList);
            EmployersListResponseDTO employersListResponseDTO = new EmployersListResponseDTO(employersDTOList, commonResponseDTO);

            sendKafkaTopic(null, employersListResponseDTO, "GETALL");

            return employersListResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.getAllEmployers : {}", e.getMessage());
            sendKafkaTopicForError(null, "GETALL", e.getMessage());
            throw new UsersException("ERRMSGEMPLYRS001");
        }
    }

    @Override
    public EmployersGeneralResponseDTO getEmployerById(Long employerId) {
        try {
            EmployersEntity employerById = employersDao.getEmployerById(employerId);
            final CommonResponseDTO commonResponseDTO = getCommonResponseDTO(employerById);
            EmployersDTO employersDTO = MAPPER.toEmployersDTO(employerById);
            EmployersGeneralResponseDTO employersGeneralResponseDTO = new EmployersGeneralResponseDTO(employersDTO, commonResponseDTO);

            sendKafkaTopic(employerId, employersGeneralResponseDTO, "GETBYID");

            return employersGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.getEmployerById : {}", e.getMessage());
            sendKafkaTopicForError(employerId, "GETBYID", e.getMessage());
            throw new UsersException("ERRMSGEMPLYRS002");
        }
    }

    @Override
    public EmployersListResponseDTO getEmployersByCompanyName(String companyName) {
        try {
            List<EmployersEntity> employersByCompanyName = employersDao.getEmployersByCompanyName(companyName);
            final CommonResponseDTO commonResponseDTO = getCommonResponseDTO(employersByCompanyName);
            List<EmployersDTO> employersDTOList = employersByCompanyName.stream().map(MAPPER::toEmployersDTO).collect(Collectors.toList());
            EmployersListResponseDTO employersListResponseDTO = new EmployersListResponseDTO(employersDTOList, commonResponseDTO);

            sendKafkaTopic(companyName, employersListResponseDTO, "GETBYNAME");

            return employersListResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.getEmployersByCompanyName : {}", e.getMessage());
            sendKafkaTopicForError(companyName, "GETBYNAME", e.getMessage());
            throw new UsersException("ERRMSGEMPLYRS003");
        }
    }

    @Override
    @Transactional
    public EmployersGeneralResponseDTO updateEmployer(EmployersDTO employersDTO) {
        try {
            Integer affectedRowCount = employersDao.updateEmployer(MAPPER.toEmployersEntity(employersDTO));
            final CommonResponseDTO commonResponseDTO;
            EmployersDTO employerByIdDTO = new EmployersDTO();
            if (affectedRowCount == 1) {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_OK, Constants.OK);
                EmployersEntity employerById = employersDao.getEmployerById(employersDTO.getUserId());
                employerByIdDTO = MAPPER.toEmployersDTO(employerById);
            } else {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_INTERNAL_ERROR, "Could not update data!");
            }

            EmployersGeneralResponseDTO employersGeneralResponseDTO = new EmployersGeneralResponseDTO(employerByIdDTO, commonResponseDTO);

            sendKafkaTopic(employersDTO, employersGeneralResponseDTO, "UPDATE");

            return employersGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.updateEmployer : {}", e.getMessage());
            sendKafkaTopicForError(employersDTO, "UPDATE", e.getMessage());
            throw new UsersException("ERRMSGEMPLYRS006", employersDTO.getUserId());
        }
    }

    @Override
    @Transactional
    public EmployersGeneralResponseDTO insertEmployer(EmployersDTO employersDTO) {
        try {
            Integer affectedRowCount = employersDao.insertEmployer(MAPPER.toEmployersEntity(employersDTO));
            final CommonResponseDTO commonResponseDTO;
            EmployersDTO employerByIdDTO = new EmployersDTO();
            if (affectedRowCount == 1) {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_OK, Constants.OK);
                EmployersEntity employerById = employersDao.getEmployerById(employersDTO.getUserId());
                employerByIdDTO = MAPPER.toEmployersDTO(employerById);
            } else {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_INTERNAL_ERROR, "Could not insert data!");
            }

            EmployersGeneralResponseDTO employersGeneralResponseDTO = new EmployersGeneralResponseDTO(employerByIdDTO, commonResponseDTO);

            sendKafkaTopic(employersDTO, employersGeneralResponseDTO, "INSERT");

            return employersGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.insertEmployer : {}", e.getMessage());
            sendKafkaTopicForError(employersDTO, "INSERT", e.getMessage());
            throw new UsersException("ERRMSGEMPLYRS004");
        }
    }

    @Override
    @Transactional
    public CommonResponseDTO deleteEmployer(Long employerId) {
        try {
            Integer affectedRowCount = employersDao.deleteEmployer(employerId);
            final CommonResponseDTO commonResponseDTO;
            if (affectedRowCount == 1) {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_OK, Constants.OK);
            } else {
                commonResponseDTO = new CommonResponseDTO(Constants.STATUS_INTERNAL_ERROR, "Could not delete data!");
            }

            sendKafkaTopic(employerId, commonResponseDTO, "DELETE");

            return commonResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.deleteEmployer : {}", e.getMessage());
            sendKafkaTopicForError(employerId, "DELETE", e.getMessage());
            throw new UsersException("ERRMSGEMPLYRS005");
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
        KafkaProducerModel kafkaProducerModel = kafkaProducerHelper.generateKafkaProducerModel(userServiceTopic,
                entityNameForKafka + "-" + operationName,
                request,
                response,
                operationName,
                entityNameForKafka,
                "");
        kafkaProducerHelper.send(kafkaProducerModel);
    }

    private void sendKafkaTopicForError(Object request, String operationName, String errorMessage) {
        KafkaProducerModel kafkaProducerModel = kafkaProducerHelper.generateKafkaProducerModel(userServiceTopic,
                entityNameForKafka + "-" + operationName,
                request,
                null,
                operationName,
                entityNameForKafka,
                errorMessage);
        kafkaProducerHelper.send(kafkaProducerModel);
    }

}
