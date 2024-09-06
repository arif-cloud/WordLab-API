package com.example.api.vocabularyapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Document(collection = "unknown_words")
public class UnknownWord {
    @Id
    private String id;
    private String userId;
    private List<SimpleWord> words = new ArrayList<>();

    public UnknownWord(String userId) {
        this.userId = userId;
    }

    public List<SimpleWord> getWords() {
        return words;
    }

    public Optional<SimpleWord> getDailyUnknownWord() {
        if (!words.isEmpty()) {
            return Optional.of(words.get(0));
        } else {
            return Optional.empty();
        }
    }

    public void addWord(SimpleWord simpleWord) {
        this.words.add(simpleWord);
    }

    public void deleteWord(int wordId) {
        this.words.removeIf( simpleWord -> simpleWord.id() == wordId );
    }

}
