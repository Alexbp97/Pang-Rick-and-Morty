package pantallas;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * @author Alejandro Bajo P�rez
 */

public interface Pantalla {

	/**
	 * M�todo que inicializa una pantalla.
	 */
	public void inicializarPantalla();

	/**
	 * M�todo que determina c�mo pinta una pantalla
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