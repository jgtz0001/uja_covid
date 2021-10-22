/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.ujacovid.app;

import es.ujaen.dae.ujacovid.entidades.Rastreador;
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
public class TestUnitarioRastreador {

    @Test
    void testValidacionRastreador() {

        Rastreador rastreador = new Rastreador(
                "25036257N",
                "Jose gabriel",
                "678987654",
                "password"
        );

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Rastreador>> violations = validator.validate(rastreador);
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    void testValidacionRastreadorMal() {

        Rastreador rastreador = new Rastreador(
                "250362558585885858587N",
                "Jose gabriel",
                "678987654",
                "password"
        );

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Rastreador>> violations = validator.validate(rastreador);
        Assertions.assertThat(violations).isNotEmpty();
    }

}
