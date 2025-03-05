package com.unal.sia.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ProfesorService {
    private final JdbcTemplate jdbcTemplate;

    public ProfesorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> obtenerProfesores() {
        String sql = "SELECT nombre, departamento FROM profesores";
        return jdbcTemplate.queryForList(sql);
    }
}

