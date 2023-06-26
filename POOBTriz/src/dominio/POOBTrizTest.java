package dominio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class POOBTrizTest {

	POOBTriz juego;
	ArrayList<String> buffos = new ArrayList<String>(Arrays.asList("BuffoStopTime", "BuffoStopDiece", "BuffoSlow", "Buffo2x"));
	
	@BeforeEach
	void setUp() throws Exception {
		juego = new POOBTriz(buffos);
	}
	
	@Test
	void deberiaAñadirFigura() {
		assertNull(juego.getFiguraActual());
		juego.setCollision(true);
		juego.checkNewShape();
		assertTrue(juego.getFiguraActual() != null);
	}
	
	@Test
	void deberiaBajar() {
		new FiguraNormal(juego);
		int y = juego.getFiguraActual().getFila();
		juego.moveDown();
		assertTrue(y + 1 == juego.getFiguraActual().getFila());
		juego.moveDown();
		assertTrue(y + 2 == juego.getFiguraActual().getFila());
	}
	
	@Test
	void noDeberiaBajar() {
		new FiguraNormal(juego);
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		juego.moveDown();
		assertFalse(juego.getFiguraActual().getFila() > 20);
	}
	
	@Test
	void deberiaMoverIzquierda() {
		new FiguraNormal(juego);
		int x = juego.getFiguraActual().getColumna();
		juego.moveLeft();
		assertTrue(x - 1 == juego.getFiguraActual().getColumna());
		juego.moveLeft();
		assertTrue(x - 2 == juego.getFiguraActual().getColumna());
		juego.moveLeft();
		assertTrue(x - 3 == juego.getFiguraActual().getColumna());
		juego.moveLeft();
		assertTrue(x - 4 == juego.getFiguraActual().getColumna());
	}
	
	@Test
	void noDeberiaMoverIzquierda() {
		new FiguraNormal(juego);
		int x = juego.getFiguraActual().getColumna();
		juego.moveLeft();
		juego.moveLeft();
		juego.moveLeft();
		juego.moveLeft();
		juego.moveLeft();
		assertFalse(x - 5 == juego.getFiguraActual().getColumna());
	}
	
	@Test
	void deberiaMoverDerechaa() {
		new FiguraNormal(juego);
		int x = juego.getFiguraActual().getColumna();
		juego.moveRight();
		assertTrue(x + 1 == juego.getFiguraActual().getColumna());
		juego.moveRight();
		assertTrue(x + 2 == juego.getFiguraActual().getColumna());
		juego.moveRight();
		juego.moveRight();
		juego.moveRight();
		juego.moveRight();
		juego.moveRight();
		juego.moveRight();
		assertFalse(x + 8 == juego.getFiguraActual().getColumna());
	}
	
	@Test
	void noDeberiaMoverDerechaa() {
		new FiguraNormal(juego);
		int x = juego.getFiguraActual().getColumna();
		juego.moveRight();
		juego.moveRight();
		juego.moveRight();
		juego.moveRight();
		juego.moveRight();
		juego.moveRight();
		juego.moveRight();
		juego.moveRight();
		assertFalse(x + 8 == juego.getFiguraActual().getColumna());
	}
	
	@Test
	void deberiaBorrarLineaCompleta() {
		// mira que la última linea esté vacía
		for (int fil = 0; fil < POOBTriz.BOARD_WIDTH; fil++) {
			assertNull(juego.getBoard()[POOBTriz.BOARD_HEIGHT-1][fil]);
		}
		// procedemos a llenar la última fila
		// ficha alargada a la izquierda
		new FiguraNormal(juego, 0);
		for (int i = 0; i < 4; i++) {
			juego.moveLeft();
		}
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		juego.setCollision(false);
		// ficha alargada a la izquierda
		new FiguraNormal(juego, 0);
		for (int i = 0; i < 4; i++) {
			juego.moveLeft();
		}
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		juego.setCollision(false);
		
		// ficha alargada a la derecha
		new FiguraNormal(juego, 0);
		for (int i = 0; i < 2; i++) {
			juego.moveRight();
		}
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		juego.setCollision(false);
		// ficha alargada a la derecha
		new FiguraNormal(juego, 0);
		for (int i = 0; i < 2; i++) {
			juego.moveRight();
		}
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		juego.setCollision(false);
		
		// cuadrado al centro
		new FiguraNormal(juego, 4);
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		juego.setCollision(false);
		
		new FiguraNormal(juego, 4);
		//System.out.println(juego);
		
		// mira que la última linea esté vacía
		for (int fil = 0; fil < POOBTriz.BOARD_WIDTH; fil++) {
			assertNull(juego.getBoard()[POOBTriz.BOARD_HEIGHT-1][fil]);
		}
	}
	
	@Test
	void noDeberiaBorrarLineaCompleta() {
		// mira que la última linea esté vacía
		for (int fil = 0; fil < POOBTriz.BOARD_WIDTH; fil++) {
			assertNull(juego.getBoard()[POOBTriz.BOARD_HEIGHT-1][fil]);
		}
		// procedemos a llenar la última fila
		// ficha alargada a la izquierda
		new FiguraNormal(juego, 0);
		for (int i = 0; i < 4; i++) {
			juego.moveLeft();
		}
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		juego.setCollision(false);
		// ficha alargada a la izquierda
		new FiguraNormal(juego, 0);
		for (int i = 0; i < 4; i++) {
			juego.moveLeft();
		}
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		juego.setCollision(false);
		
		// ficha alargada a la derecha
		new FiguraNormal(juego, 0);
		for (int i = 0; i < 2; i++) {
			juego.moveRight();
		}
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		juego.setCollision(false);
		// ficha alargada a la derecha
		new FiguraNormal(juego, 0);
		for (int i = 0; i < 2; i++) {
			juego.moveRight();
		}
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		juego.setCollision(false);
		
		// cuadrado useless al centro
		new FiguraUseless(juego, 4);
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		juego.setCollision(false);
		
		new FiguraNormal(juego, 4);
		//System.out.println(juego);
		
		// mira que la última linea no esté vacía
		for (int fil = 0; fil < POOBTriz.BOARD_WIDTH; fil++) {
			assertTrue(juego.getBoard()[POOBTriz.BOARD_HEIGHT-1][fil] != null);
		}
	}
	
	@Test
	void deberiaExplotar() {
		// creamos cuadrado useless y lo dejamos al fondo
		new FiguraUseless(juego, 4);
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		juego.setCollision(false);
		
		// creamos cuadrado bomb y lo ponemos encima del cuadrado anterior
		new FiguraBomb(juego, 4);
		for (int i = 0; i < POOBTriz.BOARD_HEIGHT; i++) {
			juego.moveDown();
		}
		
		// revisamos que se hayan borrado los bloques pertinentes
		assertTrue(juego.getBoard()[POOBTriz.BOARD_HEIGHT-1][4] != null);
		assertTrue(juego.getBoard()[POOBTriz.BOARD_HEIGHT-1][5] != null);
		assertNull(juego.getBoard()[POOBTriz.BOARD_HEIGHT-2][4]);
		assertNull(juego.getBoard()[POOBTriz.BOARD_HEIGHT-2][5]);
		assertNull(juego.getBoard()[POOBTriz.BOARD_HEIGHT-3][4]);
		assertNull(juego.getBoard()[POOBTriz.BOARD_HEIGHT-3][5]);
	}
	
	@Test
	void deberiaAcabarJuego() {
		assertTrue(juego.getState() == POOBTriz.IN_GAME);
		int j;
		for (int i = 10; i > 0; i--) {
			new FiguraNormal(juego, 1);
			j = i*2-2;
			while (j > 0) {
				juego.moveDown();
				j--;
			}
		}
		new FiguraNormal(juego);
		assertTrue(juego.getState() == POOBTriz.GAME_OVER);
	}
		

	@AfterEach
	void tearDown() throws Exception {
		juego = null;
	}
}
