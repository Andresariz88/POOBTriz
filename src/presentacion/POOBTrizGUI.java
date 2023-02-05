package presentacion;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import dominio.*;

import java.util.*;
import java.io.*;
import java.lang.reflect.Constructor;

/**
 * Clase principal del frame del videojuego POOBTriz.
 * @author Andrés Ariza & Juan Pablo Sánchez
 * @version 2021-2
 */
public class POOBTrizGUI extends JFrame {
	
	private POOBTriz juego;
	private POOBTriz juegoMulti1;
	private POOBTriz juegoMulti2;
	
	// Ventana
	private static CardLayout cl;
	private static Container contentpane;
	
	// Panel menu
	private JPanel menuPanel;
	private JLabel tituloMenu;
	private JButton botonJugar;
	private JButton botonPuntajes;
	private JButton botonSalir;
	
	//Panel modos
	private JPanel modosPanel;
	private JButton botonSolo;
	private JButton botonMulti;
	private JButton botonMaquina;
	private JButton botonAtrasModos;
	
	//Panel puntajes
	private JPanel puntajesPanel;
	private JButton botonAtrasPuntajes;
	
	//Panel cofiguracion juego solo
	private JPanel configSoloPanel;
	private JButton botonAtrasConfigSolo;
	private JButton botonJugarSolo;
	private JButton botonColorSolo;
	private JTextField campoNombre;
	private Color colorTableroSolo = new Color(23, 23, 23);
	private JButton botonCargarSolo;
	private JButton botonBuffosSolo;
	private JRadioButton lentoSolo;
	private JRadioButton rapidoSolo;
	
	//Panel selección de buffos solo
	private JPanel buffosSoloPanel;
	private ArrayList<String> buffosSolo = new ArrayList<String>(Arrays.asList("BuffoStopTime", "BuffoStopDiece", "BuffoSlow", "Buffo2x"));
	private ArrayList<JCheckBox> checkBoxesSolo = new ArrayList<JCheckBox>();
	private JButton botonOkBuffos;
	
	//Panel juego solo
	private PanelTetriz juegoSoloPanel;
	private JButton botonAtrasSolo;
	private JButton botonPausaSolo;
	private JButton botonGuardarSolo;
	
	//Panel configuracion juego multi
	private JPanel configMultiPanel;
	private JButton botonAtrasConfigMulti;
	private JButton botonJugarMulti;
	private JButton botonColorMulti;
	private JTextField campoNombreMulti;
	private JButton botonColorMulti1;
	private JTextField campoNombreMulti1;
	private Color colorTableroMulti = new Color(23, 23, 23);
	private Color colorTableroMulti1 = new Color(23, 23, 23);
	private JButton botonCargarMulti;
	private JButton botonBuffosMulti;
	private JRadioButton lentoMulti;
	private JRadioButton rapidoMulti;
	
	//Panel selección de buffos multi
	private JPanel buffosMultiPanel;
	private ArrayList<String> buffosMulti = new ArrayList<String>(Arrays.asList("BuffoStopTime", "BuffoStopDiece", "BuffoSlow", "Buffo2x"));
	private ArrayList<JCheckBox> checkBoxesMulti = new ArrayList<JCheckBox>();
	private JButton botonOkBuffosMulti;
	
	//Panel juego multi
	private PanelTetrizMulti juegoMultiPanel;
	private JButton botonAtrasMulti;
	private JButton botonPausaMulti;
	private JButton botonGuardarMulti;
	
	//Panel cofiguracion juego maquina
	private JPanel configMaquinaPanel;
	private JButton botonAtrasConfigMaquina;
	private JButton botonJugarMaquina;
	private JButton botonColorMaquina;
	private JTextField campoNombreMaquina;
	private Color colorTableroMaquina = new Color(23, 23, 23);
	//private JButton botonCargarMaquina;
	private JButton botonBuffosMaquina;
	
	//Panel juego maquina
	private PanelTetrizMaquina juegoMaquinaPanel;
	private JButton botonAtrasMaquina;
	private JButton botonPausaMaquina;
	private ArrayList<String> buffosMaquina = new ArrayList<String>(Arrays.asList("BuffoSlow", "Buffo2x"));
	
	private FileNameExtensionFilter datFilter = new FileNameExtensionFilter("dat files (*.dat)", "dat");
	
	/*
	 * Constructor de POOBTrizGUI.
	 */
	private POOBTrizGUI() {
		prepareElementos();
		prepareAcciones();
	}
	
	/*
	 * Método que prepara carácteristicas principales del frame.
	 */
	private void prepareElementos() {
		// Ajustes ventana
		setTitle("POOBTriz");
		setSize(new Dimension(1200, 675));
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon logo = new ImageIcon("./images/LogoTB.png");
		setIconImage(logo.getImage());
		
		//Layout
		cl = new CardLayout();
		contentpane = new JPanel(cl);
		add(contentpane);
		
		prepareElementosMenu();
		prepareElementosModos();
		prepareElementosPuntajes();
		prepareElementosConfigSolo();
		prepareElementosBuffos();
		prepareElementosSolo();
		prepareElementosConfigMulti();
		prepareElementosBuffosMulti();
		prepareElementosMulti();
		prepareElementosConfigMaquina();
	}

