package escoba.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Partida de escoba entre dos jugadores.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
public class Partida {

	/** Jugadores de la partida. */
    private final List<Jugador> jugadores;

    /** Mesa de la partida. */
    private final Mesa mesa;

    /** Baraja de la partida. */
    private final Baraja baraja;

    /** Jugador al que le toca jugar. */
    private Jugador turno;

    /**
     * Constructor de la partida.
     *
     * @param jugador1 primer jugador
     * @param jugador2 segundo jugador
     * @param mesa mesa
     * @param baraja baraja
     */
    public Partida(final Jugador jugador1, final Jugador jugador2, final Mesa mesa, final Baraja baraja) {
        jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        this.mesa = mesa;
        this.baraja = baraja;
        turno = jugador1;
    }

    /**
     * Obtiene la mesa.
     *
     * @return mesa
     */
    public Mesa obtenerMesa() {
        return mesa;
    }

    /**
     * Obtiene el jugador al que le toca jugar.
     *
     * @return jugador actual
     */
    public Jugador obtenerJugadorActual() {
        return turno;
    }

    /**
     * Cambia el turno al otro jugador.
     */
    public void cambiarTurno() {
        turno = turno.equals(jugadores.get(0)) ? jugadores.get(1) : jugadores.get(0);
    }

    /**
     * Obtiene la baraja.
     *
     * @return baraja
     */
    public Baraja obtenerBaraja() {
        return baraja;
    }

    /**
     * Obtiene los jugadores.
     *
     * @return jugadores
     */
    public List<Jugador> obtenerJugadores() {
        return jugadores;
    }
}

