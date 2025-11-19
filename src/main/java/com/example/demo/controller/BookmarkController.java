package com.example.demo.controller;

import com.example.demo.dto.BookmarkRequest;
import com.example.demo.model.Bookmark;
import com.example.demo.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<Bookmark> createBookmark(
            @RequestBody BookmarkRequest request) {

        Bookmark bookmark = bookmarkService.createBookmark(request);

        return ResponseEntity.ok(bookmark);
    }

    @GetMapping
    public ResponseEntity<List<Bookmark>> getBookmarks() {
        return ResponseEntity.ok(bookmarkService.getBookmarks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bookmark> updateBookmark(
            @PathVariable String id,
            @RequestBody BookmarkRequest request) {

        Bookmark updated = bookmarkService.updateBookmark(id, request);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookmark(
            @PathVariable String id) {
        bookmarkService.deleteBookmark(id);

        return ResponseEntity.ok("Bookmark deleted");
    }
}