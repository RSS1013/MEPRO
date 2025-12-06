package escoba.modelo;

import java.util.ArrayList;
import java.util.List;

import escoba.modelo.excepcion.CartaExistenteException;

/**
 *
 * Representa una baza del juego de la Escoba.
 * <p>Una baza es el conjunto de cartas retiradas (ganadas que han sumado 15) de la mesa junto con la carta
 * jugada desde la mano del jugador. Puede marcarse como {@code escoba} si al
 * realizarla la mesa queda vacía.</p>
 *
 *
 * @author Ricardo Sevilla Soba
 * @version 2.0
 * @since 2025-11-24
 */
public class Baza {

    /** Cartas que forman parte de la baza. */
    private final List<Carta> cartas;

    /** Indica si la baza fue conseguida como una escoba y queda la mesa vacía. */
    private boolean fueEscoba;

    /**
     * Crea una nueva baza vacía, sin cartas y sin marcar como escoba.
     */
    public Baza() {
        this.cartas = new ArrayList<>();
        this.fueEscoba = false;
    }

    /**
     * Añade una carta a esta baza.
     *
     * @param carta la carta que se va a agregar a la baza
     * @throws IllegalArgumentException si la carta es {@code null}
     * @throws CartaExistenteException si la carta ya existe en la baza
     */
    public void agregarCarta(final Carta carta) throws CartaExistenteException {
        if (carta == null) {
            throw new IllegalArgumentException("La carta no puede ser nula");
        }
        if (cartas.contains(carta)) {
            throw new CartaExistenteException("La carta ya existe en la baza");
        }
        cartas.add(carta);
    }

    /**
     * Genera un clon profundo de esta baza.
     *
     * @return una nueva {@code Baza} idéntica a la actual
     */
    public Baza clonar() {
        Baza clon = new Baza();
        for (Carta c : this.cartas) {
            clon.cartas.add(c);
        }
        clon.fueEscoba = this.fueEscoba;
        return clon;
    }

    /**
     * Devuelve las cartas contenidas en esta baza.
     *
     * @return una nueva lista con las cartas de esta baza
     */
    public List<Carta> consultarCartas() {
        return new ArrayList<>(cartas);
    }

    /**
     * Cuenta el número de cartas del palo de oros en la baza.
     *
     * @return número de cartas del palo {@link Palo#OROS} en la baza
     */
    public int contarOros() {
        int contador = 0;
        for (Carta c : cartas) {
            if (c.palo() == Palo.OROS) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Cuenta el número de cartas con puntuación 7 en la baza.
     *
     * @return número de cartas cuya puntuación es 7
     */
    public int contarSietes() {
        int contador = 0;
        for (Carta c : cartas) {
            if (c.puntuacion() == 7) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Comprueba si la baza contiene el siete de oros.
     *
     * @return {@code true} si contiene el siete de oros, {@code false} en caso contrario
     */
    public boolean tieneSieteOros() {
        for (Carta c : cartas) {
            if (c.palo() == Palo.OROS && c.puntuacion() == 7) {
                return true;
            }
        }
        return false;
    }

    /**
     * Marca esta baza como una escoba.
     */
    public void marcarEscoba() {
        this.fueEscoba = true;
    }

    /**
     * Indica si esta baza fue conseguida con una escoba.
     *
     * @return {@code true} si la baza fue una escoba, {@code false} en caso contrario
     */
    public boolean fueEscoba() {
        return fueEscoba;
    }

    /**
     * Calcula el código hash de esta baza.
     *
     * @return el código hash correspondiente a esta baza
     */
    @Override
    public int hashCode() {
        int resultado = 1;
        resultado = 31 * resultado + (fueEscoba ? 1 : 0);
        for (Carta c : cartas) {
            resultado = 31 * resultado + (c != null ? c.hashCode() : 0);
        }
        return resultado;
    }

    /**
     * Compara esta baza con otro objeto para determinar si son iguales.
     *
     * @param obj el objeto a comparar
     * @return {@code true} si ambas bazas son equivalentes, {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        boolean iguales = true;

        if (obj == null || getClass() != obj.getClass()) {
            iguales = false;
        } else {
            Baza otra = (Baza) obj;

            if (fueEscoba != otra.fueEscoba || cartas.size() != otra.cartas.size()) {
                iguales = false;
            }

            for (int i = 0; iguales && i < cartas.size(); i++) {
                if (!cartas.get(i).equals(otra.cartas.get(i))) {
                    iguales = false;
                }
            }
        }

        return iguales;
    }

    /**
     * Devuelve una representación en texto de la baza.
     *
     * @return texto con las cartas y el estado de escoba
     */
    @Override
    public String toString() {
        String s = "Baza [";
        for (int i = 0; i < cartas.size(); i++) {
            s += String.valueOf(cartas.get(i));
            if (i < cartas.size() - 1) {
                s += ", ";
            }
        }
        s += "] fueEscoba=" + fueEscoba;
        return s;
    }
}
