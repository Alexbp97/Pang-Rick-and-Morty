package pantallas;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * @author Alejandro Bajo Pérez
 */

public interface Pantalla {

	/**
	 * Método que inicializa una pantalla.
	 */
	public void inicializarPantalla();

	/**
	 * Método que determina cómo pinta una pantalla
	 * 
	 * @param g
	 */
	public void renderizarPantalla(Graphics g);

	/**
	 * Determina que es lo que ocurre en la pantalla en cada frame
	 */
	public void ejecutarFrame();

	public void moverRaton(MouseEvent e);

	public void pulsarRaton(MouseEvent e);

	public void redimensionarPantalla();

	public void pulsarA();

	public void pulsarD();

	public void pulsarR();

	public void pulsarM();

	public void pulsarEspacio();
}