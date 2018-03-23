package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import base.PanelJuego;
import base.Sonidos;
import base.Sprite;
import base.VentanaPrincipal;

/**
 * @author Alejandro Bajo Pérez
 */

public class Nivel2 implements Pantalla {

	private ArrayList<Sprite> bolas = new ArrayList<>();
	private Sprite personaje, disparo;
	private Random rd;
	private Image fondo = null;
	private Image fondoEscalado = null;
	private String rutaBola = "imagenes//bola.png";
	private String personajeQuieto, rutaAndarDerecha, rutaAndarIzquierda;
	private String rutaDisparo = "imagenes//disparo2.png";
	private float tiempoInicial, tiempoDeJuego, inicioPower, tiempoPower;
	private Font fuente;
	private DecimalFormat formatoDecimal;
	private static final int ANCHO_BOLA = 170;
	private static final int ALTO_BOLA = 170;
	private static final int ANCHO_PERSONAJE = 90;
	private static final int ALTO_PERSONAJE = 100;
	private PanelJuego panelJuego;
	private JFrame ventana;
	private int puntos, power;
	private Sonidos meeseek, lanzar;
	private boolean inmune, congelacion, metralleta;
	private String tipoPower;

	public Nivel2(PanelJuego panelJuego, JFrame ventana) {
		switch (VentanaPrincipal.personaje) {
		case "Rick": {
			this.personajeQuieto = "imagenes//rick.png";
			this.rutaAndarDerecha = "imagenes//rickDerecha1.png";
			this.rutaAndarIzquierda = "imagenes//rickIzquierda1.png";
			break;
		}
		case "Morty": {
			this.personajeQuieto = "imagenes//morty.png";
			this.rutaAndarDerecha = "imagenes//mortyDerecha1.png";
			this.rutaAndarIzquierda = "imagenes//mortyIzquierda1.png";
			break;
		}
		default: {
			break;
		}
		}
		this.panelJuego = panelJuego;
		this.ventana = ventana;
		this.puntos = 0;
		inicializarPantalla();
		redimensionarPantalla();
	}

