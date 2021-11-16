/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.servicios;

import com.libreriaweb.libreria.Enumeracion.Sexo;
import com.libreriaweb.libreria.entidades.Cliente;
import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.repositorios.ClienteRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Federico
 */
public class ClienteServicio {

    @Autowired
    ClienteRepositorio clienterepositorio;
  
    @Transactional
    public void guardarCliente(Long dni, String nombre, String apellido, String telefono, Sexo sexo) throws ErrorServicio {
        validar(dni, nombre, apellido, telefono);
        Cliente cliente = new Cliente();

        cliente.setDni(dni);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setSexo(sexo);
        cliente.setAlta(new Date());

        clienterepositorio.save(cliente);

    }
    @Transactional
    public void modificarCliente(String id, Long dni, String nombre, String apellido, String telefono, Sexo sexo) throws ErrorServicio {
        validar(dni, nombre, apellido, telefono);

        Optional<Cliente> respuesta = clienterepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setDni(dni);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            cliente.setSexo(sexo);
            clienterepositorio.save(cliente);
        } else {
           throw new ErrorServicio("No se encontró el cliente buscado");
        }   
    }
    @Transactional
    public void bajaCliente(String id)throws ErrorServicio{
        Optional<Cliente> respuesta=clienterepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setBaja(new Date());
            clienterepositorio.save(cliente);
        }else{
            throw new ErrorServicio("No se encontro el cliente especificado");
        }
    }
    

    private void validar(Long dni, String nombre, String apellido, String telefono) throws ErrorServicio {

        if (dni == null) {
            throw new ErrorServicio("El dni no puede ser nulo o negativo.");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio.");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido no puede estar vacio.");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new ErrorServicio("El telefono no puede estar vacio o ser nulo.");
        }

    }
}

