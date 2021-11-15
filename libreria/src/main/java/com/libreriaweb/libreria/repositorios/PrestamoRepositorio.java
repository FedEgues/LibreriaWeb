/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.repositorios;

import com.libreriaweb.libreria.entidades.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Federico
 */
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String>{
    
}
