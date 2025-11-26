package escoba.modelo.excepcion;

/**
 * Excepción comprobable que indica que una carta ya existía
 * previamente (por ejemplo, en una baza o en la mano de un jugador).
 */
public class CartaExistenteException extends Exception {

    /** Versión de serialización. */
    private static final long serialVersionUID = 1L;

    /** Constructor por defecto. */
    public CartaExistenteException() {
        super();
    }

    /** Constructor con mensaje. */
    public CartaExistenteException(final String message) {
        super(message);
    }

    /** Constructor con mensaje y causa. */
    public CartaExistenteException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /** Constructor con causa. */
    public CartaExistenteException(final Throwable cause) {
        super(cause);
    }
}
