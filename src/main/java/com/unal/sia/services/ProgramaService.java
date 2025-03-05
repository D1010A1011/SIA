package com.unal.sia.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProgramaService {
    private final JdbcTemplate jdbcTemplate;

    public ProgramaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<Integer, String> obtenerProgramas() {
        String sql = "SELECT id_programa, nombre FROM programas";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                Map.entry(rs.getInt("id_programa"), rs.getString("nombre"))
        ).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
