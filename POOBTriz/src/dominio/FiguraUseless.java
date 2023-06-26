package dominio;

import java.awt.*;
import java.io.Serializable;

/**
 * Clase de la figura Useless (metálica).
 * @author Andrés Ariza y Juan Pablo Sánchez
 * @version 2021-2
 */
public class FiguraUseless extends Figura implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected static final Color COLOR_GRIS = new Color(138, 149, 151);
	
	/**
	 * Constructor de una figura useless.
	 * @param juego POOBTriz al que se agregará la figura.
	 */
	public FiguraUseless(POOBTriz juego) {
		super(juego);
		this.color = COLOR_GRIS;
		juego.addShape(this);
	}

	/**
	 * Constructor de una figura useless.
	 * @param juego POOBTriz al que se agregará la figura.
	 * @param figura entero que representa la figura.
	 */
	public FiguraUseless(POOBTriz juego, int figura) {
		super(juego, figura);
		this.color = COLOR_GRIS;
		juego.addShape(this);
	}

}
