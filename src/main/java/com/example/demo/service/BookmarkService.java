package com.example.demo.service;

import com.example.demo.dto.BookmarkRequest;
import com.example.demo.model.Bookmark;
import com.example.demo.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    public Bookmark createBookmark(BookmarkRequest request) {
        Bookmark bookmark = new Bookmark();
        bookmark.setTitle(request.getTitle());
        bookmark.setLink(request.getLink());
        bookmark.setDescription(request.getDescription());
        bookmark.setCategory(request.getCategory());
        bookmark.setFavorite(request.getFavorite());
        bookmark.setThumbnail(request.getThumbnail());
        bookmark.setCreatedAt(LocalDateTime.now());
        bookmark.setUpdatedAt(LocalDateTime.now());

        return bookmarkRepository.save(bookmark);
    }

    public List<Bookmark> getBookmarks() {
        return bookmarkRepository.findAll();
    }

    public Bookmark updateBookmark(String id, BookmarkRequest request) {
        Bookmark existing = bookmarkRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        existing.setTitle(request.getTitle());
        existing.setLink(request.getLink());
        existing.setDescription(request.getDescription());
        existing.setCategory(request.getCategory());
        existing.setFavorite(request.getFavorite());
        existing.setThumbnail(request.getThumbnail());
        existing.setUpdatedAt(LocalDateTime.now());

        return bookmarkRepository.save(existing);
    }

    public void deleteBookmark(String id) {
        Bookmark existing = bookmarkRepository.findById(id).orElse(null);
        if (existing != null) {
            bookmarkRepository.delete(existing);
        }
    }
}