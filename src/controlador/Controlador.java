package controlador;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import modelo.eventos.Evento;
import modelo.juego.IJuego;
import modelo.jugador.IJugador;
import vista.IVista;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Random;

public class Controlador implements IControladorRemoto, Serializable {

    private IVista vista;
    private IJuego juego;
    private IJugador jugador;
    private int id;

    public void setVista(IVista vista) {
        this.vista = vista;
    }

    public IJugador getJugador() {
        return jugador;
    }

    public void conectarse(String nombre) {

        try {
            Random rand = new Random();
            id = rand.nextInt();
            juego.agregarJugador(nombre, id);

        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
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
                case INICIO_RONDA:
                    updateInicioRonda();

            }
        }
    }

    private void updateJugadorConectado(){
        try {
            this.jugador = this.juego.getJugador(this.id);

            if (this.juego.getJugadoresConectados() < this.juego.getCantidadJugadores()) {

                /* Solo agrego a la cola de espera cuando le dan al boton start.*/
                if((this.juego.getIdUltimoJugador()) == this.jugador.getId()){

                    vista.colaDeEspera(this.juego.getJugadoresConectados(), this.juego.getCantidadJugadores());
                }
            }

        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateInicioRonda() throws RemoteException {
        this.jugador = juego.getJugador(this.jugador.getId());
        vista.generarCartas(this.jugador.getCartasEnMano());

    }

}
