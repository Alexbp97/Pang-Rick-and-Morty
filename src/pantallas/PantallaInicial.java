package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import base.PanelJuego;

/**
 * @author Alejandro Bajo Pérez
 */

public class PantallaInicial implements Pantalla {

	private Color colorLetra;
	private PanelJuego panelJuego;
	private String rutaPortada = "imagenes//portada.jpg";
	private Image fondo = null;
	private Image fondoEscalado = null;
	private JFrame ventana;

	public PantallaInicial(PanelJuego panelJuego, JFrame ventana) {
		this.panelJuego = panelJuego;
		this.ventana = ventana;
		inicializarPantalla();
		redimensionarPantalla();
	}

	@Override
	public void inicializarPantalla() {
		// Fondo
		try {
			this.fondo = ImageIO.read(new File(rutaPortada));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		// Fondo
		g.drawImage(fondoEscalado, 0, 0, null);
		// Texto de comenzar
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.setColor(colorLetra);
		g.drawRect(panelJuego.getWidth() / 2 - 510, panelJuego.getHeight() / 2 + 25, 1052, 57);
		g.drawString("Pulsa \"Espacio\" para comenzar la aventura", panelJuego.getWidth() / 2 - 500,
				panelJuego.getHeight() / 2 + 70);
	}

	@Override
	public void ejecutarFrame() {
		Random rd = new Random();
		try {
			colorLetra = new Color(rd.nextInt(256), rd.nextInt(256), rd.nextInt(256));
			// Espera para cambiar de color
			Thread.sleep(270);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		panelJuego.repaint();
	}

	@Override
	public void moverRaton(MouseEvent e) {
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(ventana.getWidth(), ventana.getHeight(), Image.SCALE_SMOOTH);
	}

	@Override
	public void pulsarA() {
	}

	@Override
	public void pulsarD() {
	}

	@Override
	public void pulsarEspacio() {
		panelJuego.setPantalla(new ElegirPersonaje(panelJuego, ventana));
	}

	@Override
	public void pulsarR() {
	}

	@Override
	public void pulsarM() {
	}
}