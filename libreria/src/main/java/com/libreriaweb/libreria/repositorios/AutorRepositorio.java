/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.repositorios;

import com.libreriaweb.libreria.entidades.Autor;
import com.libreriaweb.libreria.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

/**
 *
 * @author ericec
 */
@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
    
    @Query("SELECT c FROM Autor c WHERE c.nombre = :nombre")
    public Autor buscarPorNombre(@Param("nombre")String nombre);
    
       @Query("SELECT e FROM Autor e  WHERE e.id = :id")/*hacemos la busqueda para que al buscar autores me traiga autores y no un optional*/
    public Autor buscarPorId(@Param("id") String id);
    
    
}
