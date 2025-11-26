package escoba.modelo.comparador;

import java.util.Date;
import java.util.List;

import escoba.modelo.Carta;

public final class ComparadorSinOrden implements Comparador<Carta> {

    private final Date fecha;
    private int ordenaciones;
    private int intercambios;

    public ComparadorSinOrden(final Date fecha) {
        this.fecha = new Date(fecha.getTime());
    }

    @Override
    public int comparar(final Carta o1, final Carta o2) {
        return 0;
    }

    @Override
    public void ordenar(final List<Carta> lista) {
        if (lista == null) {
            throw new IllegalArgumentException("Lista nula");
        }
        ordenaciones++;
    }

    @Override
    public int consultarOrdenaciones() {
        return ordenaciones;
    }

    @Override
    public int consultarIntercambios() {
        return intercambios;
    }

    @Override
    public Date consultarFecha() {
        return new Date(fecha.getTime());
    }
}
