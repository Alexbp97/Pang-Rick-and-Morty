package base;

import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 * @author Alejandro Bajo Pérez
 */
public class VentanaPrincipal {

	private JFrame ventana;
	private PanelJuego panel;
	public static String personaje = "Rick";

	/**
	 * Constructor
	 */
	public VentanaPrincipal() {
		this.ventana = new JFrame();
		this.ventana.setBounds(500, 300, 900, 500);
		this.ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.ventana.setTitle("Pang Rick & Morty | Alejandro Bajo Pérez");
	}

	/**
	 * Inicializa los componentes
	 */
	public void inicializarComponentes() {
		this.ventana.setLayout(new GridLayout(1, 1));
		this.panel = new PanelJuego(ventana);
		// Panel
		this.ventana.add(panel);
	}

	/**
	 * Llama a los otros métodos para construir la ventana
	 */
	public void inicializar() {
		this.ventana.setVisible(true);
		inicializarComponentes();
	}

	public static String getPersonaje() {
		return personaje;
	}

	public static void setPersonaje(String personaje) {
		VentanaPrincipal.personaje = personaje;
	}
}