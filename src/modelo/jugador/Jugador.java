package modelo.jugador;

import modelo.baraja.ICarta;

import java.util.ArrayList;
import java.util.List;

public class Jugador implements IJugador {

    private String nombre;
    private int id;
    private List<ICarta> cartasEnMano = new ArrayList<>();

    public Jugador(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public ArrayList<ICarta> getCartasEnMano() {
        return (ArrayList<ICarta>) cartasEnMano;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public void recibirCartas(ArrayList<ICarta> cartas) {
        this.cartasEnMano = cartas;
        for (ICarta carta : cartasEnMano) {
            carta.setEnMano(true);
        }
    }
}
