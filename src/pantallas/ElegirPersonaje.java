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
import base.Sonidos;
import base.VentanaPrincipal;

/**
 * @author Alejandro Bajo Pérez
 */

public class ElegirPersonaje implements Pantalla {

	private PanelJuego panelJuego;
	private String rutaFondo = "imagenes//fondo.jpg";
	private Image fondo = null, iControles = null;
	private Image fondoEscalado = null, controlesEscalados = null;
	private JFrame ventana;
	private Color color;
	private Sonidos sonido;

	public ElegirPersonaje(PanelJuego panelJuego, JFrame ventana) {
		this.panelJuego = panelJuego;
		this.ventana = ventana;
		this.color = Color.BLACK;
		inicializarPantalla();
		redimensionarPantalla();
	}

	@Override
	public void inicializarPantalla() {
		this.sonido = new Sonidos("sonidos//intro.mp3");
		new Thread(sonido).start();
		// Fondo
		try {
			this.fondo = ImageIO.read(new File(rutaFondo));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// Controles
		try {
			this.iControles = ImageIO.read(new File("imagenes//controles.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		// Fondo
		g.drawImage(fondoEscalado, 0, 0, null);
		// Título
		g.setFont(new Font("Arial", Font.BOLD, 82));
		g.setColor(new Color(255, 209, 0));
		g.drawString("- Selección de Personaje -", panelJuego.getWidth() / 4, 100);
		// Rick
		g.setFont(new Font("Arial", Font.BOLD, 29));
		g.setColor(color);
		g.drawString("Pulsa \"R\"", panelJuego.getWidth() / 2 + 110, panelJuego.getHeight() - 115);
		// Morty
		g.drawString("Pulsa \"M\"", panelJuego.getWidth() / 3 + 100, panelJuego.getHeight() - 110);
		// Controles
		g.drawImage(controlesEscalados, panelJuego.getWidth() / 4 + 80, panelJuego.getHeight() / 3 - 120, null);
	}

	@Override
	public void ejecutarFrame() {
		Random rd = new Random();
		try {
			color = new Color(rd.nextInt(256), rd.nextInt(256), rd.nextInt(256));
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
		controlesEscalados = iControles.getScaledInstance(ventana.getWidth() / 2 - 20, ventana.getHeight() / 2 - 150,
				Image.SCALE_SMOOTH);
	}

	@Override
	public void pulsarA() {
	}

	@Override
	public void pulsarD() {
	}

	@Override
	public void pulsarEspacio() {

	}

	@Override
	public void pulsarR() {
		VentanaPrincipal.setPersonaje("Rick");
		panelJuego.setPantalla(new Nivel1(panelJuego, ventana));
		sonido.detener();
	}

	@Override
	public void pulsarM() {
		VentanaPrincipal.setPersonaje("Morty");
		panelJuego.setPantalla(new Nivel1(panelJuego, ventana));
		sonido.detener();
	}
}