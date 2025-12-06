package escoba.modelo.comparador;

import java.util.Date;
import java.util.List;

import escoba.modelo.Carta;

/**
 * Comparador que no aplica ningún criterio de ordenación.
 * <p>
 * Todas las cartas se consideran equivalentes, por lo que el método
 * {@link #comparar(Carta, Carta)} devuelve siempre {@code 0}. El método
 * {@link #ordenar(List)} simplemente registra la llamada sin modificar el
 * contenido de la lista.
 * </p>
 * <p>
 * Cada instancia almacena la fecha de creación y contabiliza el número de
 * ordenaciones solicitadas, aunque no se realicen intercambios.
 * </p>
 *
 * @author Ricardo Sevilla Soba
 * @version 2.0
 * @since 2025-11-24
 */
public final class ComparadorSinOrden implements Comparador<Carta> {

    /** Fecha de creación del comparador. */
    private final Date fecha;

    /** Número de veces que se ha invocado el método {@code ordenar}. */
    private int ordenaciones;

    /** Número total de intercambios realizados (siempre será 0). */
    private int intercambios;

    /**
     * Crea un comparador sin criterio de ordenación.
     *
     * @param fecha fecha de creación del comparador
     * @throws IllegalArgumentException si la fecha es {@code null}
     */
    public ComparadorSinOrden(final Date fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        this.fecha = new Date(fecha.getTime());
    }

    /**
     * Compara dos cartas considerándolas siempre iguales.
     *
     * @param o1 primera carta
     * @param o2 segunda carta
     * @return siempre {@code 0}
     */
    @Override
    public int comparar(final Carta o1, final Carta o2) {
        return 0;
    }

    /**
     * Registra la llamada a ordenación pero no modifica la lista.
     *
     * @param lista lista de cartas
     * @throws IllegalArgumentException si la lista es {@code null}
     */
    @Override
    public void ordenar(final List<Carta> lista) {
        if (lista == null) {
            throw new IllegalArgumentException("Lista nula");
        }
        ordenaciones++;
    }

    /**
     * Devuelve el número de veces que se ha solicitado ordenar.
     *
     * @return número de ordenaciones
     */
    @Override
    public int consultarOrdenaciones() {
        return ordenaciones;
    }

    /**
     * Devuelve el número de intercambios realizados.
     * <p>
     * Este comparador nunca realiza intercambios, por lo que el valor será
     * siempre {@code 0}.
     * </p>
     *
     * @return número de intercambios (0)
     */
    @Override
    public int consultarIntercambios() {
        return intercambios;
    }

    /**
     * Devuelve la fecha de creación de este comparador.
     *
     * @return fecha de creación (copia defensiva)
     */
    @Override
    public Date consultarFecha() {
        return new Date(fecha.getTime());
    }
}
