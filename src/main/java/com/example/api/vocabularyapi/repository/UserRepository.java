package com.example.api.vocabularyapi.repository;

import com.example.api.vocabularyapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
