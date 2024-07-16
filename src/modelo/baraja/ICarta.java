package modelo.baraja;

import vista.VistaCarta;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface ICarta extends Serializable {

    boolean isEnMazo();

    void setId(int i);

    void setEnMazo(boolean b);

    void setEnMano(boolean b);

    int getNro();

    Palo getPalo();

}
