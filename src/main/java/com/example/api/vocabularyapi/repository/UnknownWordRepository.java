package com.example.api.vocabularyapi.repository;

import com.example.api.vocabularyapi.model.UnknownWord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UnknownWordRepository extends MongoRepository<UnknownWord, String> {
    UnknownWord findByUserId(String userId);
    boolean existsByUserId(String userId);
    boolean existsByUserIdAndWordsId(String userId, int id);
}
