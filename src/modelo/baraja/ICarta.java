package modelo.baraja;

import java.io.Serializable;

public interface ICarta extends Serializable {

    boolean isEnMazo();

    void setId(int i);

    void setEnMazo(boolean b);
}
