/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.servicios;

import com.libreriaweb.libreria.entidades.Editorial;
import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.repositorios.EditorRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ericec
 */
@Service
public class EditorialServicio {

    @Autowired
    private EditorRepositorio editorRepositorio;
    
    @Transactional
    public void guardarEditorial(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la editorial no puede ser nulo");
        }
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);

        editorRepositorio.save(editorial);
    }
   @Transactional
    public void modificarEditorial(String id, String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la editorial no puede ser nulo");
        }

        Optional<Editorial> respuesta = editorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro una editorial con ese Id");
        }
    }
    @Transactional
    public void darDeBaja(String id)throws ErrorServicio{
        
        Optional<Editorial> respuesta = editorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            editorRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro una editorial con ese Id");
        }
        
    }
}
