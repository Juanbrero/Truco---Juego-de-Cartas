package vista;

import modelo.baraja.Palo;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class VistaCarta extends JPanel implements IVistaCarta {

    public VistaCarta(int nro, Palo palo) {

        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(100,150));
        this.setBorder(new LineBorder(Color.BLACK));

        String pathImg = "src/vista/img/cartas/" + nro + palo.toString() + ".jpg";

        JPanel panelImg = new JPanel() {
            private BufferedImage imagen;

            {
                try {
                    // Carga la imagen
                    imagen = ImageIO.read(new File(pathImg));
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

        panelImg.setLayout(new BorderLayout());

        add(panelImg);

    }
}
