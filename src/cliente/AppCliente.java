package cliente;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.*;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import controlador.Controlador;
import vista.IVista;
import vista.VentanaPrincipal;


public class AppCliente {

    public static void main(String[] args) {

        final ArrayList<String>[] ips = new ArrayList[]{Util.getIpDisponibles()};
        String ip = "192.168.0.32";
//        String ip = (String) JOptionPane.showInputDialog(
//                null,
//                "Seleccione la IP en la que escuchará peticiones el cliente", "IP del cliente",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                ips[0].toArray(),
//                null
//        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
        String ipServidor = "192.168.0.32";
//        String ipServidor = (String) JOptionPane.showInputDialog(
//                null,
//                "Seleccione la IP en la corre el servidor", "IP del servidor",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                null,
//                null
//        );
        String portServidor = "8888";
//        String portServidor = (String) JOptionPane.showInputDialog(
//                null,
//                "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                null,
//                8888
//        );

        Controlador controlador = new Controlador();

        Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));

        IVista iVista = new VentanaPrincipal(controlador);
        iVista.iniciar();

        try {
            c.iniciar(controlador);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
