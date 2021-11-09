 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.servicios;

import com.libreriaweb.libreria.entidades.Autor;
import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.repositorios.AutorRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ericec
 */
@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void guardarAutor(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del autor no puede ser nulo.");
        }

        Autor autor = new Autor(nombre, true);
        autorRepositorio.save(autor);
    }
    
    @Transactional
    public void modificarNombre(String id, String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del autor no puede ser nulo.");
        }

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();  
            autor.setNombre(nombre);

            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el autor buscado");
        }
    }
    @Transactional
    public void darBaja(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);
        } else {
            throw new ErrorServicio("No se encontro el autor relacionado al id ingresado.");
        }

    }
}
