package escoba.modelo.excepcion;

/**
 * Excepción comprobable que indica que una carta ya existía previamente
 * en una colección, como la mano de un jugador o una baza.
 * <p>
 * Se utiliza para evitar duplicidades en estructuras donde una carta
 * no puede aparecer más de una vez.
 * </p>
 *
 * @author Ricardo Sevilla Soba
 * @version 2.0
 * @since 2025-11-24
 */
public class CartaExistenteException extends Exception {

    /** Versión de serialización. */
    private static final long serialVersionUID = 1L;

    /**
     * Crea la excepción sin mensaje asociado.
     */
    public CartaExistenteException() {
        super();
    }

    /**
     * Crea la excepción con un mensaje descriptivo.
     *
     * @param message mensaje de detalle
     */
    public CartaExistenteException(final String message) {
        super(message);
    }

    /**
     * Crea la excepción con un mensaje y una causa asociada.
     *
     * @param message mensaje descriptivo
     * @param cause   causa de la excepción
     */
    public CartaExistenteException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Crea la excepción indicando únicamente la causa.
     *
     * @param cause causa de la excepción
     */
    public CartaExistenteException(final Throwable cause) {
        super(cause);
    }
}
