package es.ujaen.dae.ujacovid.app;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
import es.ujaen.dae.ujacovid.beans.ServicioUjaCovid;
import es.ujaen.dae.ujacovid.entidades.Contacto;
import es.ujaen.dae.ujacovid.entidades.Rastreador;
import es.ujaen.dae.ujacovid.entidades.Usuario;
import es.ujaen.dae.ujacovid.entidades.Usuario.Estado;
import static es.ujaen.dae.ujacovid.entidades.Usuario.Estado.Positivo;
import es.ujaen.dae.ujacovid.excepciones.RastreadorYaRegistrado;
import es.ujaen.dae.ujacovid.excepciones.UsuarioYaRegistrado;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author PCJoseGabriel
 */
@SpringBootTest(classes = es.ujaen.dae.ujacovid.app.UjacovidApplication.class)
public class UjacovidIntegrationTest {

    public UjacovidIntegrationTest() {

    }

    @Autowired
    ServicioUjaCovid servicovid;

    @Test
    public void testAccesoServicioUjacovid() {
        Assertions.assertThat(servicovid).isNotNull();
    }

    @Test
    public void testAltaUsuarioDuplicado() {
        Usuario usuario = new Usuario(
                "zafra",
                "678909876",
                LocalDateTime.of(2021, 11, 1, 1, 1, 1),
                null,
                "Negativo"
        );

        servicovid.altaUsuario(usuario);
        Assertions.assertThatThrownBy(() -> {
            servicovid.altaUsuario(usuario);
        })
                .isInstanceOf(UsuarioYaRegistrado.class);
    }

    @Test
    public void testAltaRastreadorDuplicado() {
        Rastreador rastreador = new Rastreador(
                "25036257N",
                "Jose gabriel",
                "678987654",
                "password"
        );

        servicovid.altaRastreador(rastreador);
        Assertions.assertThatThrownBy(() -> {
            servicovid.altaRastreador(rastreador);
        })
                .isInstanceOf(RastreadorYaRegistrado.class);
    }

    @Test
    void testNotificarPositivo() {

        Usuario usuario = new Usuario(
                "zafra",
                "678909876",
                LocalDateTime.of(2021, 11, 1, 1, 1, 1),
                null,
                "Negativo"
        );
        Rastreador rastreador = new Rastreador(
                "12345678K",
                "jose gabriel",
                "665489785",
                "1234566789"
        );

        servicovid.notificarPositivo(usuario.getId(), usuario.getFecha_positivo(), rastreador);
        Assertions.assertThat(usuario.getEstado().equals(Estado.Positivo));
        Assertions.assertThat(LocalDateTime.now().equals(usuario.getFecha_positivo()));

    }
      @Test
    void testNotificarCurado() {

        Usuario usuario = new Usuario(
                "zafra",
                "678909876",
                LocalDateTime.of(2021, 11, 1, 1, 1, 1),
                null,
                "Negativo"
        );
        Rastreador rastreador = new Rastreador(
                "12345678K",
                "jose gabriel",
                "665489785",
                "1234566789"
        );
     
        servicovid.notificarPositivo(usuario.getId(), usuario.getFecha_positivo(), rastreador); 
        Assertions.assertThat(LocalDateTime.now().equals(usuario.getFecha_positivo()));
        servicovid.notificarCurado(usuario.getId());
        Assertions.assertThat(usuario.getEstado().equals(Estado.Curado));
    

    }

