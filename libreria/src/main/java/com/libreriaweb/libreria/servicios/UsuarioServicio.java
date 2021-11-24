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
import java.util.Optional;
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
    public void guardarUsuario(String nombre,String apellido,String clave1,String clave2,String mail)throws ErrorServicio{
        Usuario usuario=new Usuario();
        validar(nombre,apellido,clave1,clave2,mail);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        
        String encriptada = new BCryptPasswordEncoder().encode(clave1);
        usuario.setClave(encriptada);

       
        
        usuario.setAlta(new Date());
        usuario.setMail(mail);
        usuariorepositorio.save(usuario);
    }
    
    @Transactional
    public void modificarUsuario(String mail,String nombre,String apellido,String clave1, String clave2)throws ErrorServicio{
        validar(nombre,apellido,clave1,clave2,mail);
        
       
        /*terminar esto igual quiero persistir un usuario*/
        Usuario res = usuariorepositorio.buscarPorMail(mail);
        if (res != null) {
            
        }
        
        
    }
    
    private void validar(String nombre,String apellido,String clave1,String clave2,String mail)throws ErrorServicio{
        
        if (nombre == null || nombre.isEmpty() ) {
            throw new ErrorServicio("El nombre no puede ser nulo.");
        }
        
        if(apellido == null || apellido.isEmpty() ){
          throw new ErrorServicio("El apellido no puede estar vacio o ser nulo");
        }
        if(clave1 == null || clave1.isEmpty()){
            throw new ErrorServicio("La clave no puede estar vacia.");
        }
        if(clave2 == null || clave2.isEmpty()){
            throw new ErrorServicio("La clave no puede estar vacia.");
        }
        if(clave1.equals(clave2)){
            
        }else{
            throw new ErrorServicio("Las claves deben ser iguales");
        }
        if(mail==null || mail.isEmpty()){
            throw new ErrorServicio("El mail no puede ser nulo");
        }
    }
    

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
       Usuario usuario = usuariorepositorio.buscarPorMail(mail);
       
        if (usuario != null) {
            
          List<GrantedAuthority> permisos = new ArrayList();
          permisos.add(new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO"));
          //permisos.add(new SimpleGrantedAuthority("MODULO_AUTOR"));
          //permisos.add(new SimpleGrantedAuthority("MODULO_EDITORIAL"));
          ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("usuariosession", usuario);
                System.out.println("usuario conectado");
//                System.out.println(usuario);
                
                User user= new User(usuario.getMail(), usuario.getClave(), permisos);
                return user;

        }else return null;
            
        
        
        
    }
}
