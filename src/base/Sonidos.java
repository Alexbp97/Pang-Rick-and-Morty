package base;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * @author Alejandro Bajo Pérez
 */

public class Sonidos implements Runnable {

	private String ruta;
	private Player player;

	public Sonidos(String ruta) {
		this.ruta = ruta;
	}

	public void detener() {
		player.close();
	}

	@Override
	public void run() {
		try {
			FileInputStream fis = new FileInputStream(new File(ruta));
			BufferedInputStream bis = new BufferedInputStream(fis);
			player = new Player(bis);
			player.play();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}
}