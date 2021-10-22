/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package es.ujaen.dae.ujacovid.beans;

import es.ujaen.dae.ujacovid.entidades.Contacto;
import es.ujaen.dae.ujacovid.entidades.Rastreador;
import es.ujaen.dae.ujacovid.entidades.Usuario;
import static es.ujaen.dae.ujacovid.entidades.Usuario.Estado.Positivo;
import es.ujaen.dae.ujacovid.excepciones.ContactosRepetidos;
import es.ujaen.dae.ujacovid.excepciones.ListaContactosVacia;
import es.ujaen.dae.ujacovid.excepciones.UsuarioNoExiste;
import es.ujaen.dae.ujacovid.excepciones.RastreadorYaRegistrado;
import es.ujaen.dae.ujacovid.excepciones.UsuarioYaRegistrado;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ServicioUjaCovid {

    Map<String, Rastreador> rastreadores;
    Map<String, Usuario> usuarios;
    Map<String, Contacto> lista_contactos;
    int num_infectados_total;
    int num_infectados_actual;

    public ServicioUjaCovid() {
        rastreadores = new TreeMap<>();
        usuarios = new TreeMap<>();
        lista_contactos = new TreeMap<>();
    }

    /**
     * Dar de alta usuario
     *
     * @param usuario el usuario a dar de alta
     */
    public Usuario altaUsuario(@NotNull @Valid Usuario usuario) {

        if (usuarios.containsKey(usuario.getId())) {
            throw new UsuarioYaRegistrado();
        }
        // Registrar cliente
        usuarios.put(usuario.getId(), usuario);

        return usuario;
    }

    public Rastreador altaRastreador(@NotNull @Valid Rastreador rastreador) {
        if (rastreadores.containsKey(rastreador.getDNI())) {
            throw new RastreadorYaRegistrado();
        }
        // Registrar cliente
        rastreadores.put(rastreador.getDNI(), rastreador);

        return rastreador;

    }

    public void notificarPositivo(String id, LocalDateTime fechaPositivo, Rastreador rastreador) {
        if (!usuarios.containsKey(id)) {
            throw new UsuarioNoExiste();
        }
        if (fechaPositivo == null) {
            fechaPositivo = LocalDateTime.now();
        }
        usuarios.get(id).setEstado(Usuario.Estado.Positivo);
        num_infectados_total++;
        num_infectados_actual++;
        rastreador.incrementarPositivosReportados();
        usuarios.get(id).setFecha_positivo(fechaPositivo);

    }

    public void notificarCurado(String id) {
        if (!usuarios.containsKey(id)) {
            throw new UsuarioNoExiste();
        }
        usuarios.get(id).setEstado(Usuario.Estado.Curado);
        num_infectados_actual++;
    }

    public void notificarContacto(String id, ArrayList<Contacto> contactos) {

        if (!usuarios.containsKey(id)) {
            throw new UsuarioNoExiste();
        } else {
            if (contactos.isEmpty()) {
                throw new ListaContactosVacia();
            } else {
                for (int i = 0; i < contactos.size(); i++) {
                    if (lista_contactos.containsKey(contactos.get(i))) {
                        // Si el usuario es contacto de su contacto:
                        if (lista_contactos.containsKey(id)
                                && lista_contactos.get(id).getId_usuario()
                                == contactos.get(i).getId_contacto()) {
                            throw new ContactosRepetidos();
                        }

                        lista_contactos.get(contactos.get(i)).setFecha_contacto(contactos.get(i).getFecha_contacto());
                    } else {
                        lista_contactos.put(id, new Contacto(contactos.get(i).getId_contacto(), id, contactos.get(i).getFecha_contacto()));
                    }

                }

            }
        }

        Iterator<String> itr = lista_contactos.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (lista_contactos.get(key).getFecha_contacto().plusDays(31) == LocalDateTime.now()) {
                itr.remove();
            }
        }

    }

    public ArrayList<String> ListarContactos(String id) {
        ArrayList<String> listado = new ArrayList();
        Iterator<String> itr = lista_contactos.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (lista_contactos.get(key).getId_usuario() == id
                    && usuarios.get(id).getFecha_positivo().minusDays(14).isBefore(lista_contactos.get(key).getFecha_contacto())
                    || usuarios.get(id).getFecha_positivo().minusDays(14).isEqual(lista_contactos.get(key).getFecha_contacto())) {
                listado.add(id);
            }
        }
        return listado;
    }

    public int CalcularPositivos() {
        int positivos = 0;
        Iterator<String> itr = usuarios.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (usuarios.get(key).getEstado() == Positivo && (usuarios.get(key).getFecha_positivo().minusDays(15).isBefore(LocalDateTime.now())
                    || usuarios.get(key).getFecha_positivo().minusDays(15).isEqual(LocalDateTime.now()))) {
                positivos++;
            }
        }
        return positivos;
    }

    public double CalcularMediaContactos() {
        int usuarios_positivos = 0;

        Iterator<String> itr = usuarios.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (usuarios.get(key).getEstado() == Positivo) {
                usuarios_positivos++;
            }
        }
        return lista_contactos.size() / usuarios_positivos;
    }

    public int ObtenerNum_infectados_total() {
        return num_infectados_total;
    }

    public int ObtenerNum_infectados_actual() {
        return num_infectados_actual;
    }

}
