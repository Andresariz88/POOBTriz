package dominio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Clase principal del juego.
 * @author Andrés Ariza y Juan Pablo Sánchez
 * @version 2021-2
 */
public class POOBTriz implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int BOARD_WIDTH = 10;
	public static final int BOARD_HEIGHT = 20;
	public static final int IN_GAME = 0;
	public static final int PAUSE = 1;
	public static final int GAME_OVER = 2;

	private Color[][] board;
	private Figura figuraActual;
	private Buffo buffoEnTablero;
	private Buffo buffoActual;
	private ArrayList<String> buffos;
	private boolean collision;
	public int retrasoMov = 600;
	
	private int state = IN_GAME;
	private int points = 0;
	private int movs = 0;
	public float buffTime = -1;

	/**
	 * Constructor del tablero.
	 * @param buffos buffos que estarán disponibles para jugar
	 */
	public POOBTriz(ArrayList<String> buffos) {
		this.buffos = buffos;
		board = new Color[BOARD_HEIGHT][BOARD_WIDTH];
	}
	
	/**
	 * Añade una figura al tablero.
	 * @param shape figura.
	 */
	public void addShape(Figura shape) {
		if (state == IN_GAME) {
			checkLine();
			figuraActual = shape;
			checkGameOver();
			paint();
		}
	}
	
	/**
	 * Movimiento hacia abajo.
	 */
	public void moveDown() {
		if (state == IN_GAME) {
			erase();
			figuraActual.moveDown();
			paint();
			points++;
			movs++;
			buffTime-=0.25;
			if (movs % 150 == 0 && buffoEnTablero == null && buffos.size() > 0) {
				dropBuff();
			}
			if (buffTime == 0) {
				buffoActual.deshacer();
				buffoActual = null;
			}
		}
	}
	
	/**
	 * Movimiento hacia la izquierda.
	 */
	public void moveLeft() {
		if (state == IN_GAME) {
			erase();
			figuraActual.moveLeft();
			paint();
		}
	}
	
	/**
	 * Movimiento hacia la derecha.
	 */
	public void moveRight() {
		if (state == IN_GAME) {
			erase();
			figuraActual.moveRight();
			paint();
		}
	}
	
	/**
	 * Rotación.
	 */
	public void rotateShape() {
		if (state == IN_GAME) {
			erase();
			figuraActual.rotateShape();
			paint();
		}
	}
	
	/**
	 * Suelta un buffo en el talbero.
	 */
	public void dropBuff() {
		int random = (int) (Math.random()*buffos.size());
		try {
			Class<?> clazz = Class.forName("dominio."+buffos.get(random));
			Constructor<?> ctor = clazz.getConstructor(POOBTriz.class);
			Object object = ctor.newInstance(this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Registro.registre(e);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Registro.registre(e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Registro.registre(e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Registro.registre(e);
		}
	}
	
	/*
	 * Revisa si hay una linea que eliminar.
	 */
	private void checkLine() {
		int lineaAbajo = board.length - 1;
		for (int lineaArriba = board.length - 1; lineaArriba > 0; lineaArriba--) {
			int conteo = 0;
			for (int col = 0; col < board[0].length; col++) {
				if (board[lineaArriba][col] != null &&  board[lineaArriba][col].getRed() != FiguraUseless.COLOR_GRIS.getRed() 
						&& board[lineaArriba][col].getGreen() != FiguraUseless.COLOR_GRIS.getGreen() 
							&& board[lineaArriba][col].getBlue() != FiguraUseless.COLOR_GRIS.getBlue()) {
					conteo++;
				}
				board[lineaAbajo][col] = board[lineaArriba][col];
			}
			if(conteo < board[0].length) {
				lineaAbajo--;
			} else {
				points += 50;
			}
		}
	}
	
	/**
	 * Revisa si el juego necesita una nueva ficha.
	 */
	public void checkNewShape() {
		if (collision) {
			int random = (int) (Math.random()*100);
			if (random<85) {
				new FiguraNormal(this);
				setCollision(false);
			} else {
				random = (int) (Math.random()*100);
				if (random < 33) {
					new FiguraUseless(this);
					setCollision(false);
				} else if (random < 66) {
					new FiguraBomb(this);
					setCollision(false);
				} else {
					new FiguraWinner(this);
					setCollision(false);
				}
			}
		}
	}
	
	/**
	 * Método que evida que una figura colicione con un buffo. Este revisa si el objeto cercano a una figura es un buffo.
	 * @param buffo objeto a revisar
	 * @return true si el objeto es un buffo, false de lo contrario.
	 */
	public static boolean checkBuffo(Color buffo) {
		boolean res = false;
		if (buffo != null) {
		for (Color color : Buffo.COLORES_BUFFOS) {
			if (color.getRed() == buffo.getRed() && color.getGreen() == buffo.getGreen() && color.getBlue() == buffo.getBlue()) {
				res = true;
			}
		}
		}
		return res;
	}
	
	/*
	 * Revisa si el juego termina.
	 */
	private void checkGameOver() {
		int[][] shape = figuraActual.getShape();
		for(int fil = 0; fil < shape.length ; fil ++) {
			for(int col = 0; col < shape[0].length ; col ++) {
				if (shape[fil][col] != 0 && board[fil + figuraActual.getFila()][col + figuraActual.getColumna()] != null) {
					state = GAME_OVER;
				}
			}
		}
	}
	
	/*
	 * Borra la figura actual.
	 */
	private void erase() {
		ArrayList<Integer[]> posiciones = figuraActual.getPosiciones();
		for (Integer[] coordenada : posiciones) {
			board[coordenada[0]][coordenada[1]] = null;
		}
	}
	
	/*
	 * Dibuja la figura actual.
	 */
	private void paint() {
		ArrayList<Integer[]> posiciones = figuraActual.getPosiciones();
		for (Integer[] coordenada : posiciones) {
			board[coordenada[0]][coordenada[1]] = figuraActual.getColor();
		}
	}
	
	/**
	 * Cambia la colisión. (estado de la figura actual)
	 * @param collision estado de la colision
	 */
	public void setCollision(boolean collision) {
		this.collision = collision;
	}
	
	/**
	 * Añade un buffo al tablero.
	 * @param buffoEnTablero buffo a añadir.
	 */
	public void setBuffoEnTablero(Buffo buffoEnTablero) {
		this.buffoEnTablero = buffoEnTablero;
	}

	/**
	 * Quita el buffo del tablero, lo establece como buffo actual y le pide acción.
	 */
	public void setBuffoActual() {
		this.buffoActual = buffoEnTablero;
		buffoActual.accion();
		buffoEnTablero = null;
	}

	/**
	 * Dice si la figura actual está en estado de colisión.
	 * @return collision
	 */
	public boolean isCollision() {
		return collision;
	}

	/**
	 * Cambia el estado del juego.
	 * @param state estado del juego (0, 1 o 2);
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	/**
	 * Retorna el tablero de colores.
	 * @return board
	 */
	public Color[][] getBoard() {
		return board;
	}
	
	/**
	 * Retorna el estado del juego.
	 * @return state
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * Retorna la figura actual.
	 * @return figuraActual
	 */
	public Figura getFiguraActual() {
		return figuraActual;
	}

	/**
	 * Retorna los puntos del juego.
	 * @return points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Retorna el buffo actual que está en acción.
	 * @return buffoActual
	 */
	public Buffo getBuffoActual() {
		return buffoActual;
	}

	/**
	 * Retorna una lista correspondiente a los buffos que el usuario escogió para jugar.
	 * @return buffos
	 */
	public ArrayList<String> getBuffos() {
		return buffos;
	}

	@Override
	public String toString() {
		Color[][] board = this.getBoard();
		for(int fil = 0; fil < BOARD_HEIGHT; fil ++) {
			for(int col = 0; col < BOARD_WIDTH; col ++) {
				System.out.print(board[fil][col]+" ");
			}
			System.out.println();
		}
		return "";
	}
	
}
