/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.controladores;

import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author ericec
 */
@Controller
@RequestMapping("/")
public class MainController {
    
    
    @Autowired
    private AutorServicio autorservicio;
    
    @GetMapping("/")
    public String index(){
        return "index";
    } 
    
    @GetMapping("/OpcionesAutor")
    private String OpcionesAutor(){
            return "OpcionesAutor.html";
            }   
    
      @GetMapping("/OpcionesEditorial")
    private String OpcionesEditorial(){
            return "OpcionesEditorial.html";
            }
      @GetMapping("/OpcionesLibro")
    private String OpcionesLibro(){
            return "OpcionesLibro.html";
            }
    
     @PostMapping("/ingresarlibro")
    private String ingresarlibro(Long isgn,String titulo,Integer anio,Integer ejempalres,Integer e){
            return "OpcionesLibro.html";
            }
    
   public String guardarAutor(String nombre)throws ErrorServicio{
       autorservicio.guardarAutor(nombre);
       return "index";
   } 
}
