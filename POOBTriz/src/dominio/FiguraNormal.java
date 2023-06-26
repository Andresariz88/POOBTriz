package dominio;

import java.awt.Color;
import java.io.Serializable;

/**
 * Clase de la figura Normal.
 * @author Andrés Ariza y Juan Pablo Sánchez
 * @version 2021-2
 */
public class FiguraNormal extends Figura implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Color[] colores = {new Color(214, 214, 214), new Color(255, 21, 255), new Color(254, 129, 0), new Color(0, 230, 0), new Color(229, 230, 1)};
	
	/**
	 * Constructor de una figura normal.
	 * @param juego POOBTriz al que se agregará la figura.
	 */
	public FiguraNormal(POOBTriz juego) {
		super(juego);
		juego.addShape(this);
	}

	/**
	 * Constructor de una figura normal.
	 * @param juego POOBTriz al que se agregará la figura.
	 * @param figura entero que representa la figura.
	 */
	public FiguraNormal(POOBTriz juego, int figura) {
		super(juego, figura);
		juego.addShape(this);
	}
	
}
