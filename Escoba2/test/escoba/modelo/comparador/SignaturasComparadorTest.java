package escoba.modelo.comparador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.TypeVariable;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import escoba.SignaturasUtil;

/**
 * Tests de signaturas para la interfaz Comparador.
 * Comprueba por reflexión que se define como interface y
 * que sus métodos tienen las signaturas exactas esperadas.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 * @see escoba.modelo.comparador.Comparador
 */
@DisplayName("Tests sobre signaturas de la interfaz Comparador")
public class SignaturasComparadorTest extends SignaturasUtil {

	/** Constructor. */
	private SignaturasComparadorTest() {
		// Constructor privado para evitar instanciación
	}

	/**
	 * Comprobación de que Comparador es una interfaz.
	 */
    @Test
    @DisplayName("Comprobar que comparador es una interfaz")
    void testEsInterfaz() {
        assertTrue(Comparador.class.isInterface(), "Comparador debe ser una interfaz");
    }

    /**
     * Comprobación de metodo comparar definido en la interfaz Comparador.
     */
    @Test
    @DisplayName("Comprobar que define el método comparar(T, T) con retorno int")
    void testMetodoComparar() {
        verificarMetodo(
            Comparador.class,
            "comparar",
            int.class,
            new Class<?>[] { Object.class, Object.class },
            new Class<?>[] {}
        );
    }

    /**
     * Comprobación de metodo ordenar definido en la interfaz Comparador.
     */
    @Test
    @DisplayName("Comprobar que define el método ordenar(List<T>) con retorno void")
    void testMetodoOrdenar() {
        verificarMetodo(
            Comparador.class,
            "ordenar",
            void.class,
            new Class<?>[] { List.class },
            new Class<?>[] {}
        );
    }

    /**
     * Comprobación de metodo consultarOrdenaciones definido en la interfaz Comparador.
     */
    @Test
    @DisplayName("Comprobar que define el método consultarOrdenaciones() con retorno int")
    void testMetodoConsultarOrdenaciones() {
        verificarMetodo(
            Comparador.class,
            "consultarOrdenaciones",
            int.class,
            new Class<?>[] {},
            new Class<?>[] {}
        );
    }

    /**
     * Comprobación de metodo consultarIntercambios definido en la interfaz Comparador.
     */
    @Test
    @DisplayName("Comprobar que define el método consultarIntercambios() con retorno int")
    void testMetodoConsultarIntercambios() {
        verificarMetodo(
            Comparador.class,
            "consultarIntercambios",
            int.class,
            new Class<?>[] {},
            new Class<?>[] {}
        );
    }

    /**
     * Comprobación de metodo consultarFecha definido en la interfaz Comparador.
     */
    @Test
    @DisplayName("Comprobar que define el método consultarFecha() con retorno Date")
    void testMetodoConsultarFecha() {
        verificarMetodo(
            Comparador.class,
            "consultarFecha",
            Date.class,
            new Class<?>[] {},
            new Class<?>[] {}
        );
    }

    /**
     * Comprobación de único parámetro formal T en la interfaz Comparador.
     */
    @Test
    @DisplayName("Compprobar que utiliza un parámetro de tipo genérico formal T")
    void testUsaParametroGenericoT() {
        TypeVariable<Class<Comparador>>[] parametros = Comparador.class.getTypeParameters();
        assertEquals(1, parametros.length, "La interfaz Comparador debe tener un único parámetro genérico formal");
        assertEquals("T", parametros[0].getName(), "El parámetro genérico formal debería llamarse 'T'");
    }
}
