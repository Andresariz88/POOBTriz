package presentacion;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import dominio.*;

public class PanelTetriz extends JPanel implements KeyListener {
	
	private static int FPS = 60;
	private static int retraso = FPS / 1000;
	
	public static final int BOARD_WIDTH = 10;
	public static final int BOARD_HEIGHT = 20;
	public static final int BLOCK_SIZE = 30;
	
	private String nombre;
	private POOBTriz juego;
	private Color colorTablero;
	
	private int normal = 600;
	private int rapido = 50;
	private long inicio;
	
	private Timer looper;
	
	public PanelTetriz(POOBTriz juego, String nombre, Color color, int vel) {
		this.juego = juego;
		this.nombre = nombre;
		normal = vel;
		juego.retrasoMov = vel;
		colorTablero = color;
		setLayout(null);
		repaint();
		new FiguraNormal(juego);
	}
	
	public void beginGame() {
		looper = new Timer(retraso, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				if (System.currentTimeMillis() - inicio > juego.retrasoMov) {
					if(juego.getState() == POOBTriz.GAME_OVER) {
						gameover();
						return;
					}
					inicio = System.currentTimeMillis();
					refresque();
					revalidate();
					repaint();
				}
			}
		});
		looper.start();
	}
	
	public void refresque() {
		juego.checkNewShape();
		juego.moveDown();
	}
	
	public void pause() {
		if (juego.getState() == POOBTriz.IN_GAME) {
			juego.setState(POOBTriz.PAUSE);
		} else if (juego.getState() == POOBTriz.PAUSE) {
			juego.setState(POOBTriz.IN_GAME);
		}
	}
	
	public void guardar(File directorio) {
		ObjectOutputStream escribiendoFichero;
		try {
			escribiendoFichero = new ObjectOutputStream( 
			new FileOutputStream(directorio) );
			escribiendoFichero.writeObject(juego);
			escribiendoFichero.writeObject(nombre);
			escribiendoFichero.writeObject(colorTablero);
			escribiendoFichero.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			Registro.registre(e);
		}
	}
	
	public void abrir(File archivo) {
		ObjectInputStream leyendo;
    	try {
			leyendo = new ObjectInputStream(new FileInputStream(archivo));
			Object aux = leyendo.readObject();
			juego = (POOBTriz)aux;
			aux = leyendo.readObject();
			nombre = (String)aux;
			aux = leyendo.readObject();
			colorTablero = (Color)aux;
			pause();
			beginGame();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			Registro.registre(e);
		} catch (ClassNotFoundException e) {
			System.out.println(POOBTrizException.CLASE_NO_ENCONTRADA);
			System.out.println(e.getMessage());
			Registro.registre(e);
		}
	}
	
	public void gameover() {
		looper.stop();
		ImageIcon imagenFondo = new ImageIcon("./images/gameover.png");
		JLabel fondo = new JLabel();
		fondo.setBounds((this.getWidth()/2)-200,150,410,455);
		fondo.setIcon(imagenFondo);
		this.add(fondo);
		checkHighscore();
	}
	
	public void checkHighscore() {
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
				if (juego.getPoints() > Integer.valueOf(palabras[1]) && !cambio) {
					if (i == 0) {highScore();}
					texto += nombre+" "+juego.getPoints()+"\n";
					i++;
					cambio = true;
				}
				texto+=linea+"\n";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Registro.registre(e);
		}
		//System.out.println(texto);
		FileWriter nfichero;
		PrintWriter pw;
		try {
			nfichero = new FileWriter("puntajes.txt");
			pw = new PrintWriter(nfichero);
			pw.println(texto);
			nfichero.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			Registro.registre(e);
		}
	}
	
	private void highScore() {
		JLabel highscore = new JLabel("¡Acabas de marcar un nuevo puntaje más alto!");
		highscore.setForeground(Color.WHITE);
		highscore.setBounds(785, 225, 480, 50);
		highscore.setFont(new Font("Calibri", Font.PLAIN, 20));
		highscore.setOpaque(true);
		highscore.setBackground(new Color(23, 23, 23));
		add(highscore);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Color[][] board = juego.getBoard();
		super.paintComponent(g);
		
		//Fondos
		g.setColor(new Color(23, 23, 23));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(colorTablero); //param
		g.fillRect(450, BLOCK_SIZE, BLOCK_SIZE*BOARD_WIDTH, BLOCK_SIZE*BOARD_HEIGHT);
		g.setColor(Color.white);
		
		//Info
		g.setFont(new Font("Dialog", Font.PLAIN, 21));
		g.drawString("Puntos: "+juego.getPoints()+"", 785, 200);
		
		//Buffos
		if (juego.getBuffoActual() != null) {
			try {
				g.drawImage(ImageIO.read(new File("./images/"+juego.getBuffoActual().getClass().getSimpleName()+".png")), 785, 370, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			g.drawString(juego.getBuffoActual().getClass().getSimpleName(), 847, 350);
		}
		if (juego.getBuffos().size() > 0) {
			g.drawString("Buffo:", 785, 350);
			g.drawRect(785, 370, 75 ,75);
		}
		
		//Figuras
		for (int fil = 0; fil < BOARD_HEIGHT; fil ++) {
			for (int col = 0; col < BOARD_WIDTH; col ++) {
				if (board[fil][col] != null) {
					g.setColor(board[fil][col]);
					g.fillRect((col*BLOCK_SIZE)+450, (fil*BLOCK_SIZE)+BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
		}
		
		//Tablero
		g.setColor(Color.white);
		for (int fil = 0; fil <= BOARD_HEIGHT; fil++) {
			g.drawLine(450, (BLOCK_SIZE * fil)+BLOCK_SIZE, (BLOCK_SIZE * BOARD_WIDTH)+450, (BLOCK_SIZE * fil)+BLOCK_SIZE);
		}
		for (int col = 0; col < BOARD_WIDTH+1; col++) {
			g.drawLine((BLOCK_SIZE * col)+450, BLOCK_SIZE, (BLOCK_SIZE * col)+450, (BLOCK_SIZE * BOARD_HEIGHT)+BLOCK_SIZE);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (juego.getState() == POOBTriz.IN_GAME) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN && (juego.getBuffoActual() == null || juego.getBuffoActual() instanceof BuffoStopDiece || juego.getBuffoActual() instanceof BuffoStopTime)) {
					juego.retrasoMov = rapido;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				juego.moveRight();
				repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				juego.moveLeft();
				repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				juego.rotateShape();
				repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN && (juego.getBuffoActual() == null || juego.getBuffoActual() instanceof BuffoStopDiece || juego.getBuffoActual() instanceof BuffoStopTime)) {
			juego.retrasoMov = normal;
		}
	}
}
