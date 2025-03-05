package com.unal.sia.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EstudianteService {
    private final JdbcTemplate jdbcTemplate;

    public EstudianteService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> obtenerHistorialAcademico(Long idUsuario) {
        String sql = "SELECT hstl_academico(?)";
        return jdbcTemplate.queryForMap(sql, idUsuario);
    }

    public List<Map<String, Object>> obtenerHistorialAsignaturas(Long idUsuario) {
        String sql = "SELECT hstl_academico_asignaturas(?)";
        return jdbcTemplate.queryForList(sql, idUsuario);
    }
}
