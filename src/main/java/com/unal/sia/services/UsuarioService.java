package com.unal.sia.services;

import com.unal.sia.models.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;

public class UserService {
    private final JdbcTemplate jdbcTemplate;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        String sql = "SELECT * FROM obtener_usuario(?)";

        RowMapper<Usuario> rowMapper = (rs, rowNum) -> new Usuario(
                rs.getString("nombre_usuario"),
                rs.getString("correo_institucional"),
                rs.getString("contrasena"),
                rs.getString("rol")
        );

        return jdbcTemplate.query(sql, rowMapper, email).stream().findFirst();
    }
}
