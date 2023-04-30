package com.tolgakumbul.loggingservice.dao;

import com.tolgakumbul.loggingservice.dao.model.LoggingErrorObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoggingErrorObjectRepository extends MongoRepository<LoggingErrorObject, String> {
}
