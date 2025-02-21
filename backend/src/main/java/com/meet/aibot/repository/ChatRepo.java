package com.meet.aibot.repository;

import com.meet.aibot.models.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRepo extends MongoRepository<Chat, UUID> {
}
