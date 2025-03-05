package com.unal.sia.controllers;

import com.unal.sia.services.AsignaturaService;
import com.unal.sia.services.EstudianteService;
import com.unal.sia.services.ProfesorService;
import com.unal.sia.services.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HomeController {
    private final EstudianteService estudianteService;
    private final AsignaturaService asignaturaService;
    private final ProfesorService profesorService;
    private final UsuarioService usuarioService;

    public HomeController(EstudianteService estudianteService, AsignaturaService asignaturaService, ProfesorService profesorService,
                          UsuarioService usuarioService) {
        this.estudianteService = estudianteService;
        this.asignaturaService = asignaturaService;
        this.profesorService = profesorService;
        this.usuarioService=usuarioService;
    }

    @GetMapping("/home/info-academica")
    public String verHistorialAcademico(@RequestParam("idUsuario") Long idUsuario, Model model) {
        Map<String, Object> historial = estudianteService.obtenerHistorialAcademico(idUsuario);
        List<Map<String, Object>> asignaturas = estudianteService.obtenerHistorialAsignaturas(idUsuario);

        model.addAttribute("historial", historial);
        model.addAttribute("asignaturas", asignaturas);
        return "home";
    }
}
