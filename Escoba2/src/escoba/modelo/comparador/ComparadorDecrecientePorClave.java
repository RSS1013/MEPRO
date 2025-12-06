package escoba.modelo.comparador;

import java.util.Date;
import java.util.List;

import escoba.modelo.Carta;

/**
 * Comparador que ordena cartas en orden decreciente según su clave.
 * <p>
 * Cada instancia registra la fecha en la que fue creada, así como el número de
 * ordenaciones e intercambios realizados mediante sus métodos.
 * </p>
 *
 * @author Ricardo Sevilla Soba
 * @version 2.0
 * @since 2025-11-24
 */
public final class ComparadorDecrecientePorClave implements Comparador<Carta> {

    /** Fecha de creación del comparador. */
    private final Date fecha;

    /** Número de llamadas al método {@code ordenar}. */
    private int ordenaciones;

    /** Número total de intercambios realizados durante las ordenaciones. */
    private int intercambios;

    /**
     * Crea un comparador que ordena cartas en orden decreciente por clave.
     *
     * @param fecha fecha de creación del comparador
     * @throws IllegalArgumentException si la fecha es {@code null}
     */
    public ComparadorDecrecientePorClave(final Date fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        this.fecha = new Date(fecha.getTime());
    }

    /**
     * Compara dos cartas según su clave en orden decreciente.
     *
     * @param o1 primera carta
     * @param o2 segunda carta
     * @return valor negativo si {@code o1 > o2}, cero si son iguales,
     *         valor positivo si {@code o1 < o2}
     */
    @Override
    public int comparar(final Carta o1, final Carta o2) {
        return Integer.compare(o2.clave(), o1.clave());
    }

    /**
     * Ordena en sitio la lista de cartas según el criterio decreciente por clave.
     *
     * @param lista lista de cartas a ordenar
     * @throws IllegalArgumentException si la lista es {@code null}
     */
    @Override
    public void ordenar(final List<Carta> lista) {
        if (lista == null) {
            throw new IllegalArgumentException("Lista nula");
        }
        ordenaciones++;

        boolean cambiado;
        int n = lista.size();

        do {
            cambiado = false;
            for (int i = 0; i < n - 1; i++) {
                if (comparar(lista.get(i), lista.get(i + 1)) > 0) {
                    Carta tmp = lista.get(i);
                    lista.set(i, lista.get(i + 1));
                    lista.set(i + 1, tmp);
                    intercambios++;
                    cambiado = true;
                }
            }
            n--;
        } while (cambiado);
    }

    /**
     * Devuelve el número de ordenaciones realizadas por este comparador.
     *
     * @return número de ordenaciones
     */
    @Override
    public int consultarOrdenaciones() {
        return ordenaciones;
    }

    /**
     * Devuelve el número total de intercambios realizados durante las ordenaciones.
     *
     * @return número de intercambios
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
