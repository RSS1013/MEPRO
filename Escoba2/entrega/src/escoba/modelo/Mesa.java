package escoba.modelo;

import java.util.ArrayList;
import java.util.List;

import escoba.modelo.comparador.Comparador;

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

	/** Cartas que hay en la mesa. */
	private final List<Carta> cartasEnMesa;

	/** Cantidad de cartas que hay en la mesa. */
	private int cantidad;

	/** Comparador de cartas. */
	private final Comparador<Carta> comparador;

	/**
	 * Constructor de la clase Mesa.
	 *
	 * @param comparadorCarta comparador de cartas
	 */
	public Mesa(final Comparador<Carta> comparadorCarta) {
		cartasEnMesa = new ArrayList<>();
		this.comparador = comparadorCarta;
	}

	/**
	 * Añade una carta a la mesa.
	 *
	 * @param carta carta
	 */
	public void ponerCarta(final Carta carta) {
		cartasEnMesa.add(carta);
		comparador.ordenar(cartasEnMesa);
		cantidad++;
	}

	/**
	 * Consulta las cartas que hay en la mesa.
	 *
	 * @return cartas que hay en la mesa
	 */
	public List<Carta> consultarCartasEnMesa() {
		final List<Carta> copia = new ArrayList<>();
		for (final Carta carta : cartasEnMesa) {
			copia.add(carta);
		}
		return copia;
	}

	/**
	 * Quita una carta de la mesa.
	 *
	 * @param carta carta
	 */
	public void quitarCarta(final Carta carta) {
		boolean encontrado = false;
		for (int i = 0; i < cantidad && !encontrado; i++) {
			if (cartasEnMesa.get(i).equals(carta)) {
				cartasEnMesa.remove(i);
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
	public boolean esCombinacionValida(final Carta cartaJugador, final List<Carta> combinacion) {
		int suma = 0;
		if (cartaJugador != null && combinacion != null) {
			suma = cartaJugador.puntuacion();
			for (final Carta carta : combinacion) {
				boolean encontrada = false;
				for (int i = 0; i < combinacion.size() && !encontrada; i++) {
					if (combinacion.get(i).clave() == carta.clave()) {
						suma += carta.puntuacion();
						encontrada = true;
					}
				}
			}
		}
		return suma == SUMA_PUNTOS_A_ALCANZAR;
	}
}
