package escoba.modelo.comparador;

import java.util.Date;
import java.util.List;

/**
 * Interfaz genérica que define los métodos necesarios para comparar y ordenar
 * elementos dentro del juego de la Escoba.
 * <p>
 * Las implementaciones permiten aplicar diferentes criterios de
 * comparación sobre las cartas, controlando también las estadísticas internas como el
 * número de ordenaciones o intercambios realizados.
 * </p>
 *
 * @param <T> tipo de elementos que este comparador es capaz de comparar
 * 
 * @author Ricardo Sevilla Soba
 * @since 2025-11-24
 * @version 2.0
 */
public interface Comparador<T> {

    /**
     * Compara dos elementos del tipo {@code T}.
     *
     * @param primero primer elemento
     * @param segundo segundo elemento
     * @return valor negativo si {@code primero < segundo}, cero si son iguales,
     *         valor positivo si {@code primero > segundo}
     */
    int comparar(T primero, T segundo);

    /**
     * Ordena en su sitio la lista recibida siguiendo el criterio de comparación
     * definido por esta implementación.
     *
     * @param lista lista a ordenar
     * @throws IllegalArgumentException si la lista es {@code null}
     */
    void ordenar(List<T> lista);

    /**
     * Devuelve el número total de ordenaciones realizadas mediante este comparador.
     *
     * @return número de llamadas al método {@link #ordenar(List)}
     */
    int consultarOrdenaciones();

    /**
     * Devuelve el número total de intercambios efectuados durante las
     * ordenaciones realizadas con este comparador.
     *
     * @return número de intercambios realizados
     */
    int consultarIntercambios();

    /**
     * Devuelve la fecha en la que este comparador fue creado.
     *
     * @return fecha de creación del comparador
     */
    Date consultarFecha();
}
