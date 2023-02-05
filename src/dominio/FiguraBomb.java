package dominio;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase de la figura Bomb (explosiva).
 * @author Andrés Ariza y Juan Pablo Sánchez
 * @version 2021-2
 */
public class FiguraBomb extends Figura implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de una figura bomb.
	 * @param juego POOBTriz al que se agregará la figura.
	 */
	public FiguraBomb(POOBTriz juego) {
		super(juego);
		this.color = Color.RED;;
		juego.addShape(this);
	}

	/**
	 * Constructor de una figura bomb.
	 * @param juego POOBTriz al que se agregará la figura.
	 * @param figura entero que representa la figura.
	 */
	public FiguraBomb(POOBTriz juego, int figura) {
		super(juego, figura);
		this.color = Color.RED;;
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
				autodestruir(); //cuando ya se ubicó
			}
		} else {
			autodestruir(); //cuando ya se ubicó
			juego.setCollision(true);
		}
	}

	/*
	 * Destruye los bloques cercanos y a la figura.
	 */
	private void autodestruir() {
		Color[][] board = juego.getBoard();
		for (int fil = 0; fil < shape.length; fil++) {
			for (int col = 0; col < shape[0].length; col++) {
				// destruye izquierda
				if (columna+col-1>=0) {
					if (shape[fil][col] != 0 && board[fila+fil][columna+col-1] != null) {
						board[fila+fil][columna+col-1] = null;
					}
				}
				//destruye derecha
				if (columna+col+1 < 10) {
					if (shape[fil][col] != 0 && board[fila+fil][columna+col+1] != null) {
						board[fila+fil][columna+col+1] = null;
					}
				}
				//destruye arriba
				if (fila+fil-1>=0) {
					if (shape[fil][col] != 0 && board[fila+fil-1][columna+col] != null) {
						board[fila+fil-1][columna+col] = null;
					}
				}
				
				//destruye abajo
				if (fila+fil+1 < 20) {
					if (shape[fil][col] != 0 && board[fila+fil+1][columna+col] != null) {
						board[fila+fil+1][columna+col] = null;
					}
				}
				
			}
		}
		//se destruye a si misma
		for (int fil = 0; fil < shape.length; fil++) {
			for (int col = 0; col < shape[0].length; col++) {
				shape[fil][col] = 0;
				posiciones = new ArrayList<Integer[]>();
				generarPosiciones();
			}
		}
	}
	
}
