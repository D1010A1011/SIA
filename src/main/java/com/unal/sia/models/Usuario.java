package com.unal.sia.models;

import lombok.Getter;
import lombok.Setter;

public class Usuario {
    private String nombre_usuario;
    private String correoInstitucional;
    private String contrasena;
    private String rol; // "EST", "PROF" o "PROF-EST"

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String nombreUsuario, String correoInstitucional, String contrasena, String rol) {
        this.nombre_usuario = nombreUsuario;
        this.correoInstitucional = correoInstitucional;
        this.contrasena = contrasena;
        this.rol = rol;
    }
    public String getNombreUsuario(){
        return nombre_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}