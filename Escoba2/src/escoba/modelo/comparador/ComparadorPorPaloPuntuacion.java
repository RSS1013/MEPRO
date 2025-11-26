package escoba.modelo.comparador;

import java.util.Date;
import java.util.List;

import escoba.modelo.Carta;
import escoba.modelo.Palo;

public final class ComparadorPorPaloPuntuacion implements Comparador<Carta> {

    private final Date fecha;
    private int ordenaciones;
    private int intercambios;

    public ComparadorPorPaloPuntuacion(final Date fecha) {
        this.fecha = new Date(fecha.getTime());
    }

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
