package modelo.baraja;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Mazo {

    private List<ICarta> cartasEnMazo = new ArrayList<>();
    private static final int MAX_CARTAS = 40;
    private int cantidadCartasEnMazo = 0;

    public Mazo() {

        for (Palo palo : Palo.values()) {

            for (int nroCarta = 1; nroCarta <= 12; nroCarta++) {

                if((nroCarta != 8) || (nroCarta != 9)) {

                    Carta carta = new Carta(palo, nroCarta);
                    carta.setEnMazo(true);
                    cartasEnMazo.add(carta);
                }

            }
        }
        this.cantidadCartasEnMazo = MAX_CARTAS;
    }

    public int getCantidadCartasEnMazo() {
        return cantidadCartasEnMazo;
    }


    public void mezclar() {
        Collections.shuffle(cartasEnMazo);
    }

    public ArrayList<ICarta> repartir() {

        int indice = 0;
        ArrayList<ICarta> cartas = new ArrayList<>();
        while (indice < MAX_CARTAS && cantidadCartasEnMazo > 0 && cartas.size() < 3) {

            // REVISAR USO DE SET ID

            if (cartasEnMazo.get(indice).isEnMazo()) {

                cartas.add(cartasEnMazo.get(indice));
                cartasEnMazo.get(indice).setId(cartas.size() - 1);
                cartasEnMazo.get(indice).setEnMazo(false);
                this.cantidadCartasEnMazo--;
            }

            indice++;
        }
        return cartas;
    }

    public void juntarCartas() {

        int indice = 0;
        while (cantidadCartasEnMazo < MAX_CARTAS) {
            this.cartasEnMazo.get(indice).setEnMazo(true);
            this.cartasEnMazo.get(indice).setId(0);
            this.cantidadCartasEnMazo++;
            indice++;
        }
    }

}
