package modelo.equipo;

import modelo.jugador.IJugador;

import java.rmi.RemoteException;

public interface IEquipo {

    void agregarJugador(IJugador jugador) throws RemoteException;
}
