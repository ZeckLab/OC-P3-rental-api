package com.openclassrooms.rentalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.rentalapi.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
