package escoba.vista;

import java.util.InputMismatchException;
import java.util.Scanner;

import escoba.modelo.Baraja;
import escoba.modelo.Carta;
import escoba.modelo.Jugador;
import escoba.modelo.Mesa;
import escoba.modelo.Partida;
import escoba.vista.util.CartaTextoColor;
import escoba.vista.util.ManejadorArray;

/**
 * Visualización en consola.
 * 
 * Se introducen datos, validan y muestran estados intermedios de una partida.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
public class VistaConsola {

	/** Número de sietes en la baraja. */
	private static final int TOTAL_SIETES_EN_BARAJA = 4;

	/** Numero de cartas por palo en la baraja. */
	private static final int NUMERO_CARTAS_POR_PALO = 10;

	/** Número de cartas que dejan al contrario por debajo del mínimo. */
	private static final int MUCHAS_MAS_CARTAS_QUE_EL_CONTRARIO = Baraja.NUMERO_CARTAS - 10;

	/** Teclado. */
	private Scanner scanner;

	/**
	 * Constructor.
	 * 
	 */
	public VistaConsola() {
		scanner = new Scanner(System.in);
	}

	/**
	 * Visualiza el estado de la mesa actual con los índices para la selección de
	 * cartas.
	 * 
	 * @param mesa    mesa
	 * @param jugador jugador
	 * @param baraja  baraja
	 * @param ronda   ronda
	 */
	public void mostrarEstado(Mesa mesa, Jugador jugador, Baraja baraja, int ronda) {
		System.out.println("=".repeat(30));
		System.out.println("Estado actual de la partida");
		System.out.println("=".repeat(30));

		ronda++;
		System.out.println("Ronda: " + ronda + " Cartas actualmente en la baraja: " + baraja.consultarNumeroCartas());
		System.out.println("Mesa:");
		mostrarCartasEnLaMesa(mesa);

		System.out.print("\nTurno de " + jugador.consultarNombre());
		mostrarEstadoJugadorEnPartida(jugador);
		mostrarCartasJugador(jugador);
	}

	/**
	 * Muestra el estado del jugador en la partida.
	 * 
	 * Se incluye por motivos de depuración para facilitar la visualización de
	 * estado y comprobación de corrección de la ejecución.
	 * 
	 * @param jugador jugador
	 */
	public void mostrarEstadoJugadorEnPartida(Jugador jugador) {
		System.out.print("- Escobas conseguidas: " + jugador.consultarEscobas());
		System.out.print(" - Bazas conseguidas: " + jugador.consultarBazas().length);
		System.out.println(" - Cartas conseguidas: " + jugador.contarCartas());
	}

	/**
	 * Muestra cartas actualmente en la mano del jugador.
	 * 
	 * @param jugador jugador
	 */
	private void mostrarCartasJugador(Jugador jugador) {

		// Mostrar cartas del jugador con visualización mejorada
		Carta[] cartas = jugador.consultarMano();
		if (cartas.length > 0) {
			System.out.println("\nCartas de " + jugador.consultarNombre());
			mostrarCartas(cartas);
		}
		System.out.println();
	}

	/**
	 * Muestra las cartas pasadas como parámetro con índices.
	 * 
	 * @param cartas cartas a mostrar
	 */
	private void mostrarCartas(Carta[] cartas) {
		// Mostrar índices encima de las cartas
		for (int i = 0; i < cartas.length; i++) {
			System.out.printf("    [%d]     ", i);
		}
		System.out.println();
		// Crear array de cartas visuales para impresión
		String[][] cartasVisuales = new String[cartas.length][];
		for (int i = 0; i < cartas.length; i++) {
			cartasVisuales[i] = CartaTextoColor.dibujarCarta(cartas[i]);
		}
		// Imprimir las cartas
		imprimirCartas(cartasVisuales);

	}

	/**
	 * Muestra las cartas que están sobre la mesa.
	 * 
	 * @param mesa mesa
	 */
	private void mostrarCartasEnLaMesa(Mesa mesa) {
		// Mostrar cartas en la mesa con visualización mejorada

		Carta[] cartas = mesa.consultarCartasEnMesa();
		if (cartas.length == 0) {
			System.out.println("(vacía)");
		} else {
			mostrarCartas(cartas);
		}
	}

	/**
	 * Pide la carta que quiere colocar sobre la mesa el jugador.
	 * 
	 * @param jugador jugador
	 * @return carta seleccionada para colocar sobre la mesa
	 */
	public Carta pedirCarta(Jugador jugador) {
		Carta[] cartasEnMano = jugador.consultarMano();
		Carta resultado = null;

		if (cartasEnMano.length > 0) {
			int opcion = -1;
			while (opcion < 0 || opcion >= cartasEnMano.length) {
				System.out.print("Elige la carta a jugar (0-" + (cartasEnMano.length - 1) + "): ");
				try {
					opcion = scanner.nextInt();
					if (opcion < 0 || opcion >= cartasEnMano.length) {
						System.out.println("❌ Opción inválida. Debe estar entre 0 y " + (cartasEnMano.length - 1));
					}
				} catch (NumberFormatException | InputMismatchException _) { // unnamed variable since JDK 22
					System.out.println("Por favor, introduce un número válido.");
					scanner.nextLine(); // Limpiar el buffer
				}
			}
			resultado = cartasEnMano[opcion];
		}
		return resultado;
	}

	/**
	 * Pide las cartas a retirar de la mesa para completar la baza junto con la
	 * carta previamente jugada.
	 * 
	 * @param mesa        mesa
	 * @param cartaJugada carta seleccionada por el jugador para completar la baza
	 * @return cartas que completan la baza junto con la carta jugada, si las cartas
	 *         no suman 15 estará vacío
	 */
	public Carta[] pedirCartasMesa(Mesa mesa, Carta cartaJugada) {
		Carta[] mesaCartas = mesa.consultarCartasEnMesa();
		Carta[] cartasARetirar;
		if (mesaCartas.length == 0) {
			System.out.println("La mesa está vacía. Tu carta se quedará en la mesa.");
			cartasARetirar = new Carta[0];
		} else {
			mostrarCartaRetiradaYCartasSobreLaMesa(cartaJugada, mesa);
			String entrada = leerCartasARetirar();
			// decidir qué cartas retirar
			cartasARetirar = retirarCartas(mesaCartas, entrada);
			if (cartasARetirar.length > 0) {
				mostrarVerificacionCartas(cartaJugada, cartasARetirar);
				if (mesa.esCombinacionValida(cartaJugada, cartasARetirar)) {
					System.out.println(" ✅ ¡Jugada válida!");
				} else {
					System.out.printf("🪲 ERROR: La suma no es %d. Tu carta se quedará en la mesa.%n",
							Mesa.SUMA_PUNTOS_A_ALCANZAR);
					cartasARetirar = new Carta[0]; // eliminamos las cartas a retirar al ser una suma incorrecta
				}
			} else {
				System.out.println("⚠️ No recoges ninguna carta. Tu carta se queda en la mesa.");
			}
		}
		return cartasARetirar;
	}

	/**
	 * Muestra la verificación de cartas elegidas de la mesa.
	 * 
	 * @param cartaJugada    carta jugada
	 * @param cartasARetirar cartas a retirar de la mesa
	 */
	private void mostrarVerificacionCartas(Carta cartaJugada, Carta[] cartasARetirar) {
		System.out.print("🤔 Verificando jugada: " + cartaJugada.aTexto());
		for (Carta carta : cartasARetirar) {
			System.out.print(" + " + carta.aTexto());
		}
	}

	/**
	 * Retira las cartas de la mesa a partir de las coordenadas dadas desde teclado.
	 * 
	 * @param mesaCartas cartas sobre la mesa
	 * @param entrada    texto de entrada
	 * @return cartas retiradas de la mesa
	 */
	private Carta[] retirarCartas(Carta[] mesaCartas, String entrada) {
		Carta[] cartasARetirar;
		if (entrada.isEmpty()) {
			cartasARetirar = new Carta[0];
		} else {
			String[] partes = entrada.split("\\s+"); // dividir cadena
			cartasARetirar = new Carta[partes.length];
			procesarPartes(mesaCartas, cartasARetirar, partes);
		}
		// compactamos el array dado que puede contener nulos al final
		cartasARetirar = ManejadorArray.compactar(cartasARetirar);

		return cartasARetirar;
	}

	/**
	 * Procesa las partes de la entrada para extraer los índices y obtener las
	 * cartas a retirar.
	 * 
	 * @param mesaCartas     mesa cartas
	 * @param cartasARetirar cartas a retirar
	 * @param partes         partes de la entrada
	 */
	private void procesarPartes(Carta[] mesaCartas, Carta[] cartasARetirar, String[] partes) {
		int contador = 0;
		for (String parte : partes) {
			try {
				int indice = Integer.parseInt(parte);
				if (indice >= 0 && indice < mesaCartas.length) {
					Carta carta = mesaCartas[indice];
					if (!ManejadorArray.contiene(carta, cartasARetirar)) {
						cartasARetirar[contador++] = carta;
					}
				} else {
					System.out.println("Índice " + indice + " fuera de rango, ignorado.");
				}
			} catch (NumberFormatException _) {
				System.out.println("'" + parte + "' no es un número válido, ignorado.");
			}
		}
	}

	/**
	 * Lee desde teclado los índices de las cartas a retirar.
	 * 
	 * @return texto con índices separados por espacios en blanco
	 */
	private String leerCartasARetirar() {
		String entrada;
		System.out.println(
				"Introduce los índices de las cartas separados por espacios (o 'enter' para no recoger nada):");
		scanner.nextLine(); // Limpiar el buffer
		entrada = scanner.nextLine().trim(); // limpiar cadena
		return entrada;
	}

	/**
	 * Muestra la carta jugadas y las cartas que se retiran de la mesa.
	 * 
	 * @param cartaJugada carta jugada
	 * @param mesa        mesa
	 */
	private void mostrarCartaRetiradaYCartasSobreLaMesa(Carta cartaJugada, Mesa mesa) {
		System.out.println("\nHas jugado la siguiente carta:");
		// Mostrar la carta jugada con visualización mejorada
		String[] cartaJugadaVisual = CartaTextoColor.dibujarCarta(cartaJugada);
		String[][] cartasArray = { cartaJugadaVisual };
		imprimirCartas(cartasArray);

		System.out.print("¿Qué cartas quieres recoger de la mesa?");
		System.out.println(" (Deben sumar " + (15 - cartaJugada.puntuacion()) + " puntos para hacer 15 total)");
		System.out.println("Cartas disponibles en la mesa:");
		mostrarCartasEnLaMesa(mesa);
	}

	/**
	 * Anuncia en pantalla que se ha conseguido una escoba.
	 * 
	 * @param nombreJugador nombre de jugador
	 */
	public void anunciarEscoba(String nombreJugador) {
		System.out.println("\n🎉 ¡¡¡ESCOBA para " + nombreJugador.toUpperCase() + "!!! 🎉");
		System.out.println("🪧La mesa ha quedado vacía.");
	}

	/**
	 * Muestra el resultado final de la partida para todos los jugadores.
	 * 
	 * @param partida partida
	 */
	public void mostrarResultadoFinal(Partida partida) {
		System.out.println("\n" + "=".repeat(30));
		System.out.println("Resultado final de la partida");
		System.out.println("=".repeat(30));

		for (Jugador jugador : partida.obtenerJugadores()) {
			System.out.println(jugador.consultarNombre() + ":");
			System.out.println(" - Bazas conseguidas: " + jugador.consultarBazas().length);
			System.out.println(" - Cartas recogidas: " + jugador.contarCartas());
			mostrarDesglosePuntos(jugador);
			System.out.println();
		}
		// se supone que finaliza la partida y cerramos recursos si se quiere usar este
		// método para depurar y solo por ese motivo se debería comentar la siguiente
		// línea, puesto que posteriores lecturas de teclado generarían excepción
		scanner.close();
	}

	/**
	 * Muestra el desglose de puntos según reglas aplicadas.
	 * 
	 * @param jugador jugador
	 */
	private void mostrarDesglosePuntos(Jugador jugador) {
		mostrarPuntosPorEscobas(jugador);
		mostrarPuntosPorOros(jugador);
		mostrarPuntosPorSieteOros(jugador);
		mostrarPuntosPorSietes(jugador);
		mostrarPuntosPorMayoríaCartas(jugador);
		mostrarPuntosPorCartasContrario(jugador);
		mostrarMensajeSiNoTieneBazas(jugador);
	}

	/**
	 * Muestra los puntos obtenidos por escobas.
	 * 
	 * @param jugador jugador
	 */
	private void mostrarPuntosPorEscobas(Jugador jugador) {
		int escobas = jugador.consultarEscobas();
		if (escobas != 0) {
			System.out.printf(" %d Puntos - Escobas.%n", escobas);
		}
	}

	/**
	 * Muestra los puntos obtenidos por oros.
	 * 
	 * @param jugador jugador
	 */
	private void mostrarPuntosPorOros(Jugador jugador) {
		int oros = jugador.contarOros();
		if (oros == NUMERO_CARTAS_POR_PALO) {
			System.out.println(" 2 Puntos - Todos los oros. ");
		} else if (oros > NUMERO_CARTAS_POR_PALO / 2) {
			System.out.println(" 1 Punto - Mayoría de oros: " + oros);
		}
	}

	/**
	 * Muestra los puntos obtenidos por tener el siete de oros.
	 * 
	 * @param jugador jugador
	 */
	private void mostrarPuntosPorSieteOros(Jugador jugador) {
		if (jugador.tieneSieteOros()) {
			System.out.println(" 1 Punto - Siete de oros (\"guindis\").");
		}
	}

	/**
	 * Muestra los puntos obtenidos por sietes.
	 * 
	 * @param jugador jugador
	 */
	private void mostrarPuntosPorSietes(Jugador jugador) {
		int sietes = jugador.contarSietes();
		if (sietes == TOTAL_SIETES_EN_BARAJA) {
			System.out.println(" 2 Puntos - Tener todos los sietes.");
		} else if (sietes >= TOTAL_SIETES_EN_BARAJA - 1) {
			System.out.println(" 1 Punto - Tener mayoría de sietes.");
		}
	}

	/**
	 * Muestra los puntos obtenidos por mayoría de cartas.
	 * 
	 * @param jugador jugador
	 */
	private void mostrarPuntosPorMayoríaCartas(Jugador jugador) {
		if (jugador.contarCartas() > Baraja.NUMERO_CARTAS / 2) {
			System.out.println(" 1 Punto - Tener mayoría de cartas.");
		}
	}

	/**
	 * Muestra los puntos obtenidos por tener menos de 10 cartas que el contrario.
	 * 
	 * @param jugador jugador
	 */
	private void mostrarPuntosPorCartasContrario(Jugador jugador) {
		if (jugador.contarCartas() > MUCHAS_MAS_CARTAS_QUE_EL_CONTRARIO) {
			System.out.println(" 2 Puntos - Tener el contrario menos de 10 cartas.");
		}
	}

	/**
	 * Muestra un mensaje si el jugador no ha conseguido ninguna baza.
	 * 
	 * @param jugador jugador
	 */
	private void mostrarMensajeSiNoTieneBazas(Jugador jugador) {
		if (jugador.consultarBazas().length == 0) {
			System.out.println("☠️ ¡¡Pierdes la partida por no completar ninguna baza!!!");
		}
	}

	/**
	 * Imprime varias cartas en la misma fila.
	 * 
	 * @param cartas como un array de arrays de cadenas de texto representando
	 *               cartas
	 */
	private static void imprimirCartas(String[][] cartas) {
		if (cartas.length != 0) {
			for (int i = 0; i < cartas[0].length; i++) {
				for (String[] carta : cartas) {
					System.out.print(carta[i] + " ");
				}
				System.out.println();
			}
		}
	}
}