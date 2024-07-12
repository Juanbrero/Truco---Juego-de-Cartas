package modelo.juego;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.jugador.IJugador;

import java.rmi.RemoteException;

public interface IJuego extends IObservableRemoto {

    int getJugadoresConectados() throws RemoteException;

    IJugador agregarJugador(String nombre) throws RemoteException;

    int getCantidadJugadores() throws RemoteException;
}