	@Override
	public void inicializarPantalla() {
		this.formatoDecimal = new DecimalFormat("#.##");
		this.fuente = new Font("TimesRoman", Font.BOLD, 27);
		this.tiempoDeJuego = 0;
		this.disparo = null;
		this.rd = new Random();
		// Power ups
		this.congelacion = false;
		this.metralleta = false;
		this.inmune = false;
		this.power = 4;
		this.tiempoPower = 0;
		this.inicioPower = System.nanoTime();
		this.tipoPower = "";
		// Sonido
		this.meeseek = new Sonidos("sonidos//mr_meeseeks.mp3");
		this.lanzar = new Sonidos("sonidos//disparo.mp3");
		// Bola
		bolas.add(new Sprite(ANCHO_BOLA, ALTO_BOLA, 10, 10, rd.nextInt(11) + 1, rd.nextInt(11) + 21, rutaBola, 1));
		bolas.add(new Sprite(ANCHO_BOLA, ALTO_BOLA, 10, 10, rd.nextInt(11) + 1, rd.nextInt(11) + 21, rutaBola, 2));
		// Fondo
		try {
			this.fondo = ImageIO.read(new File("imagenes//nivel2.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// Personaje
		this.personaje = new Sprite(ANCHO_PERSONAJE, ALTO_PERSONAJE, panelJuego.getWidth() / 2,
				panelJuego.getHeight() - ALTO_PERSONAJE, 0, 0, personajeQuieto, 1);
		// Tiempo
		this.tiempoDeJuego = 0;
		this.tiempoInicial = System.nanoTime();
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		pintarFondo(g);
		pintarBolas(g);
		pintarPersonaje(g);
		pintarContadorPunos(g);
		pintarDisparo(g);
		if (bolas.size() <= 0) {
			panelJuego.setPantalla(new Victoria2(panelJuego, tiempoDeJuego, ventana, puntos));
		}
	}

	@Override
	public void ejecutarFrame() {
		// Espera para que no se colapse el procesador
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Power ups
		if (inmune == false && congelacion == false && metralleta == false) {
			tiempoPower = 0;
			inicioPower = System.nanoTime();
			switch (power) {
			case 1: {
				inmune = true;
				tipoPower = "Inmune";
				break;
			}
			case 2: {
				congelacion = true;
				tipoPower = "Congelación";
				break;
			}
			case 3: {
				metralleta = true;
				tipoPower = "Metralleta";
				break;
			}
			default: {
				break;
			}
			}
			power = 4;
		}
		// Movimiento de los sprites
		moverSprites();
		// Contador de tiempo
		actualizarTiempo();
		// Compruebo las colisiones
		comprobarColision();
		panelJuego.repaint();
	}

	@Override
	public void moverRaton(MouseEvent e) {
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		if (disparo == null) {
			personaje.setRuta(personajeQuieto);
			personaje.actualizar();
			disparo = new Sprite(23, panelJuego.getHeight(), personaje.getPosX() + personaje.getAncho() / 3,
					personaje.getPosY() - personaje.getAlto() / 3, 0, -17, rutaDisparo, 1);
			new Thread(lanzar).start();
		}
		// Power up -> Metralleta
		if (metralleta) {
			personaje.setRuta(personajeQuieto);
			personaje.actualizar();
			disparo = new Sprite(23, panelJuego.getHeight(), personaje.getPosX() + personaje.getAncho() / 3,
					personaje.getPosY() - personaje.getAlto() / 3, 0, -17, rutaDisparo, 1);
			new Thread(lanzar).start();
		}
	}

	@Override
	public void pulsarA() {
		if (personaje.getPosX() >= 10) {
			switch (VentanaPrincipal.personaje) {
			case "Rick": {
				if (rutaAndarDerecha.equals("imagenes//rickDerecha1.png")) {
					rutaAndarDerecha = "imagenes//rickDerecha2.png";
				} else {
					rutaAndarDerecha = "imagenes//rickDerecha1.png";
				}
				break;
			}
			case "Morty": {
				if (rutaAndarDerecha.equals("imagenes//mortyDerecha1.png")) {
					rutaAndarDerecha = "imagenes//mortyDerecha2.png";
				} else {
					rutaAndarDerecha = "imagenes//mortyDerecha1.png";
				}
				break;
			}

			default: {
				break;
			}
			}
			personaje.setRuta(rutaAndarDerecha);
			personaje.setPosX(personaje.getPosX() - 20);
			personaje.actualizar();
		}
	}

	@Override
	public void pulsarD() {
		if (personaje.getPosX() < (panelJuego.getWidth() - personaje.getAncho() - 7)) {
			switch (VentanaPrincipal.personaje) {
			case "Rick": {
				if (rutaAndarIzquierda.equals("imagenes//rickIzquierda1.png")) {
					rutaAndarIzquierda = "imagenes//rickIzquierda2.png";
				} else {
					rutaAndarIzquierda = "imagenes//rickIzquierda1.png";
				}
				break;
			}
			case "Morty": {
				if (rutaAndarIzquierda.equals("imagenes//mortyIzquierda1.png")) {
					rutaAndarIzquierda = "imagenes//mortyIzquierda2.png";
				} else {
					rutaAndarIzquierda = "imagenes//mortyIzquierda1.png";
				}
				break;
			}
			default: {
				break;
			}
			}
			personaje.setPosX(personaje.getPosX() + 20);
			personaje.setRuta(rutaAndarIzquierda);
			personaje.actualizar();
		}
	}

	@Override
	public void pulsarEspacio() {
	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
		personaje.setPosY(panelJuego.getHeight() - personaje.getAlto());
		personaje.setPosX(panelJuego.getWidth() / 2);
	}

	/**
	 * Método que pinta el contador de tiempo y la puntuación
	 * 
	 * @param g
	 */
	private void pintarContadorPunos(Graphics g) {
		g.setColor(Color.YELLOW);
		g.setFont(fuente);
		g.drawString("Puntuación -> " + puntos, 40, 50);
		g.setColor(Color.MAGENTA);
		g.drawString("Tiempo -> " + formatoDecimal.format(tiempoDeJuego / 1000000000), 40, 80);
		if (inmune == true || congelacion == true || metralleta == true) {
			g.setColor(Color.BLUE);
			g.drawString(tipoPower + " -> " + formatoDecimal.format(tiempoPower / 1000000000), 40, 110);
		}
	}

	/**
	 * Método que pinta el fondo
	 * 
	 * @param g
	 */
	private void pintarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}

	/**
	 * Método que pinta las bolas
	 * 
	 * @param g
	 */
	private void pintarBolas(Graphics g) {
		for (int i = 0; i < bolas.size(); i++) {
			bolas.get(i).pintarSprite(g);
		}
	}

	/**
	 * Método que pinta el personaje
	 * 
	 * @param g
	 */
	public void pintarPersonaje(Graphics g) {
		personaje.pintarSprite(g);
	}

	/**
	 * Método que pinta el disparo
	 * 
	 * @param g
	 */
	public void pintarDisparo(Graphics g) {
		if (disparo != null) {
			disparo.pintarSprite(g);
		}
	}

	/**
	 * Método que actualiza el tiempo del marcador
	 */
	public void actualizarTiempo() {
		float tiempoActual = System.nanoTime();
		tiempoDeJuego = tiempoActual - tiempoInicial;
		// Power ups
		if (inmune == true || congelacion == true || metralleta == true) {
			tiempoPower = tiempoActual - inicioPower;
			if (tiempoPower / 1000000000 >= 8) {
				inmune = false;
				congelacion = false;
				metralleta = false;
			}
		}
	}

	/**
	 * Comprueba si a habido una colisión, elimina a los sprites que hayan
	 * colisionado
	 */
	public void comprobarColision() {
		// Disparo
		for (int i = 0; i < bolas.size() && disparo != null; i++) {
			if (bolas.get(i).colisionan(disparo)) {
				disparo = null;
				bolas.get(i).setContador(bolas.get(i).getContador() - 1);
				puntos += bolas.get(i).getPuntuacion();
				if (bolas.get(i).getContador() > 0) {
					bolas.add(new Sprite(ANCHO_BOLA, ALTO_BOLA, bolas.get(i).getPosX(), bolas.get(i).getPosY(),
							rd.nextInt(11) + 1, rd.nextInt(11) + 21, rutaBola, bolas.get(i).getTipo() + 1));
					bolas.add(new Sprite(ANCHO_BOLA, ALTO_BOLA, bolas.get(i).getPosX(), bolas.get(i).getPosY(),
							rd.nextInt(11) + 1, rd.nextInt(11) + 21, rutaBola, bolas.get(i).getTipo() + 1));
					new Thread(meeseek).start();
				}
				bolas.remove(i);
				// Power up aleatorio
				power = rd.nextInt(10);
			}
		}
		// Personaje
		if (!inmune) { // Power up -> Inmune
			for (int j = 0; j < bolas.size(); j++) {
				if (personaje.colisionan(bolas.get(j))) {
					panelJuego.setPantalla(new GameOver2(panelJuego, tiempoDeJuego, ventana, puntos));
				}
			}
		}
	}

	/**
	 * Método que mueve los sprites
	 */
	public void moverSprites() {
		// Bolas
		if (!congelacion) {// Power up -> Congelación
			for (int i = 0; i < bolas.size(); i++) {
				bolas.get(i).moverSprite(panelJuego.getWidth(), panelJuego.getHeight());
			}
		}
		// Disparo
		if (disparo != null) {
			disparo.mover();
			// Cuando el dispao toca el borde superior lo borro poniendolo a null
			if (disparo.getPosY() < 0) {
				disparo = null;
			}
		}
	}

	@Override
	public void pulsarR() {
	}

	@Override
	public void pulsarM() {
	}
}