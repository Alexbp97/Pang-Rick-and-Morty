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

public class Victoria1 implements Pantalla {

	private PanelJuego panelJuego;
	private float tiempo;
	private DecimalFormat formatoDecimal;
	private Image fondo = null;
	private Image fondoEscalado = null;
	private String rutaFondo = "imagenes//victoria.jpg";
	private JFrame ventana;
	private Sonidos sonido;
	private int puntuacion;

	public Victoria1(PanelJuego panelJuego, float tiempo, JFrame ventana, int puntuacion) {
		this.panelJuego = panelJuego;
		this.tiempo = tiempo;
		this.puntuacion = puntuacion;
		this.ventana = ventana;
		inicializarPantalla();
		redimensionarPantalla();
	}

	@Override
	public void inicializarPantalla() {
		this.sonido = new Sonidos("sonidos//portal_gun.mp3");
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
		g.drawString("¡VICTORIA!", panelJuego.getWidth() / 2 - 565, panelJuego.getHeight() / 2 - 220);
		// Datos
		g.setColor(Color.YELLOW);
		g.setFont(new Font("TimesRoman", Font.BOLD, 40));
		g.drawString("Tiempo: " + formatoDecimal.format(tiempo / 1000000000) + " \t | \t Puntuación:  " + puntuacion,
				panelJuego.getWidth() / 2 - 275, panelJuego.getHeight() / 2 - 150);
		// Historia
		g.setFont(new Font("Arial", Font.ITALIC, 30));
		g.setColor(Color.WHITE);
		g.drawString("Bien hecho, pero ahora a Rick se le ha ido", 20, panelJuego.getHeight() - 110);
		g.drawString("un poco la mano con una poción para Morty", 20, panelJuego.getHeight() - 80);
		g.drawString("y ahora los humanos se han convertido", 20, panelJuego.getHeight() - 50);
		g.drawString("en unos monstruos horribles, ¡Ten cuidado!", 20, panelJuego.getHeight() - 20);
		// Reiniciar
		g.setFont(new Font("Arial", Font.ITALIC, 25));
		g.setColor(Color.ORANGE);
		g.drawString("Pulsa \"Espacio\" para el siguiente nivel ->", panelJuego.getWidth() - 490, 30);
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
	}

	@Override
	public void pulsarR() {
	}

	@Override
	public void pulsarM() {
	}
}