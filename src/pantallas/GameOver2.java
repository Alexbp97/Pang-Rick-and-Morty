package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import base.PanelJuego;
import base.Sonidos;

public class GameOver2 implements Pantalla {

	private PanelJuego panelJuego;
	private float tiempo;
	private DecimalFormat formatoDecimal;
	private Image fondo = null;
	private Image fondoEscalado = null;
	private String rutaFondo = "imagenes//gameOver2.png";
	private JFrame ventana;
	private int puntuacion;
	private Sonidos sonido;

	public GameOver2(PanelJuego panelJuego, float tiempo, JFrame ventana, int puntuacion) {
		this.panelJuego = panelJuego;
		this.tiempo = tiempo;
		this.ventana = ventana;
		this.puntuacion = puntuacion;
		inicializarPantalla();
		redimensionarPantalla();
	}

	@Override
	public void inicializarPantalla() {
		this.sonido = new Sonidos("sonidos//loser.mp3");
		new Thread(sonido).start();
		// Fondo
		try {
			this.fondo = ImageIO.read(new File(rutaFondo));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.formatoDecimal = new DecimalFormat("#.##");
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		// Fondo
		g.drawImage(fondoEscalado, 0, 0, null);
		// Texto
		g.setFont(new Font("Arial", Font.BOLD, 200));
		g.setColor(Color.RED);
		g.drawString("GAME OVER", panelJuego.getWidth() / 2 - 650, panelJuego.getHeight() / 2 - 220);
		// Datos
		g.setColor(Color.YELLOW);
		g.setFont(new Font("TimesRoman", Font.BOLD, 40));
		g.drawString("Tiempo: " + formatoDecimal.format(tiempo / 1000000000) + " \t | \t Puntuación:  " + puntuacion,
				panelJuego.getWidth() / 2 - 270, panelJuego.getHeight() / 2 - 150);
		g.setColor(Color.WHITE);
		g.drawRect(panelJuego.getWidth() / 3 - 10, panelJuego.getHeight() - 115, 677, 50);
		g.drawString("Pulsa \"Espacio\" para reiniciar el nivel", panelJuego.getWidth() / 3,
				panelJuego.getHeight() - 80);
	}

	@Override
	public void ejecutarFrame() {
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
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	}

	@Override
	public void pulsarA() {
	}

	@Override
	public void pulsarD() {
	}

	@Override
	public void pulsarEspacio() {
		panelJuego.setPantalla(new Nivel2(panelJuego, ventana));
		sonido.detener();
	}

	@Override
	public void pulsarR() {
	}

	@Override
	public void pulsarM() {
	}
}