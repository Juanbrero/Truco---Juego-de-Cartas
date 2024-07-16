package modelo.baraja;

import java.util.ArrayList;
import java.util.Collections;

public interface IMazo {

    int getCantidadCartasEnMazo();


    void mezclar();

    ArrayList<ICarta> repartir();

    void juntarCartas();

}