	/*
	 * Método que prepara el panel correspondiente al menu.
	 */
	private void prepareElementosMenu() {
		menuPanel = new JPanel();
		contentpane.add(menuPanel, "Menu");
		menuPanel.setLayout(null);
		
		//Titulo
        tituloMenu = new JLabel();
		tituloMenu.setIcon(new ImageIcon("./images/LogoGrande2.png"));
		tituloMenu.setBounds((this.getWidth()/2)-200,150,410,100);
        menuPanel.add(tituloMenu);
		
        //Boton jugar
		botonJugar = new JButton("Jugar");
		botonJugar.setBounds((this.getWidth()/2)-85,320,170,40);
		botonJugar.setBackground(new Color(0, 191, 254));
		botonJugar.setFocusPainted(false);
		menuPanel.add(botonJugar);
		
		//Boton puntajes
		botonPuntajes = new JButton("Mejores puntajes");
		botonPuntajes.setBounds((this.getWidth()/2)-85,375,170,40);
		botonPuntajes.setBackground(Color.green);
		botonPuntajes.setFocusPainted(false);
		menuPanel.add(botonPuntajes);
		
		//Boton salir
		botonSalir = new JButton("Salir");
		botonSalir.setBounds((this.getWidth()/2)-85,430,170,40);
		botonSalir.setBackground(new Color(241, 28, 48));
		botonSalir.setFocusPainted(false);
		menuPanel.add(botonSalir);
		
		//Fondo
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, getWidth(), getHeight());
		fondo.setIcon(new ImageIcon("./images/Fondo.png"));
		menuPanel.add(fondo);
		
	}
	
	/*
	 * Método que prepara el panel correspondiente a los modos de juego.
	 */
	private void prepareElementosModos() {
		modosPanel = new JPanel();
		contentpane.add(modosPanel, "Modos");
		modosPanel.setLayout(null);
		
		//Boton solo
		botonSolo = new JButton("Solo");
		botonSolo.setBounds((this.getWidth()/2)-85,265,170,40);
		//botonSolo.setBackground(new Color(0, 191, 254));
		botonSolo.setFocusPainted(false);
		modosPanel.add(botonSolo);
		
		//Boton multijugador
		botonMulti = new JButton("Jugador vs Jugador");
		botonMulti.setBounds((this.getWidth()/2)-85,320,170,40);
		//botonMulti.setBackground(new Color(0, 191, 254));
		botonMulti.setFocusPainted(false);
		modosPanel.add(botonMulti);
		
		//Boton multijugador
		botonMaquina = new JButton("Jugador vs Máquina");
		botonMaquina.setBounds((this.getWidth()/2)-85,375,170,40);
		//botonMaquina.setBackground(new Color(0, 191, 254));
		botonMaquina.setFocusPainted(false);
		modosPanel.add(botonMaquina);
		
		//Boton atras
		botonAtrasModos = new JButton("Atrás");
		botonAtrasModos.setBounds(10,585,70,40);
		botonAtrasModos.setBackground(new Color(241, 28, 48));
		botonAtrasModos.setFocusPainted(false);
		modosPanel.add(botonAtrasModos);
		
		//Fondo
		ImageIcon imagenFondo = new ImageIcon("./images/FondoModos.png");
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, getWidth(), getHeight());
		fondo.setIcon(imagenFondo);
		modosPanel.add(fondo);
	}
	
	/*
	 * Método que prepara el panel correspondiente a las configuraciones en modo solitario.
	 */
	private void prepareElementosConfigSolo() {
		configSoloPanel = new JPanel();
		contentpane.add(configSoloPanel, "ConfigSolo");
		configSoloPanel.setLayout(null);
		
		//Campo de texto
		campoNombre = new JTextField("Player");
        campoNombre.setHorizontalAlignment(JTextField.CENTER);
        campoNombre.setBounds((getWidth()/2)-100,260,200,28);
        campoNombre.setForeground(new Color(0x00FF00));
        campoNombre.setBackground(Color.BLACK);
        configSoloPanel.add(campoNombre);
		
        //Boton jugar
        botonJugarSolo = new JButton("Jugar");
        botonJugarSolo.setBounds((this.getWidth()/2)-85,320,170,40);
        configSoloPanel.add(botonJugarSolo);
        
        //Boton color
        botonColorSolo = new JButton("Color");
        botonColorSolo.setBounds((this.getWidth()/2)-85,370,170,40);
        configSoloPanel.add(botonColorSolo);
        
        //Boton buffos
        botonBuffosSolo = new JButton("Buffos");
        botonBuffosSolo.setBounds((this.getWidth()/2)-85,420,170,40);
        configSoloPanel.add(botonBuffosSolo);
        
        //Boton cargar
        botonCargarSolo = new JButton("Cargar");
        botonCargarSolo.setBounds((this.getWidth()/2)-85,470,170,40);
        configSoloPanel.add(botonCargarSolo);
        
		//Boton atras
		botonAtrasConfigSolo = new JButton("Atrás");
		botonAtrasConfigSolo.setBounds(10,585,70,40);
		botonAtrasConfigSolo.setBackground(new Color(241, 28, 48));
		botonAtrasConfigSolo.setFocusPainted(false);
		configSoloPanel.add(botonAtrasConfigSolo);
		
		//Velocidades
		ButtonGroup bg=new ButtonGroup();
		lentoSolo=new JRadioButton("Lento");
		lentoSolo.setBounds(520,520,70,30);
        configSoloPanel.add(lentoSolo);
        lentoSolo.setSelected(true);
        bg.add(lentoSolo);
        rapidoSolo=new JRadioButton("Rápido");
        rapidoSolo.setBounds(600,520,80,30);
        configSoloPanel.add(rapidoSolo);
        bg.add(rapidoSolo);
		
		//Fondo
		ImageIcon imagenFondo = new ImageIcon("./images/FondoConfig.png");
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, getWidth(), getHeight());
		fondo.setIcon(imagenFondo);
		configSoloPanel.add(fondo);
	}
	
	/*
	 * Método que prepara el panel correspondiente al menú de selección de buffos.
	 */
	private void prepareElementosBuffos() {
		buffosSoloPanel = new JPanel();
		contentpane.add(buffosSoloPanel, "Buffos");
		buffosSoloPanel.setLayout(null);
		
		JCheckBox stopTime = new JCheckBox("BuffoStopTime");
		stopTime.setBounds(485, 281, 120, 25);
		stopTime.setSelected(true);
		JCheckBox stopDiece = new JCheckBox("BuffoStopDiece");
		stopDiece.setBounds(485, 425, 120, 25);
		stopDiece.setSelected(true);
		JCheckBox slow = new JCheckBox("BuffoSlow");
		slow.setBounds(800, 281, 85, 25);
		slow.setSelected(true);
		JCheckBox x2 = new JCheckBox("Buffo2x");
		x2.setBounds(800, 425, 85, 25);
		x2.setSelected(true);
		
		buffosSoloPanel.add(stopTime);
		buffosSoloPanel.add(stopDiece);
		buffosSoloPanel.add(slow);
		buffosSoloPanel.add(x2);
		
		checkBoxesSolo.add(stopTime);
		checkBoxesSolo.add(stopDiece);
		checkBoxesSolo.add(slow);
		checkBoxesSolo.add(x2);
		
		//Boton ok
        botonOkBuffos = new JButton("Ok");
        botonOkBuffos.setBounds((this.getWidth()/2)-85,525,170,40);
        buffosSoloPanel.add(botonOkBuffos);
        
        //Fondo
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, getWidth(), getHeight());
		fondo.setIcon(new ImageIcon("./images/FondoBuffos.png"));
		buffosSoloPanel.add(fondo);
	}
	
	/*
	 * Método que prepara el panel correspondiente al juego en modo solitario.
	 */
	private void prepareElementosSolo() {
		juego = new POOBTriz(buffosSolo);
		int vel = rapidoSolo.isSelected() ? 380: 650;
		juegoSoloPanel = new PanelTetriz(juego, campoNombre.getText(), colorTableroSolo, vel);
		
		juegoSoloPanel.setLayout(null);
		contentpane.add(juegoSoloPanel, "Solo");
		
		//Botón pausa
		botonPausaSolo = new JButton();
		botonPausaSolo.setIcon(new ImageIcon("./images/BotonPausa.png"));
		botonPausaSolo.setBounds(770, 570, 50, 50);
		botonPausaSolo.setFocusPainted(false);
		botonPausaSolo.setBorderPainted(false);
		botonPausaSolo.setContentAreaFilled(false);
		botonPausaSolo.setHorizontalAlignment(JLabel.CENTER);
		botonPausaSolo.setVerticalAlignment(JLabel.CENTER);
		juegoSoloPanel.add(botonPausaSolo);
		
		//Botón guardar
		botonGuardarSolo = new JButton();
		botonGuardarSolo.setIcon(new ImageIcon("./images/BotonGuardar.png"));
		botonGuardarSolo.setBounds(1115,570,50,50);
		botonGuardarSolo.setFocusPainted(false);
		botonGuardarSolo.setBorderPainted(false);
		botonGuardarSolo.setContentAreaFilled(false);
		botonGuardarSolo.setHorizontalAlignment(JLabel.CENTER);
		botonGuardarSolo.setVerticalAlignment(JLabel.CENTER);
		juegoSoloPanel.add(botonGuardarSolo);
		
		//Botón atrás
		botonAtrasSolo = new JButton("Atrás");
		botonAtrasSolo.setBounds(10,585,70,40);
		botonAtrasSolo.setBackground(new Color(241, 28, 48));
		botonAtrasSolo.setFocusPainted(false);
		juegoSoloPanel.add(botonAtrasSolo);
		
		//Nombre
		JLabel nombreJugadorSolo = new JLabel(campoNombre.getText().toUpperCase());
		nombreJugadorSolo.setForeground(Color.WHITE);
        nombreJugadorSolo.setFont(franchiseFont(46));
        nombreJugadorSolo.setBounds(780,35,250,32);
        juegoSoloPanel.add(nombreJugadorSolo);
		
		//Fondo
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, getWidth(), getHeight());
		fondo.setIcon(new ImageIcon("./images/Instrucciones.png"));
		juegoSoloPanel.add(fondo);
		
		prepareAccionesSolo();
	}
	
	/*
	 * Método que prepara el panel correspondiente a las configuraciones en modo 1vs1.
	 */
	private void prepareElementosConfigMulti() {
		configMultiPanel = new JPanel();
		contentpane.add(configMultiPanel, "ConfigMulti");
		configMultiPanel.setLayout(null);
		
		//Campo de texto
		campoNombreMulti = new JTextField("Player1");
        campoNombreMulti.setHorizontalAlignment(JTextField.CENTER);
        campoNombreMulti.setBounds(154,332,200,28);
        campoNombreMulti.setForeground(new Color(0x00FF00));
        campoNombreMulti.setBackground(Color.BLACK);
        configMultiPanel.add(campoNombreMulti);
        
        //Campo de texto
  		campoNombreMulti1 = new JTextField("Player2");
		campoNombreMulti1.setHorizontalAlignment(JTextField.CENTER);
		campoNombreMulti1.setBounds(846,332,200,28);
		campoNombreMulti1.setForeground(new Color(0x00FF00));
		campoNombreMulti1.setBackground(Color.BLACK);
		configMultiPanel.add(campoNombreMulti1);
		
        //Boton jugar
        botonJugarMulti = new JButton("Jugar");
        botonJugarMulti.setBounds((this.getWidth()/2)-85,320,170,40);
        configMultiPanel.add(botonJugarMulti);
        
        //Boton color
        botonColorMulti = new JButton("Color");
        botonColorMulti.setBounds(169,370,170,40);
        configMultiPanel.add(botonColorMulti);
        
        //Boton color1
        botonColorMulti1 = new JButton("Color");
        botonColorMulti1.setBounds(861,370,170,40);
        configMultiPanel.add(botonColorMulti1);
        
        //Boton buffos
        botonBuffosMulti = new JButton("Buffos");
        botonBuffosMulti.setBounds((this.getWidth()/2)-85,370,170,40);
        configMultiPanel.add(botonBuffosMulti);
        
        //Boton cargar
        botonCargarMulti = new JButton("Cargar");
        botonCargarMulti.setBounds((this.getWidth()/2)-85,420,170,40);
        configMultiPanel.add(botonCargarMulti);
        
        //Velocidades
  		ButtonGroup bg=new ButtonGroup();
  		lentoMulti=new JRadioButton("Lento");
  		lentoMulti.setBounds(520,470,70,30);
  		configMultiPanel.add(lentoMulti);
  		lentoMulti.setSelected(true);
  		bg.add(lentoMulti);
  		rapidoMulti=new JRadioButton("Rápido");
  		rapidoMulti.setBounds(600,470,80,30);
  		configMultiPanel.add(rapidoMulti);
		bg.add(rapidoMulti);
        
		//Boton atras
		botonAtrasConfigMulti = new JButton("Atrás");
		botonAtrasConfigMulti.setBounds(10,585,70,40);
		botonAtrasConfigMulti.setBackground(new Color(241, 28, 48));
		botonAtrasConfigMulti.setFocusPainted(false);
		configMultiPanel.add(botonAtrasConfigMulti);
		
		//Fondo
		ImageIcon imagenFondo = new ImageIcon("./images/FondoConfig.png");
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, getWidth(), getHeight());
		fondo.setIcon(imagenFondo);
		configMultiPanel.add(fondo);
	}
	
	/*
	 * Método que prepara el panel correspondiente al menú de selección de buffos en modo 1vs1.
	 */
	private void prepareElementosBuffosMulti() {
		buffosMultiPanel = new JPanel();
		contentpane.add(buffosMultiPanel, "BuffosMulti");
		buffosMultiPanel.setLayout(null);
		
		JCheckBox stopTime = new JCheckBox("BuffoStopTime");
		stopTime.setBounds(485, 281, 120, 25);
		stopTime.setSelected(true);
		JCheckBox stopDiece = new JCheckBox("BuffoStopDiece");
		stopDiece.setBounds(485, 425, 120, 25);
		stopDiece.setSelected(true);
		JCheckBox slow = new JCheckBox("BuffoSlow");
		slow.setBounds(800, 281, 85, 25);
		slow.setSelected(true);
		JCheckBox x2 = new JCheckBox("Buffo2x");
		x2.setBounds(800, 425, 85, 25);
		x2.setSelected(true);
		
		buffosMultiPanel.add(stopTime);
		buffosMultiPanel.add(stopDiece);
		buffosMultiPanel.add(slow);
		buffosMultiPanel.add(x2);
		
		checkBoxesMulti.add(stopTime);
		checkBoxesMulti.add(stopDiece);
		checkBoxesMulti.add(slow);
		checkBoxesMulti.add(x2);
		
		//Boton ok
        botonOkBuffosMulti = new JButton("Ok");
        botonOkBuffosMulti.setBounds((this.getWidth()/2)-85,525,170,40);
        buffosMultiPanel.add(botonOkBuffosMulti);
        
        //Fondo
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, getWidth(), getHeight());
		fondo.setIcon(new ImageIcon("./images/FondoBuffos.png"));
		buffosMultiPanel.add(fondo);
	}
	
	/*
	 * Método que prepara el panel correspondiente al juego en modo 1vs1.
	 */
	private void prepareElementosMulti() {
		juegoMulti1 = new POOBTriz(buffosMulti);
		juegoMulti2 = new POOBTriz(buffosMulti);
		int vel = rapidoMulti.isSelected() ? 380: 650;
		juegoMultiPanel = new PanelTetrizMulti(juegoMulti1, campoNombreMulti.getText(), colorTableroMulti, juegoMulti2, campoNombreMulti1.getText(), colorTableroMulti1, vel);
		
		juegoMultiPanel.setLayout(null);
		contentpane.add(juegoMultiPanel, "Multi");
		
		//Nombre1
		JLabel nombreJugadorMulti1 = new JLabel(campoNombreMulti.getText().toUpperCase());
		nombreJugadorMulti1.setForeground(Color.WHITE);
		nombreJugadorMulti1.setFont(franchiseFont(46));
		nombreJugadorMulti1.setBounds(5,35,170,32);
		nombreJugadorMulti1.setHorizontalAlignment(SwingConstants.RIGHT);
        juegoMultiPanel.add(nombreJugadorMulti1);
        
        //Nombre2
		JLabel nombreJugadorMulti2 = new JLabel(campoNombreMulti1.getText().toUpperCase());
		nombreJugadorMulti2.setForeground(Color.WHITE);
		nombreJugadorMulti2.setFont(franchiseFont(46));
		nombreJugadorMulti2.setBounds(1030,35,170,32);
	    juegoMultiPanel.add(nombreJugadorMulti2);
	    
	    //Botón guardar
	    botonGuardarMulti = new JButton();
	    botonGuardarMulti.setIcon(new ImageIcon("./images/BotonGuardar.png"));
	    botonGuardarMulti.setBounds(1115,570,50,50);
	    botonGuardarMulti.setFocusPainted(false);
	    botonGuardarMulti.setBorderPainted(false);
	    botonGuardarMulti.setContentAreaFilled(false);
	    botonGuardarMulti.setHorizontalAlignment(JLabel.CENTER);
	    botonGuardarMulti.setVerticalAlignment(JLabel.CENTER);
  		juegoMultiPanel.add(botonGuardarMulti);

	    //Botón pausa
	    botonPausaMulti = new JButton();
	    botonPausaMulti.setIcon(new ImageIcon("./images/BotonPausa.png"));
	    botonPausaMulti.setBounds(550, 545, 100, 100);
	    botonPausaMulti.setFocusPainted(false);
	    botonPausaMulti.setBorderPainted(false);
	    botonPausaMulti.setContentAreaFilled(false);
	    botonPausaMulti.setHorizontalAlignment(JLabel.CENTER);
	    botonPausaMulti.setVerticalAlignment(JLabel.CENTER);
	    juegoMultiPanel.add(botonPausaMulti);
	    
	  	//Boton atras
  		botonAtrasMulti = new JButton("Atrás");
  		botonAtrasMulti.setBounds(10,585,70,40);
  		botonAtrasMulti.setBackground(new Color(241, 28, 48));
  		botonAtrasMulti.setFocusPainted(false);
  		juegoMultiPanel.add(botonAtrasMulti);
  		
  		prepareAccionesMulti();
	}
	
	/*
	 * Método que prepara el panel correspondiente al menú mejores puntajes.
	 */
	private void prepareElementosPuntajes() {
		puntajesPanel = new JPanel();
		contentpane.add(puntajesPanel, "Puntajes");
		puntajesPanel.setLayout(null);
		
		String a = "<html><body>";
		FileReader fichero = null;
    	BufferedReader br = null;
    	String linea;
    	int n_linea = 1;
		try {
			fichero = new FileReader(new File("puntajes.txt"));
			br = new BufferedReader(fichero);
			while ((linea=br.readLine()) != null && n_linea < 6) {
				a += n_linea+". "+linea+"  Puntos<br>";
				n_linea++;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		a += "</body></html>";
		JLabel puntajes = new JLabel(a);
		puntajes.setForeground(Color.WHITE);
		puntajes.setBounds(485, 225, 210, 120);
		puntajes.setFont(new Font("Calibri", Font.PLAIN, 18));
		puntajes.setOpaque(true);
		puntajes.setBackground(new Color(23, 23, 23));
		puntajesPanel.add(puntajes);
		
		//Boton atras
		botonAtrasPuntajes = new JButton("Atrás");
		botonAtrasPuntajes.setBounds(10,585,70,40);
		botonAtrasPuntajes.setBackground(new Color(241, 28, 48));
		botonAtrasPuntajes.setFocusPainted(false);
		puntajesPanel.add(botonAtrasPuntajes);
		
		//Fondo
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, getWidth(), getHeight());
		fondo.setIcon(new ImageIcon("./images/FondoPuntajes.png"));
		puntajesPanel.add(fondo);
		prepareAccionesPuntajes();
	}
	
	/*
	 * Método que prepara el panel correspondiente al las configuraciones en modo 1vsMaquina.
	 */
	private void prepareElementosConfigMaquina() {
		configMaquinaPanel = new JPanel();
		contentpane.add(configMaquinaPanel, "ConfigMaquina");
		configMaquinaPanel.setLayout(null);
		
		//Campo de texto
		campoNombreMaquina = new JTextField("Player");
        campoNombreMaquina.setHorizontalAlignment(JTextField.CENTER);
        campoNombreMaquina.setBounds((getWidth()/2)-100,260,200,28);
        campoNombreMaquina.setForeground(new Color(0x00FF00));
        campoNombreMaquina.setBackground(Color.BLACK);
        configMaquinaPanel.add(campoNombreMaquina);
		
        //Boton jugar
        botonJugarMaquina = new JButton("Jugar");
        botonJugarMaquina.setBounds((this.getWidth()/2)-85,320,170,40);
        configMaquinaPanel.add(botonJugarMaquina);
        
        //Boton color
        botonColorMaquina = new JButton("Color");
        botonColorMaquina.setBounds((this.getWidth()/2)-85,370,170,40);
        configMaquinaPanel.add(botonColorMaquina);
        
        //Boton buffos
        botonBuffosMaquina = new JButton("Buffos");
        botonBuffosMaquina.setBounds((this.getWidth()/2)-85,420,170,40);
        configMaquinaPanel.add(botonBuffosMaquina);
        
        //Boton cargar
//        botonCargarSolo = new JButton("Cargar");
//        botonCargarSolo.setBounds((this.getWidth()/2)-85,470,170,40);
//        configSoloPanel.add(botonCargarSolo);
        
		//Boton atras
		botonAtrasConfigMaquina = new JButton("Atrás");
		botonAtrasConfigMaquina.setBounds(10,585,70,40);
		botonAtrasConfigMaquina.setBackground(new Color(241, 28, 48));
		botonAtrasConfigMaquina.setFocusPainted(false);
		configMaquinaPanel.add(botonAtrasConfigMaquina);
		
		//Fondo
		ImageIcon imagenFondo = new ImageIcon("./images/FondoConfig.png");
		JLabel fondo = new JLabel();
		fondo.setBounds(0, 0, getWidth(), getHeight());
		fondo.setIcon(imagenFondo);
		configMaquinaPanel.add(fondo);
	}
	
	/*
	 * Método que prepara el panel correspondiente al juego 1vsMaquina.
	 */
	private void prepareElementosMaquina() {
		juegoMulti1 = new POOBTriz(buffosMulti);
		juegoMulti2 = new POOBTriz(buffosMaquina);
		juegoMaquinaPanel = new PanelTetrizMaquina(juegoMulti1, campoNombreMaquina.getText(), colorTableroMaquina, juegoMulti2, "Maquina", new Color(23,23,23));
		
		juegoMaquinaPanel.setLayout(null);
		contentpane.add(juegoMaquinaPanel, "Maquina");
		
		//Nombre1
		JLabel nombreJugadorMaquina1 = new JLabel(campoNombreMaquina.getText().toUpperCase());
		nombreJugadorMaquina1.setForeground(Color.WHITE);
		nombreJugadorMaquina1.setFont(franchiseFont(46));
		nombreJugadorMaquina1.setBounds(5,35,170,32);
		nombreJugadorMaquina1.setHorizontalAlignment(SwingConstants.RIGHT);
        juegoMaquinaPanel.add(nombreJugadorMaquina1);
        
        //Nombre2
		JLabel nombreJugadorMaquina2 = new JLabel("MAQUINA");
		nombreJugadorMaquina2.setForeground(Color.WHITE);
		nombreJugadorMaquina2.setFont(franchiseFont(46));
		nombreJugadorMaquina2.setBounds(1030,35,170,32);
	    juegoMaquinaPanel.add(nombreJugadorMaquina2);
	    
	    //Botón pausa
	    botonPausaMaquina = new JButton();
	    botonPausaMaquina.setIcon(new ImageIcon("./images/BotonPausa.png"));
	    botonPausaMaquina.setBounds(550, 545, 100, 100);
	    botonPausaMaquina.setFocusPainted(false);
	    botonPausaMaquina.setBorderPainted(false);
	    botonPausaMaquina.setContentAreaFilled(false);
	    botonPausaMaquina.setHorizontalAlignment(JLabel.CENTER);
	    botonPausaMaquina.setVerticalAlignment(JLabel.CENTER);
	    juegoMaquinaPanel.add(botonPausaMaquina);
	    
	  	//Boton atras
  		botonAtrasMaquina = new JButton("Atrás");
  		botonAtrasMaquina.setBounds(10,585,70,40);
  		botonAtrasMaquina.setBackground(new Color(241, 28, 48));
  		botonAtrasMaquina.setFocusPainted(false);
  		juegoMaquinaPanel.add(botonAtrasMaquina);
  		
  		prepareAccionesMaquina();
	}
	
	/*
	 * Método que prepara las acciones de todos lo páneles.
	 */
	private void prepareAcciones() {
		/*Salir*/
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
				//salga();
			}
		});
		
		prepareAccionesMenu();
		prepareAccionesModos();
		prepareAccionesConfigSolo();
		prepareAccionesBuffos();
		prepareAccionesConfigMulti();
		prepareAccionesBuffosMulti();
		prepareAccionesConfigMaquina();
		
	}
	
	/*
	 * Método que prepara las acciones de los elemenos en el panel menú.
	 */
	private void prepareAccionesMenu() {
		botonJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				modos();
			}
		});
		
		botonPuntajes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				prepareElementosPuntajes();
				puntajes();
			}
		});
		
		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				salga();
			}
		});
	}
	
	/*
	 * Método que prepara las acciones de los elemenos en el panel de selección de modos.
	 */
	private void prepareAccionesModos() {
		botonSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				configSolo();
			}
		});
		
		botonMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				configMulti();
			}
		});
		
		botonMaquina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				configMaquina();
			}
		});
		
		botonAtrasModos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				menu();
			}
		});
		
	}
	
	/*
	 * Método que prepara las acciones de los elemenos en el panel de configuración solo.
	 */
	private void prepareAccionesConfigSolo() {
		botonAtrasConfigSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				modos();
			}
		});
		
		botonJugarSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				solo();
			}
		});
		
		botonColorSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JColorChooser colorChooser = new JColorChooser();
				colorTableroSolo = colorChooser.showDialog(null, "Seleccionar un Color", new Color(23, 23, 23));
				
			}
		});
		
		botonBuffosSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				buffos();
			}
		});
		
		botonCargarSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				abrir();
			}
		});
	}
	
	/*
	 * Método que prepara las acciones de los elemenos en el panel de selección de buffos en solitario.
	 */
	private void prepareAccionesBuffos() {
		botonOkBuffos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ArrayList<String> a = new ArrayList<String>();
				for (JCheckBox caja : checkBoxesSolo) {
					if (caja.isSelected()) {
						a.add(caja.getText());
					}
				}
				buffosSolo = a;
				configSolo();
			}
		});
	}
	
	/*
	 * Método que prepara las acciones de los elemenos en el panel de juego en solitario.
	 */
	private void prepareAccionesSolo() {
		botonAtrasSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				juegoSoloPanel.gameover();
				juegoSoloPanel.removeAll();
				modos();
			}
		});
		
		botonPausaSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				juegoSoloPanel.pause();
				requestFocus();
			}
		});
		
		botonGuardarSolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				requestFocus();
				if (juego.getState() == POOBTriz.IN_GAME) {juegoSoloPanel.pause();}
				JFileChooser salvarChooser = new JFileChooser();
				salvarChooser.addChoosableFileFilter(datFilter);
				salvarChooser.setFileFilter(datFilter);
				int seleccion = salvarChooser.showSaveDialog(botonGuardarSolo);
				if (seleccion == 0) {
					File file = salvarChooser.getSelectedFile();
					File aux;
					if (!file.toString().contains(".dat")) {
						aux = new File(file.toString()+".dat");
					} else {
						aux = file;
					}
					juegoSoloPanel.guardar(aux);
				}
				juegoSoloPanel.pause();
			}
		});
		
		MouseListener mouseListenerMenu = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getSource().equals(botonPausaSolo)) {
					botonPausaSolo.setIcon(new ImageIcon("./images/BotonPausa.png"));
				} else if (e.getSource().equals(botonGuardarSolo)) {
					botonGuardarSolo.setIcon(new ImageIcon("./images/BotonGuardar.png"));
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getSource().equals(botonPausaSolo)) {
					botonPausaSolo.setIcon(new ImageIcon("./images/BotonPausaG.png"));
				} else if (e.getSource().equals(botonGuardarSolo)) {
					botonGuardarSolo.setIcon(new ImageIcon("./images/BotonGuardarG.png"));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (e.getSource().equals(botonPausaSolo)) {
					botonPausaSolo.setIcon(new ImageIcon("./images/BotonPausaG.png"));
				} else if (e.getSource().equals(botonGuardarSolo)) {
					botonGuardarSolo.setIcon(new ImageIcon("./images/BotonGuardarG.png"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (e.getSource().equals(botonPausaSolo)) {
					botonPausaSolo.setIcon(new ImageIcon("./images/BotonPausa.png"));
				} else if (e.getSource().equals(botonGuardarSolo)) {
					botonGuardarSolo.setIcon(new ImageIcon("./images/BotonGuardar.png"));
				}
			}
		};
		botonPausaSolo.addMouseListener(mouseListenerMenu);
		botonGuardarSolo.addMouseListener(mouseListenerMenu);
	}
	
	/*
	 * Método que prepara las acciones de los elemenos en el panel de configuración en modo 1vs1.
	 */
	private void prepareAccionesConfigMulti() {
		botonAtrasConfigMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				modos();
			}
		});
		
		botonJugarMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				multi();
			}
		});
		
		botonBuffosMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				buffosMulti();
			}
		});
		
		botonColorMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JColorChooser colorChooser = new JColorChooser();
				colorTableroMulti = colorChooser.showDialog(null, "Seleccionar un Color", new Color(23, 23, 23));
			}
		});
		
		botonCargarMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				abrirMulti();
			}
		});
		
		botonColorMulti1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JColorChooser colorChooser = new JColorChooser();
				colorTableroMulti1 = colorChooser.showDialog(null, "Seleccionar un Color", new Color(23, 23, 23));
			}
		});
	}
	
	/*
	 * Método que prepara las acciones de los elemenos en el panel de seleccion de buffos en modo 1vs1.
	 */
	private void prepareAccionesBuffosMulti() {
		botonOkBuffosMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				ArrayList<String> a = new ArrayList<String>();
				for (JCheckBox caja : checkBoxesMulti) {
					if (caja.isSelected()) {
						a.add(caja.getText());
					}
				}
				buffosMulti = a;
				configMulti();
			}
		});
	}
	
	/*
	 * Método que prepara las acciones de los elemenos en el panel del juego en modo 1vs1.
	 */ 
	private void prepareAccionesMulti() {
		botonAtrasMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				juegoMulti1.setState(POOBTriz.PAUSE);
				juegoMulti2.setState(POOBTriz.PAUSE);
				juegoMultiPanel.removeAll();
				modos();
			}
		});
		
		botonPausaMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				juegoMultiPanel.pause();
				requestFocus();
			}
		});
		
		botonGuardarMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				requestFocus();
				juegoMultiPanel.guardar();
				
			}
		});
		
		MouseListener mouseListenerMenu = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getSource().equals(botonPausaMulti)) {
					botonPausaMulti.setIcon(new ImageIcon("./images/BotonPausa.png"));
				} else if (e.getSource().equals(botonGuardarMulti)) {
					botonGuardarMulti.setIcon(new ImageIcon("./images/BotonGuardar.png"));
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getSource().equals(botonPausaMulti)) {
					botonPausaMulti.setIcon(new ImageIcon("./images/BotonPausaG.png"));
				} else if (e.getSource().equals(botonGuardarMulti)) {
					botonGuardarMulti.setIcon(new ImageIcon("./images/BotonGuardarG.png"));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (e.getSource().equals(botonPausaMulti)) {
					botonPausaMulti.setIcon(new ImageIcon("./images/BotonPausaG.png"));
				} else if (e.getSource().equals(botonGuardarMulti)) {
					botonGuardarMulti.setIcon(new ImageIcon("./images/BotonGuardarG.png"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (e.getSource().equals(botonPausaMulti)) {
					botonPausaMulti.setIcon(new ImageIcon("./images/BotonPausa.png"));
				} else if (e.getSource().equals(botonGuardarMulti)) {
					botonGuardarMulti.setIcon(new ImageIcon("./images/BotonGuardar.png"));
				}
			}
		};
		botonPausaMulti.addMouseListener(mouseListenerMenu);
		botonGuardarMulti.addMouseListener(mouseListenerMenu);
		
	}
	
	/*
	 * Método que prepara las acciones de los elemenos en el panel menu de mejores puntajes.
	 */
	private void prepareAccionesPuntajes() {
		botonAtrasPuntajes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				menu();
			}
		});
	}
	
	/*
	 * Método que prepara las acciones de los elemenos en el panel de configuración en modo 1vsMaquina.
	 */
	private void prepareAccionesConfigMaquina() {
		botonAtrasConfigMaquina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				modos();
			}
		});
		
		botonJugarMaquina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				maquina();
			}
		});
		
		botonColorMaquina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JColorChooser colorChooser = new JColorChooser();
				colorTableroMaquina = colorChooser.showDialog(null, "Seleccionar un Color", new Color(23, 23, 23));
				
			}
		});
		
		botonBuffosMaquina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//buffosMaquina();
			}
		});
		
