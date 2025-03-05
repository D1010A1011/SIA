package com.unal.sia.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class AsignaturaService {
    private final JdbcTemplate jdbcTemplate;

    public AsignaturaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> obtenerAsignaturasInscritas() {
        String sql = "SELECT nombre, creditos FROM asignatura WHERE id IN (SELECT id_asignatura FROM inscripcione WHERE id_usuario = ?)";
        return jdbcTemplate.queryForList(sql, 1);
    }

    public List<Map<String, Object>> obtenerAsignaturasDisponibles() {
        String sql = "SELECT nombre, creditos FROM asignatura WHERE id NOT IN (SELECT id_asignatura FROM inscripcion WHERE id_usuario = ?)";
        return jdbcTemplate.queryForList(sql, 1);
    }
}

