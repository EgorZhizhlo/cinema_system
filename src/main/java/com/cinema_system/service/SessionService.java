package com.cinema_system.service;

import com.cinema_system.model.Session;
import com.cinema_system.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Session getSessionByIdWithDetails(Long id) {
        return sessionRepository.findByIdWithDetails(id).orElse(null);
    }

    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public long countAll() {
        return sessionRepository.count();
    }
}