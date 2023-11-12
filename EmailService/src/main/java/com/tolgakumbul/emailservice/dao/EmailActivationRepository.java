package com.tolgakumbul.emailservice.dao;

import com.tolgakumbul.emailservice.entity.EmailActivationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailActivationRepository extends JpaRepository<EmailActivationEntity, Integer> {

    Optional<EmailActivationEntity> findByUserId(Integer userId);

    Optional<EmailActivationEntity> findByEmail(String email);

}
