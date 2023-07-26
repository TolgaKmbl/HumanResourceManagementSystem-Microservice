package com.tolgakumbul.userservice.service.impl;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.constants.ErrorCode;
import com.tolgakumbul.userservice.dao.EmployersDao;
import com.tolgakumbul.userservice.entity.EmployersEntity;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.helper.aspect.KafkaHelper;
import com.tolgakumbul.userservice.mapper.EmployersMapper;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersDTO;
import com.tolgakumbul.userservice.model.employers.EmployersGeneralResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersListResponseDTO;
import com.tolgakumbul.userservice.service.EmployersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployersServiceImpl implements EmployersService {

    private static final Logger LOGGER = LogManager.getLogger(EmployersServiceImpl.class);

    private final EmployersMapper MAPPER = EmployersMapper.INSTANCE;

    private final EmployersDao employersDao;

    public EmployersServiceImpl(EmployersDao employersDao) {
        this.employersDao = employersDao;
    }

    @Override
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.EMPLOYERS_KAFKA, operationName = Constants.GET_ALL)
    public EmployersListResponseDTO getAllEmployers() {
        try {
            List<EmployersEntity> allEmployers = employersDao.getAllEmployers();
            List<EmployersDTO> employersDTOList = allEmployers.stream().map(MAPPER::toEmployersDTO).collect(Collectors.toList());
            EmployersListResponseDTO employersListResponseDTO = new EmployersListResponseDTO(employersDTOList);

            return employersListResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.getAllEmployers : {}", e.getMessage());
            throw new UsersException(ErrorCode.ALL_EMPLOYERS_FETCH_ERROR, e.getMessage());
        }
    }

    @Override
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.EMPLOYERS_KAFKA, operationName = Constants.GET_BY_ID)
    public EmployersGeneralResponseDTO getEmployerById(Long employerId) {
        try {
            EmployersEntity employerById = employersDao.getEmployerById(employerId);
            EmployersDTO employersDTO = MAPPER.toEmployersDTO(employerById);
            EmployersGeneralResponseDTO employersGeneralResponseDTO = new EmployersGeneralResponseDTO(employersDTO);

            return employersGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.getEmployerById : {}", e.getMessage());
            throw new UsersException(ErrorCode.EMPLOYER_BY_ID_FETCH_ERROR, e.getMessage());
        }
    }

    @Override
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.EMPLOYERS_KAFKA, operationName = Constants.GET_BY_NAME)
    public EmployersListResponseDTO getEmployersByCompanyName(String companyName) {
        try {
            List<EmployersEntity> employersByCompanyName = employersDao.getEmployersByCompanyName(companyName);
            List<EmployersDTO> employersDTOList = employersByCompanyName.stream().map(MAPPER::toEmployersDTO).collect(Collectors.toList());
            EmployersListResponseDTO employersListResponseDTO = new EmployersListResponseDTO(employersDTOList);

            return employersListResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.getEmployersByCompanyName : {}", e.getMessage());
            throw new UsersException(ErrorCode.EMPLOYER_BY_COMPANY_NAME_FETCH_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.EMPLOYERS_KAFKA, operationName = Constants.UPDATE)
    public EmployersGeneralResponseDTO updateEmployer(EmployersDTO employersDTO) {
        try {
            employersDao.updateEmployer(MAPPER.toEmployersEntity(employersDTO));

            EmployersEntity employerById = employersDao.getEmployerById(employersDTO.getUserId());

            EmployersGeneralResponseDTO employersGeneralResponseDTO = new EmployersGeneralResponseDTO(MAPPER.toEmployersDTO(employerById));

            return employersGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.updateEmployer : {}", e.getMessage());
            throw new UsersException(ErrorCode.EMPLOYER_UPDATE_ERROR, employersDTO.getUserId(), e.getMessage());
        }
    }

    @Override
    @Transactional
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.EMPLOYERS_KAFKA, operationName = Constants.INSERT)
    public EmployersGeneralResponseDTO insertEmployer(EmployersDTO employersDTO) {
        try {
            employersDao.insertEmployer(MAPPER.toEmployersEntity(employersDTO));

            EmployersEntity employerById = employersDao.getEmployerById(employersDTO.getUserId());

            EmployersGeneralResponseDTO employersGeneralResponseDTO = new EmployersGeneralResponseDTO(MAPPER.toEmployersDTO(employerById));

            return employersGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.insertEmployer : {}", e.getMessage());
            throw new UsersException(ErrorCode.EMPLOYER_INSERT_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.EMPLOYERS_KAFKA, operationName = Constants.DELETE)
    public CommonResponseDTO deleteEmployer(Long employerId) {
        try {
            employersDao.deleteEmployer(employerId);

            CommonResponseDTO commonResponseDTO = new CommonResponseDTO(Constants.STATUS_OK, Constants.OK);

            return commonResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in EmployersServiceImpl.deleteEmployer : {}", e.getMessage());
            throw new UsersException(ErrorCode.EMPLOYER_DELETE_ERROR, e.getMessage());
        }
    }

}
