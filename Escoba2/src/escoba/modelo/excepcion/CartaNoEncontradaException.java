package escoba.modelo.excepcion;

/**
 * Excepción comprobable que indica que una carta no se encuentra
 * donde debería (por ejemplo, al intentar jugar una carta que
 * no está en la mano).
 */
public class CartaNoEncontradaException extends Exception {

    /** Versión de serialización. */
    private static final long serialVersionUID = 1L;

    /** Constructor por defecto. */
    public CartaNoEncontradaException() {
        super();
    }

    /** Constructor con mensaje. */
    public CartaNoEncontradaException(final String message) {
        super(message);
    }

    /** Constructor con mensaje y causa. */
    public CartaNoEncontradaException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /** Constructor con causa. */
    public CartaNoEncontradaException(final Throwable cause) {
        super(cause);
    }
}
