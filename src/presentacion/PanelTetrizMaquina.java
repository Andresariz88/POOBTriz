package presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import dominio.BuffoStopDiece;
import dominio.BuffoStopTime;
import dominio.FiguraNormal;
import dominio.POOBTriz;

public class PanelTetrizMaquina extends JPanel implements KeyListener {
	private static int FPS = 60;
	private static int retraso = FPS / 1000;
	
	public static final int BOARD_WIDTH = 10;
	public static final int BOARD_HEIGHT = 20;
	public static final int BLOCK_SIZE = 30;
	
	private String nombre1;
	private POOBTriz juego1;
	private Color colorTablero1;
	
	private String nombre2;
	private POOBTriz juego2;
	private Color colorTablero2;
	
	private int normal = 600;
	private int rapido = 50;
	private long inicio1;
	private long inicio2;
	
	private Timer looper1;
	private Timer looper2;
	
	
	public PanelTetrizMaquina(POOBTriz juego, String nombre, Color color, POOBTriz juego2, String nombre2, Color color2) {
		this.juego1 = juego;
		this.nombre1 = nombre;
		colorTablero1 = color;
		this.juego2 = juego2;
		this.nombre2 = nombre2;
		colorTablero2 = color2;
		
		setLayout(null);
		repaint();
		new FiguraNormal(juego1);
		new FiguraNormal(juego2);
		
	}
	
