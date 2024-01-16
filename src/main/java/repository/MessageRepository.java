package com.example.repository;

import com.example.entity.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Message SET message_text = :text WHERE message_id = :id")
    int updateMessage(@Param("id") int messageId, @Param("text") String messageText);

    @Query(value = "SELECT m FROM Message m WHERE posted_by = :id")
	List<Message> getMessagesByAccountId(@Param("id") int accountId);
}