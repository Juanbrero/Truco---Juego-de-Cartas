package modelo.equipo;

import modelo.jugador.IJugador;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IEquipo {

    ArrayList<IJugador> getJugadores() throws RemoteException;

    void agregarJugador(IJugador jugador) throws RemoteException;
}
