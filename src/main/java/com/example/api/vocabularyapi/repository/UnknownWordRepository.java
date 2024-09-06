package com.example.api.vocabularyapi.repository;

import com.example.api.vocabularyapi.model.SimpleWord;
import com.example.api.vocabularyapi.model.UnknownWord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UnknownWordRepository extends MongoRepository<UnknownWord, String> {
    UnknownWord findByUserId(String userId);
    boolean existsByUserIdAndWordsId(String userId, int id);
}
