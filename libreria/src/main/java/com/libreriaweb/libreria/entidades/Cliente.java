    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.libreria.entidades;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Federico
 */
@Entity
public class Cliente implements Serializable {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
    private Long dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String sexo;
    
    @Temporal(TemporalType.DATE)
    private Date Alta;
    @Temporal(TemporalType.DATE)
    private Date Baja;
    @OneToOne
    private FotoCliente fotocliente;
    
    
      public Cliente() {    
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getAlta() {
        return Alta;
    }

    public void setAlta(Date Alta) {
        this.Alta = Alta;
    }

    public Date getBaja() {
        return Baja;
    }

    public void setBaja(Date Baja) {
        this.Baja = Baja;
    }


    public FotoCliente getFotocliente() {
        return fotocliente;
    }

    public void setFotocliente(FotoCliente fotocliente) {
        this.fotocliente = fotocliente;
    }

   

  
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}
