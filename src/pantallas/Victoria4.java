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

public class Victoria4 implements Pantalla {

	private PanelJuego panelJuego;
	private float tiempo;
	private DecimalFormat formatoDecimal;
	private Image fondo = null;
	private Image fondoEscalado = null;
	private String rutaFondo = "imagenes//victoria4.jpg";
	private JFrame ventana;
	private int puntuacion;
	private Sonidos sonido;

	public Victoria4(PanelJuego panelJuego, float tiempo, JFrame ventana, int puntuacion) {
		this.panelJuego = panelJuego;
		this.tiempo = tiempo;
		this.puntuacion = puntuacion;
		this.ventana = ventana;
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
		this.formatoDecimal = new DecimalFormat("#.##");
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		// Fondo
		g.drawImage(fondoEscalado, 0, 0, null);
		// Texto
		g.setFont(new Font("Arial", Font.BOLD, 200));
		g.setColor(Color.RED);
		g.drawString("¡VICTORIA!", panelJuego.getWidth() / 2 - 565, panelJuego.getHeight() / 2 - 260);
		// Datos
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("TimesRoman", Font.BOLD, 40));
		g.drawString("Tiempo: " + formatoDecimal.format(tiempo / 1000000000), panelJuego.getWidth() - 480,
				panelJuego.getHeight() - 90);
		g.drawString("Puntuación: " + puntuacion, panelJuego.getWidth() - 480, panelJuego.getHeight() - 50);
		// Historia
		g.setFont(new Font("Arial", Font.ITALIC, 30));
		g.setColor(Color.WHITE);
		g.drawString("¡Felicidades! has conseguido pasarte", 20, panelJuego.getHeight() - 80);
		g.drawString("el juego con lo cual el universo está", 20, panelJuego.getHeight() - 50);
		g.drawString("tranquilo, bueno al menos por ahora.", 20, panelJuego.getHeight() - 20);
		// Reiniciar
		g.setFont(new Font("Arial", Font.ITALIC, 25));
		g.setColor(Color.ORANGE);
		g.drawString("Pulsa \"Espacio\" para reiniciar el juego", panelJuego.getWidth() - 490, 30);
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
		panelJuego.setPantalla(new PantallaInicial(panelJuego, ventana));
		sonido.detener();
	}

	@Override
	public void pulsarR() {
	}

	@Override
	public void pulsarM() {
	}
}