package escoba.control;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import escoba.SignaturasUtil;
import escoba.modelo.Baza;
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
	private SignaturasControladorTest() {	}
	
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
		verificarMetodoPrivado(Controlador.class, "procesarJugada", 
				Jugador.class, Carta.class, Carta[].class);
	}

	/**
	 * Comprobación de signatura de método darCartasRestantes.
	 */
	@Test
	@DisplayName("Comprobación de signatura de método privado darCartasRestantes")
	void testComprobarMetodoDarCartasRestantes() {
		verificarMetodoPrivado(Controlador.class, "darCartasRestantes");
	}
}
