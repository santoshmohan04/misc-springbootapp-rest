package com.example.demo.repository;

import com.example.demo.model.Bookmark;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookmarkRepository extends MongoRepository<Bookmark, String> {

    List<Bookmark> findByUserId(String userId);

    Bookmark findByIdAndUserId(String id, String userId);
}