package modelo.juego;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import modelo.baraja.Mazo;
import modelo.equipo.Equipo;
import modelo.equipo.IEquipo;
import modelo.eventos.Evento;
import modelo.jugador.IJugador;
import modelo.jugador.Jugador;

import java.rmi.RemoteException;
import java.util.Observer;

public class Juego extends ObservableRemoto implements IJuego {

    private static Juego instancia;
    private Mazo mazo;
    private int cantidadJugadores= 2;
    private int jugadoresConectados = 0;
    private Equipo equipo1;
    private Equipo equipo2;

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

    public IJugador agregarJugador(String nombre) throws RemoteException {

        IJugador nuevoJugador = (IJugador) new Jugador(nombre, jugadoresConectados);

        if (jugadoresConectados == 0 || jugadoresConectados == 2 || jugadoresConectados == 4) {
            this.equipo1.agregarJugador(nuevoJugador);
        }
        else {
            this.equipo2.agregarJugador(nuevoJugador);
        }

        this.jugadoresConectados++;

        notificarObservadores(Evento.JUGADOR_CONECTADO);

        return nuevoJugador;
    }

    public void iniciarJuego() throws RemoteException{

        if(jugadoresConectados == cantidadJugadores) {
            notificarObservadores(Evento.START);
            mazo.mezclar();
            jugarRonda();
        }
    }

    private void jugarRonda() {
    }

}
