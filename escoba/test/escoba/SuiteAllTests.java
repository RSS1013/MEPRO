package escoba;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Suite ejecutando TODOS los tests de signaturas de las clases 
 * solicitadas al alumnado en la práctica del juego Escoba-1.0.
 * 
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 1.0
 * @see escoba.modelo.Jugador
 * @see escoba.modelo.Baza
 * @see escoba.control.Controlador
 */
@SelectPackages({
	"escoba.modelo",
	"escoba.control"
	})
@Suite
@SuiteDisplayName("Ejecución de todos los tests sobre signaturas de las clases solicitadas.")
public class SuiteAllTests {
	
	/** Constructor. */
	private SuiteAllTests() {	}
}
