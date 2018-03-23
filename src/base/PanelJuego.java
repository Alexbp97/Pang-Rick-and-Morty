package base;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pantallas.Pantalla;
import pantallas.PantallaInicial;

/**
 * @author Alejandro Bajo Pérez
 */

public class PanelJuego extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	private Pantalla pantallaActual;
	private static Font fuenteGrande = new Font("Arial", Font.PLAIN, 25);
	private JFrame ventana;

	/**
	 * Constructor
	 */
	public PanelJuego(JFrame ventana) {
		this.pantallaActual = new PantallaInicial(this, ventana);
		this.ventana = ventana;
		inicializarListeners();
		new Thread(this).start();
	}

	public Font getFuenteGrande() {
		return fuenteGrande;
	}

	public void setPantalla(Pantalla pantalla) {
		this.pantallaActual = pantalla;
	}

	/**
	 * Método que inicializa los listeners
	 */
	public void inicializarListeners() {
		// Ratón
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				pantallaActual.moverRaton(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				pantallaActual.moverRaton(e);
			}
		});
		// Disparo
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pantallaActual.pulsarRaton(e);
			}
		});
		// Personaje
		this.ventana.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int tecla = e.getKeyCode();
				// Movimiento hacia la izquierda
				if (tecla == KeyEvent.VK_A) {
					pantallaActual.pulsarA();
				}
				// Movimiento hacia la derecha
				else if (tecla == KeyEvent.VK_D) {
					pantallaActual.pulsarD();
					// Siquiente pantalla
				} else if (tecla == KeyEvent.VK_SPACE) {
					pantallaActual.pulsarEspacio();
				}
				// Elegir a Rick
				else if (tecla == KeyEvent.VK_R) {
					pantallaActual.pulsarR();
				}
				// Elegir a Morty
				else if (tecla == KeyEvent.VK_M) {
					pantallaActual.pulsarM();
				}
				if (tecla == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});
		// Reescalado
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				pantallaActual.redimensionarPantalla();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		pantallaActual.renderizarPantalla(g);
	}

	@Override
	public void run() {
		while (true) {
			pantallaActual.ejecutarFrame();
		}
	}
}