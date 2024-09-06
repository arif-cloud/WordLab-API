package com.example.api.vocabularyapi.repository;

import com.example.api.vocabularyapi.model.WordDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordDetailRepository extends MongoRepository<WordDetail, String> {
    WordDetail findById(int id);

}