    @Test
    void testNotificarContactosyObtenerContactos() {
        ArrayList<Contacto> contactos;
        contactos = new ArrayList<Contacto>();

        Rastreador rastreador = new Rastreador(
                "12345678K",
                "jose gabriel",
                "665489785",
                "1234566789"
        );
        servicovid.altaRastreador(rastreador);

        Usuario usuario = new Usuario(
                "zafra",
                "678909876",
                LocalDateTime.of(2021, 11, 1, 1, 1, 1),
                null,
                "Negativo"
        );
        servicovid.altaUsuario(usuario);
        servicovid.notificarPositivo(usuario.getId(), usuario.getFecha_positivo(), rastreador);

        Usuario usuario2 = new Usuario(
                "zafra",
                "678909877",
                LocalDateTime.of(2021, 11, 1, 1, 1, 1),
                null,
                "Negativo"
        );
        servicovid.altaUsuario(usuario2);
        servicovid.notificarContacto(usuario.getId(), contactos);

        Usuario usuario3 = new Usuario(
                "zafra",
                "678909878",
                LocalDateTime.of(2021, 11, 1, 1, 1, 1),
                null,
                "Negativo"
        );
        servicovid.altaUsuario(usuario3);
        servicovid.notificarContacto(usuario.getId(), contactos);

        Usuario usuario4 = new Usuario(
                "zafra",
                "678909879",
                LocalDateTime.of(2021, 11, 1, 1, 1, 1),
                null,
                "Negativo"
        );
        servicovid.altaUsuario(usuario4);
        servicovid.notificarContacto(usuario.getId(), contactos);
        
         Assertions.assertThat(contactos.size() == 4);
         servicovid.ListarContactos(usuario.getId());
         Assertions.assertThat(servicovid.ListarContactos(usuario.getId()).equals(contactos));
         
    }
    
      @Test
      
    void testEstadisticas() {
       
        ArrayList<Contacto> contactos;
        contactos = new ArrayList<Contacto>();

        Rastreador rastreador = new Rastreador(
                "12345678K",
                "jose gabriel",
                "665489785",
                "1234566789"
        );
        int positivos_reportados = rastreador.getPositivos_reportados();
        servicovid.altaRastreador(rastreador);

        Usuario usuario = new Usuario(
                "zafra",
                "678909876",
                LocalDateTime.of(2021, 11, 1, 1, 1, 1),
                null,
                "Negativo"
        );
        servicovid.altaUsuario(usuario);
        servicovid.notificarPositivo(usuario.getId(), usuario.getFecha_positivo(), rastreador);

        Usuario usuario2 = new Usuario(
                "zafra",
                "678909877",
                LocalDateTime.of(2021, 11, 1, 1, 1, 1),
                null,
                "Negativo"
        );
        servicovid.altaUsuario(usuario2);
        servicovid.notificarContacto(usuario.getId(), contactos);

        Usuario usuario3 = new Usuario(
                "zafra",
                "678909878",
                LocalDateTime.of(2021, 11, 1, 1, 1, 1),
                null,
                "Negativo"
        );
        servicovid.altaUsuario(usuario3);
        servicovid.notificarContacto(usuario.getId(), contactos);

        Usuario usuario4 = new Usuario(
                "zafra",
                "678909879",
                LocalDateTime.of(2021, 11, 1, 1, 1, 1),
                null,
                "Negativo"
                        
        );
        servicovid.altaUsuario(usuario4);
        servicovid.notificarContacto(usuario.getId(), contactos);

        
        
         servicovid.notificarPositivo(usuario2.getId(), usuario2.getFecha_positivo(), rastreador);
         servicovid.ObtenerNum_infectados_total();
         Assertions.assertThat(servicovid.ObtenerNum_infectados_total() == 2);
         servicovid.ObtenerNum_infectados_actual();
         Assertions.assertThat(servicovid.ObtenerNum_infectados_actual()== 2);
         servicovid.CalcularPositivos();
         Assertions.assertThat(servicovid.CalcularPositivos()== 2);
         servicovid.CalcularMediaContactos();
         Assertions.assertThat(servicovid.CalcularMediaContactos()== 2.0);
         servicovid.notificarPositivo(usuario3.getId(), usuario3.getFecha_positivo(), rastreador);
         Assertions.assertThat(servicovid.ObtenerNum_infectados_total()== positivos_reportados);
         
    }
}
