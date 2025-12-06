package escoba.modelo.comparador;

import java.util.Date;
import java.util.List;

import escoba.modelo.Carta;
import escoba.modelo.Palo;

/**
 * Comparador que ordena cartas por palo y, en caso de empate, por puntuación.
 * <p>
 * Primero se compara el palo de las cartas según el orden definido en
 * {@link Palo}. Si los palos son iguales, se compara la puntuación y,
 * finalmente, la clave de la carta como criterio de desempate.
 * </p>
 * <p>
 * Cada instancia registra la fecha en la que fue creada, así como el número de
 * ordenaciones e intercambios realizados mediante sus métodos.
 * </p>
 *
 * @author Ricardo Sevilla Soba
 * @version 2.0
 * @since 2025-11-24
 */
public final class ComparadorPorPaloPuntuacion implements Comparador<Carta> {

    /** Fecha de creación del comparador. */
    private final Date fecha;

    /** Número de llamadas al método {@code ordenar}. */
    private int ordenaciones;

    /** Número total de intercambios realizados durante las ordenaciones. */
    private int intercambios;

    /**
     * Crea un comparador que ordena cartas por palo y puntuación.
     *
     * @param fecha fecha de creación del comparador
     * @throws IllegalArgumentException si la fecha es {@code null}
     */
    public ComparadorPorPaloPuntuacion(final Date fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        this.fecha = new Date(fecha.getTime());
    }

    /**
     * Compara dos cartas por palo, puntuación y clave.
     *
     * @param o1 primera carta
     * @param o2 segunda carta
     * @return valor negativo si {@code o1 < o2}, cero si son iguales,
     *         valor positivo si {@code o1 > o2}
     */
    @Override
    public int comparar(final Carta o1, final Carta o2) {
        Palo p1 = o1.palo();
        Palo p2 = o2.palo();

        int cmpPalo = Integer.compare(p1.ordinal(), p2.ordinal());
        if (cmpPalo != 0) {
            return cmpPalo;
        }

        int cmpPuntuacion = Integer.compare(o1.puntuacion(), o2.puntuacion());
        if (cmpPuntuacion != 0) {
            return cmpPuntuacion;
        }

        return Integer.compare(o1.clave(), o2.clave());
    }

    /**
     * Ordena en sitio la lista de cartas por palo y puntuación.
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
