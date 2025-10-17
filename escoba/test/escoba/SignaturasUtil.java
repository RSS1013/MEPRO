package escoba;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import escoba.modelo.Baza;
import escoba.modelo.Carta;

/**
 * Clase auxiliar con métodos para verificar signaturas de clases mediante
 * reflexión.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
public class SignaturasUtil {
	
	/** Constructor. */
	protected SignaturasUtil() {	}

	/**
	 * Método auxiliar para verificar la signatura de un constructor.
	 * 
	 * @param clase             clase a verificar
	 * @param nombreConstructor nombre del constructor para mensajes
	 * @param tiposParametros   tipos de parámetros del constructor
	 */
	public void verificarConstructor(Class<?> clase, String nombreConstructor, Class<?>... tiposParametros) {
		try {
			Constructor<?> constructor = clase.getDeclaredConstructor(tiposParametros);
			assertNotNull(constructor, "El constructor " + nombreConstructor + " no existe");
			assertTrue(Modifier.isPublic(constructor.getModifiers()),
					"El constructor " + nombreConstructor + " debe ser público.");
		} catch (NoSuchMethodException _) {
			fail("El constructor " + nombreConstructor + " no existe, es obligatoria su implementación.");
		}
	}

	/**
	 * Método auxiliar para verificar la signatura de un método público.
	 * 
	 * @param clase           clase a verificar
	 * @param nombreMetodo    nombre del método
	 * @param tipoRetorno     tipo de retorno esperado
	 * @param tiposParametros tipos de parámetros del método
	 */
	public void verificarMetodo(Class<?> clase, String nombreMetodo, Class<?> tipoRetorno,
			Class<?>... tiposParametros) {
		try {
			Method method = clase.getDeclaredMethod(nombreMetodo, tiposParametros);
			assertNotNull(method, "El método " + nombreMetodo + " no existe");
			assertTrue(Modifier.isPublic(method.getModifiers()), "El método " + nombreMetodo + " debe ser público.");
			assertThat("El método " + nombreMetodo + " debe devolver " + tipoRetorno.getSimpleName(),
					method.getReturnType(), equalTo(tipoRetorno));
		} catch (NoSuchMethodException _) {
			String parametros = tiposParametros.length == 0 ? "()"
					: "(" + java.util.Arrays.stream(tiposParametros).map(Class::getSimpleName)
							.reduce((a, b) -> a + ", " + b).orElse("") + ")";
			fail("El método " + nombreMetodo + parametros + " no existe, es obligatoria su implementación.");
		}
	}

	/**
	 * Método auxiliar para verificar la signatura de un método privado.
	 * 
	 * @param clase           clase a verificar
	 * @param nombreMetodo    nombre del método
	 * @param tiposParametros tipos de parámetros del método
	 */
	public void verificarMetodoPrivado(Class<?> clase, String nombreMetodo, Class<?>... tiposParametros) {
		try {
			Method method = clase.getDeclaredMethod(nombreMetodo, tiposParametros);
			assertNotNull(method, "El método " + nombreMetodo + " no existe");
			assertTrue(Modifier.isPrivate(method.getModifiers()), "El método " + nombreMetodo + " debe ser privado.");
		} catch (NoSuchMethodException _) {
			String parametros = tiposParametros.length == 0 ? "()"
					: "(" + java.util.Arrays.stream(tiposParametros).map(Class::getSimpleName)
							.reduce((a, b) -> a + ", " + b).orElse("") + ")";
			fail("El método " + nombreMetodo + parametros + " no existe, es obligatoria su implementación.");
		}
	}

	/**
	 * Método auxiliar para obtener una clase a partir de su nombre. Maneja tanto
	 * clases primitivas como clases (tipo objeto) y arrays.
	 * 
	 * @param nombreClase nombre de la clase
	 * @return clase correspondiente
	 * @throws ClassNotFoundException si la clase no se encuentra
	 */
	public Class<?> obtenerClase(String nombreClase) throws ClassNotFoundException {
		return switch (nombreClase) {
		case "void" -> void.class;
		case "int" -> int.class;
		case "boolean" -> boolean.class;
		case "double" -> double.class;
		case "float" -> float.class;
		case "long" -> long.class;
		case "short" -> short.class;
		case "byte" -> byte.class;
		case "char" -> char.class;
		case "[Lescoba.modelo.Carta;" -> Carta[].class;
		case "[Lescoba.modelo.Baza;" -> Baza[].class;
		default -> Class.forName(nombreClase);
		};
	}

}
