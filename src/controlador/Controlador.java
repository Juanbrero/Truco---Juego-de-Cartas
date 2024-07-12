package controlador;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.eventos.Evento;
import modelo.juego.IJuego;
import modelo.jugador.IJugador;
import vista.IVista;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Controlador implements IControladorRemoto, Serializable {

    private IVista vista;
    private IJuego juego;
    private IJugador jugador;
    private int id;

    public void setVista(IVista vista) {
        this.vista = vista;
    }

    public void conectarse(String nombre) {

        try {
            this.id = juego.getJugadoresConectados();
            this.jugador = juego.agregarJugador(nombre);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void inicio() throws RemoteException {
        this.juego.iniciarJuego();
    }



    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
        this.juego = (IJuego) modeloRemoto;
    }

    @Override
    public void actualizar(IObservableRemoto modelo, Object evento) throws RemoteException {

        if (evento instanceof Evento) {
            switch ((Evento) evento) {
                case JUGADOR_CONECTADO:
                    updateJugadorConectado();
                    break;
                case START:
                    vista.iniciarPartida();
            }
        }
    }

    private void updateJugadorConectado(){
        try {

            if (this.juego.getJugadoresConectados() < this.juego.getCantidadJugadores()) {

                /* Solo agrego a la cola de espera cuando le dan al boton start.*/
                if((this.juego.getJugadoresConectados() - 1) == this.id){

                    vista.colaDeEspera(this.juego.getJugadoresConectados(), this.juego.getCantidadJugadores());
                }
            }

        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
