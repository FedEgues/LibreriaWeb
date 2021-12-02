/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.controladores;

import com.libreriaweb.libreria.entidades.Usuario;
import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.repositorios.UsuarioRepositorio;
import com.libreriaweb.libreria.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Federico
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioServicio usuarioservicio;
    @Autowired
    private UsuarioRepositorio usuariorepositorio;
    
    @GetMapping("/editarUsuario")
    public String editarUsuario(String id,ModelMap modelo){
        
        
        try{
            Usuario usuario = usuarioservicio.buscarUsuarioporId(id);
            modelo.addAttribute("perfil",usuario);
        }catch(ErrorServicio ex){
            modelo.addAttribute("error",ex.getMessage());
        }
        return "perfil.html";
    }
    
    @PostMapping("/actualizarUsuario")
    public String actualizarUsuario (ModelMap modelo,String nombre, String apellido, String clave,String mail) {
        
        try {
           usuarioservicio.modificarUsuario(nombre, apellido, clave, mail);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            return "registro.html";
        }
        modelo.put("exito", "El ingreso del usuario fue actualizado exitosamente.");
        return "indexp";
}
}
