/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujacovid.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author PCJoseGabriel
 */
public class Usuario implements Serializable{
    
    private String id = UUID.randomUUID().toString();
    private String telefono;
    private LocalDateTime fecha_alta;
    private LocalDateTime fecha_positivo;
    @Enumerated(EnumType.STRING)
    private Estado estado;
     public enum Estado {
        Positivo,
        Negativo,
        Curado;
    }
   

    public Usuario(String id ,String telefono, LocalDateTime fecha_alta, LocalDateTime fecha_positivo) {
        this.id = id;
        this.telefono = telefono;
        this.fecha_alta = fecha_alta;
        this.fecha_positivo = fecha_positivo;
        this.estado = Estado.Negativo;
       
    }
       public Usuario(String id ,String telefono, LocalDateTime fecha_alta, LocalDateTime fecha_positivo, String estado) {
        this.id = id;
        this.telefono = telefono;
        this.fecha_alta = fecha_alta;
        this.fecha_positivo = fecha_positivo;
        this.estado = Estado.Negativo;
        
    }
  
    
  
   
      public Usuario() {

    }

    public String getId() {
        return id;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDateTime getFecha_alta() {
        return fecha_alta;
    }

    public LocalDateTime getFecha_positivo() {
        return fecha_positivo;
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * @param fecha_positivo the fecha_positivo to set
     */
    public void setFecha_positivo(LocalDateTime fecha_positivo) {
        this.fecha_positivo = fecha_positivo;
    }


}
