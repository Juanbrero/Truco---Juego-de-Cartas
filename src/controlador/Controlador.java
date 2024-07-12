package controlador;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import vista.IVista;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Controlador implements IControladorRemoto, Serializable {

    private IVista vista;
    private int id;

    public void setVista(IVista vista) {
        this.vista = vista;
    }

    public void conectarse(String nombre) {

        try {
            this.id = juego.getJugadoresConectados();
            juego.agregarJugador(nombre);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void inicio() throws RemoteException {

    }



    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {

    }

    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {

    }
}
