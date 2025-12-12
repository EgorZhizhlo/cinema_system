package com.cinema_system.service;

import com.cinema_system.model.News;
import com.cinema_system.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    @Transactional(readOnly = true)
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<News> findById(Long id) {
        return newsRepository.findById(id);
    }

    @Transactional
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Transactional
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countAll() {
        return newsRepository.count();
    }
}