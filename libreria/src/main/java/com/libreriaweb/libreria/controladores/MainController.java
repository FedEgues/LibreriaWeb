/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.controladores;

import com.libreriaweb.libreria.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping("")
    public String index(){
        return "index";
    } 
    
   public String guardarAutor(String nombre){
       autorservicio.guardarAutor(nombre);
       return "index";
   } 
}
