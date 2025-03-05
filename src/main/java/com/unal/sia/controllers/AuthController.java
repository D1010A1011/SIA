package com.unal.sia.controllers;

import com.unal.sia.dtos.UsuarioDto;
import com.unal.sia.models.Usuario;
import com.unal.sia.services.ProgramaService;
import com.unal.sia.services.UsuarioService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
@SessionAttributes("usuario")
public class AuthController {

    private UsuarioService usuarioService;
    private ProgramaService programaService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

        @GetMapping("/register")
        public String registerPage(Model model) {
            model.addAttribute("usuarioDto", new UsuarioDto());
            Map<Integer, String> programas = programaService.obtenerProgramas();
            model.addAttribute("programas", programas);
            return "register";
        }

    @PostMapping("/register")
    public String registerUser(@Valid UsuarioDto usuarioDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("programas", programaService.obtenerProgramas()); // Recargar programas si hay error
            return "register";
        }

        try {
            usuarioService.registrarUsuario(usuarioDto);
            return "redirect:/auth/login?success";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo registrar el usuario. Inténtalo de nuevo.");
            model.addAttribute("programas", programaService.obtenerProgramas()); // Recargar programas en caso de error
            return "register";
        }
    }
    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Obtiene el email del usuario autenticado
        Optional<Usuario> usuarioData = usuarioService.obtenerUsuarioPorEmail(email);
        model.addAttribute("usuario", usuarioData.map(Usuario::getNombreUsuario));

        return "home";
    }

}
