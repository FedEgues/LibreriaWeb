/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.servicios;

import com.libreriaweb.libreria.entidades.Autor;
import com.libreriaweb.libreria.entidades.Editorial;
import com.libreriaweb.libreria.entidades.Libro;
import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.repositorios.LibroRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Federico
 */
@Service
public class LibroServicio {
    
    @Autowired
    LibroRepositorio libroRepositorio;
    
    /*CRED*/
    @Transactional
    public void guardarLibro(Long isbn,String titulo,Integer anio,Integer ejemplares,Integer ejemplaresPrestados,Integer ejemplaresRestantes,Boolean alta,Editorial editorial,Autor autor)throws ErrorServicio{
          validar(isbn,titulo,anio,ejemplares,ejemplaresPrestados,ejemplaresRestantes,editorial,autor);
        Libro libro= new Libro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, true,null,null);
        libroRepositorio.save(libro);
    }
    @Transactional
    public void modificarLibro(String id,Long isbn,String titulo,Integer anio,Integer ejemplares,Integer ejemplaresPrestados,Integer ejemplaresRestantes,Boolean alta,Editorial editorial,Autor autor)throws ErrorServicio{
           validar(isbn,titulo,anio,ejemplares,ejemplaresPrestados,ejemplaresRestantes,editorial,autor);
           Optional<Libro> resultado =libroRepositorio.findById(id);
           if (resultado.isPresent()) {
               Libro libro=resultado.get();
               libro.setIsbn(isbn);
               libro.setTitulo(titulo);
               libro.setAnio(anio);
               libro.setEjemplares(ejemplares);
               libro.setEjemplaresPrestados(ejemplaresPrestados);
               libro.setEjemplaresRestantes(ejemplaresRestantes);
               libro.setAlta(true);
               libro.setAutor(null);
               libro.setEditorial(null);
               libroRepositorio.save(libro);
            
        }else{
               throw new ErrorServicio("No se encontro un libro para el id indicado");
           }
    }
    @Transactional
    public void darDeBajar(String id)throws ErrorServicio{
        Optional<Libro> resultado =libroRepositorio.findById(id);
        if (resultado.isPresent()) {
           Libro libro =resultado.get();
           libro.setAlta(false);
           libroRepositorio.save(libro);
        }else{
            throw new ErrorServicio("No se encontro un libro para el id indicado");
        }
            
    }
    
    
    public void validar(Long ISBN,String titulo,Integer anio,Integer ejemplares,Integer ejemplaresPrestados,Integer ejemplaresRestantes,Editorial editorial,Autor autor)throws ErrorServicio{
        
        if(ISBN ==null ){
            throw new ErrorServicio("El valor isbn no puede ser nulo");
        }
        if(titulo==null || titulo.isEmpty()){
            throw new ErrorServicio("El titulo no puede ser nulo");
        }
        if(anio==null || anio<0){
            throw new ErrorServicio("El año es incorrecto");
        }
        if(ejemplares==null || ejemplares<1){
            throw new ErrorServicio("El número de ejemplares debe ser 1 o mayor ");
        }
         if(ejemplaresPrestados==null || ejemplaresPrestados<0){
            throw new ErrorServicio("El número de ejemplares prestados debe ser 0 o mayor ");
        }
          if(ejemplaresRestantes==null || ejemplaresRestantes<0){
            throw new ErrorServicio("El número de ejemplares restantes debe ser 0 o mayor ");
        }
//          if (autor==null) {
//            throw new ErrorServicio("El libro debe tener un autor.");
//        }
//          if (editorial==null) {
//            throw new ErrorServicio("El libro debe tener una editorial.");
//        }
//        
    }

}