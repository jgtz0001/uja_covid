/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujacovid.entidades;
import java.io.Serializable;
/**
 *
 * @author PCJoseGabriel
 */
public class Rastreador implements Serializable{
    
    private String DNI;
    private String nombre_completo;
    private String telefono;
    private String contraseña;
    private int positivos_reportados;

    public Rastreador(String DNI, String nombre_completo, String telefono, String contraseña) {
        this.DNI = DNI;
        this.nombre_completo = nombre_completo;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.positivos_reportados = 0;
    }

    public int getPositivos_reportados() {
        return positivos_reportados;
    }
    

  public Rastreador() {

    }

    public String getDNI() {
        return DNI;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getContraseña() {
        return contraseña;
    }
  
    public void incrementarPositivosReportados(){
        this.positivos_reportados++;
    }
}