package escoba.modelo;

/**
 * Mesa sobra la que se juega a la escoba. Contiene las cartas que hay en la
 * mesa y los métodos para manipularlas.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
public class Mesa {
	
	/** Puntos que hay que sumar para hacer una escoba. */
	public static final int SUMA_PUNTOS_A_ALCANZAR = 15;

	/** Tamaño máximo de cartas en la mesa. */
	private static final int TAMAÑO_MAXIMO = 40; // en teoría sería un 9 pero jugando a no retirar todas explota el
													// programa

	/** Cartas que hay en la mesa. */
	private Carta[] cartasEnMesa;

	/** Cantidad de cartas que hay en la mesa. */
	private int cantidad;

	/**
	 * Constructor de la clase Mesa.
	 */
	public Mesa() {
		cartasEnMesa = new Carta[TAMAÑO_MAXIMO];
	}

	/**
	 * Añade una carta a la mesa.
	 * 
	 * @param carta carta
	 */
	public void ponerCarta(Carta carta) {
		cartasEnMesa[cantidad] = carta;
		cantidad++;
	}

	/**
	 * Consulta las cartas que hay en la mesa.
	 * 
	 * @return cartas que hay en la mesa
	 */
	public Carta[] consultarCartasEnMesa() {
		Carta[] copia = new Carta[cantidad];
		for (int i = 0; i < cantidad; i++) {
			copia[i] = cartasEnMesa[i];
		}
		return copia;
	}

	/**
	 * Quita una carta de la mesa.
	 * 
	 * @param carta carta
	 */
	public void quitarCarta(Carta carta) {
		boolean encontrado = false;
		for (int i = 0; i < cantidad && !encontrado; i++) {
			if (cartasEnMesa[i].equals(carta)) {
				for (int j = i; j < cantidad - 1; j++) {
					cartasEnMesa[j] = cartasEnMesa[j + 1];
				}
				cantidad--;
				encontrado = true;
			}
		}
	}

	/**
	 * Consulta si la mesa está vacía.
	 * 
	 * @return true si la mesa está vacía, false en caso contrario
	 */
	public boolean estaVacia() {
		return cantidad == 0;
	}

	/**
	 * Consulta si una combinación de algunas de las cartas elegidas en la mesaes
	 * válida para sumar 15 puntos con la carta del jugador.
	 * 
	 * @param cartaJugador carta del jugador
	 * @param combinacion  combinación de algunas cartas de la mesa
	 * @return true si la combinación es válida, false en caso contrario
	 */
	public boolean esCombinacionValida(Carta cartaJugador, Carta[] combinacion) {
		int suma = 0;
		if (cartaJugador != null && combinacion != null) {
			suma = cartaJugador.puntuacion();
			for (Carta carta : combinacion) {
				boolean encontrada = false;
				for (int i = 0; i < combinacion.length && !encontrada; i++) {
					if (combinacion[i].clave() == carta.clave()) {
						suma += carta.puntuacion();
						encontrada = true;
					}
				}
			}
		}
		return suma == SUMA_PUNTOS_A_ALCANZAR;
	}
}
