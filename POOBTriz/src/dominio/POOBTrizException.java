package dominio;

/**
 * Clase de excepciones para POOBTriz.
 * @author Andrés Ariza y Juan Pablo Sánchez
 *
 */
public class POOBTrizException extends Exception {
	
	public static final String CLASE_NO_ENCONTRADA = "Clase no encontrada";
	
	public POOBTrizException(String message) {
		super(message);
	}
}
