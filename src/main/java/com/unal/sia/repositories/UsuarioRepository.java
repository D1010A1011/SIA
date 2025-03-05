package com.unal.sia.repositories;

import com.unal.sia.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM obtener_usuario(:email)", nativeQuery = true)
    Optional<Usuario> obtenerUsuarioPorEmail(@Param("email") String email);
}
