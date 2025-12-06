package escoba.modelo;

/**
 * Palos de una bajara española.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
public enum Palo {
	/** Oros. */
	OROS(1),
	/** Copas. */
	COPAS(2),
	/** Espadas. */
	ESPADAS(3),
	/** Bastos. */
	BASTOS(4);

	/** Orden del palo. */
	private final int orden;

	/**
	 * Constructor.
	 *
	 * @param orden orden interno para comparar
	 */
	Palo(final int orden) { // constructor en tipo enumerado es privado implícitamente
		this.orden = orden;
	}

	/**
	 * Consulta el orden del palo.
	 *
	 * @return orden
	 */
	public int consultarOrden() {
		return orden;
	}


}
