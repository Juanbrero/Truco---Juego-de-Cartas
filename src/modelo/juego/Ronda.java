package modelo.juego;

import modelo.jugador.IJugador;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Ronda {

    private IJuego modelo;
    private List<IJugador> jugadores = new ArrayList<>();
    private int nroRonda;

    public Ronda(IJuego modelo, int nroRonda, ArrayList<IJugador> jugadores) {
        this.modelo = modelo;
        this.nroRonda = nroRonda;
        int mano = (nroRonda - 1) % jugadores.size();  // Cada nueva ronda va a comenzar por el proximo jugador a la derecha.
        acomodarJugadoresPorOrden(mano, jugadores);

    }

    private void acomodarJugadoresPorOrden(int mano, ArrayList<IJugador> jugadores) {

        int jugador = mano;
        for (int i = 0; i < jugadores.size(); i++) {

            this.jugadores.add(i, jugadores.get(jugador));
            jugador = (jugador + 1) % jugadores.size();
        }
    }

    public void repartirCartas() throws RemoteException {

        for (IJugador jugador : jugadores) {

            jugador.recibirCartas(modelo.getMazo().repartir());

        }
    }
}
