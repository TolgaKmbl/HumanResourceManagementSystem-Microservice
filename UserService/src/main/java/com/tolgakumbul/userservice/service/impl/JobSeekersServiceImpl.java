package com.tolgakumbul.userservice.service.impl;

import com.tolgakumbul.userservice.dao.JobSeekersDao;
import com.tolgakumbul.userservice.service.JobSeekersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class JobSeekersServiceImpl implements JobSeekersService {

    private static final Logger LOGGER = LogManager.getLogger(JobSeekersServiceImpl.class);

    private final JobSeekersDao jobSeekersDao;

    public JobSeekersServiceImpl(JobSeekersDao jobSeekersDao) {
        this.jobSeekersDao = jobSeekersDao;
    }

}
