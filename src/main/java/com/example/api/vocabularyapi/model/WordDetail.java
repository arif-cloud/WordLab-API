package com.example.api.vocabularyapi.model;

import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@ToString
@Document(collection = "wordsDetail")
public class WordDetail {
    @Id
    private ObjectId _id;
    @Getter
    private int id;
    @Getter
    private String word;
    @Getter
    private String level;
    @Getter
    private String image_url;
    @Getter
    private List<Meaning> meanings;

}
