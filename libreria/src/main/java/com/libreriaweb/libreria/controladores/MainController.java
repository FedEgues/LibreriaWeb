/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.controladores;


import com.libreriaweb.libreria.entidades.Autor;
import com.libreriaweb.libreria.entidades.Editorial;
import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.repositorios.AutorRepositorio;
import com.libreriaweb.libreria.repositorios.EditorRepositorio;
import com.libreriaweb.libreria.servicios.AutorServicio;
import com.libreriaweb.libreria.servicios.ClienteServicio;
import com.libreriaweb.libreria.servicios.EditorialServicio;
import com.libreriaweb.libreria.servicios.LibroServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


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
    @Autowired
    private ClienteServicio clienteservicio;
    
    
    @Autowired
    private EditorRepositorio editorrepositorio;
    @Autowired
    private AutorRepositorio autorrepositorio;
    
    
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
    private String OpcionesLibro(ModelMap model){
           List<Editorial> editoriales = editorrepositorio.findAll();
           List<Autor> autores = autorrepositorio.findAll();
           model.put("editoriales",editoriales);/*pone a disposicion la lista de editoriales para el th*/
           model.put("autores", autores);/*pone a disposicion la lista de autores para el th*/
            return "OpcionesLibro.html";
            }
     @GetMapping("/OpcionesCliente")
    private String OpcionesCliente(){
            return "OpcionesCliente.html";
            }
     
    
    
    
    
    @PostMapping("/ingresarAutor")
    private String ingresarAutor(ModelMap modelo,@RequestParam String nombre){
       
        try {
            
            autorservicio.guardarAutor(nombre);
        } catch (ErrorServicio ex) {
            modelo.put("nombre", nombre);
            modelo.put("error",ex.getMessage());
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return "OpcionesAutor.html";    
        }
        modelo.put("exito","La editorial fue ingresada con éxito");
        return "OpcionesAutor.html";
    }
    
     @PostMapping("/ingresarEditorial")
    private String ingresarEditorial(ModelMap modelo,@RequestParam String nombre){
        /*Se utiliza ModelMap modelo, para interactuar con la vista a traves de thymeleaf*/
        try {
            editorialservicio.guardarEditorial(nombre);
            
        } catch (ErrorServicio ex) {
            modelo.put("nombre", nombre);/*para que el formulario carge los valores*/
            modelo.put("error",ex.getMessage());/*para que cargue el error en la vista*/
            return "OpcionesEditorial.html";    
        }
        modelo.put("exito","La editorial fue ingresada con éxito");
        return "OpcionesEditorial.html";
    }
      
   @PostMapping("/ingresarLibro")
   public String ingresarLibro(ModelMap modelo,@RequestParam Long isbn,@RequestParam String titulo,@RequestParam Integer anio,@RequestParam Integer ejemplares,String ideditorial,String  idautor){
       try{
           
           libroservicio.guardarLibro(isbn, titulo, anio, ejemplares, ideditorial, idautor);
       }catch (ErrorServicio ex){
          
           modelo.put("isbn", isbn);
           modelo.put("titulo", titulo);
           modelo.put("anio", anio);
           modelo.put("ejemplares", ejemplares);
           modelo.put("error",ex.getMessage());
           return "OpcionesLibro.html";
        
       }
       
       modelo.put("exito","El libro fue ingresado con éxito");
       
       return "OperacionesLibro.html";
   }

     @PostMapping("/ingresarCliente")
    private String ingresarCliente(ModelMap modelo,@RequestParam MultipartFile archivo,Long dni,String nombre,String apellido,String telefono,String sexo){
       
        try {
            
            clienteservicio.guardarCliente(archivo, dni, nombre, apellido, telefono, sexo);
        } catch (ErrorServicio ex) {
            modelo.put("nombre", nombre);
            modelo.put("dni", dni);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("error",ex.getMessage());
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return "OpcionesAutor.html";    
        }
        modelo.put("exito","La editorial fue ingresada con éxito");
        return "OpcionesAutor.html";
    }
    
}