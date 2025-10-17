package escoba.modelo;

/**
 * Carta de una bajara española.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 * @param clave      número identificativo único
 * @param palo       palo
 * @param puntuacion puntuación
 */
public record Carta(int clave, Palo palo, int puntuacion) {

	/**
	 * Recupera el estado actual de la carta en formato texto.
	 * 
	 * @return texto descriptivo
	 */
	public String aTexto() {
		return traducir(puntuacion) + " de " + palo.toString().toLowerCase() + " (" + puntuacion + ")";
	}

	/**
	 * Traduce la puntuación al texto correspondiente.
	 * 
	 * @param puntuacion puntuación
	 * @return texto asociado
	 */
	private static String traducir(int puntuacion) {
		String[] cartas = { "<No definido>", "As", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Sota", "Caballo",
				"Rey" };
		return (puntuacion >= 1 && puntuacion <= 10) ? cartas[puntuacion] : cartas[0];
	}
}
