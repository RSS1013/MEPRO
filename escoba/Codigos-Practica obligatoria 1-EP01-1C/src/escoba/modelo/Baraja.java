package escoba.modelo;

/**
 * Baraja.
 * 
 * Se asume que es una bajara española de 40 cartas (sin ochos ni nueves).
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.1
 * @see escoba.modelo.Carta
 * @see escoba.modelo.Palo
 */
public class Baraja {

	/** Número de cartas. */
	public static final int NUMERO_CARTAS = 40;

	/** Cartas que componen una baraja. */
	private Carta[] cartas;

	/**
	 * Contador interno para saber en qué posición está la primera carta disponible.
	 */
	private int cima;

	/**
	 * Constructor. Inicializa la baraja con las 40 cartas.
	 */
	public Baraja() {
		cartas = new Carta[NUMERO_CARTAS];
		int clave = 1; // empezamos identificando en uno

		// para cada palo (nos aprovechamos del orden de declaración en el tipo
		// enumerado)...
		for (Palo palo : Palo.values()) {
			// 10 cartas por palo...
			for (int contador = 0; contador < NUMERO_CARTAS / Palo.values().length; contador++) {
				cartas[clave - 1] = new Carta(clave, palo, contador + 1); // se crea una nueva carta y se "coloca"
				clave++;
			}
		}
	}

	/**
	 * Extrae la carta en la primera posición.
	 * 
	 * @return carta
	 */
	public Carta extraerCarta() {
		Carta carta;
		if (cima == cartas.length) {
			carta = null; // ante ausencia de cartas disponibles en la baraja
		} else {
			// extraemos y ponemos a null la posicion de la carta extraída
			carta = cartas[cima];
			cartas[cima] = null;
			cima++; // avanzamos la cima
		}
		return carta;
	}

	/**
	 * Baraja aleatoriamente las cartas restantes.
	 */
	public void barajar() {
		// iteramos partiendo desde la cima (en posiciones previas no hay cartas)
		for (int contador = cima; contador < consultarNumeroCartas() + cima; contador++) {
			// calculamos posición aleatoria con el número de cartas restantes
			// y sumamos la cima
			int nuevaPosicion = (int) (Math.random() * consultarNumeroCartas()) + cima;
			intercambiarCartas(contador, nuevaPosicion);
		}
	}

	/**
	 * Intercambia dos cartas entre las posiciones dadas.
	 * 
	 * Sirve como ejemplo de método privado para dividir la funcionalidad más
	 * compleja en métodos más pequeños (este comentario solo tiene fines docentes).
	 * 
	 * @param posicionInicial posición inicial
	 * @param posicionFinal   posición final
	 */
	private void intercambiarCartas(int posicionInicial, int posicionFinal) {
		// intercambiamos las dos cartas entre ambas posiciones
		Carta temporal = cartas[posicionFinal];
		cartas[posicionFinal] = cartas[posicionInicial];
		cartas[posicionInicial] = temporal;
	}

	/**
	 * Consulta el número de cartas disponibles.
	 * 
	 * @return número de cartas disponibles
	 */
	public int consultarNumeroCartas() {
		return cartas.length - cima; // número de cartas inicial menos las cartas retiradas
	}

	/**
	 * Recupera el contenido de la baraja concatenando los textos de cada una de la
	 * cartas disponibles.
	 *
	 * @return texto con las cartas disponibles
	 */
	public String aTexto() {
		StringBuilder texto = new StringBuilder();
		// saltamos las cartas ya extraídas accediendo a la cima directamente
		// en el bucle
		for (int contador = cima; contador < cartas.length; contador++) {
			texto.append(cartas[contador].aTexto()).append('\n'); // reutilizamos el método ya implementado en Carta
		}
		return texto.toString();
	}

	/**
	 * Comprueba si está vacía la baraja.
	 * 
	 * @return true si está vacía, false en caso contrario
	 * @since 2.0
	 */
	public boolean estaVacia() {
		return consultarNumeroCartas() == 0;
	}
}
