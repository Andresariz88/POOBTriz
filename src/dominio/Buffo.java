package dominio;

import java.awt.*;
import java.io.Serializable;
 
/**
 * Clase padre de los buffos.
 * @author Andrés Ariza y Juan Pablo Sánchez
 * @version 2021-2
 */
public abstract class Buffo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final Color[] COLORES_BUFFOS = {Color.yellow, new Color(107, 27, 154), new Color(0, 143, 57), Color.orange};
	protected POOBTriz juego;

	/**
	 * Constructor de la clase Buffo
	 * @param juego POOBTriz al que se añadirá el buffo
	 */
	public Buffo(POOBTriz juego) {
		this.juego = juego;
	}
	
	/**
	 * Ejecuta la accion del buffo.
	 */
	abstract public void accion();
	
	/**
	 * Deshace la acción del buffo.
	 */
	abstract public void deshacer();
	
}
