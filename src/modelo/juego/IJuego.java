package modelo.juego;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.baraja.IMazo;
import modelo.jugador.IJugador;

import java.rmi.RemoteException;

public interface IJuego extends IObservableRemoto {

    int getJugadoresConectados() throws RemoteException;

    int getCantidadJugadores() throws RemoteException;

    int getIdUltimoJugador() throws RemoteException;

    IJugador getJugador(int idJugador) throws RemoteException;

    IMazo getMazo() throws RemoteException;

    void agregarJugador(String nombre, int id) throws RemoteException;

    void jugarRonda() throws RemoteException;
}
