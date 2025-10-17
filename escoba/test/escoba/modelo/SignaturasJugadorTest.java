package escoba.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import escoba.SignaturasUtil;

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
	private SignaturasJugadorTest() {	}

	/**
	 * Comprobación de signatura del constructor con argumento.
	 */
	@Test
	@DisplayName("Comprobación de signatura del constructor Jugador(String)")
	void testComprobarQueExisteConstructorConNombre() {
		verificarConstructor(Jugador.class, "Jugador(String)", String.class);
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
	@CsvSource({
		"consultarBazas, [Lescoba.modelo.Baza;",
		"consultarEscobas, int",
		"consultarMano, [Lescoba.modelo.Carta;",
		"consultarNombre, java.lang.String",		
		"contarCartas, int",
		"contarOros, int",
		"contarSietes, int",
		"estaSinCartas, boolean",		
		"tieneSieteOros, boolean",		
	})
	void testComprobarMetodosSinParametros(String nombreMetodo, String nombreTipoRetorno) throws ClassNotFoundException {
		Class<?> tipoRetorno = obtenerClase(nombreTipoRetorno);
		verificarMetodo(Jugador.class, nombreMetodo, tipoRetorno);
	}

	/**
	 * Comprobación de signaturas de métodos con un argumento.
	 * 
	 * @param nombreMetodo      nombre del método
	 * @param nombreTipoRetorno nombre del tipo de retorno
	 * @param nombreClaseParametro	nombre de la clase como argumento formal
	 * @throws ClassNotFoundException si no se encuentra la clase
	 */
	@ParameterizedTest
	@DisplayName("Comprobación de signaturas de métodos públicos con argumentos")
	@CsvSource({
		"agregarBaza, void, escoba.modelo.Baza",
		"jugarCarta, void, escoba.modelo.Carta",
		"recibirCarta, void, escoba.modelo.Carta"		
	})
	void testComprobarMetodosConParametros(String nombreMetodo, String nombreTipoRetorno, String nombreClaseParametro) 
			throws ClassNotFoundException {
		Class<?> tipoRetorno = obtenerClase(nombreTipoRetorno);
		Class<?> tipoParametro = obtenerClase(nombreClaseParametro);
		verificarMetodo(Jugador.class, nombreMetodo, tipoRetorno, tipoParametro);
	}
}
