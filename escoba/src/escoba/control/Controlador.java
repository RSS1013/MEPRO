package escoba.control;

import escoba.modelo.*;
import escoba.vista.VistaConsola;

public class Controlador {

    // --- Estado principal ---
    private final Partida partida;
    private final VistaConsola vista;
    private Jugador ultimoGanador = null;
    private int rondasJugadas = 0; // 0..35 (36 jugadas)

    // === Signatura requerida por los tests ===
    public Controlador(Partida partida, VistaConsola vista) {
        this.partida = partida;
        this.vista = vista;
    }

    // === Signatura requerida por los tests ===
    public void ejecutarPartida() {
        repartirCartasIniciales();

        while (rondasJugadas < 36) {
            final Jugador jugador = partida.obtenerJugadorActual();
            final Mesa mesa = partida.obtenerMesa();
            final Baraja baraja = partida.obtenerBaraja();

            // Tu VistaConsola incrementa la ronda internamente (ronda++), así que pasamos 0..35
            vista.mostrarEstado(mesa, jugador, baraja, rondasJugadas);

            // Entrada por consola usando tus métodos reales
            Carta cartaElegida = vista.pedirCarta(jugador);
            Carta[] cartasMesa = vista.pedirCartasMesa(mesa, cartaElegida);

            procesarJugada(jugador, cartaElegida, cartasMesa);
            rondasJugadas++;

            // Entre tandas: si ambos sin cartas y todavía queda baraja -> repartir
            if (estanAmbosSinCartas() && !baraja.estaVacia()) {
                repartirCartas();
            }

            // Alternar turno
            partida.cambiarTurno();
        }

        // Asignar cartas restantes (no cuenta como escoba)
        darCartasRestantes();

        // Resultado final
        vista.mostrarResultadoFinal(partida);
    }

    // === Signatura requerida por los tests ===
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

        // Quitar una a una (tu Mesa no tiene retirarCartas(Carta[]))
        for (Carta c : resto) {
            mesa.quitarCarta(c);
        }
    }

    // === Signatura requerida por los tests ===
    private void procesarJugada(Jugador jugador, Carta carta, Carta[] cartasMesa) {
        final Mesa mesa = partida.obtenerMesa();

        if (cartasMesa != null && cartasMesa.length > 0) {
            // Se completa una baza: carta en mano + cartas de la mesa
            Baza baza = new Baza();
            baza.agregarCarta(carta);
            for (Carta c : cartasMesa) {
                baza.agregarCarta(c);
            }

            // Actualizar estado del jugador y de la mesa
            jugador.jugarCarta(carta);
            for (Carta c : cartasMesa) {
                mesa.quitarCarta(c);
            }

            // --- Detectar y marcar escoba ANTES de agregar la baza ---
            if (mesa.estaVacia()) {
                baza.marcarEscoba();
                vista.anunciarEscoba(jugador.consultarNombre());
            }

            // Guardar la baza (Jugador.agregarBaza clona, así que ya conserva fueEscoba)
            jugador.agregarBaza(baza);

            ultimoGanador = jugador;
        } else {
            // No retira: deja la carta en mesa
            jugador.jugarCarta(carta);
            mesa.ponerCarta(carta);
        }
    }

    // === Signatura requerida por los tests ===
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

    // === Signatura requerida por los tests ===
    private void repartirCartasIniciales() {
        final Baraja baraja = partida.obtenerBaraja();
        final Mesa mesa = partida.obtenerMesa();
        final Jugador[] jugadores = partida.obtenerJugadores();

        // (Opcional) Barajar al inicio si procede:
        // baraja.barajar();

        // 3 cartas a cada jugador
        for (int i = 0; i < 3; i++) {
            for (Jugador j : jugadores) {
                Carta extraida = baraja.extraerCarta();
                if (extraida != null) {
                    j.recibirCarta(extraida);
                }
            }
        }

        // 4 cartas a la mesa
        for (int i = 0; i < 4; i++) {
            Carta extraida = baraja.extraerCarta();
            if (extraida != null) {
                mesa.ponerCarta(extraida);
            }
        }
    }

    // --- Auxiliar privado permitido ---
    private boolean estanAmbosSinCartas() {
        final Jugador[] jugadores = partida.obtenerJugadores();
        return jugadores[0].estaSinCartas() && jugadores[1].estaSinCartas();
    }
}
