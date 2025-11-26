package escoba.modelo.comparador;

import java.util.Date;
import java.util.List;

/**
 * Comparador genérico para cartas (u otros tipos).
 */
public interface Comparador<T> {

    /**
     * Compara dos elementos.
     *
     * @param primero primer elemento
     * @param segundo segundo elemento
     * @return valor negativo si primero < segundo, cero si son iguales,
     *         valor positivo si primero > segundo
     */
    int comparar(T primero, T segundo);

    /**
     * Ordena en sitio la lista recibida usando este comparador.
     *
     * @param lista lista a ordenar
     */
    void ordenar(List<T> lista);

    /**
     * Devuelve el número de ordenaciones realizadas con este comparador.
     *
     * @return número de llamadas a {@link #ordenar(List)}
     */
    int consultarOrdenaciones();

    /**
     * Devuelve el número de intercambios realizados en las ordenaciones.
     *
     * @return número de intercambios realizados
     */
    int consultarIntercambios();

    /**
     * Devuelve la fecha de creación del comparador.
     *
     * @return fecha de creación
     */
    Date consultarFecha();
}
