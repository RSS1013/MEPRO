package escoba.control;

import escoba.modelo.*;
import escoba.vista.VistaConsola;

/**
 * *       RRRRR    SSSSS    SSSSS
 *         R    R  S        S
 *         RRRRR    SSSS     SSSS
 *         R  R          S        S
 *         R    R   SSSSS     SSSSS
 *
 * Controla el flujo completo de la partida del juego de la Escoba.
 * Actúa como mediador entre el modelo ({@link escoba.modelo.Partida})
 * y la vista ({@link escoba.vista.VistaConsola}), gestionando los turnos,
 * el reparto de cartas, el procesamiento de las jugadas y la determinación
 * del ganador final.
 *
 * <p>El juego consta de 36 rondas (40 cartas menos las 4 iniciales en mesa).
 * Todas las interacciones con el usuario (mostrar estado, pedir carta, anunciar escoba, etc.)
 * se delegan en la vista de consola.</p>
 *
 * @author Ricardo Sevilla Soba
 * @version 1.0
 * @since 2025-10-16
 */
public class Controlador {

    /** Estado general del juego: partida con baraja, jugadores y mesa. */
    private final Partida partida;

    /** Vista encargada de la interacción con el usuario. */
    private final VistaConsola vista;

    /** Último jugador que ganó una baza, necesario para asignar las cartas restantes al final. */
    private Jugador ultimoGanador = null;

    /** Contador de rondas jugadas (0 a 35). */
    private int rondasJugadas = 0;

    /**
     * Crea un controlador para gestionar una {@link Partida} y su {@link VistaConsola}.
     *
     * @param partida partida que contiene el estado general del juego
     * @param vista   vista que gestiona la entrada/salida por consola
     */
    public Controlador(Partida partida, VistaConsola vista) {
        this.partida = partida;
        this.vista = vista;
    }

    /**
     * Ejecuta la partida completa del juego de la Escoba.
     * <p>Inicializa el reparto de cartas, ejecuta las 36 rondas alternando turnos entre jugadores,
     * y al finalizar asigna las cartas restantes al último ganador. Finalmente,
     * solicita a la vista que muestre el resultado de la partida.</p>
     */
    public void ejecutarPartida() {
        repartirCartasIniciales();

        while (rondasJugadas < 36) {
            final Jugador jugador = partida.obtenerJugadorActual();
            final Mesa mesa = partida.obtenerMesa();
            final Baraja baraja = partida.obtenerBaraja();

            vista.mostrarEstado(mesa, jugador, baraja, rondasJugadas);

            Carta cartaElegida = vista.pedirCarta(jugador);
            Carta[] cartasMesa = vista.pedirCartasMesa(mesa, cartaElegida);

            procesarJugada(jugador, cartaElegida, cartasMesa);
            rondasJugadas++;

            if (estanAmbosSinCartas() && !baraja.estaVacia()) {
                repartirCartas();
            }

            partida.cambiarTurno();
        }

        darCartasRestantes();
        vista.mostrarResultadoFinal(partida);
    }

    /**
     * Asigna las cartas que queden sobre la mesa al jugador que ganó la última baza.
     * <p>Estas cartas no cuentan como escoba. Si nadie ha ganado bazas o no hay cartas,
     * el método no realiza ninguna acción.</p>
     */
    private void darCartasRestantes() {
        if (ultimoGanador == null) {
            return;
        }

        final Mesa mesa = partida.obtenerMesa();
        final Carta[] resto = mesa.consultarCartasEnMesa();

        if (resto.length == 0) {
            return;
        }

        Baza baza = new Baza();
        for (Carta c : resto) {
            baza.agregarCarta(c);
        }

        ultimoGanador.agregarBaza(baza);

        for (Carta c : resto) {
            mesa.quitarCarta(c);
        }
    }

    /**
     * Procesa la jugada del jugador actual, gestionando tanto las bazas como las escobas.
     * <ul>
     *     <li>Si {@code cartasMesa} contiene cartas, se crea una baza con la carta jugada y las retiradas.</li>
     *     <li>Si la mesa queda vacía, se marca la baza como escoba y se anuncia por la vista.</li>
     *     <li>Si el jugador no recoge cartas, la carta se deja sobre la mesa.</li>
     * </ul>
     *
     * @param jugador    jugador que realiza la jugada
     * @param carta      carta elegida de su mano
     * @param cartasMesa cartas retiradas de la mesa (validadas por la vista)
     */
    private void procesarJugada(Jugador jugador, Carta carta, Carta[] cartasMesa) {
        final Mesa mesa = partida.obtenerMesa();

        if (cartasMesa != null && cartasMesa.length > 0) {
            Baza baza = new Baza();
            baza.agregarCarta(carta);
            for (Carta c : cartasMesa) {
                baza.agregarCarta(c);
            }

            jugador.jugarCarta(carta);
            for (Carta c : cartasMesa) {
                mesa.quitarCarta(c);
            }

            if (mesa.estaVacia()) {
                baza.marcarEscoba();
                vista.anunciarEscoba(jugador.consultarNombre());
            }

            jugador.agregarBaza(baza);
            ultimoGanador = jugador;
        } else {
            jugador.jugarCarta(carta);
            mesa.ponerCarta(carta);
        }
    }

    /**
     * Reparte tres cartas a cada jugador si ambos se han quedado sin cartas
     * y la baraja aún tiene cartas disponibles.
     */
    private void repartirCartas() {
        final Baraja baraja = partida.obtenerBaraja();
        final Jugador[] jugadores = partida.obtenerJugadores();

        for (int i = 0; i < 3 && !baraja.estaVacia(); i++) {
            for (Jugador j : jugadores) {
                if (!baraja.estaVacia()) {
                    Carta extraida = baraja.extraerCarta();
                    if (extraida != null) {
                        j.recibirCarta(extraida);
                    }
                }
            }
        }
    }

    /**
     * Realiza el reparto inicial de cartas al comienzo de la partida.
     * <ul>
     *     <li>Baraja las cartas.</li>
     *     <li>Reparte tres cartas a cada jugador.</li>
     *     <li>Coloca cuatro cartas sobre la mesa.</li>
     * </ul>
     */
    private void repartirCartasIniciales() {
        final Baraja baraja = partida.obtenerBaraja();
        final Mesa mesa = partida.obtenerMesa();
        final Jugador[] jugadores = partida.obtenerJugadores();

        baraja.barajar();

        for (int i = 0; i < 3; i++) {
            for (Jugador j : jugadores) {
                Carta extraida = baraja.extraerCarta();
                if (extraida != null) {
                    j.recibirCarta(extraida);
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            Carta extraida = baraja.extraerCarta();
            if (extraida != null) {
                mesa.ponerCarta(extraida);
            }
        }
    }

    /**
     * Comprueba si ambos jugadores se han quedado sin cartas en la mano.
     *
     * @return {@code true} si los dos jugadores no tienen cartas, {@code false} en caso contrario
     */
    private boolean estanAmbosSinCartas() {
        final Jugador[] jugadores = partida.obtenerJugadores();
        return jugadores[0].estaSinCartas() && jugadores[1].estaSinCartas();
    }
}
