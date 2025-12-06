package escoba.modelo.excepcion;

/**
 * Excepción comprobable que indica que una carta no se ha encontrado
 * en la colección en la que debería estar presente, como la mano de
 * un jugador o una baza.
 * <p>
 * Se utiliza para señalar errores lógicos al intentar acceder o
 * manipular cartas inexistentes en un contexto determinado.
 * </p>
 *
 * @author Ricardo Sevilla Soba
 * @version 2.0
 * @since 2025-11-24
 */
public class CartaNoEncontradaException extends Exception {

    /** Versión de serialización. */
    private static final long serialVersionUID = 1L;

    /**
     * Crea la excepción sin mensaje asociado.
     */
    public CartaNoEncontradaException() {
        super();
    }

    /**
     * Crea la excepción con un mensaje descriptivo.
     *
     * @param message mensaje de detalle
     */
    public CartaNoEncontradaException(final String message) {
        super(message);
    }

    /**
     * Crea la excepción con un mensaje y una causa asociada.
     *
     * @param message mensaje descriptivo
     * @param cause   causa de la excepción
     */
    public CartaNoEncontradaException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Crea la excepción indicando únicamente la causa.
     *
     * @param cause causa de la excepción
     */
    public CartaNoEncontradaException(final Throwable cause) {
        super(cause);
    }
}