	public void beginGame() {
		looper1 = new Timer(retraso, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				if (System.currentTimeMillis() - inicio1 > juego1.retrasoMov) {
					if(juego1.getState() == POOBTriz.GAME_OVER || juego2.getState() == POOBTriz.GAME_OVER) {
						gameover();
						return;
					}
					inicio1 = System.currentTimeMillis();
					refresque1();
					revalidate();
					repaint();
				}
			}
		});
		looper1.start();
		
		looper2 = new Timer(retraso, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				if (System.currentTimeMillis() - inicio2 > juego2.retrasoMov) {
					if(juego1.getState() == POOBTriz.GAME_OVER || juego2.getState() == POOBTriz.GAME_OVER) {
						gameover();
						return;
					}
					inicio2 = System.currentTimeMillis();
					refresque2();
					revalidate();
					repaint();
				}
			}
		});
		
		looper2.start();
	}
	
	public void refresque1() {
		juego1.checkNewShape();
		juego1.moveDown();
		
	}
	
	public void refresque2() {
		juego2.checkNewShape();
		juego2.moveDown();
		int random = (int) (Math.random()*100);
		if (!juego2.isCollision()) {
			if (random < 30) {
				juego2.moveLeft();
			} else if (random < 60) {
				juego2.moveRight();
			} else if (random < 80) {
				
			} else {
				juego2.rotateShape();
			}
		}
	}
	
	public void pause() {
		if (juego1.getState() == POOBTriz.IN_GAME && juego2.getState() == POOBTriz.IN_GAME) {
			juego1.setState(POOBTriz.PAUSE);
			juego2.setState(POOBTriz.PAUSE);
		} else if (juego1.getState() == POOBTriz.PAUSE && juego2.getState() == POOBTriz.PAUSE) {
			juego1.setState(POOBTriz.IN_GAME);
			juego2.setState(POOBTriz.IN_GAME);
		}
	}
	
	/*public void guardar() {
		ObjectOutputStream escribiendoFichero;
		try {
			escribiendoFichero = new ObjectOutputStream( 
			new FileOutputStream("data.dat") );
			escribiendoFichero.writeObject(juego1);
			escribiendoFichero.writeObject(nombre1);
			escribiendoFichero.writeObject(colorTablero1);
			escribiendoFichero.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void abrir() {
		ObjectInputStream leyendo;
    	try {
			leyendo = new ObjectInputStream(new FileInputStream("data.dat"));
			Object aux = leyendo.readObject();
			juego1 = (POOBTriz)aux;
			aux = leyendo.readObject();
			nombre1 = (String)aux;
			aux = leyendo.readObject();
			colorTablero1 = (Color)aux;
			beginGame();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}*/
	
	public void gameover() {
		looper1.stop();
		looper2.stop();
		ImageIcon imagenFondo = new ImageIcon("./images/gameover.png");
		JLabel fondo = new JLabel();
		if (juego1.getState() == POOBTriz.GAME_OVER) { 
			fondo.setBounds(150,150,410,455);
			juego2.setState(POOBTriz.PAUSE);
		} else {
			fondo.setBounds(650,150,410,455);
			juego1.setState(POOBTriz.PAUSE);
		}
		fondo.setIcon(imagenFondo);
		this.add(fondo);
		//checkHighscore();
	}
	
	/*public void checkHighscore() {
		FileReader fichero = null;
    	BufferedReader br = null;
    	String linea;
    	String texto = "";
    	String[] palabras = null;
    	boolean cambio = false;
		try {
			fichero = new FileReader(new File("puntajes.txt"));
			br = new BufferedReader(fichero);
			for (int i = 0; i < 5; i++) {
				linea=br.readLine();
				palabras = linea.trim().split(" ");
				if (juego1.getPoints() > Integer.valueOf(palabras[1]) && !cambio) {
					texto += nombre1+" "+juego1.getPoints()+"\n";
					i++;
					cambio = true;
				}
				texto+=linea+"\n";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(texto);
	}*/
	
	@Override
	protected void paintComponent(Graphics g) {
		Color[][] board1 = juego1.getBoard();
		Color[][] board2 = juego2.getBoard();
		super.paintComponent(g);
		
		g.setColor(new Color(23, 23, 23));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//Fondo1
		g.setColor(colorTablero1); //param
		g.fillRect(200, BLOCK_SIZE, BLOCK_SIZE*BOARD_WIDTH, BLOCK_SIZE*BOARD_HEIGHT);
		g.setColor(Color.white);
		
		//Info1
		g.setFont(new Font("Dialog", Font.PLAIN, 21));
		g.drawString("Puntos: "+juego1.getPoints()+"", 65, 200);
		
		//Buffos1
		if (juego1.getBuffoActual() != null) {
			try {
				g.drawImage(ImageIO.read(new File("./images/"+juego1.getBuffoActual().getClass().getSimpleName()+".png")), 95, 370, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			g.drawString(juego1.getBuffoActual().getClass().getSimpleName(), 65, 350);
		}
		if (juego1.getBuffos().size() > 0) {
			//g.drawString("Buffo:", 65, 350);
			g.drawRect(95, 370, 75 ,75);
		}
		
		//Figuras1
		for (int fil = 0; fil < BOARD_HEIGHT; fil ++) {
			for (int col = 0; col < BOARD_WIDTH; col ++) {
				if (board1[fil][col] != null) {
					g.setColor(board1[fil][col]);
					g.fillRect((col*BLOCK_SIZE)+200, (fil*BLOCK_SIZE)+BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
		}
		
		//Tablero1
		g.setColor(Color.white);
		for (int fil = 0; fil <= BOARD_HEIGHT; fil++) {
			g.drawLine(200, (BLOCK_SIZE * fil)+BLOCK_SIZE, (BLOCK_SIZE * BOARD_WIDTH)+200, (BLOCK_SIZE * fil)+BLOCK_SIZE);
		}
		for (int col = 0; col < BOARD_WIDTH+1; col++) {
			g.drawLine((BLOCK_SIZE * col)+200, BLOCK_SIZE, (BLOCK_SIZE * col)+200, (BLOCK_SIZE * BOARD_HEIGHT)+BLOCK_SIZE);
		}
		
		//Fondo2
		g.setColor(colorTablero2); //param
		g.fillRect(700, BLOCK_SIZE, BLOCK_SIZE*BOARD_WIDTH, BLOCK_SIZE*BOARD_HEIGHT);
		g.setColor(Color.white);
		
		//Info2
		g.setFont(new Font("Dialog", Font.PLAIN, 21));
		g.drawString("Puntos: "+juego2.getPoints()+"", 1030, 200);
		
		//Buffos2
		if (juego2.getBuffoActual() != null) {
			try {
				g.drawImage(ImageIO.read(new File("./images/"+juego2.getBuffoActual().getClass().getSimpleName()+".png")), 1030, 370, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			g.drawString(juego2.getBuffoActual().getClass().getSimpleName(), 1030, 350);
		}
		if (juego2.getBuffos().size() > 0) {
			//g.drawString("Buffo:", 65, 350);
			g.drawRect(1030, 370, 75 ,75);
		}
		
		//Figuras2
		for (int fil = 0; fil < BOARD_HEIGHT; fil ++) {
			for (int col = 0; col < BOARD_WIDTH; col ++) {
				if (board2[fil][col] != null) {
					g.setColor(board2[fil][col]);
					g.fillRect((col*BLOCK_SIZE)+700, (fil*BLOCK_SIZE)+BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
		}
		
		//Tablero2
		g.setColor(Color.white);
		for (int fil = 0; fil <= BOARD_HEIGHT; fil++) {
			g.drawLine(700, (BLOCK_SIZE * fil)+BLOCK_SIZE, (BLOCK_SIZE * BOARD_WIDTH)+700, (BLOCK_SIZE * fil)+BLOCK_SIZE);
		}
		for (int col = 0; col < BOARD_WIDTH+1; col++) {
			g.drawLine((BLOCK_SIZE * col)+700, BLOCK_SIZE, (BLOCK_SIZE * col)+700, (BLOCK_SIZE * BOARD_HEIGHT)+BLOCK_SIZE);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (juego1.getState() == POOBTriz.IN_GAME) {
			if (e.getKeyCode() == KeyEvent.VK_S && (juego1.getBuffoActual() == null || juego1.getBuffoActual() instanceof BuffoStopDiece || juego1.getBuffoActual() instanceof BuffoStopTime)) {
				juego1.retrasoMov = rapido;
			} else if (e.getKeyCode() == KeyEvent.VK_D) {
				juego1.moveRight();
				repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_A) {
				juego1.moveLeft();
				repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				juego1.rotateShape();
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (juego1.getState() == POOBTriz.IN_GAME) {
			if (e.getKeyCode() == KeyEvent.VK_S && (juego1.getBuffoActual() == null || juego1.getBuffoActual() instanceof BuffoStopDiece || juego1.getBuffoActual() instanceof BuffoStopTime)) {
			juego1.retrasoMov = normal;
			}
		}
	}

}
