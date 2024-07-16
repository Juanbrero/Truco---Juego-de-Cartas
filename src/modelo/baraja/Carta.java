package modelo.baraja;

import vista.VistaCarta;

import java.rmi.RemoteException;


public class Carta implements ICarta {

    private Palo palo;
    private int nro;
    private boolean enMazo;
    private boolean enMano;
    private boolean enMesa;
    private double valor;

    public Carta(Palo palo, int nro) {
        this.palo = palo;
        this.nro = nro;
        this.setEnMazo(false);
        this.setEnMano(false);
        this.setEnMesa(false);

        // Asigno una escala de valores del menos significativo al mas significativo.
        switch (nro) {
            case 4:
                this.valor = 0;
                break;
            case 5:
                this.valor = 1;
                break;
            case 6:
                this.valor = 2;
                break;
            case 7:
                if (this.palo.equals(Palo.BASTO) || this.palo.equals(Palo.COPA)) {
                    this.valor = 3;
                }
                else if (this.palo.equals(Palo.ORO)) {
                    this.valor = 10;
                }
                else {
                    this.valor = 11;
                }
                break;
            case 10:
                this.valor = 4;
                break;
            case 11:
                this.valor = 5;
                break;
            case 12:
                this.valor = 6;
                break;
            case 1:
                if (this.palo.equals(Palo.COPA) || this.palo.equals(Palo.ORO)) {
                    this.valor = 7;
                }
                else if (this.palo.equals(Palo.BASTO)) {
                    this.valor = 12;
                }
                else {
                    this.valor = 13;
                }
                break;
            case 2:
                this.valor = 8;
                break;
            case 3:
                this.valor = 9;
                break;
        }
    }

    public Palo getPalo() {
        return palo;
    }

    public int getNro() {
        return nro;
    }

    public boolean isEnMazo() {
        return enMazo;
    }

    @Override
    public void setId(int i) {

    }

    public void setEnMazo(boolean enMazo) {
        this.enMazo = enMazo;
    }


    public boolean isEnMano() {
        return enMano;
    }

    public void setEnMano(boolean enMano) {
        this.enMano = enMano;
    }

    public boolean isEnMesa() {
        return enMesa;
    }

    public void setEnMesa(boolean enMesa) {
        this.enMesa = enMesa;
    }

    public double getValor() {
        return valor;
    }

}
