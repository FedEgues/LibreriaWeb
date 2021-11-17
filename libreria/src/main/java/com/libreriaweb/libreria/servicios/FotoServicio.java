/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.servicios;

import com.libreriaweb.libreria.entidades.FotoCliente;
import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.repositorios.FotoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ericec
 */
@Service
public class FotoServicio {

    @Autowired
    FotoRepositorio fotorepositorio;
    @Transactional
    public FotoCliente guardar(MultipartFile archivo) throws ErrorServicio {

        if (archivo != null) {
            try {
                FotoCliente fotocliente = new FotoCliente();
                fotocliente.setMime(archivo.getContentType());
                fotocliente.setContenido(archivo.getBytes());
                return fotorepositorio.save(fotocliente);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }
        return null;
    }
    @Transactional
    public FotoCliente actualizar(String id,MultipartFile archivo)throws ErrorServicio{
        
        
         if (archivo != null) {
              FotoCliente fotocliente = new FotoCliente();
              Optional<FotoCliente> respfotocliente= fotorepositorio.findById(id);
              if(respfotocliente.isPresent()){
            try {
                fotocliente=respfotocliente.get();
                fotocliente.setMime(archivo.getContentType());
                fotocliente.setContenido(archivo.getBytes());
                return fotorepositorio.save(fotocliente);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
              }
        }
        return null;
    }
}
