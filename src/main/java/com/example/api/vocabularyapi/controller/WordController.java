package com.example.api.vocabularyapi.controller;

import com.example.api.vocabularyapi.model.*;
import com.example.api.vocabularyapi.repository.UnknownWordRepository;
import com.example.api.vocabularyapi.repository.UserRepository;
import com.example.api.vocabularyapi.repository.WordDetailRepository;
import com.example.api.vocabularyapi.repository.WordRepository;
import com.example.api.vocabularyapi.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class WordController {
    @Autowired(required = false)
    UserRepository userRepository;
    @Autowired(required = false)
    WordRepository wordRepository;
    @Autowired(required = false)
    WordDetailRepository wordDetailRepository;
    @Autowired(required = false)
    UnknownWordRepository unknownWordRepository;

    @PostMapping("/login")
    public ResponseEntity<PostResponse> loginUser(@RequestBody User user) {
        try {
            User savedUser = userRepository.save(user);
            unknownWordRepository.save(new UnknownWord(savedUser.getId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponse(HttpStatus.CREATED.value(), savedUser.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PostResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
    @GetMapping("profile/{userId}")
    public Optional<User> getProfileInfo(@PathVariable String userId) {
        return userRepository.findById(userId);
    }

    @PostMapping("edit/profile/{userId}")
    public ResponseEntity<PostResponse> editProfileInfo(@PathVariable String userId, @RequestBody User user) {
        try {
            Optional<User> currentUser = userRepository.findById(userId);
            if (currentUser.isPresent()) {
                currentUser.get().editUser(user);
                userRepository.save(currentUser.get());
                return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponse(HttpStatus.CREATED.value(), "Edit successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PostResponse(HttpStatus.CREATED.value(), "User not found!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PostResponse(HttpStatus.CREATED.value(), e.getMessage()));
        }
    }

    @GetMapping("/words/random")
    public List<Word> getRandomWord() { return wordRepository.findRandomWords(); }

    @GetMapping("/words/search")
    public List<Word> getWordsBySearch(@RequestParam String word) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("word"));
        return wordRepository.findByWordContainingIgnoreCase(word, pageable);
    }

    @GetMapping("/words")
    public Page<Word> getWords(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return wordRepository.findAll(pageable);
    }

    @GetMapping("/word")
    public WordDetail getWordDetail(@RequestParam int id) {
        return wordDetailRepository.findById(id);
    }

    @GetMapping("/word/random/{level}")
    public Word getRandomWordByLevel(@PathVariable String level) {
        return wordRepository.findRandomWordByLevel(level);
    }

    @GetMapping("words/unknown")
    public List<SimpleWord> getUnknownWords(@RequestParam String userId) {
        return unknownWordRepository.findByUserId(userId).getWords();
    }

    @GetMapping("word/unknown")
    public Optional<SimpleWord> getDailyUnknownWord(@RequestParam String userId) {
        return unknownWordRepository.findByUserId(userId).getDailyUnknownWord();
    }

    @PostMapping("add/unknown_word")
    public ResponseEntity<PostResponse> addUnknownWord(@RequestParam String userId, @RequestBody SimpleWord simpleWord) {
        try {
            UnknownWord userWords = unknownWordRepository.findByUserId(userId);
            if (!unknownWordRepository.existsByUserIdAndWordsId(userId, simpleWord.id())) {
                userWords.addWord(simpleWord);
                unknownWordRepository.save(userWords);
                return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponse(HttpStatus.CREATED.value(), "Saved successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new PostResponse(HttpStatus.CONFLICT.value(), "Word is already exist"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PostResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping("delete/unknown_word/{wordId}")
    public ResponseEntity<PostResponse> removeUnknownWord(@PathVariable int wordId, @RequestParam String userId) {
        try {
            UnknownWord userWords = unknownWordRepository.findByUserId(userId);
            userWords.deleteWord(wordId);
            unknownWordRepository.save(userWords);
            return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponse(HttpStatus.CREATED.value(), "Delete successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PostResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping("check/unknown_word/{id}")
    public boolean checkUnknownWord(@RequestParam String userId, @PathVariable int id) {
        return unknownWordRepository.existsByUserIdAndWordsId(userId, id);
    }

}
