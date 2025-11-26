package escoba;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
	 * Método auxiliar para verificar la signatura de un constructor con tipos genéricos.
	 *
	 * @param clase             clase a verificar
	 * @param nombreConstructor nombre del constructor para mensajes
	 * @param tiposParametrosEsperados descripciones de los tipos de parámetros genéricos esperados
	 */
	public void verificarConstructorConGenericos(Class<?> clase, String nombreConstructor, String... tiposParametrosEsperados) {
		try {
			// Obtener las clases raw de los parámetros para buscar el constructor
			Class<?>[] clasesParametros = obtenerClasesRaw(tiposParametrosEsperados);

			Constructor<?> constructor = clase.getDeclaredConstructor(clasesParametros);
			assertNotNull(constructor, "El constructor " + nombreConstructor + " no existe");
			assertTrue(Modifier.isPublic(constructor.getModifiers()),
					"El constructor " + nombreConstructor + " debe ser público.");

			// Verificar los tipos genéricos de los parámetros
			Type[] genericParameterTypes = constructor.getGenericParameterTypes();

			if (genericParameterTypes.length != tiposParametrosEsperados.length) {
				fail("El constructor " + nombreConstructor + " debe tener " + tiposParametrosEsperados.length +
					 " parámetros, pero tiene " + genericParameterTypes.length);
			}

			for (int i = 0; i < genericParameterTypes.length; i++) {
				String tipoParametroReal = obtenerNombreTipoGenerico(genericParameterTypes[i]);
				assertThat("El parámetro " + (i + 1) + " del constructor " + nombreConstructor +
						   " debe ser de tipo " + tiposParametrosEsperados[i],
						tipoParametroReal, equalTo(tiposParametrosEsperados[i]));
			}

		} catch (NoSuchMethodException _) {
			String parametros = tiposParametrosEsperados.length == 0 ? "()"
					: "(" + String.join(", ", tiposParametrosEsperados) + ")";
			fail("El constructor " + nombreConstructor + parametros + " no existe, es obligatoria su implementación.");
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


	/**
	 * Método auxiliar para verificar la signatura de un método público con tipos genéricos.
	 * Soporta tanto tipo de retorno genérico como parámetros genéricos.
	 *
	 * @param clase           clase a verificar
	 * @param nombreMetodo    nombre del método
	 * @param tipoRetornoEsperado descripción del tipo de retorno genérico esperado (ej: {@code java.util.List<escoba.modelo.Carta>})
	 * @param tiposParametrosEsperados descripciones de los tipos de parámetros genéricos esperados
	 */
	public void verificarMetodoConGenericos(Class<?> clase, String nombreMetodo, String tipoRetornoEsperado,
			String... tiposParametrosEsperados) {
		try {
			// Obtener las clases raw de los parámetros para buscar el método
			Class<?>[] clasesParametros = obtenerClasesRaw(tiposParametrosEsperados);

			Method method = clase.getDeclaredMethod(nombreMetodo, clasesParametros);
			assertNotNull(method, "El método " + nombreMetodo + " no existe");
			assertTrue(Modifier.isPublic(method.getModifiers()), "El método " + nombreMetodo + " debe ser público.");

			// Verificar el tipo genérico de retorno
			Type genericReturnType = method.getGenericReturnType();
			String tipoRetornoReal = obtenerNombreTipoGenerico(genericReturnType);

			assertThat("El método " + nombreMetodo + " debe devolver " + tipoRetornoEsperado,
					tipoRetornoReal, equalTo(tipoRetornoEsperado));

			// Verificar los tipos genéricos de los parámetros
			Type[] genericParameterTypes = method.getGenericParameterTypes();

			if (genericParameterTypes.length != tiposParametrosEsperados.length) {
				fail("El método " + nombreMetodo + " debe tener " + tiposParametrosEsperados.length +
					 " parámetros, pero tiene " + genericParameterTypes.length);
			}

			for (int i = 0; i < genericParameterTypes.length; i++) {
				String tipoParametroReal = obtenerNombreTipoGenerico(genericParameterTypes[i]);
				assertThat("El parámetro " + (i + 1) + " del método " + nombreMetodo +
						   " debe ser de tipo " + tiposParametrosEsperados[i],
						tipoParametroReal, equalTo(tiposParametrosEsperados[i]));
			}

		} catch (NoSuchMethodException _) {
			String parametros = tiposParametrosEsperados.length == 0 ? "()"
					: "(" + String.join(", ", tiposParametrosEsperados) + ")";
			fail("El método " + nombreMetodo + parametros + " no existe, es obligatoria su implementación.");
		}
	}


	/**
	 * Método auxiliar para verificar la signatura de un método privado con tipos genéricos sin retorno.
	 * Soporta tanto tipo de retorno genérico como parámetros genéricos.
	 *
	 * @param clase           clase a verificar
	 * @param nombreMetodo    nombre del método
	 * @param tiposParametrosEsperados descripciones de los tipos de parámetros genéricos esperados
	 */
	public void verificarMetodoPrivadoSinRetornoConGenericos(Class<?> clase, String nombreMetodo,
			String... tiposParametrosEsperados) {
		try {
			// Obtener las clases raw de los parámetros para buscar el método
			Class<?>[] clasesParametros = obtenerClasesRaw(tiposParametrosEsperados);

			Method method = clase.getDeclaredMethod(nombreMetodo, clasesParametros);
			assertNotNull(method, "El método " + nombreMetodo + " no existe");
			assertTrue(Modifier.isPrivate(method.getModifiers()), "El método " + nombreMetodo + " debe ser privado.");

			// Verificar los tipos genéricos de los parámetros
			Type[] genericParameterTypes = method.getGenericParameterTypes();

			if (genericParameterTypes.length != tiposParametrosEsperados.length) {
				fail("El método " + nombreMetodo + " debe tener " + tiposParametrosEsperados.length +
					 " parámetros, pero tiene " + genericParameterTypes.length);
			}

			for (int i = 0; i < genericParameterTypes.length; i++) {
				String tipoParametroReal = obtenerNombreTipoGenerico(genericParameterTypes[i]);
				assertThat("El parámetro " + (i + 1) + " del método " + nombreMetodo +
						   " debe ser de tipo " + tiposParametrosEsperados[i],
						tipoParametroReal, equalTo(tiposParametrosEsperados[i]));
			}

		} catch (NoSuchMethodException _) {
			String parametros = tiposParametrosEsperados.length == 0 ? "()"
					: "(" + String.join(", ", tiposParametrosEsperados) + ")";
			fail("El método " + nombreMetodo + parametros + " no existe, es obligatoria su implementación.");
		}
	}

	/**
	 * Método auxiliar para verificar la signatura de un método público con tipos genéricos.
	 * Soporta tanto tipo de retorno genérico como parámetros genéricos.
	 *
	 * @param clase           clase a verificar
	 * @param nombreMetodo    nombre del método
	 * @param tipoRetornoEsperado descripción del tipo de retorno genérico esperado (ej: {@code java.util.List<escoba.modelo.Carta>})
	 */
	public void verificarMetodoConGenericos(Class<?> clase, String nombreMetodo, String tipoRetornoEsperado) {
		try {

			Method method = clase.getDeclaredMethod(nombreMetodo);
			assertNotNull(method, "El método " + nombreMetodo + " no existe");
			assertTrue(Modifier.isPublic(method.getModifiers()), "El método " + nombreMetodo + " debe ser público.");

			// Verificar el tipo genérico de retorno
			Type genericReturnType = method.getGenericReturnType();
			String tipoRetornoReal = obtenerNombreTipoGenerico(genericReturnType);

			assertThat("El método " + nombreMetodo + " debe devolver " + tipoRetornoEsperado,
					tipoRetornoReal, equalTo(tipoRetornoEsperado));

		} catch (NoSuchMethodException _) {
			fail("El método " + nombreMetodo + " no existe, es obligatoria su implementación.");
		}
	}

	/**
	 * Obtiene las clases raw (sin información genérica) a partir de nombres de tipos que pueden ser genéricos.
	 *
	 * @param nombresTypes nombres de los tipos (pueden incluir genéricos como {@code java.util.List<escoba.modelo.Carta>})
	 * @return array de clases raw
	 */
	private Class<?>[] obtenerClasesRaw(String... nombresTypes) {
		Class<?>[] clases = new Class<?>[nombresTypes.length];
		for (int i = 0; i < nombresTypes.length; i++) {
			clases[i] = extraerClaseRaw(nombresTypes[i]);
		}
		return clases;
	}

	/**
	 * Extrae la clase raw de un nombre de tipo que puede ser genérico.
	 * Ejemplo: {@code java.util.List<escoba.modelo.Carta> -> java.util.List.class}
	 *
	 * @param nombreType nombre del tipo (puede incluir genéricos)
	 * @return clase raw
	 */
	private Class<?> extraerClaseRaw(String nombreType) {
		try {
			// Si contiene '<', extraer solo la parte antes del '<'
			String nombreClaseRaw = nombreType.contains("<") ?
					nombreType.substring(0, nombreType.indexOf('<')) : nombreType;
			return obtenerClase(nombreClaseRaw);
		} catch (ClassNotFoundException e) {
			fail("No se puede encontrar la clase: " + nombreType);
			return null; // No se alcanzará debido al fail
		}
	}

	/**
	 * Obtiene el nombre completo de un tipo genérico.
	 *
	 * @param type tipo a procesar
	 * @return nombre del tipo en formato String
	 */
	private String obtenerNombreTipoGenerico(Type type) {
		if (type instanceof ParameterizedType paramType) {
			Type rawType = paramType.getRawType();
			Type[] typeArgs = paramType.getActualTypeArguments();

			StringBuilder sb = new StringBuilder();
			sb.append(rawType.getTypeName());

			if (typeArgs.length > 0) {
				sb.append("<");
				for (int i = 0; i < typeArgs.length; i++) {
					if (i > 0) {
						sb.append(", ");
					}
					sb.append(obtenerNombreTipoGenerico(typeArgs[i]));
				}
				sb.append(">");
			}
			return sb.toString();
		} else if (type instanceof Class<?>) {
			return ((Class<?>) type).getName();
		} else {
			return type.getTypeName();
		}
	}


	/**
	 * Verifica que un método declara las excepciones esperadas en su cláusula throws.
	 *
	 * @param clase               clase donde se busca el método.
	 * @param nombreMetodo        nombre del método.
	 * @param tiposParametros     tipos de parámetros del método.
	 * @param excepcionesEsperadas tipos de excepciones que debe declarar.
	 */
	protected void verificarThrows(Class<?> clase, String nombreMetodo, Class<?>[] tiposParametros, Class<?>[] excepcionesEsperadas) {
	    try {
	        var metodo = clase.getDeclaredMethod(nombreMetodo, tiposParametros);
	        Class<?>[] excepcionesDeclaradas = metodo.getExceptionTypes();

	        for (Class<?> exEsperada : excepcionesEsperadas) {
	            boolean encontrada = false;
	            for (Class<?> exDeclarada : excepcionesDeclaradas) {
	                if (exDeclarada.equals(exEsperada)) {
	                    encontrada = true;
	                    break;
	                }
	            }
	            assertTrue(encontrada,
	                () -> "El método " + metodo.getName() + " no declara la excepción esperada: " + exEsperada.getSimpleName());
	        }

	    } catch (NoSuchMethodException e) {
	        fail("No se encontró el método: " + nombreMetodo + " en " + clase.getSimpleName());
	    }
	}

	/**
	 * Verifica que en una clase o interfaz exista un método con la signatura esperada.
	 *
	 * @param clase           clase o interfaz a comprobar.
	 * @param nombreMetodo    nombre del método.
	 * @param tipoRetorno     tipo de retorno esperado.
	 * @param tiposParametros tipos de parámetros esperados.
	 * @param excepcionesEsperadas excepciones declaradas esperadas (puede ser vacío).
	 */
	protected void verificarMetodo(
	        Class<?> clase,
	        String nombreMetodo,
	        Class<?> tipoRetorno,
	        Class<?>[] tiposParametros,
	        Class<?>[] excepcionesEsperadas) {

	    try {
	        var metodo = clase.getDeclaredMethod(nombreMetodo, tiposParametros);

	        // Verificar tipo de retorno
	        assertEquals(tipoRetorno, metodo.getReturnType(),
	                "El método " + nombreMetodo + " debe retornar " + tipoRetorno.getSimpleName());

	        // Verificar excepciones declaradas
	        Class<?>[] excepcionesDeclaradas = metodo.getExceptionTypes();
	        assertEquals(excepcionesEsperadas.length, excepcionesDeclaradas.length,
	                "Número de excepciones declaradas incorrecto en " + nombreMetodo);

	        for (Class<?> exEsperada : excepcionesEsperadas) {
	            boolean encontrada = false;
	            for (Class<?> exDeclarada : excepcionesDeclaradas) {
	                if (exDeclarada.equals(exEsperada)) {
	                    encontrada = true;
	                    break;
	                }
	            }
	            assertTrue(encontrada,
	                    "El método " + nombreMetodo + " no declara la excepción esperada: " + exEsperada.getSimpleName());
	        }

	    } catch (NoSuchMethodException e) {
	        fail("No se encontró el método: " + nombreMetodo + " en " + clase.getSimpleName());
	    }
	}


	/**
	 * Verifica que un método no declara excepciones esperadas en su cláusula throws.
	 *
	 * @param clase               clase donde se busca el método.
	 * @param nombreMetodo        nombre del método.
	 * @param tiposParametros     tipos de parámetros del método.
	 * @param excepcionesEsperadas tipos de excepciones que debe declarar que debería estar vacío
	 */
	protected void verificarNoThrows(Class<?> clase, String nombreMetodo, Class<?>[] tiposParametros, Class<?>[] excepcionesEsperadas) {
	    try {
	        var metodo = clase.getDeclaredMethod(nombreMetodo, tiposParametros);
	        Class<?>[] excepcionesDeclaradas = metodo.getExceptionTypes();

			assertThat("El método " + nombreMetodo + " no debe declarar lanzamiento de excepciones en cláusula throws.", excepcionesDeclaradas.length,
					equalTo(0));

	    } catch (NoSuchMethodException e) {
	        fail("No se encontró el método: " + nombreMetodo + " en " + clase.getSimpleName());
	    }
	}



}
