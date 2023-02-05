package dominio;

import java.awt.Color;

/**
 * Clase del buffo StopDiece.
 * @author Andrés Ariza y Juan Pablo Sánchez
 * @version 2021-2
 */
public class BuffoStopDiece extends Buffo {

	private static final long serialVersionUID = 1L;
	private Color color = COLORES_BUFFOS[1];
	
	/**
	 * Constructor de la clase BuffoStopDiece
	 * @param juego POOBTriz al que se añadirá el buffo
	 */
	public BuffoStopDiece(POOBTriz juego) {
		super(juego);
		int columna = (int) (Math.random()*9);
		juego.getBoard()[4][columna] = color;
		juego.setBuffoEnTablero(this);
	}
	
	@Override
	public void accion() {
		juego.buffTime = (float)0.5;
		juego.retrasoMov = 2000000;
	}
	
	@Override
	public void deshacer() {
		juego.retrasoMov = 600;
	}
}
