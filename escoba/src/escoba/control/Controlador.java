package escoba.control;

import escoba.modelo.*;
import escoba.vista.VistaConsola;

/**
 * Controlamos el flujo completo de la partida del juego de la Escoba.
 * Actúa como conector entre el modelo ({@link escoba.modelo.Partida})
 * y la vista ({@link escoba.vista.VistaConsola}), gestionamos los turnos,
 * como es el reparto de las cartas, el procesamiento de las jugadas y la determinación
 * del ganador final.
 *
 * <p>El juego consta de 36 rondas (40 cartas menos las 4 iniciales puestas en la mesa).
 * Todas las interacciones con el usuario (mostrar estado, pedir carta, anunciar escoba, etc.)
 * se controlan desde la vista de consola.</p>
 *
 * @author Ricardo Sevilla Soba
 * @version 1.1
 * @since 2025-10-16
 */
public class Controlador {

    /** Estado general del juego: partida con baraja, jugadores y mesa. */
    private final Partida partida;

    /** Vista encargada de la interacción con el usuario. */
    private final VistaConsola vista;

    /** Último jugador que ganó una baza, necesario para asignar las cartas restantes sin Baza al final. */
    private Jugador ultimoGanador = null;

    /** Contador de rondas jugadas (0 a 35) 36 en total. */
    private int rondasJugadas = 0;

    /**
     * Creamos un controlador para gestionar una {@link Partida} y su {@link VistaConsola}.
     *
     * @param partida partida que contiene el estado general del juego, partida, jugadores y mesa
     * @param vista   vista que gestiona la entrada/salida por consola interacción con el jugador
     */
    public Controlador(Partida partida, VistaConsola vista) {
        this.partida = partida;
        this.vista = vista;
    }

    /**
     * Este metodo ejecuta la partida completa de nuestro juego.
     * <p>Inicializa el reparto de cartas, ejecuta las 36 rondas alternando turnos entre jugadores,
     * y al finalizar asigna las cartas restantes al último jugador que ha ganado una baza. Finalmente,
     * solicita a la vista que muestre el resultado de la partida.</p>
     */
    public void ejecutarPartida() {
        repartirCartasIniciales();

        while (rondasJugadas < 36) {
            Jugador jugador = partida.obtenerJugadorActual();
            Mesa mesa = partida.obtenerMesa();
            Baraja baraja = partida.obtenerBaraja();

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
     * <p>Estas cartas no cuentan como escoba. Si nadie ha ganado bazas (cosa complicada)o no hay cartas,
     * el método no realiza ninguna acción y muestra el resultado final.</p>
     */
    private void darCartasRestantes() {
        if (ultimoGanador == null) {
            return;
        }

        Mesa mesa = partida.obtenerMesa();
        Carta[] resto = mesa.consultarCartasEnMesa();

        if (resto.length == 0) {
            return;
        }

        Baza baza = new Baza();
        for (int i = 0; i < resto.length; i++) {
            baza.agregarCarta(resto[i]);
        }

        ultimoGanador.agregarBaza(baza);

        for (int i = 0; i < resto.length; i++) {
            mesa.quitarCarta(resto[i]);
        }
    }

    /**
     * Procesa la jugada del jugador actual, se gestiona tanto las bazas como las escobas.
     * <ul>
     *     <li>Si {@code cartasMesa} contiene cartas, se crea una baza con la carta jugada y las retiradas.</li>
     *     <li>Si la mesa queda vacía, la baza se cuenta como una escoba y se muestra por la vista.</li>
     *     <li>Si el jugador no recoge cartas o comete un error a la hora de sumar la baza, la carta se deja sobre la mesa.</li>
     * </ul>
     *
     * @param jugador    jugador que realiza la jugada
     * @param carta      carta elegida de su mano
     * @param cartasMesa cartas retiradas de la mesa (validadas por la vista)
     */
    private void procesarJugada(Jugador jugador, Carta carta, Carta[] cartasMesa) {
        Mesa mesa = partida.obtenerMesa();

        if (cartasMesa != null && cartasMesa.length > 0) {
            Baza baza = new Baza();
            baza.agregarCarta(carta);
            for (int i = 0; i < cartasMesa.length; i++) {
                baza.agregarCarta(cartasMesa[i]);
            }

            jugador.jugarCarta(carta);
            for (int i = 0; i < cartasMesa.length; i++) {
                mesa.quitarCarta(cartasMesa[i]);
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
     * Reparte tres cartas a cada jugador si los dos jugadores se han quedado sin cartas
     * y la baraja todavía tiene cartas disponibles.
     */
    private void repartirCartas() {
        Baraja baraja = partida.obtenerBaraja();
        Jugador[] jugadores = partida.obtenerJugadores();

        for (int i = 0; i < 3 && !baraja.estaVacia(); i++) {
            for (int j = 0; j < jugadores.length; j++) {
                if (!baraja.estaVacia()) {
                    Carta extraida = baraja.extraerCarta();
                    if (extraida != null) {
                        jugadores[j].recibirCarta(extraida);
                    }
                }
            }
        }
    }

    /**
     * Realiza el reparto inicial de cartas al comienzo de la partida.
     * <ul>
     *     <li>Se barajan las cartas.</li>
     *     <li>Se reparten tres cartas a cada jugador.</li>
     *     <li>Se colocan cuatro cartas sobre la mesa para comenzar la partida.</li>
     * </ul>
     */
    private void repartirCartasIniciales() {
        Baraja baraja = partida.obtenerBaraja();
        Mesa mesa = partida.obtenerMesa();
        Jugador[] jugadores = partida.obtenerJugadores();

        baraja.barajar();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < jugadores.length; j++) {
                Carta extraida = baraja.extraerCarta();
                if (extraida != null) {
                    jugadores[j].recibirCarta(extraida);
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
     * Comprueba si los dos jugadores se han quedado sin cartas en la mano.
     *
     * @return {@code true} si los dos jugadores no tienen cartas, {@code false} en caso contrario
     */
    private boolean estanAmbosSinCartas() {
        Jugador[] jugadores = partida.obtenerJugadores();
        return jugadores[0].estaSinCartas() && jugadores[1].estaSinCartas();
    }
}
