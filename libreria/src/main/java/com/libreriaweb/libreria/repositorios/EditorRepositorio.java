/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.repositorios;

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
public interface EditorRepositorio extends JpaRepository<Editorial, String> {
    
    @Query("SELECT e FROM Editorial e  WHERE e.id = :id")/*hacemos la busqueda para que al buscar editores me traiga autores y no un optional*/
    public Editorial buscarPorId(@Param("id") String id);

    
}
