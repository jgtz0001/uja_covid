/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujacovid.entidades;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author PCJoseGabriel
 */
public class Contacto {
    
   
    private String id_usuario;
    private String id_contacto;
    private LocalDateTime fecha_contacto;
    
    public Contacto (){
    
    }
    
    public Contacto(String id_contacto,String id_usuario, LocalDateTime fecha_contacto) {
       
        this.id_contacto = id_contacto;
        this.id_usuario = id_usuario;
        this.fecha_contacto = fecha_contacto;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getId_contacto() {
        return id_contacto;
    }

    public LocalDateTime getFecha_contacto() {
        return fecha_contacto;
    }

    public void setFecha_contacto(LocalDateTime fecha_contacto) {
        this.fecha_contacto = fecha_contacto;
    }

    
    
    
}
