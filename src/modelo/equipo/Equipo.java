package modelo.equipo;

import modelo.jugador.IJugador;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private int idEquipo;
    private List<IJugador> jugadores = new ArrayList<IJugador>();
    private int pts = 0;

    public Equipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public void agregarJugador(IJugador jugador) throws RemoteException {
        jugadores.add(jugador);
    }

}
