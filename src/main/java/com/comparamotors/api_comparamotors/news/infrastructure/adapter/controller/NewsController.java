package com.comparamotors.api_comparamotors.news.infrastructure.adapter.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.comparamotors.api_comparamotors.news.application.dto.*;
import com.comparamotors.api_comparamotors.news.application.port.input.NewsService;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping(value = "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NewsResponseDTO> createNews(
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart(value = "redirectLink", required = false) String redirectLink,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        
        NewsRequestDTO request = NewsRequestDTO.builder()
            .title(title)
            .content(content)
            .redirectLink(redirectLink)
            .images(images)
            .build();

        return ResponseEntity.ok(newsService.createNews(request));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NewsResponseDTO> updateNews(
            @PathVariable Long id,
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart(value = "redirectLink", required = false) String redirectLink,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        NewsRequestDTO request = NewsRequestDTO.builder()
            .title(title)
            .content(content)
            .redirectLink(redirectLink)
            .images(images)
            .build();

        return ResponseEntity.ok(newsService.updateNews(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> getNewsById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @GetMapping
    public ResponseEntity<List<NewsResponseDTO>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.badRequest().body("Error processing file: " + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}