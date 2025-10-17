package escoba.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import escoba.SignaturasUtil;

/**
 * Tests de signatura para la clase Baza.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 * @see escoba.modelo.Baza
 */
@DisplayName("Tests sobre signaturas de Baza")
public class SignaturasBazaTest extends SignaturasUtil {
	
	/** Constructor. */
	private SignaturasBazaTest() {	}

	/**
	 * Comprobación de signatura del constructor sin argumentos.
	 */
	@Test
	@DisplayName("Comprobación de signatura del constructor Baza()")
	void testComprobarQueExisteConstructorSinParametros() {
		verificarConstructor(Baza.class, "Baza()");
	}

	/**
	 * Comprobación de signaturas de métodos sin argumentos.
	 * 
	 * @param nombreMetodo      nombre del método
	 * @param nombreTipoRetorno nombre del tipo de retorno
	 * @throws ClassNotFoundException si no se encuentra la clase
	 */
	@ParameterizedTest
	@DisplayName("Comprobación de signaturas de métodos públicos sin argumentos")
	@CsvSource({
		"clonar, escoba.modelo.Baza",
		"consultarCartas, [Lescoba.modelo.Carta;",
		"contarOros, int",
		"contarSietes, int",		
		"fueEscoba, boolean",
		"hashCode, int",		
		"marcarEscoba, void",	
		"tieneSieteOros, boolean",
		"toString, java.lang.String"		
	})
	void testComprobarMetodosSinParametros(String nombreMetodo, String nombreTipoRetorno) throws ClassNotFoundException {
		Class<?> tipoRetorno = obtenerClase(nombreTipoRetorno);
		verificarMetodo(Baza.class, nombreMetodo, tipoRetorno);
	}

	/**
	 * Comprobación de signaturas de métodos con un argumento.
	 * 
	 * @param nombreMetodo       nombre del método
	 * @param nombreTipoRetorno  nombre de la clase del tipo de retorno
	 * @param nombreClaseParametro nombre de la clase del argumento
	 * @throws ClassNotFoundException si no se encuentra la clase
	 */
	@ParameterizedTest
	@DisplayName("Comprobación de signaturas de métodos públicos con argumentos")
	@CsvSource({
		"agregarCarta, void, escoba.modelo.Carta",
		"equals, boolean, java.lang.Object"
	})
	void testComprobarMetodosConParametros(String nombreMetodo, String nombreTipoRetorno, String nombreClaseParametro) 
			throws ClassNotFoundException {
		Class<?> tipoRetorno = obtenerClase(nombreTipoRetorno);
		Class<?> tipoParametro = obtenerClase(nombreClaseParametro);
		verificarMetodo(Baza.class, nombreMetodo, tipoRetorno, tipoParametro);
	}
}
