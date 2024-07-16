package vista;

import modelo.baraja.ICarta;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IVista {


    public void iniciar();

    void colaDeEspera(int jugadoresConectados, int cantidadJugadores);

    void iniciarPartida();

    void generarCartas(ArrayList<ICarta> cartas) throws RemoteException;
}
