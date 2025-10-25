package escoba.modelo;

/**
 *
 * Representa una baza del juego de la Escoba.
 * <p>Una baza es el conjunto de cartas retiradas (ganadas que han sumado 15) de la mesa junto con la carta
 * jugada desde la mano del jugador. Puede marcarse como {@code escoba} si al
 * realizarla la mesa queda vacía.</p>
 *
 * 
 * @author Ricardo Sevilla Soba
 * @version 1.3
 * @since 2025-10-16
 */
public class Baza {

    /** Cartas que forman parte de la baza. */
    private Carta[] cartas;

    /** Indica si la baza fue conseguida como una escoba y queda la mesa vacía. */
    private boolean fueEscoba;

    /**
     * Crea una nueva baza vacía, sin cartas y sin marcar como escoba.
     */
    public Baza() {
        this.cartas = new Carta[0];
        this.fueEscoba = false;
    }

    /**
     * Añade una carta a esta baza.
     *
     * @param carta la carta que se va a agregar a la baza
     */
    public void agregarCarta(Carta carta) {
        Carta[] nuevo = new Carta[cartas.length + 1];
        for (int i = 0; i < cartas.length; i++) {
            nuevo[i] = cartas[i];
        }
        nuevo[nuevo.length - 1] = carta;
        cartas = nuevo;
    }

    /**
     * Genera un clon profundo de esta baza.
     *
     * @return una nueva {@code Baza} idéntica a la actual
     */
    public Baza clonar() {
        Baza clon = new Baza();
        clon.cartas = new Carta[this.cartas.length];
        for (int i = 0; i < this.cartas.length; i++) {
            clon.cartas[i] = this.cartas[i];
        }
        clon.fueEscoba = this.fueEscoba;
        return clon;
    }

    /**
     * Devuelve las cartas contenidas en esta baza.
     *
     * @return un nuevo array con las cartas de esta baza
     */
    public Carta[] consultarCartas() {
        Carta[] copia = new Carta[cartas.length];
        for (int i = 0; i < cartas.length; i++) {
            copia[i] = cartas[i];
        }
        return copia;
    }

    /**
     * Cuenta el número de cartas del palo de oros en la baza.
     *
     * @return número de cartas del palo {@link Palo#OROS} en la baza
     */
    public int contarOros() {
        int contador = 0;
        for (int i = 0; i < cartas.length; i++) {
            Carta c = cartas[i];
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
        for (int i = 0; i < cartas.length; i++) {
            Carta c = cartas[i];
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
        for (int i = 0; i < cartas.length; i++) {
            Carta c = cartas[i];
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
     * Calcula el código hash de esta baza sin usar {@code java.util.Objects}.
     *
     * @return el código hash correspondiente a esta baza
     */
    @Override
    public int hashCode() {
        int resultado = 1;
        resultado = 31 * resultado + (fueEscoba ? 1 : 0);
        for (int i = 0; i < cartas.length; i++) {
            Carta c = cartas[i];
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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Baza otra = (Baza) obj;
        if (this.fueEscoba != otra.fueEscoba || this.cartas.length != otra.cartas.length) {
            return false;
        }
        for (int i = 0; i < cartas.length; i++) {
            if (!this.cartas[i].equals(otra.cartas[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Devuelve una representación textual de la baza.
     *
     * @return texto con las cartas y el estado de escoba
     */
    @Override
    public String toString() {
        String s = "Baza [";
        for (int i = 0; i < cartas.length; i++) {
            s += String.valueOf(cartas[i]);
            if (i < cartas.length - 1) {
                s += ", ";
            }
        }
        s += "] fueEscoba=" + fueEscoba;
        return s;
    }
}
