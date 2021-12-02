/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.servicios;

import com.libreriaweb.libreria.entidades.Usuario;
import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Federico
 */
@Service
public class UsuarioServicio implements UserDetailsService
{
    
    @Autowired
    UsuarioRepositorio usuariorepositorio;
    
    
    
    @Transactional
    public void guardarUsuario(String nombre,String apellido,String clave,String mail)throws ErrorServicio{
        
        Usuario usuario=new Usuario();
        if(nombre ==null || nombre.isEmpty() || apellido ==null || clave==null  || apellido.isEmpty() || clave.isEmpty() || mail.isEmpty()){
            throw new ErrorServicio("Ninguno de los valores puede ser nulo");
        }
                
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptada);

       
        
        usuario.setAlta(new Date());
        usuario.setMail(mail);
        usuariorepositorio.save(usuario);
    }
    
     @Transactional
    public void modificarUsuario(String nombre,String apellido,String clave,String mail)throws ErrorServicio{
        
        
        Usuario usuario=usuariorepositorio.buscarPorMail(mail);
         if (usuario == null) {
             throw new ErrorServicio("No se encontro el usuario");
         }
                
                
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptada);

       
        
        usuario.setAlta(usuario.getAlta());
        usuario.setMail(mail);
        usuariorepositorio.save(usuario);
    }
    @Transactional
    public Usuario buscarUsuarioporId(String id)throws ErrorServicio{
        
        
        Usuario usuario = usuariorepositorio.buscarPorId(id);
        if (usuario ==null) {
            throw new ErrorServicio("El usuario no ha sido encontrado.");
        }
           return usuario;
        
    }
     

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
       Usuario usuario = usuariorepositorio.buscarPorMail(mail);
       
        if (usuario != null) {
            
          List<GrantedAuthority> permisos = new ArrayList();
          permisos.add(new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO"));
          //permisos.add(new SimpleGrantedAuthority("MODULO_AUTOR"));
          //permisos.add(new SimpleGrantedAuthority("MODULO_EDITORIAL"));
          
          
          /*Ya ingersando el usuario de manera correcta, se incerta esto para  guardar el usuario de la base de datos y meterlo en la sesion web*/
          /*SIRVE PARA PODER UTILIZAR LOS ATRIBUTOS DEL USUARIO Y PLASMARLOS EN EL FRONT*/
          
          ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
             /*Recupera los atributos del request htpp y solicita los datos de sesion*/
                HttpSession session = attr.getRequest().getSession(true);
                /*atributo usuariosession, se guardan todos los atributos del usuario*/
                session.setAttribute("usuariosession", usuario);
                System.out.println("usuario conectado");
//                System.out.println(usuario);
                

                User user= new User(usuario.getMail(), usuario.getClave(), permisos);
                return user;

        }else return null;
            
        
        
        
    }
    
    
}
