package escoba.fabrica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import escoba.SignaturasUtil;
import escoba.fabrica.FabricaComparador;
import escoba.modelo.comparador.Comparador;

/**
 * Test de signaturas de FabricaComparador.
 *
 * Verifica que la clase contiene un único método con la signatura:
 * {@code public static Comparador<Carta> crearComparador(String[] discriminante)}.
 *
 * Además debe contener un constructo privado sin argumentos.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 2.0 20251111
 */
@DisplayName("Test sobre signaturas de FabricaComparador")
public class SignaturasFabricaComparadorTest extends SignaturasUtil {

	/**
	 * Constructor privado.
	 */
	private SignaturasFabricaComparadorTest() {
        // Constructor privado para evitar instanciación
    }

	/**
	 * Verifica que la clase FabricaComparador contiene exactamente un método.
	 */
    @Test
    @DisplayName("FabricaComparador contiene exactamente un método con la signatura esperada.")
    void testUnicoMetodoConSignaturaEsperada() {
        Class<?> clase = FabricaComparador.class;

        // Verifica que es una clase concreta
        assertFalse(clase.isInterface(), "FabricaComparador no debe ser una interfaz");
        assertFalse(Modifier.isAbstract(clase.getModifiers()), "FabricaComparador no debe ser abstracta");

        // Obtiene los métodos declarados (sin heredar)
        Method[] metodos = clase.getDeclaredMethods();

        // Arreglar bug con Jacoco para cobertura de métodos (añade método artificial)
        List<Method> metodosLimpios = new ArrayList<>();
		for (final Method metodo : metodos) {
			if (!metodo.getName().contains("$jacoco"))  { // si el método no es de Jacoco
				metodosLimpios.add(metodo);
			}
		}
		metodos = metodosLimpios.toArray(new Method[0]); // cambiamos el array original
        assertEquals(1, metodos.length, "FabricaComparador debe tener exactamente un único método declarado");

        // Comprueba que el método único
        Method metodo = metodos[0];
        assertEquals("crearComparador", metodo.getName(), "El método debe llamarse crearComparador");

        // Verifica modificadores del método: public y static
        int mods = metodo.getModifiers();
        assertTrue(Modifier.isPublic(mods), "El método debe ser public");
        assertTrue(Modifier.isStatic(mods), "El método debe ser static");

        // Verifica el tipo de retorno
        assertEquals(Comparador.class, metodo.getReturnType(),
                "El método debe retornar un tipo Comparador");

        // Verifica los parámetros
        Class<?>[] parametros = metodo.getParameterTypes();
        assertEquals(1, parametros.length, "El método debe tener exactamente un parámetro");
        assertEquals(String[].class, parametros[0],
                "El parámetro del método debe ser de tipo String[]");

        // Verifica que no declara excepciones
        assertEquals(0, metodo.getExceptionTypes().length,
                "El método no debe declarar cláusulas throws");
    }

    /**
     * Verifica que el constructor sin argumentos de FabricaComparador es privado.
     *
     * @throws Exception si la verificación ha generado un error.
     */
    @Test
    public void testConstructorPrivadoSinArgumentos() throws Exception {
        Class<?> clazz = FabricaComparador.class;

        // Obtener el constructor sin argumentos
        Constructor<?> constructor = clazz.getDeclaredConstructor();

        // Comprobar que es privado
        assertTrue(Modifier.isPrivate(constructor.getModifiers()),
                "El constructor sin argumentos debe ser privado");

        // Intentar hacerlo accesible y crear una instancia (opcional pero recomendable)
        constructor.setAccessible(true);
        Object instancia = constructor.newInstance();
        assertNotNull(instancia, "El constructor privado debería poder invocar una instancia");
    }

}
