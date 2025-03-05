package com.unal.sia.services;

import com.unal.sia.dtos.UsuarioDto;
import com.unal.sia.models.Usuario;
import com.unal.sia.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private EntityManager entityManager;


    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.obtenerUsuarioPorEmail(email);
    }

    @Transactional
    public void registrarUsuario(UsuarioDto u) {
        String username = u.getPrimer_nombre().charAt(0) + "" + u.getSegundo_nombre().charAt(0) +
                u.getPrimer_apellido() + "" + u.getSegundo_apellido().charAt(0);

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("insertar_usuario");
        query.registerStoredProcedureParameter(1, String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter(4, String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter(6, String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter(7, String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter(8, Integer.class, jakarta.persistence.ParameterMode.IN);

        query.setParameter(1, username);
        query.setParameter(2, new BCryptPasswordEncoder().encode(u.getContrasena()));
        query.setParameter(3, u.getPrimer_nombre() + " " + u.getSegundo_nombre());
        query.setParameter(4, u.getPrimer_apellido() + " " + u.getSegundo_apellido());
        query.setParameter(5, u.getTelefono());
        query.setParameter(6, u.getTipo_identificacion());
        query.setParameter(7, u.getNum_identificacion());
        query.setParameter(8, u.getPrograma());

        query.execute();
    }
}


