package escoba.vista.util;

import java.util.Optional;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import escoba.modelo.Carta;
import escoba.modelo.Palo;

/**
 * Generación de textos con colores para imprimir las cartas en la consola.
 * 
 * Las cartas se van a representar en consola como cinco líneas de texto, la
 * primera y la última con líneas, la segunda y cuarta con el valor númerico o
 * letra correspondiente y la línea de enmedio con el palo coloreado con un
 * fondo correspondiente al color de dicho palo.
 * 
 * Se siguen los colores clásicos de la baraja española.
 * <ul>
 * <li>Oros es amarillo.</li>
 * <li>Copas es rojo.</li>
 * <li>Espadas es azul.</li>
 * <li>Bastos es verde.</li>
 * </ul>
 * 
 * Depende de la biblioteca <a href="https://github.com/dialex/JColor">JColor</a>
 * para la correcta visualización con colores en modo consola
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 * @since JDK 24.0.2
 */
public class CartaTextoColor {

	/** Puntos del siete. */
	private static final int SIETE = 7;

	/** Puntos del as. */
	private static final int AS = 1;

	/** Puntos del rey. */
	private static final int REY = 10;

	/** Puntos del caballo. */
	private static final int CABALLO = 9;

	/** Puntos de la sota. */
	private static final int SOTA = 8;

	/** Texto blanco. */
	private static final Attribute TEXTO_BLANCO = Attribute.TEXT_COLOR(255, 255, 255);

	/** Negrita. */
	private static final Attribute NEGRITA = Attribute.BOLD();

	/** Ancho interno del contenido. */
	private static final int ANCHO = 9;

	/** Constructor privado. */
	private CartaTextoColor() {
	}

	/**
	 * Elige el color de fondo.
	 * 
	 * @param palo palo de la carta
	 * @return color de fondo
	 */
	private static Attribute obtenerColorPalo(Palo palo) {
		// usando switch expression directamente
		return switch (palo) {
		case OROS -> Attribute.BACK_COLOR(204, 153, 0); // Amarillo
		case COPAS -> Attribute.BACK_COLOR(255, 0, 0); // Rojo
		case ESPADAS -> Attribute.BACK_COLOR(0, 0, 255); // Azul
		case BASTOS -> Attribute.BACK_COLOR(0, 128, 0); // Verde
		};
	}

	/**
	 * Convierte la puntuación de una carta al valor visual para mostrar.
	 * 
	 * @param puntuacion puntuación de la carta (1-10)
	 * @return representación visual del valor
	 */
	private static String puntuacionAValorVisual(int puntuacion) {
		String resultado;
		if (puntuacion >= AS && puntuacion <= SIETE) {
			resultado = String.valueOf(puntuacion);
		} else if (puntuacion == SOTA) {
			resultado = "Sota"; // Sota
		} else if (puntuacion == CABALLO) {
			resultado = "Caballo"; // Caballo
		} else if (puntuacion == REY) {
			resultado = "Rey"; // Rey
		} else {
			resultado = "?";
		}
		return resultado;
	}

	/**
	 * Obtiene el nombre del palo formateado.
	 * 
	 * @param palo el palo de la carta
	 * @return nombre del palo capitalizado
	 */
	private static String obtenerNombrePalo(Palo palo) {
		String nombre = palo.toString().toLowerCase();
		return nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
	}

	/**
	 * Dibuja una carta con fondo coloreado y texto en blanco/negrita.
	 * 
	 * @param carta la carta a dibujar
	 * @return array de cadenas de texto representando las líneas de la carta
	 */
	public static String[] dibujarCarta(Carta carta) {
		String valor = puntuacionAValorVisual(carta.puntuacion());
		String nombrePalo = obtenerNombrePalo(carta.palo());
		Attribute colorFondo = obtenerColorPalo(carta.palo());

		return dibujarCarta(valor, nombrePalo, colorFondo);
	}

	/**
	 * Dibuja una carta con fondo coloreado y texto en blanco/negrita.
	 * 
	 * @param valor      valor a mostrar en las esquinas
	 * @param nombrePalo nombre del palo
	 * @param colorFondo color de fondo
	 * @return array de cadenas de texto epresentando las líneas de la carta
	 */
	private static String[] dibujarCarta(String valor, String nombrePalo, Attribute colorFondo) {
		int desp = valor.length() > 1 ? valor.length() : 2;
		String filaArriba = String.format("│%-2s%-" + (ANCHO - desp) + "s│", valor, "");
		String filaAbajo = String.format("│%" + (ANCHO - desp) + "s%2s│", "", valor);

		// Calcular centrado del texto del palo
		int longitud = nombrePalo.length();
		int espacios = ANCHO - longitud;
		int izq = espacios / 2;
		int der = espacios - izq;
		String paloCentrado = " ".repeat(izq) + nombrePalo + " ".repeat(der);

		String filaCentral;
		// Si se soportan ANSI, devolver sin colores
		if (soportaAnsi()) {
			filaCentral = Ansi.colorize(paloCentrado, TEXTO_BLANCO, NEGRITA, colorFondo);
		} else {
			filaCentral = paloCentrado;
		}

		// Desactivamos el formateo automático en Eclipse con CTRL+MAY+F5
		// en esta parte del texto para visualizar el array de cadenas de texto
		// tal y como luego será pintado en pantalla.
		// @formatter:off
		return new String[] { 
				"┌─────────┐", 
				filaArriba, 
				"│" + filaCentral + "│", 
				filaAbajo, 
				"└─────────┘" };
		// @formatter:on
	}

	/**
	 * Detecta si está ejecutando desde Eclipse para no deshabilitar los colores.
	 * 
	 * @return true si estamos ejecutando desde Eclipse o false en caso contrario
	 */
	public static boolean estaEjecutandoseDesdeEclipse() {
		ProcessHandle handle = ProcessHandle.current();

		boolean seguir = true;
		// Subimos por la cadena de procesos hasta el root
		while (seguir) {
			Optional<ProcessHandle> parent = handle.parent();
			if (parent.isEmpty()) {
				seguir = false;
			} else {
				handle = parent.get();
				Optional<String> cmd = handle.info().command();
				if (cmd.isPresent()) {
					String lower = cmd.get().toLowerCase();
					if (lower.contains("eclipse")) { // estamos ejecutando en Eclipse...
						return true;
					}
				}
			}
		}
		return false; // si no ejecutamos desde Eclipse, asumimos que es consola (cmd)
	}

	/**
	 * Detecta si el entorno de ejecución soporta ANSI.
	 * 
	 * @return true si soporta ANSI, false en caso contrario
	 */
	private static boolean soportaAnsi() {
		String sistemaOperativo = System.getProperty("os.name").toLowerCase();
		return System.getenv("WT_SESSION") != null // Windows Terminal
				|| System.getenv("ANSICON") != null // ConEmu, Cmder
				|| System.getenv("ConEmuANSI") != null // ConEmu
				|| System.getenv("TERM_PROGRAM") != null // macOS Terminal, VSCode
				|| (System.getenv("TERM") != null && !"dumb".equals(System.getenv("TERM"))) // Linux, Git Bash, WSL
				|| estaEjecutandoseDesdeEclipse() // Desde eclipse...
				|| sistemaOperativo.contains("windows 11") // windows 11 terminal
				|| sistemaOperativo.contains("linux") || sistemaOperativo.contains("mac");
	}

}