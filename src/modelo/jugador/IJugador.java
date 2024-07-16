package modelo.jugador;

import modelo.baraja.ICarta;

import java.io.Serializable;
import java.util.ArrayList;

public interface IJugador extends Serializable {

    ArrayList<ICarta> getCartasEnMano();

    int getId();

    String getNombre();

    void recibirCartas(ArrayList<ICarta> cartas);
}