//		botonCargarSolo.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ev) {
//				abrir();
//			}
//		});
	}
	
	/*
	 * Método que prepara las acciones de los elemenos en el panel de jueo en modo 1vsMaquina.
	 */
	private void prepareAccionesMaquina() {
		botonAtrasMaquina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				juegoMulti1.setState(POOBTriz.PAUSE);
				juegoMulti2.setState(POOBTriz.PAUSE);
				juegoMaquinaPanel.removeAll();
				modos();
			}
		});
		
		botonPausaMaquina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				juegoMaquinaPanel.pause();
				requestFocus();
			}
		});
	}
	
	/*
	 * Muestra el panel Menu
	 */
	private void menu() {
		cl.show(contentpane, "Menu");
	}
	
	/*
	 * Muestra el panel Puntajes
	 */
	private void puntajes() {
		cl.show(contentpane, "Puntajes");
	}
	
	/*
	 * Muestra el panel Modos
	 */
	private void modos() {
		cl.show(contentpane, "Modos");
	}
	
	/*
	 * Muestra el panel Configuración en solitario
	 */
	private void configSolo() {
		cl.show(contentpane, "ConfigSolo");
	}
	
	/*
	 * Muestra el panel Configuración en 1vs1
	 */
	private void configMulti() {
		cl.show(contentpane, "ConfigMulti");
	}
	
	/*
	 * Muestra el panel Configuración en 1vsMaquina
	 */
	private void configMaquina() {
		cl.show(contentpane, "ConfigMaquina");
	}
	
	/*
	 * Muestra el panel Buffos en modo solitario
	 */
	private void buffos() {
		cl.show(contentpane, "Buffos");
	}
	
	/*
	 * Muestra el panel Buffos en modo 1vs1
	 */
	private void buffosMulti() {
		cl.show(contentpane, "BuffosMulti");
	}
	
	/*
	 * Muestra el panel de juego en solitario
	 */
	private void solo() {
		prepareElementosSolo();
		this.addKeyListener(juegoSoloPanel);
		setFocusable(true);
		contentpane.add(juegoSoloPanel, "Solo");
		juegoSoloPanel.beginGame();
		cl.show(contentpane, "Solo");
	}
	
	/*
	 * Muestra el panel de juego en modo 1vs1
	 */
	private void multi() {
		prepareElementosMulti();
		this.addKeyListener(juegoMultiPanel);
		setFocusable(true);
		contentpane.add(juegoMultiPanel, "Multi");
		juegoMultiPanel.beginGame();
		cl.show(contentpane, "Multi");
	}
	
	/*
	 * Muestra el panel de juego en modo 1vsMaquina
	 */
	private void maquina() {
		prepareElementosMaquina();
		this.addKeyListener(juegoMaquinaPanel);
		setFocusable(true);
		contentpane.add(juegoMaquinaPanel, "Maquina");
		juegoMaquinaPanel.beginGame();
		cl.show(contentpane, "Maquina");
	}
	
	/*
	 * Abre un juego en modo solitario
	 */
	private void abrir() {
		JFileChooser abrirChooser = new JFileChooser();
		abrirChooser.addChoosableFileFilter(datFilter);
		abrirChooser.setFileFilter(datFilter);
		int seleccion = abrirChooser.showOpenDialog(botonCargarSolo);
		if (seleccion == 0) {
			File file = abrirChooser.getSelectedFile();
			prepareElementosSolo();
			this.addKeyListener(juegoSoloPanel);
			requestFocus();
			contentpane.add(juegoSoloPanel, "Solo");
			juegoSoloPanel.abrir(file);
			cl.show(contentpane, "Solo");
		}
		
	}
	
	/*
	 * Abre un juego en modo 1vs1
	 */
	private void abrirMulti() {
		prepareElementosMulti();
		this.addKeyListener(juegoMultiPanel);
		setFocusable(true);
		contentpane.add(juegoMultiPanel, "Multi");
		juegoMultiPanel.abrir();
		cl.show(contentpane, "Multi");
	}
	
	/*
     * Metodo que crea una fuente personalizada
     */
    private Font franchiseFont(int size) {
        try {
            InputStream is = new FileInputStream("./font/franchise.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(Font.PLAIN, size);
        }
        catch (Exception a){
            return new Font("Arial", Font.PLAIN, 14);
        }
    }
	
    /*
     * Cierra el frame
     */
	private void salga() {
		int ventanaYesNot = JOptionPane.showConfirmDialog(null, "¿Quieres salir del juego?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(ventanaYesNot == 0) {
			setVisible(false);
			System.exit(0);
		}
	}
	
	/*
	 * main
	 */
	public static void main(String[] args) {
		POOBTrizGUI gui = new POOBTrizGUI();
		gui.setVisible(true);
	}

	
}
