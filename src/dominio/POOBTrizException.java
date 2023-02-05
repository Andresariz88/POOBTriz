package dominio;

/**
 * Clase de excepciones para POOBTriz.
 * @author Andr�s Ariza y Juan Pablo S�nchez
 *
 */
public class POOBTrizException extends Exception {
	
	public static final String CLASE_NO_ENCONTRADA = "Clase no encontrada";
	
	public POOBTrizException(String message) {
		super(message);
	}
}
