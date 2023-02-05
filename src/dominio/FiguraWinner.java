package dominio;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase de la figura Winner.
 * @author Andrés Ariza y Juan Pablo Sánchez
 * @version 2021-2
 */
public class FiguraWinner extends Figura implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Color dorado = new Color(179,146,52);
	
	/**
	 * Constructor de una figura winner.
	 * @param juego POOBTriz al que se agregará la figura.
	 */
	public FiguraWinner(POOBTriz juego) {
		super(juego);
		color = dorado;
		juego.addShape(this);
	}

	/**
	 * Constructor de una figura winner.
	 * @param juego POOBTriz al que se agregará la figura.
	 * @param figura entero que representa la figura.
	 */
	public FiguraWinner(POOBTriz juego, int figura) {
		super(juego, figura);
		color = dorado;
		juego.addShape(this);
	}
	
	@Override
	public void moveDown() {
		if (fila + shape.length < 20) {
			Color[][] board = juego.getBoard();
			// miramos si hay otra figura en la dirección a la que va esta
			for (int fil = 0; fil < shape.length; fil++) {
				for (int col = 0; col < shape[0].length; col++) {
					if (shape[fil][col] != 0 && board[fila+fil+1][columna+col] != null) {
						juego.setCollision(true);
					} if (shape[fil][col] != 0 && POOBTriz.checkBuffo(board[fila+fil+1][columna+col])) {
						juego.setBuffoActual();
						juego.setCollision(false);
					}
				}
			}
			if (!juego.isCollision()) { // si el mov se puede hacer cambiamos las coordenadas de la figura
				fila += 1;
				for (Integer[] coordenada : posiciones) {
					coordenada[0] += 1;
				}
			} else {
				escurrir();
			}
		} else {
			escurrir();
			juego.setCollision(true);
		}
	}
	
	/*
	 * Hace que los bloques de la figura que puedan seguir bajando bajen una fila.
	 */
	private void escurrir() {
		Color[][] board = juego.getBoard();
		for (int fil = shape.length-1; fil > -1 ; fil--) {
			for (int col = shape[0].length-1; col > -1 ; col--) {
				// abajo
				if (fila+fil+1 < 20) {
					if (fil+1 < shape.length) {
						if (shape[fil][col] != 0 && board[fila+fil+1][columna+col] == null && shape[fil+1][col] == 0) {
							shape[fil][col] = 0;
							board[fila+fil+1][columna+col] = dorado;
						}
					} else {
						if (shape[fil][col] != 0 && board[fila+fil+1][columna+col] == null) {
							shape[fil][col] = 0;
							board[fila+fil+1][columna+col] = dorado;
						}
					}
				}
				
			}
		}
		posiciones = new ArrayList<Integer[]>();
		generarPosiciones();
	}

}
