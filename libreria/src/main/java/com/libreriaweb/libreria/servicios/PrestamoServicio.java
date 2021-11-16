/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.servicios;

import com.libreriaweb.libreria.entidades.Cliente;
import com.libreriaweb.libreria.entidades.Libro;
import com.libreriaweb.libreria.entidades.Prestamo;
import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.repositorios.ClienteRepositorio;
import com.libreriaweb.libreria.repositorios.LibroRepositorio;
import com.libreriaweb.libreria.repositorios.PrestamoRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Federico
 */
public class PrestamoServicio {
    
    @Autowired
    PrestamoRepositorio prestamorepositorio;
    @Autowired
    LibroRepositorio librorepositorio;
    @Autowired
    ClienteRepositorio clienterepositorio;
    
    
    @Transactional
    public void crearPrestamo(String idlibro,String idcliente)throws ErrorServicio{
        Prestamo prestamo = new Prestamo();
        
        Optional<Cliente> respcliente=clienterepositorio.findById(idcliente);
        Optional<Libro> resplibro = librorepositorio.findById(idlibro);
        
        if (respcliente.isPresent() ) {
            Cliente cliente = respcliente.get();
            prestamo.setCliente(cliente);
            prestamorepositorio.save(prestamo);
        }else{
            throw new ErrorServicio("No se encontro el cliente especificado");
        }
        if (resplibro.isPresent()) {
            Libro libro =resplibro.get();
            prestamo.setLibro(libro);
            prestamorepositorio.save(prestamo);
        }else{
        throw new ErrorServicio("No se encontro el libro especificado");
        }
        prestamo.setFechaPrestamo(new Date());
        
    }
    
}
