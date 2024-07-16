package modelo.equipo;

import modelo.jugador.IJugador;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Equipo implements IEquipo {

    private int idEquipo;
    private ArrayList<IJugador> jugadores = new ArrayList<IJugador>();
    private int pts = 0;

    public Equipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    @Override
    public ArrayList<IJugador> getJugadores() throws RemoteException {

        return this.jugadores;
    }

    public void agregarJugador(IJugador jugador) throws RemoteException {
        jugadores.add(jugador);
    }

}
