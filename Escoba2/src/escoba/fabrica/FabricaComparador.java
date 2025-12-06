package escoba.fabrica;

import java.time.Instant;
import java.util.Date;

import escoba.modelo.Carta;
import escoba.modelo.comparador.Comparador;
import escoba.modelo.comparador.ComparadorDecrecientePorClave;
import escoba.modelo.comparador.ComparadorPorPaloPuntuacion;
import escoba.modelo.comparador.ComparadorPorPuntuacion;
import escoba.modelo.comparador.ComparadorSinOrden;

/**
 * Fábrica encargada de crear instancias de los distintos comparadores
 * utilizados para ordenar cartas en el juego de la Escoba.
 * <p>
 * El comparador devuelto depende del parámetro recibido en la aplicación.
 * Si no se especifica un modo válido, se devuelve un {@link ComparadorSinOrden}.
 * </p>
 *
 * <p>Modos reconocidos:</p>
 * <ul>
 *     <li><b>"clave"</b>: crea un {@link ComparadorDecrecientePorClave}.</li>
 *     <li><b>"puntuacion"</b>: crea un {@link ComparadorPorPuntuacion}.</li>
 *     <li><b>"palo"</b>: crea un {@link ComparadorPorPaloPuntuacion}.</li>
 *     <li><b>cualquier otro valor</b>: crea un {@link ComparadorSinOrden}.</li>
 * </ul>
 *
 * <p>
 * Todos los comparadores se inicializan con una fecha de creación
 * obtenida mediante {@link Instant#now()}.
 * </p>
 *
 * @author Ricardo Sevilla Soba
 * @version 2.0
 * @since 2025-11-24
 */
public final class FabricaComparador {

    /**
     * Constructor privado que evita la instanciación de la clase.
     */
    private FabricaComparador() {
        // Evitar instanciación
    }

    /**
     * Crea un comparador para cartas según los argumentos recibidos.
     * <p>
     * Si {@code args} es nulo, está vacío o no contiene un modo válido, se
     * devuelve un {@link ComparadorSinOrden}. El modo es interpretado en
     * minúsculas para evitar errores por mayúsculas o espacios.
     * </p>
     *
     * @param args argumentos proporcionados por la aplicación (pueden ser nulos)
     * @return una instancia concreta de {@link Comparador} según el modo solicitado
     */
    public static Comparador<Carta> crearComparador(final String[] args) {
        final Date fecha = Date.from(Instant.now());

        String modo = "";
        if (args != null && args.length > 0 && args[0] != null) {
            modo = args[0].trim().toLowerCase();
        }

        switch (modo) {
        case "clave":
            return new ComparadorDecrecientePorClave(fecha);
        case "puntuacion":
            return new ComparadorPorPuntuacion(fecha);
        case "palo":
            return new ComparadorPorPaloPuntuacion(fecha);
        default:
            return new ComparadorSinOrden(fecha);
        }
    }
}
