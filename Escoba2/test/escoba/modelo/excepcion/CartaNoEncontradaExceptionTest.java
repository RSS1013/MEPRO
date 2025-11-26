package escoba.modelo.excepcion;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias sobre la excepción CartaNoEncontradaException.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 2.0 20251111
 *
 */
@DisplayName("Tests sobre la excepción comprobable CartaNoEncontradaException")
public class CartaNoEncontradaExceptionTest {

	/**
	 * Constructor privado.
	 */
	private CartaNoEncontradaExceptionTest() {
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
			() -> assertThat("La clase CartaNoEncontradaException debe heredar de Exception.",
					Exception.class.isAssignableFrom(CartaNoEncontradaException.class), is(true)),

			() -> assertThat("La clase CartaNoEncontradaException NO debe heredar de RuntimeException.",
					RuntimeException.class.isAssignableFrom(CartaNoEncontradaException.class), is(false))
			);
	}
}