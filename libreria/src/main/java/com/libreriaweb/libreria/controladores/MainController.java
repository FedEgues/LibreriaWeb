/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.controladores;

import com.libreriaweb.libreria.entidades.Autor;
import com.libreriaweb.libreria.entidades.Cliente;
import com.libreriaweb.libreria.entidades.Editorial;
import com.libreriaweb.libreria.entidades.Libro;
import com.libreriaweb.libreria.errores.ErrorServicio;
import com.libreriaweb.libreria.repositorios.AutorRepositorio;
import com.libreriaweb.libreria.repositorios.ClienteRepositorio;
import com.libreriaweb.libreria.repositorios.EditorRepositorio;
import com.libreriaweb.libreria.repositorios.LibroRepositorio;
import com.libreriaweb.libreria.repositorios.PrestamoRepositorio;
import com.libreriaweb.libreria.repositorios.UsuarioRepositorio;
import com.libreriaweb.libreria.servicios.AutorServicio;
import com.libreriaweb.libreria.servicios.ClienteServicio;
import com.libreriaweb.libreria.servicios.EditorialServicio;
import com.libreriaweb.libreria.servicios.LibroServicio;
import com.libreriaweb.libreria.servicios.PrestamoServicio;
import com.libreriaweb.libreria.servicios.UsuarioServicio;
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
    private PrestamoServicio prestamoservicio;
    @Autowired
    private UsuarioServicio usuarioservicio;

    @Autowired
    private EditorRepositorio editorrepositorio;
    @Autowired
    private AutorRepositorio autorrepositorio;
    @Autowired
    private PrestamoRepositorio prestamorepositorio;
    @Autowired
    private LibroRepositorio librorepositorio;
    @Autowired
    private ClienteRepositorio clienterepositorio;
    @Autowired
    private UsuarioRepositorio usuariorepositorio;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required=false)  String error,String loggout,ModelMap model) {
        if (error != null) {
        model.put("error","El usuario o claves son incorrectos");
        }
        
        return "login.html";
    }
    
    @GetMapping("/logginexitoso")
    public String logginexitoso(){
        
        return "logginexitoso.html";
    }
//        @GetMapping("/loggout")
//    public String loggout(){
//        
//        return "loggout.html";
//    }
    
  
    
    
      @GetMapping("/registro")
    public String registro() {
        return "registro.html";
    }
/*GETMAPPING AUTOR*/
    @GetMapping("/OpcionesAutor")
    private String OpcionesAutor() {
        return "OpcionesAutor.html";
    }
    
     @GetMapping("/verAutores")
    private String verAutores(ModelMap model) {
        List<Autor> autores = autorrepositorio.findAll();
        model.put("autores", autores);
       
        return "verAutores.html";
    }


/*GETMAPPING EDITORIAL*/
    @GetMapping("/OpcionesEditorial")
    private String OpcionesEditorial() {
        return "OpcionesEditorial.html";
    }
/*GETMAPPING LIBRO*/
    @GetMapping("/OpcionesLibro")
    private String OpcionesLibro(ModelMap model) {
        List<Editorial> editoriales = editorrepositorio.findAll();
        List<Autor> autores = autorrepositorio.findAll();
        model.put("editoriales", editoriales);/*pone a disposicion la lista de editoriales para el th*/
        model.put("autores", autores);/*pone a disposicion la lista de autores para el th*/
        return "OpcionesLibro.html";
    }
/*GETMAPPING CLIENTE*/
    @GetMapping("/OpcionesCliente")
    private String OpcionesCliente() {
        return "OpcionesCliente.html";
    }
