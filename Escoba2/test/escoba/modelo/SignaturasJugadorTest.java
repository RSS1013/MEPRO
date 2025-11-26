package escoba.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import escoba.SignaturasUtil;
import escoba.modelo.excepcion.CartaExistenteException;
import escoba.modelo.excepcion.CartaNoEncontradaException;

/**
 * Tests de signatura para la clase Jugador.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 * @see escoba.modelo.Jugador
 */
@DisplayName("Tests sobre signaturas de Jugador")
public class SignaturasJugadorTest extends SignaturasUtil {

	/** Constructor. */
	private SignaturasJugadorTest() {
	}

	/**
	 * Comprobación de signatura del constructor con argumento.
	 */
	@Test
	@DisplayName("Comprobación de signatura del constructor Jugador(String)")
	void testComprobarQueExisteConstructorConNombre() {
		verificarConstructorConGenericos(Jugador.class, "Jugador(String)", "java.lang.String",
				"escoba.modelo.comparador.Comparador<escoba.modelo.Carta>");
	}

	/**
	 * Comprobación de signaturas de métodos sin argumentos.
	 *
	 * @param nombreMetodo      nombre del método
	 * @param nombreTipoRetorno nombre de tipo de retorno
	 * @throws ClassNotFoundException si no se encuentra la clase
	 */
	@ParameterizedTest
	@DisplayName("Comprobación de signaturas de métodos públicos sin argumentos")
	@CsvSource({ "consultarBazas, java.util.List<escoba.modelo.Baza>", "consultarEscobas, int",
			"consultarMano, java.util.List<escoba.modelo.Carta>", "consultarNombre, java.lang.String",
			"contarCartas, int", "contarOros, int", "contarSietes, int", "estaSinCartas, boolean",
			"tieneSieteOros, boolean", })
	void testComprobarMetodosSinParametros(String nombreMetodo, String nombreTipoRetorno)
			throws ClassNotFoundException {
		verificarMetodoConGenericos(Jugador.class, nombreMetodo, nombreTipoRetorno);
	}

	/**
	 * Comprobación de signaturas de métodos con un argumento.
	 *
	 * @param nombreMetodo         nombre del método
	 * @param nombreTipoRetorno    nombre del tipo de retorno
	 * @param nombreClaseParametro nombre de la clase como argumento formal
	 * @throws ClassNotFoundException si no se encuentra la clase
	 */
	@ParameterizedTest
	@DisplayName("Comprobación de signaturas de métodos públicos con argumentos")
	@CsvSource({ "agregarBaza, void, escoba.modelo.Baza", "jugarCarta, void, escoba.modelo.Carta",
			"recibirCarta, void, escoba.modelo.Carta" })
	void testComprobarMetodosConParametros(String nombreMetodo, String nombreTipoRetorno, String nombreClaseParametro)
			throws ClassNotFoundException {
		Class<?> tipoRetorno = obtenerClase(nombreTipoRetorno);
		Class<?> tipoParametro = obtenerClase(nombreClaseParametro);
		verificarMetodo(Jugador.class, nombreMetodo, tipoRetorno, tipoParametro);
	}


	/**
	 * Comprobación de las cláusula throws de jugarCarta.
	 */
    @Test
    @DisplayName("El método jugar(Carta) solo debería declarar throws CartaNoEncontradaException")
    void testJugarCartaDeclaraThrows() {
        verificarThrows(
            Jugador.class,
            "jugarCarta",
            new Class<?>[] { Carta.class },
            new Class<?>[] { CartaNoEncontradaException.class }
        );
    }

	/**
	 * Comprobación de la cláusula throws de recibirCarta.
	 */
    @Test
    @DisplayName("El método recibir(Carta) solo debería declarar throws CartaExistenteException")
    void testRecibirCartaDeclaraThrows() {
        verificarThrows(
            Jugador.class,
            "recibirCarta",
            new Class<?>[] { Carta.class },
            new Class<?>[] { CartaExistenteException.class }
        );
    }

	/**
	 * Comprobación de la no existencia de cláusulas throws de agregarBaza.
	 */
    @Test
    @DisplayName("El método agregarBaza(Baza) no debería declarar cláusula throws")
    void testAgregarBazaNoDebeDeclararThrows() {
        verificarThrows(
            Jugador.class,
            "agregarBaza",
            new Class<?>[] { Baza.class },
            new Class<?>[] {}
        );
    }
}
