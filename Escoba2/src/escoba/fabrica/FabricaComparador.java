package escoba.fabrica;

import java.time.Instant;
import java.util.Date;

import escoba.modelo.Carta;
import escoba.modelo.comparador.Comparador;
import escoba.modelo.comparador.ComparadorDecrecientePorClave;
import escoba.modelo.comparador.ComparadorPorPaloPuntuacion;
import escoba.modelo.comparador.ComparadorPorPuntuacion;
import escoba.modelo.comparador.ComparadorSinOrden;

public final class FabricaComparador {

    private FabricaComparador() {
        // Evitar instanciación
    }

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
