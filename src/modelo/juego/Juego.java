package modelo.juego;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import modelo.baraja.IMazo;
import modelo.baraja.Mazo;
import modelo.equipo.Equipo;
import modelo.equipo.IEquipo;
import modelo.eventos.Evento;
import modelo.jugador.IJugador;
import modelo.jugador.Jugador;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Juego extends ObservableRemoto implements IJuego {

    private static Juego instancia;
    private IMazo mazo;
    private int cantidadJugadores= 2;
    private int jugadoresConectados = 0;
    private Equipo equipo1;
    private Equipo equipo2;
    private int rondaActual = 1;
    private int idUltimoJugador;

    private Juego() {
        this.mazo = new Mazo();
        this.equipo1 = new Equipo(1);
        this.equipo2 = new Equipo(2);

    }

    public static Juego getInstancia() {
        if (Juego.instancia == null) {
            Juego.instancia = new Juego();
        }
        return Juego.instancia;
    }

    public int getJugadoresConectados() throws RemoteException {
        return jugadoresConectados;
    }

    public int getCantidadJugadores() throws RemoteException {
        return cantidadJugadores;
    }

    @Override
    public int getIdUltimoJugador() throws RemoteException {
        return this.idUltimoJugador;
    }

    @Override
    public IJugador getJugador(int idJugador) throws RemoteException {

        IJugador jugador = null;

        System.out.println("jugadore en equipo 1: " + equipo1.getJugadores().size());
        System.out.println("jugadore en equipo 2: " + equipo2.getJugadores().size());

        for (int i = 0; i < cantidadJugadores/2; i++) {

            if (equipo1.getJugadores().get(i).getId() == idJugador) {
                jugador = equipo1.getJugadores().get(i);
                break;
            }
            else if (equipo2.getJugadores().get(i).getId() == idJugador) {
                jugador = equipo2.getJugadores().get(i);
                break;
            }
        }
        System.out.println("Id jugador buscado: " + jugador.getId());
        return jugador;
    }

    @Override
    public IMazo getMazo() throws RemoteException{
        return this.mazo;
    }

    public void agregarJugador(String nombre, int id) throws RemoteException {

        IJugador nuevoJugador = (IJugador) new Jugador(nombre, id);

        if (jugadoresConectados % 2 == 0) {
            this.equipo1.agregarJugador(nuevoJugador);
        }
        else {
            this.equipo2.agregarJugador(nuevoJugador);
        }

        this.jugadoresConectados++;
        this.idUltimoJugador = nuevoJugador.getId();
        System.out.println("id ultimo jugador: "+ idUltimoJugador);
        notificarObservadores(Evento.JUGADOR_CONECTADO);

        if(jugadoresConectados == cantidadJugadores) {
            iniciarJuego();
        }

    }

    private void iniciarJuego() throws RemoteException {

        notificarObservadores(Evento.START);
        mazo.mezclar();
        jugarRonda();

    }

    public void jugarRonda() throws RemoteException{

        ArrayList<IJugador> jugadores = new ArrayList<>();

        for(int i = 0; i < cantidadJugadores/2; i++){
            jugadores.add(equipo1.getJugadores().get(i));
            jugadores.add(equipo2.getJugadores().get(i));
        }

        Ronda nuevaRonda = new Ronda(this, rondaActual, jugadores);

        nuevaRonda.repartirCartas();

        notificarObservadores(Evento.INICIO_RONDA);
        this.rondaActual++;
    }



}
