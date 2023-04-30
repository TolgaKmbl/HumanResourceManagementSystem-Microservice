package com.tolgakumbul.loggingservice.dao;

import com.tolgakumbul.loggingservice.dao.model.LoggingObjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingObjectRepository extends MongoRepository<LoggingObjectEntity, String> {
}
