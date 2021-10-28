/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.servicios;

import com.libreriaweb.libreria.entidades.Autor;
import com.libreriaweb.libreria.repositorios.AutorRepositorio;
import java.util.List;
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
     
    public void guardarAutor(String nombre){
        Autor autor = new Autor(nombre,true);
        autorRepositorio.save(autor);
    }
    
    public List<Autor>  listarAutores(){
        return autorRepositorio.findAll();
    }
}
