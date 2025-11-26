package escoba.control;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import escoba.SignaturasUtil;
import escoba.modelo.Carta;
import escoba.modelo.Jugador;
import escoba.modelo.Partida;
import escoba.vista.VistaConsola;

/**
 * Tests de signatura para la clase Controlador.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 * @see escoba.control.Controlador
 */
@DisplayName("Tests sobre signaturas de Controlador")
public class SignaturasControladorTest extends SignaturasUtil {

	/** Constructor. */
	private SignaturasControladorTest() {
		// Constructor privado para evitar instanciación
	}

	/**
	 * Comprobación de signatura del constructor con argumentos.
	 */
	@Test
	@DisplayName("Comprobación de signatura del constructor Controlador(Partida, Vista)")
	void testComprobarQueExisteConstructorConNombre() {
		verificarConstructor(Controlador.class, "Controlador(Partida,VistaConsola)", Partida.class, VistaConsola.class);
	}


	/**
	 * Comprobación de signaturas de métodos públicos sin parámetros.
	 *
	 * @param nombreMetodo      nombre del método
	 * @param nombreTipoRetorno nombre del tipo de retorno
	 * @throws ClassNotFoundException si no se encuentra la clase
	 */
	@ParameterizedTest
	@DisplayName("Comprobación de signaturas de métodos públicos sin parámetros")
	@CsvSource({
		"ejecutarPartida, void"
	})
	void testComprobarMetodosPublicosSinParametros(String nombreMetodo, String nombreTipoRetorno)
			throws ClassNotFoundException {
		Class<?> tipoRetorno = obtenerClase(nombreTipoRetorno);
		verificarMetodo(Controlador.class, nombreMetodo, tipoRetorno);
	}

	/**
	 * Comprobación de signaturas de métodos privados sin parámetros.
	 */
	@Test
	@DisplayName("Comprobación de signatura de método privado repartirCartasIniciales")
	void testComprobarMetodoRepartirCartasIniciales() {
		verificarMetodoPrivado(Controlador.class, "repartirCartasIniciales");
	}

	/**
	 * Comprobación de signatura de método repartirCartas.
	 */
	@Test
	@DisplayName("Comprobación de signatura de método privado repartirCartas")
	void testComprobarMetodoRepartirCartas() {
		verificarMetodoPrivado(Controlador.class, "repartirCartas");
	}

	/**
	 * Comprobación de signatura de método procesarJugada.
	 */
	@Test
	@DisplayName("Comprobación de signatura de método privado procesarJugada")
	void testComprobarMetodoProcesarJugada() {
		verificarMetodoPrivadoSinRetornoConGenericos(Controlador.class, "procesarJugada", "escoba.modelo.Jugador",
				"escoba.modelo.Carta", "java.util.List<escoba.modelo.Carta>");
	}

	/**
	 * Comprobación de signatura de método darCartasRestantes.
	 */
	@Test
	@DisplayName("Comprobación de signatura de método privado darCartasRestantes")
	void testComprobarMetodoDarCartasRestantes() {
		verificarMetodoPrivado(Controlador.class, "darCartasRestantes");
	}


	// Tests throws

	/**
	 * Comprobación de signaturas de método ejecutarPartida que no declara excepciones.
	 */
	@Test
	@DisplayName("El método ejecutarPartida() no debería declarar clásula throws")
	void testEjecutarPartidaNoDeclaraThrows() {
		verificarNoThrows(Controlador.class, "ejecutarPartida", new Class<?>[] {}, new Class<?>[] {} // no debe declarar nada
		);
	}

	/**
	 * Comprobación de signaturas de método darCartasRestantes que no declara excepciones.
	 */
	@Test
	@DisplayName("El método darCartasRestantes() no debería declarar clásula throws")
	void testDarCartasRestantesNoDeclaraThrows() {
		verificarNoThrows(Controlador.class, "darCartasRestantes", new Class<?>[] {}, new Class<?>[] {} // no debe declarar nada
		);
	}

	/**
	 * Comprobación de signaturas de método repartirCartas que no declara excepciones.
	 */
	@Test
	@DisplayName("El método repartirCartas() no debería declarar clásula throws")
	void testRepartirCartasNoDeclaraThrows() {
		verificarNoThrows(Controlador.class, "repartirCartas", new Class<?>[] {}, new Class<?>[] {} // no debe declarar nada
		);
	}


	/**
	 * Comprobación de signaturas de método repartirCartasIniciales que no declara excepciones.
	 */
	@Test
	@DisplayName("El método repartirCartas() no debería declarar clásula throws")
	void testRepartirCartasInicialsNoDeclaraThrows() {
		verificarNoThrows(Controlador.class, "repartirCartasIniciales", new Class<?>[] {}, new Class<?>[] {} // no debe declarar nada
		);
	}


	/**
	 * Comprobación de signaturas de método procesarJugada que no declara excepciones.
	 */
	@Test
	@DisplayName("El método procesarJugada no debería declarar clásula throws")
	void testProcesarJugadaNoDeclaraThrows() {
		verificarNoThrows(Controlador.class, "procesarJugada", new Class<?>[] {Jugador.class, Carta.class, List.class}, new Class<?>[] {} // no debe declarar nada
		);
	}



}
