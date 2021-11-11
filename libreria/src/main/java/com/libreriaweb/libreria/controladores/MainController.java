/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.controladores;

import com.libreriaweb.libreria.entidades.Autor;
import com.libreriaweb.libreria.entidades.Editorial;
import com.libreriaweb.libreria.entidades.Libro;
import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.servicios.AutorServicio;
import com.libreriaweb.libreria.servicios.EditorialServicio;
import com.libreriaweb.libreria.servicios.LibroServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author ericec
 */
@Controller
@RequestMapping("/")
public class MainController {
    
    
    @Autowired
    private AutorServicio autorservicio;
    @Autowired
    private EditorialServicio editorialservicio;
    @Autowired
    private LibroServicio libroservicio;
    
    
    
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
    
    @PostMapping("/ingresarAutor")
    private String ingresarAutor(ModelMap modelo,@RequestParam String nombre){
        
        try {
            
            autorservicio.guardarAutor(nombre);
        } catch (ErrorServicio ex) {
            modelo.put(nombre, nombre);
            modelo.put("error",ex.getMessage());
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return "OpcionesAutor.html";    
        }
        return "index.html";
    }
    
     @PostMapping("/ingresarEditorial")
    private String ingresarEditorial(ModelMap modelo,@RequestParam String nombre){
        /*Se utiliza ModelMap modelo, para interactuar con la vista a traves de thymeleaf*/
        try {
            editorialservicio.guardarEditorial(nombre);
            
        } catch (ErrorServicio ex) {
            modelo.put(nombre, nombre);/*para que el formulario carge los valores*/
            modelo.put("error",ex.getMessage());/*para que cargue el error en la vista*/
            return "OpcionesEditorial.html";    
        }
        modelo.put("exito","La editorial fue ingresada con éxito");
        return "OpcionesEditorial.html";
    }
      
    
     @PostMapping("/ingresarlibro")
   private String ingresarlibro(ModelMap modelo,@RequestParam Long ISBN,@RequestParam String titulo,@RequestParam Integer anio,@RequestParam Integer ejemplarestotales,@RequestParam Integer ejemplaresprestados,@RequestParam Integer ejemplaresrestantes){
       
        try {
            libroservicio.guardarLibro(ISBN, titulo, anio, ejemplarestotales, ejemplaresprestados,ejemplaresrestantes,true,null,null);
        } catch (ErrorServicio ex) {
            modelo.put("error",ex.getMessage());
            modelo.put("ISBN",ISBN);
            modelo.put("titulo",titulo);
            modelo.put("anio",anio);
            modelo.put("ejemplarestotales",ejemplarestotales);
            modelo.put("ejemplaresprestados",ejemplaresprestados);
            modelo.put("ejemplaresrestantes",ejemplaresrestantes);
//            modelo.put("autor",autor);
//            modelo.put("editorial",editorial);
             modelo.put("error",ex.getMessage());
            return "OpcionesLibro.html";
        }
                
            return "index.html";
            }
}
