package com.tolgakumbul.emailservice.dao;

import com.tolgakumbul.emailservice.entity.EmailActivationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailActivationRepository extends JpaRepository<EmailActivationEntity, Integer> {


}
