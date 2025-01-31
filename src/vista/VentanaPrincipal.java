package vista;

import controlador.Controlador;
import modelo.baraja.ICarta;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame implements IVista {

    private Controlador controlador;
    private String nombre;
    private JTextField nombreUsuario = new JTextField();
    private JDialog colaEspera;
    private JPanel panelCartasMano;

    public VentanaPrincipal(Controlador controlador) {
        this.controlador = controlador;
        this.controlador.setVista(this);

        this.setTitle("Truco Argentino");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        pantallaJuego();

    }

    public void generarCartas(ArrayList<ICarta> cartas) throws RemoteException {

        int indice = 1;

        for (ICarta carta : cartas) {

            String pathImg = "src/vista/img/cartas/" + carta.getNro() + carta.getPalo().toString() + ".png";
            int grados;
            int vgap;

            if (indice == 1) {
                grados = -20;
                vgap = 20;
            }
            else if (indice == 2) {
                grados = 0;
                vgap = 0;
            }
            else {
                grados = 20;
                vgap = 20;
            }
            JPanel vistaCarta = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, vgap));

            vistaCarta.setPreferredSize(new Dimension(300,480));
            vistaCarta.setOpaque(false);
            vistaCarta.add(rotarCartas(pathImg,grados));

            panelCartasMano.add(vistaCarta);
            this.repaint();
            indice++;
        }

    }

    private void botonStartOnClick() {

        this.nombre = nombreUsuario.getText();
        if (nombre.isEmpty()) {
            nombre = "AnonPlayer" + this.hashCode();
        }
        nombreUsuario.setText("");

        controlador.conectarse(nombre);

    }

    private void pantallaJuego() {
        this.setSize(1500, 1000);
        this.setLocationRelativeTo(null);

        pintarFondoJuego();

        // Paneles de base
        JPanel panelBase = (JPanel) this.getContentPane();
        panelBase.setLayout(new BorderLayout());
        panelBase.setOpaque(false);

        panelBase.add(panelAdversario(), BorderLayout.NORTH);

        // Paneles centrales donde van las cartas jugadas
        panelBase.add(panelMesa(), BorderLayout.CENTER);

        // Paneles de parte inferior
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);
        panelBase.add(panelSur, BorderLayout.SOUTH);

        // Paneles de jugadas
        JPanel abc = new JPanel(new FlowLayout());
        abc.setPreferredSize(new Dimension(200, 100));
        abc.setOpaque(false);
        panelSur.add(abc, BorderLayout.WEST);

        // Paneles de cartas en mano
        panelSur.add(panelMano(),BorderLayout.CENTER);

        // Panel de puntos
        panelSur.add(panelPuntos(), BorderLayout.EAST);

        this.repaint();
    }

    private void pantallaMenu() {

        this.setSize(750, 550);
        this.setLocationRelativeTo(null);

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

    private void pintarFondoJuego() {
        // Fondo
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
    }

    private JPanel panelAdversario() {

        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER,0,25));
        panelNorte.setPreferredSize(new Dimension(800,170));
        panelNorte.setOpaque(false);

        JPanel panelCartasNorte = new JPanel(new GridLayout(1,3,4,0));
        panelCartasNorte.setOpaque(false);
        panelNorte.add(panelCartasNorte);

        panelCartasNorte.add(pintarDorsoCarta());
        panelCartasNorte.add(pintarDorsoCarta());
        panelCartasNorte.add(pintarDorsoCarta());

        return panelNorte;
    }

    private JPanel pintarDorsoCarta() {
        JPanel dorsoCarta = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        dorsoCarta.setPreferredSize(new Dimension(70,175));
        dorsoCarta.setOpaque(false);

        String pathImgDorso = "src/vista/img/cartas/dorso.jpg";

        JPanel panelImgDorso = new JPanel() {
            private BufferedImage imagen;

            {
                try {
                    // Carga la imagen
                    imagen = ImageIO.read(new File(pathImgDorso));
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
        panelImgDorso.setPreferredSize(new Dimension(70,118));
        panelImgDorso.setLayout(new BorderLayout());
        dorsoCarta.add(panelImgDorso);

        return dorsoCarta;
    }

    private JPanel panelMano() {

        JPanel panelMano = new JPanel(new FlowLayout(FlowLayout.CENTER,15,-90));
        panelMano.setPreferredSize(new Dimension(800,200));
        panelMano.setOpaque(false);

        panelCartasMano = new JPanel(new GridLayout(1,3,-160,0));
        panelMano.add(panelCartasMano);
        panelCartasMano.setOpaque(false);

        return panelMano;
    }

    private JPanel panelMesa() {
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER,0,80));
        panelCentro.setPreferredSize(new Dimension(1000,350));
        panelCentro.setOpaque(false);

        JPanel panelRondas = new JPanel(new GridLayout(1,3,25,50));
        panelCentro.add(panelRondas);
        panelRondas.setOpaque(false);

        JPanel ronda1 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        panelRondas.add(ronda1);
        ronda1.setPreferredSize(new Dimension(220,290));
//        ronda1.setOpaque(false);

        JPanel ronda2 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        panelRondas.add(ronda2);
        ronda2.setPreferredSize(new Dimension(220,290));
//        ronda2.setOpaque(false);

        JPanel ronda3 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        panelRondas.add(ronda3);
        ronda3.setPreferredSize(new Dimension(220,290));
//        ronda3.setOpaque(false);

        return panelCentro;
    }

    private JPanel rotarCartas(String imgPath, int grados) {

        JPanel panelImg = new JPanel(new FlowLayout()) {
            private BufferedImage imagen;

            {
                try {
                    // Carga la imagen
                    imagen = ImageIO.read(new File(imgPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagen != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    AffineTransform at = new AffineTransform();
                    double scaleX = (double) getWidth() / imagen.getHeight();
                    double scaleY = (double) getHeight() / imagen.getWidth();
                    double scale = Math.min(scaleX, scaleY);
                    at.translate(getWidth() / 2, getHeight() / 2);

                    at.rotate(Math.toRadians(grados));

                    at.scale(scale,scale);
                    at.translate(-imagen.getWidth() / 2, -imagen.getHeight() / 2);
                    g2d.drawImage(imagen, at, null);

                }

            }
        };
        panelImg.setPreferredSize(new Dimension(300, 480));
        panelImg.setOpaque(false);

        return panelImg;
    }

    private JPanel panelPuntos() {
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new Dimension(220,350));

        JPanel panelTabla = new JPanel(new GridLayout(1,2,-2,0));
        panelDerecho.add(panelTabla);
        panelTabla.setOpaque(false);

        panelTabla.add(panelEquiposTablaPuntos("Nos"));
        panelTabla.add(panelEquiposTablaPuntos("Ellos"));

        return panelDerecho;
    }

    private JPanel panelEquiposTablaPuntos(String equipo) {

        JPanel panelEquipoBase = new JPanel(new BorderLayout(0,-2));
        panelEquipoBase.setOpaque(false);

        JPanel panelEquipo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEquipo.setPreferredSize(new Dimension(110,50));
        panelEquipo.setOpaque(false);
        panelEquipo.setBorder(new LineBorder(Color.BLACK, 3));

        JLabel labelEquipo = new JLabel(equipo);
        labelEquipo.setFont(new Font("Arial", Font.BOLD, 30));
        labelEquipo.setForeground(Color.BLACK);
        panelEquipo.add(labelEquipo);

        panelEquipoBase.add(panelEquipo, BorderLayout.NORTH);

        // Panel malas
        panelEquipoBase.add(panel15PuntosEquipo(), BorderLayout.CENTER);
        // Panel buenas
        panelEquipoBase.add(panel15PuntosEquipo(), BorderLayout.SOUTH);

        return panelEquipoBase;
    }

    private JPanel panel15PuntosEquipo() {

        JPanel panelBase = new JPanel(new FlowLayout(FlowLayout.CENTER,0,2));
        panelBase.setOpaque(false);
        panelBase.setPreferredSize(new Dimension(110,150));
        panelBase.setBorder(new LineBorder(Color.BLACK, 3));

        JPanel panel15Puntos = new JPanel(new GridLayout(3,1,0,0));
        panel15Puntos.setOpaque(false);

        for (int i = 0; i < 3; i++) {
            panel15Puntos.add(generarContadorPuntos());
        }

        panelBase.add(panel15Puntos);

        return panelBase;
    }

    private JPanel generarContadorPuntos() {

        JPanel panelCasillaPuntos = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        panelCasillaPuntos.setOpaque(false);
        panelCasillaPuntos.setPreferredSize(new Dimension(110,50));

        JPanel puntos = new JPanel(new GridLayout(1,3,-15,0));
        puntos.setOpaque(false);
        panelCasillaPuntos.add(puntos);

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.RIGHT,-2,2));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setPreferredSize(new Dimension(35,50));
        puntos.add(panelIzquierdo);
        panelIzquierdo.add(pintarPuntos(OrientacionPunto.VERTICAL));

        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER,0,-15));
        panelCentro.setPreferredSize(new Dimension(40,40));
        panelCentro.setOpaque(false);
        puntos.add(panelCentro);

        JPanel panelPuntosCentrales = new JPanel(new GridLayout(3,1,0,-15));
        panelPuntosCentrales.setOpaque(false);
        panelPuntosCentrales.add(pintarPuntos(OrientacionPunto.HORIZONTAL));
        panelPuntosCentrales.add(pintarPuntos(OrientacionPunto.DIAGONAL));
        panelPuntosCentrales.add(pintarPuntos(OrientacionPunto.HORIZONTAL));
        panelCentro.add(panelPuntosCentrales);

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.LEFT,-3,2));
        panelDerecho.setPreferredSize(new Dimension(35,50));
        puntos.add(panelDerecho);
        panelDerecho.add(pintarPuntos(OrientacionPunto.VERTICAL));
        panelDerecho.setOpaque(false);

        return panelCasillaPuntos;
    }

    private JPanel pintarPuntos(OrientacionPunto orientacion) {
        JPanel panelPunto = new JPanel(new FlowLayout());
        panelPunto.setPreferredSize(new Dimension(35,30));
        panelPunto.setOpaque(false);

        String pathImgPunto = "src/vista/img/punto.png";

        JPanel panelImgPunto = new JPanel() {
            private BufferedImage imagen;

            {
                try {
                    // Carga la imagen
                    imagen = ImageIO.read(new File(pathImgPunto));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagen != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    AffineTransform at = new AffineTransform();
                    double scaleX = (double) getWidth() / imagen.getHeight();
                    double scaleY = (double) getHeight() / imagen.getWidth();
                    double scale = Math.min(scaleX, scaleY);
                    at.translate(getWidth() / 2, getHeight() / 2);

                    if (orientacion == OrientacionPunto.HORIZONTAL) {
                        at.rotate(Math.toRadians(0));
                    } else if (orientacion == OrientacionPunto.DIAGONAL) {
                        at.rotate(Math.toRadians(-45));
                    } else {
                        at.rotate(Math.toRadians(90));
                    }

                    at.scale(scale, scale);
                    at.translate(-imagen.getWidth() / 2, -imagen.getHeight() / 2);
                    g2d.drawImage(imagen, at, null);

                }
            }
        };
        panelImgPunto.setPreferredSize(new Dimension(35,30));
        panelImgPunto.setLayout(new BorderLayout());
        panelImgPunto.setOpaque(false);
        panelPunto.add(panelImgPunto);

        return panelPunto;
    }
}
