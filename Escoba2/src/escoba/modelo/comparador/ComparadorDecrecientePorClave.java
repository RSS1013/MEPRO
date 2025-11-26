package escoba.modelo.comparador;

import java.util.Date;
import java.util.List;

import escoba.modelo.Carta;

public final class ComparadorDecrecientePorClave implements Comparador<Carta> {

    private final Date fecha;
    private int ordenaciones;
    private int intercambios;

    public ComparadorDecrecientePorClave(final Date fecha) {
        this.fecha = new Date(fecha.getTime());
    }

    @Override
    public int comparar(final Carta o1, final Carta o2) {
        return Integer.compare(o2.clave(), o1.clave());
    }

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
