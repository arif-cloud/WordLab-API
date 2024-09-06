package com.example.api.vocabularyapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id()
    private String id;

    private String name;

    private String baseLanguage;

    public String getId() {
        return id;
    }

    public void editUser(User user) {
        this.name = user.name;
        this.baseLanguage = user.baseLanguage;
    }

}
