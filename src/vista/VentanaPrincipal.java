package vista;

import controlador.Controlador;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

public class VentanaPrincipal extends JFrame implements IVista {

    private Controlador controlador;
    private String nombre;
    private JTextField nombreUsuario = new JTextField();
    private JDialog colaEspera;

    public VentanaPrincipal(Controlador controlador) {
        this.controlador = controlador;
        this.controlador.setVista(this);

        this.setTitle("Truco Argentino");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        pantallaMenu();

    }

    @Override
    public void iniciar() {
        this.setVisible(true);
    }

    @Override
    public void colaDeEspera(int jugadoresConectados, int cantidadJugadores) {

        colaEspera = new JDialog(this);
        colaEspera.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        colaEspera.setSize(260, 95);
        colaEspera.setLocationRelativeTo(null);
        colaEspera.setTitle("Cola de espera");
        colaEspera.setLayout(new FlowLayout());
        JPanel mensaje = new JPanel(new GridLayout());
        mensaje.add(new JLabel("Esperando al resto de jugadores.. [" + jugadoresConectados + "/" + cantidadJugadores + "]"));
        colaEspera.getContentPane().add(mensaje);

        colaEspera.setVisible(true);

    }

    @Override
    public void iniciarPartida() {
        if (colaEspera != null) {
            this.colaEspera.dispose();
        }

//        this.setVisible(false);
        pantallaJuego();
//        pantallaJuego = new VistaGrafica(jugador, this);
    }

    private void botonStartOnClick() {

        this.nombre = nombreUsuario.getText();
        if (nombre.isEmpty()) {
            nombre = "AnonPlayer" + this.hashCode();
        }
        nombreUsuario.setText("");

        controlador.conectarse(nombre);

        try {
            controlador.inicio();
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void pantallaJuego() {
        this.setSize(1500, 1000);

        JPanel panelBackground = new JPanel() {
            private BufferedImage imagen;

            {
                try {
                    // Carga la imagen
                    imagen = ImageIO.read(new File("src/vista/img/mesa.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagen != null) {
                    // Dibuja la imagen como fondo
                    g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panelBackground.setLayout(new BorderLayout());

        this.setContentPane(panelBackground);

        JPanel panelBase = (JPanel) this.getContentPane();
        panelBase.setLayout(new BorderLayout());
        panelBase.setOpaque(false);

        JPanel panelCentro = new JPanel(new FlowLayout());
        panelBase.add(panelCentro, BorderLayout.CENTER);
        panelCentro.setOpaque(false);

        JPanel panelMesa = new JPanel(new GridLayout(2,1));
        panelCentro.add(panelMesa);
        panelMesa.setOpaque(false);

        JPanel panelCartasJugadas = new JPanel(new FlowLayout());
        panelMesa.add(panelCartasJugadas);
        panelCartasJugadas.setOpaque(false);

        JPanel panelRondas = new JPanel(new GridLayout(1,3,15,0));
        panelCartasJugadas.add(panelRondas);
        panelRondas.setOpaque(false);

        JPanel ronda1 = new JPanel(new FlowLayout());
        panelRondas.add(ronda1);
        ronda1.setOpaque(false);
        JPanel ronda2 = new JPanel(new FlowLayout());
        panelRondas.add(ronda2);
        ronda2.setOpaque(false);
        JPanel ronda3 = new JPanel(new FlowLayout());
        panelRondas.add(ronda3);
        ronda3.setOpaque(false);

        JPanel panelMano = new JPanel(new FlowLayout());
        panelMesa.add(panelMano);
        panelMano.setOpaque(false);

        JPanel panelCartasMano = new JPanel(new GridLayout(1,3,15,10));
        panelMano.add(panelCartasMano);
        panelCartasMano.setOpaque(false);

        this.repaint();
    }

    private void pantallaMenu() {

        this.setSize(750, 550);

        JPanel panelBackground = new JPanel() {
            private BufferedImage imagen;

            {
                try {
                    // Carga la imagen
                    imagen = ImageIO.read(new File("src/vista/img/BGimg.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagen != null) {
                    // Dibuja la imagen como fondo
                    g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panelBackground.setLayout(new BorderLayout());

        this.setContentPane(panelBackground);

        crearPanelesGenerales();
        this.repaint();
    }

    private void crearPanelesGenerales(){
        JPanel panelBase = (JPanel) this.getContentPane();
        panelBase.setLayout(new BorderLayout());
        panelBase.setOpaque(false);

        JPanel panelCentro = new JPanel(new GridLayout(3,1));
        panelCentro.setOpaque(false);
        panelBase.add(panelCentro, BorderLayout.CENTER);

        JPanel panelTitulo = new JPanel(new FlowLayout());
        panelTitulo.setOpaque(false);
        panelCentro.add(panelTitulo);
        JLabel titulo = new JLabel("Menu Principal");
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        panelTitulo.add(titulo);

        JPanel panelMenu = new JPanel(new FlowLayout());
        panelMenu.setOpaque(false);
        panelCentro.add(panelMenu);

        JPanel panelOpciones = new JPanel(new GridLayout(2, 1));
        panelOpciones.setOpaque(false);
        panelMenu.add(panelOpciones);

        JPanel panelUser = new JPanel(new GridLayout(1, 2));
        panelUser.setOpaque(false);
        JPanel panelLabel = new JPanel(new FlowLayout());
        panelLabel.setOpaque(false);
        JLabel usuario = new JLabel("USUARIO");
        usuario.setFont(new Font("Arial", Font.BOLD, 18));
        usuario.setForeground(Color.BLACK);
        panelLabel.add(usuario);
        panelUser.add(panelLabel);

        panelUser.add(nombreUsuario);

        panelOpciones.add(panelUser);

        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        JButton botonStart = new JButton("Jugar!");
        panelBoton.add(botonStart);
        botonStart.setPreferredSize(new Dimension(100, 30));
        panelCentro.add(panelBoton);

        botonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                botonStartOnClick();
            }
        });

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);
        JPanel panelCopy = new JPanel(new FlowLayout());
        panelCopy.setOpaque(false);
        panelSur.add(panelCopy, BorderLayout.EAST);

        JLabel copyright = new JLabel("Por Juan Brero");
        panelCopy.add(copyright);

        panelBase.add(panelSur, BorderLayout.SOUTH);

    }
}
