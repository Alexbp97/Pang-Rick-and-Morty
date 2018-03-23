package base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Alejandro Bajo Pérez
 */

public class Sprite {

	private BufferedImage buffer;
	private Color color;
	private int ancho;
	private int alto;
	private int posX;
	private int posY;
	private int velocidadX;
	private int velocidadY;
	private String ruta;
	private int contador, puntuacion, tipo;
	private Sonidos rebote;

	/**
	 * Constructor
	 * 
	 * @param ancho
	 * @param alto
	 * @param posX
	 * @param posY
	 * @param velocidadX
	 * @param velocidadY
	 */
	public Sprite(int ancho, int alto, int posX, int posY, int velocidadX, int velocidadY, String ruta, int tipo) {
		this.color = Color.GREEN;
		this.puntuacion = 0;
		this.tipo = tipo;
		this.contador = 0;
		switch (tipo) {
		case 1: {
			this.alto = alto;
			this.ancho = ancho;
			this.contador = 2;
			this.puntuacion = 50;
			break;
		}
		case 2: {
			this.alto = alto / 2;
			this.ancho = ancho / 2;
			this.contador = 2;
			this.puntuacion = 100;
			break;
		}
		case 3: {
			this.alto = alto / 3;
			this.ancho = ancho / 3;
			this.contador = 1;
			this.puntuacion = 200;
			break;
		}
		default: {
			break;
		}
		}
		this.rebote = new Sonidos("sonidos//rebote.mp3");
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.ruta = ruta;
		actualizar();
	}

	/**
	 * Método para pintar el sprite
	 */
	public void actualizar() {
		Image imagen;
		buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		try {
			imagen = ImageIO.read(new File(ruta));
			Graphics graficos = buffer.getGraphics();
			graficos.drawImage(imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);
		} catch (IOException e) {
			Graphics graficos = buffer.getGraphics();
			graficos.setColor(color);
			graficos.fillRect(0, 0, ancho, alto);
			graficos.dispose();
		}
	}

	public void pintarSprite(Graphics g) {
		g.drawImage(buffer, posX, posY, null);
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferedImage Buffer) {
		this.buffer = Buffer;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getVelocidadX() {
		return velocidadX;
	}

	public void setVelocidadX(int velocidadX) {
		this.velocidadX = velocidadX;
	}

	public int getVelocidadY() {
		return velocidadY;
	}

	public void setVelocidadY(int velocidadY) {
		this.velocidadY = velocidadY;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public int getContador() {
		return contador;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método para comprobar los limites de la ventana y hacer el movimiento
	 * 
	 * @param ancho
	 * @param alto
	 */
	public void moverSprite(int ancho, int alto) {
		// Controlo el choque con el borde derecho
		if ((posX + this.ancho) >= ancho) {
			velocidadX = -Math.abs(velocidadX);
			new Thread(rebote).start();
		}
		// Controlo el choque con el borde izquierdo
		if (posX <= 0) {
			velocidadX = Math.abs(velocidadX);
			new Thread(rebote).start();
		}
		// Controlo el choque con el borde inferior
		if ((posY + this.alto) >= alto) {
			velocidadY = -Math.abs(velocidadY);
			new Thread(rebote).start();
		}
		// Controlo el choque con el borde superior
		if (posY <= 0) {
			velocidadY = Math.abs(velocidadY);
			new Thread(rebote).start();
		}
		mover();
	}

	/**
	 * Para hacer el movimiento de los Sprites
	 */
	public void mover() {
		// Hago el movimiento horizontal
		posX += velocidadX;
		// Hago el movimiento vertical
		posY += velocidadY;
	}

	/**
	 * Comprueba si a habido una colisión entre este sprite y el pasado por
	 * párametros
	 * 
	 * @param sprite
	 * @return
	 */
	public boolean colisionan(Sprite sprite) {
		boolean colision = false;
		// Si el sprite actual está más cerca de la esquina superior izquierda
		if (posX < sprite.getPosX() && posY < sprite.getPosY()) {
			// Si el borde izquierdo del sprite pasado por parámetros iguala o sobrepasa
			// el borde izquierdo del sprite actual && si el borde superior del sprite
			// pasado
			// por parámetros iguala o sobrepasa el borde superior del sprite actual hay
			// colisión
			if ((posX + ancho) >= sprite.getPosX() && (posY + alto) >= sprite.getPosY()) {
				colision = true;
			}
			// Si el sprite pasado por parámetros está más cerca de las esquina superior
			// izquierda
		} else if (sprite.getPosX() < posX && sprite.getPosY() < posY) {
			// Si el borde izquierdo del sprite actual iguala o sobrepasa el borde izquierdo
			// del sprite pasado por parámetros && si el borde superior del sprite actual
			// iguala
			// o sobrepasa el borde superior del sprite pasado por parámetros hay colisión
			if ((sprite.getPosX() + sprite.getAncho()) >= posX && (sprite.getPosY() + sprite.getAlto()) >= posY) {
				colision = true;
			}
		}
		return colision;
	}
}