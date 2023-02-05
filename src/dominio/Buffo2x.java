package dominio;

import java.awt.Color;

/**
 * Clase del buffo 2x.
 * @author Andrés Ariza y Juan Pablo Sánchez
 * @version 2021-2
 */
public class Buffo2x extends Buffo {

	private static final long serialVersionUID = 1L;
	private Color color = COLORES_BUFFOS[3];
	
	/**
	 * Constructor de la clase Buffo2x
	 * @param juego POOBTriz al que se añadirá el buffo
	 */
	public Buffo2x(POOBTriz juego) {
		super(juego);
		int columna = (int) (Math.random()*9);
		juego.getBoard()[4][columna] = color;
		juego.setBuffoEnTablero(this);
	}
	
	@Override
	public void accion() {
		juego.buffTime = 4;
		juego.retrasoMov = 150;
	}
	
	@Override
	public void deshacer() {
		juego.retrasoMov = 600;
	}
}
