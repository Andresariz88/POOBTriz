package dominio;

import java.awt.Color;

/**
 * Clase del buffo slow.
 * @author Andrés Ariza y Juan Pablo Sánchez
 * @version 2021-2
 */
public class BuffoSlow extends Buffo {

	private static final long serialVersionUID = 1L;
	private Color color = COLORES_BUFFOS[2];
	
	/**
	 * Constructor de la clase BuffoSlow
	 * @param juego POOBTriz al que se añadirá el buffo
	 */
	public BuffoSlow(POOBTriz juego) {
		super(juego);
		int columna = (int) (Math.random()*9);
		juego.getBoard()[4][columna] = color;
		juego.setBuffoEnTablero(this);
	}
	
	@Override
	public void accion() {
		juego.buffTime = 2;
		juego.retrasoMov = 800;
	}
	
	@Override
	public void deshacer() {
		juego.retrasoMov = 600;
	}
}