/*GETMAPPING PRESTAMO*/
    @GetMapping("/OpcionesPrestamo")
    private String OpcionesPrestamo(ModelMap model) {
        List<Libro> libros = librorepositorio.findAll();
        List<Cliente> clientes = clienterepositorio.findAll();
        model.put("clientes", clientes);
        model.put("libros", libros);
        return "OpcionesPrestamo.html";
    }

   
    
    /*ACA COMIENZAN LOS POSTMAPPING*/

    
     /*AUTOR*/
    @PostMapping("/ingresarAutor")
    private String ingresarAutor(ModelMap modelo, @RequestParam String nombre) {

        try {

            autorservicio.guardarAutor(nombre);
        } catch (ErrorServicio ex) {
            modelo.put("nombre", nombre);
            modelo.put("error", ex.getMessage());
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return "OpcionesAutor.html";
        }
        modelo.put("exito", "El editor fue ingresado con éxito");
        return "OpcionesAutor.html";
    }
 
    /*EDITORIAL*/
    @PostMapping("/ingresarEditorial")
    private String ingresarEditorial(ModelMap modelo, @RequestParam String nombre) {
        /*Se utiliza ModelMap modelo, para interactuar con la vista a traves de thymeleaf*/
        try {
            editorialservicio.guardarEditorial(nombre);

        } catch (ErrorServicio ex) {
            modelo.put("nombre", nombre);/*para que el formulario carge los valores*/
            modelo.put("error", ex.getMessage());/*para que cargue el error en la vista*/
            return "OpcionesEditorial.html";
        }
        modelo.put("exito", "La editorial fue ingresada con éxito");
        return "OpcionesEditorial.html";
    }
    /*LIBRO*/ 
    @PostMapping("/ingresarLibro")
    public String ingresarLibro1(ModelMap modelo, Long isbn, String titulo, Integer anio, Integer ejemplares, String ideditorial, String idautor) {

        try {
            
            libroservicio.guardarLibro(isbn, titulo, anio, ejemplares, ideditorial, idautor);
        } catch (ErrorServicio ex) {
            List<Editorial> editoriales = editorrepositorio.findAll();
            List<Autor> autores = autorrepositorio.findAll();
            modelo.put("editoriales", editoriales);
            modelo.put("autores", autores);
            System.out.println("Se rompio en el error");
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("error", ex.getMessage());
            return "OpcionesLibro.html";

        }
        List<Editorial> editoriales = editorrepositorio.findAll();
        List<Autor> autores = autorrepositorio.findAll();
        modelo.put("editoriales", editoriales);
        modelo.put("autores", autores);

        modelo.put("exito", "El libro fue ingresado con éxito");
        return "OpcionesLibro.html";
//       return "redirect:/OpcionesLibro";

    }
   /*CLIENTE*/
    @PostMapping("/ingresarCliente")
    private String ingresarCliente(ModelMap modelo, @RequestParam MultipartFile archivo, Long dni, String nombre, String apellido, String telefono, String sexo) {

        try {

            clienteservicio.guardarCliente(archivo, dni, nombre, apellido, telefono, sexo);
        } catch (ErrorServicio ex) {
            modelo.put("nombre", nombre);
            modelo.put("dni", dni);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("error", ex.getMessage());

            return "OpcionesCliente.html";
        }
        modelo.put("exito", "El cliente fue ingresada con éxito");
        return "OpcionesCliente.html";
    }
    /*PRESTAMO*/
    @PostMapping("/ingresarPrestamo")
    private String ingresarPrestamo(ModelMap model, String idlibro, String idcliente) {

        try {
            prestamoservicio.crearPrestamo(idlibro, idcliente);
        } catch (ErrorServicio ex) {
                model.put("error", ex.getMessage());
            return "OpcionesPrestamo.html";
        } 
        List<Libro> libros = librorepositorio.findAll();
        List<Cliente> clientes = clienterepositorio.findAll();
        model.put("clientes", clientes);
        model.put("libros", libros);
        model.put("exito", "El prestamo fue creado con éxito");
        return "OpcionesPrestamo.html";
    }
    /*USUARIO*/
    @PostMapping("/registrarUsuario")
    private String registrarUsuario(ModelMap modelo, String nombre, String apellido, String clave,String mail) {

        try {
            usuarioservicio.guardarUsuario(nombre, apellido, clave,mail);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            return "registro.html";
        }
        modelo.put("exito", "El ingreso del usuario fue exitoso.");
        return "registro.html";

    }
   

}
