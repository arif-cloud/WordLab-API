package com.example.api.vocabularyapi.repository;

import com.example.api.vocabularyapi.model.Word;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WordRepository extends MongoRepository<Word, String> {
    @Aggregation(pipeline = { "{ '$sample' : { 'size' : 10 } }" })
    List<Word> findRandomWords();

    List<Word> findByWordContainingIgnoreCase(String word, Pageable pageable);

    @Aggregation(pipeline = {
            "{ '$match': { 'level': { '$regex': ?0, '$options': 'i' } } }",
            "{ '$sample': { 'size': 1 } }"
    })
    Word findRandomWordByLevel(String level);

}
