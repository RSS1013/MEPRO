package escoba;

import escoba.control.Controlador;
import escoba.fabrica.FabricaComparador;
import escoba.modelo.Baraja;
import escoba.modelo.Carta;
import escoba.modelo.Jugador;
import escoba.modelo.Mesa;
import escoba.modelo.Partida;
import escoba.modelo.comparador.Comparador;
import escoba.vista.VistaConsola;

/**
 * Clase raíz.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 2.0
 * @since JDK 24.0.2
 */
public final class AplicacionEscoba { // se impide la herencia

	/**
	 * Constructor privado para impedir instanciaciones.
	 */
	private AplicacionEscoba() {
	}

	/**
	 * Métod principal.
	 *
	 * Se incorpora tratamiento de excepciones para capturar cualquier error grave
	 * haciendo esta versión más robusta
	 *
	 * @param args argumentos en línea de comandos (sin uso)
	 */
	public static void main(final String[] args) {
		VistaConsola vista = null;
		try {
			final Comparador<Carta> comparador = FabricaComparador.crearComparador(args);
			informarComparadorSeleccionado(comparador);

			final Jugador jugador1 = new Jugador("Juan", comparador);
			final Jugador jugador2 = new Jugador("María", comparador);
			final Mesa mesa = new Mesa(comparador);
			final Baraja baraja = new Baraja();

			final Partida partida = new Partida(jugador1, jugador2, mesa, baraja);
			vista = new VistaConsola();
			final Controlador controlador = new Controlador(partida, vista);

			controlador.ejecutarPartida();

			informarComparador(comparador);
		} catch (Exception e) {
			System.err.println("Error grave, revisar la traza para corregir el error: " + e.getMessage());
		} finally {
			if (vista != null) {
				vista.cerrarConsola();
			}
		}
	}

	/**
	 * Informe del comparador seleccionado al inicio de la partida.
	 *
	 * @param comparador compardor configurado al incio de la partida
	 */
	private static void informarComparadorSeleccionado(final Comparador<Carta> comparador) {
		System.out.print("Informe del comparador seleccionado:");
		System.out.println(comparador.getClass().getSimpleName());
	}

	/**
	 * Informe final de la partida resumiendo las estadísticas del comparador.
	 *
	 * @param comparador comparador utilizado en la partida
	 */
	private static void informarComparador(final Comparador<Carta> comparador) {
		System.out.println("Comparador utilizado: " + comparador.getClass().getName());
		System.out.println("Número de ordenaciones realizadas: " + comparador.consultarOrdenaciones());
		System.out.println("Número de intercambios realizados: " + comparador.consultarIntercambios());
		System.out.println("Fecha y hora de finalización: " + java.time.LocalDateTime.now());
	}
}
