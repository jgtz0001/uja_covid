/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujacovid.app;
import es.ujaen.dae.ujacovid.entidades.Rastreador;
import es.ujaen.dae.ujacovid.entidades.Usuario;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author PCJoseGabriel
 */
public class TestUnitarioUsuario {
     public TestUnitarioUsuario (){
        
    }
    

    @Test
    void testValidacionUsuario() {

        Usuario usuario = new Usuario(
        "zafra",
        "678909876",
         LocalDateTime.of(1990, 11, 1,1,1,1),
         null,
         "Negativo"
        );
                
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);      
        Assertions.assertThat(violations).isNotEmpty();
    }
    
    
    @Test
    void testValidacionUsuarioMal() {

        Usuario usuario = new Usuario(
        "",
        "",
         LocalDateTime.of(1990, 11, 1,1,1,1),
         null,
         ""
        );
                
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);      
        Assertions.assertThat(violations).isNotEmpty();
    }
    
  
}
