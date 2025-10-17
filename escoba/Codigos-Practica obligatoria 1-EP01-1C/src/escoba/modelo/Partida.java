package escoba.modelo;

/**
 * Partida de escoba entre dos jugadores.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
public class Partida {
	
	/** Jugadores de la partida. */
    private Jugador[] jugadores;
    
    /** Mesa de la partida. */
    private Mesa mesa;
    
    /** Baraja de la partida. */
    private Baraja baraja;
    
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
    public Partida(Jugador jugador1, Jugador jugador2, Mesa mesa, Baraja baraja) {
        jugadores = new Jugador[2];
        jugadores[0] = jugador1;
        jugadores[1] = jugador2;
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
        turno = turno.equals(jugadores[0]) ? jugadores[1] : jugadores[0];
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
    public Jugador[] obtenerJugadores() {
        return jugadores;
    }
}

