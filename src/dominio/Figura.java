package dominio;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase padre de las figuras.
 * @author Andrés Ariza y Juan Pablo Sánchez
 * @version 2021-2
 */
public abstract class Figura implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int fila = 0, columna = 4;
	private int[][][] shapes = {
		{{1, 1, 1, 1}},
		
		{{1, 1, 1}, 
		 {0, 1, 0}},
		
		{{1, 1, 1},
		 {1, 0, 0}},
		
		{{0, 1, 1},
		 {1, 1, 0}},
		
		{{1, 1},
		 {1, 1}}
	};
	
	protected int[][] shape;
	protected  ArrayList<Integer[]> posiciones = new ArrayList<Integer[]>();

	protected Color[] colores = {new Color(26, 214, 214), new Color(255, 21, 255), new Color(254, 129, 0), new Color(0, 230, 0), new Color(229, 230, 1)};
	protected Color color;
	
	protected POOBTriz juego;
	
	/**
	 * Constructor de una figura aleatoria.
	 * @param juego POOBTriz al que se agregará la figura.
	 */
	public Figura(POOBTriz juego) {
		int random = (int) (Math.random()*shapes.length);
		shape = shapes[random];
		color = colores[random];
		this.juego = juego;
		generarPosiciones();
		//juego.addShape(this);
	}
	
	// clase creada meramente para casos de prueba, ya que las figuras salen aleatoriamente
	/**
	 * Constructor de una figura.
	 * @param juego POOBTriz al que se agregará la figura.
	 * @param figura entero que representa la figura
	 */
	public Figura(POOBTriz juego, int figura) {
		shape = shapes[figura];
		color = colores[figura];
		this.juego = juego;
		generarPosiciones();
		//juego.addShape(this);
	}

	/**
	 * Mueve la figura hacia abajo.
	 */
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
			}
		} else {
			juego.setCollision(true);
		}
	} 
	
	/**
	 * Mueve la figura a la izquierda.
	 */
	public void moveLeft() {
		boolean left = true;
		if (columna != 0) {
			Color[][] board = juego.getBoard();
			// miramos si hay otra figura en la dirección a la que va esta
			for (int fil = 0; fil < shape.length; fil++) {
				for (int col = 0; col < shape[0].length; col++) {
					if (shape[fil][col] != 0 && board[fila+fil][columna+col-1] != null) {
						left = false;
					} if (shape[fil][col] != 0 && POOBTriz.checkBuffo(board[fila+fil][columna+col-1])) {
						juego.setBuffoActual();
						left = true;
					}
 				}
			}
			if (left) { // si el mov se puede hacer cambiamos las coordenadas de la figura
				columna -= 1;
				for (Integer[] coordenada : posiciones) {
					coordenada[1] -= 1;
				}
			}
		}
	}
	
	/**
	 * Mueve la figura a la derecha.
	 */
	public void moveRight() {
		boolean right = true;
		if (columna + shape[0].length < 10) {
			Color[][] board = juego.getBoard();
			// miramos si hay otra figura en la dirección a la que va esta
			for (int fil = 0; fil < shape.length; fil++) {
				for (int col = 0; col < shape[0].length; col++) {
					if (shape[fil][col] != 0 && board[fila+fil][columna+col+1] != null) {
						right = false;
					} if (shape[fil][col] != 0 && POOBTriz.checkBuffo(board[fila+fil][columna+col+1])) {
						juego.setBuffoActual();
						right = true;
					}
				}
			}
			if (right) { // si el mov se puede hacer cambiamos las coordenadas de la figura
				columna += 1;
				for (Integer[] coordenada : posiciones) {
					coordenada[1] += 1;
				}
			}
		}
	}
	
	/**
	 * Rota la figura.
	 */
	public void rotateShape() {
		int[][] transpuesta = new int[shape[0].length][shape.length];
		for(int fil = 0; fil < shape.length; fil++) {
			for(int col = 0; col < shape[0].length; col++) {
				transpuesta[col][fil] = shape[fil][col];
			}
		}
		int middle = transpuesta.length / 2;
		// Invertir filas excepto la de la mitad, esto da el efecto de girarla
		for (int fil = 0; fil < middle; fil++) {
			int[] temp = transpuesta[fil];
			transpuesta[fil] = transpuesta[transpuesta.length - fil - 1];
			transpuesta[transpuesta.length - fil - 1] = temp;
		}
		// Mira si al girarla se sale del tablero
		if (columna + transpuesta[0].length > POOBTriz.BOARD_WIDTH || fila + transpuesta.length > POOBTriz.BOARD_HEIGHT) {
			return;
		}
		// Mira si al girarla se choca con una figura
		for(int fil = 0; fil < transpuesta.length; fil++) {
			for(int col = 0; col < transpuesta[0].length; col++) {
				if (transpuesta[fil][col] != 0 && juego.getBoard()[fila+fil][columna+col] != null) {
					return;
				}
			}
		}
		shape = transpuesta;
		posiciones = new ArrayList<Integer[]>();
		generarPosiciones();
	}
	
	/**
	 * Genera la lista posiciones, esta contiene los lugares del tablero donde hay figura.
	 */
	public void generarPosiciones() {
		for (int i=0; i < (shape.length); i++) {
			for (int j=0; j < (shape[0].length);j++) {
				if (shape[i][j] != 0) {
					Integer[] posi = {i+fila,j+columna};
					posiciones.add(posi);
				}
			}
		}
	}
	
	/**
	 * Retorna la lista de posiciones.
	 * @return posiciones
	 */
	public ArrayList<Integer[]> getPosiciones() {
		return posiciones;
	}

	/**
	 * Retrona la fila en la que está la figura.
	 * @return fila
	 */
	public int getFila() {
		return fila;
	}
	
	/**
	 * Retrona la fila en la que está la columna.
	 * @return columna
	 */
	public int getColumna() {
		return columna;
	}

	/**
	 * Retrona el arreglo correspondiente a la forma de la figura.
	 * @return shape
	 */
	public int[][] getShape() {
		return shape;
	}
	
	/**
	 * Retorna el color de la figura.
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Retorna la longitud del arreglo correspondiente a la forma de la figura.
	 * @return longitud de shape
	 */
	public int length() {
		return shape.length;
	}
}
