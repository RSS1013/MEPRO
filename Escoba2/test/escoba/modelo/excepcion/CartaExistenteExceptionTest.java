package escoba.modelo.excepcion;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import escoba.modelo.excepcion.CartaExistenteException;

/**
 * Pruebas unitarias sobre la excepción CartaExistenteException.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 2.0 20251111
 *
 */
@DisplayName("Tests sobre la excepción comprobable CartaExistenteException")
public class CartaExistenteExceptionTest {

	/**
	 * Constructor privado.
	 */
	private CartaExistenteExceptionTest() {
		// Constructor privado para evitar instanciación
	}

	/**
	 * Correcta definición de cláusula de herencia.
	 */
	@DisplayName("Comprobar que la cláusula extends de la excepción es correcta.")
	@Test
	public void probarCorrectaHerencia() {
		// se necesitan ambos asertos por la relaciones de herencia entre ambas clases
		assertAll("incorrecta cláusula de herencia en la excepción",
			() -> assertThat("La clase CartaExistenteException debe heredar de Exception.",
					Exception.class.isAssignableFrom(CartaExistenteException.class), is(true)),

			() -> assertThat("La clase CartaExistenteException NO debe heredar de RuntimeException.",
					RuntimeException.class.isAssignableFrom(CartaExistenteException.class), is(false))
			);
	}
}