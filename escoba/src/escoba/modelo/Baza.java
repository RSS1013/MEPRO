package escoba.modelo;

import java.util.Arrays;
import java.util.Objects;

/**
 * *       RRRRR    SSSSS    SSSSS
 *         R    R  S        S
 *         RRRRR    SSSS     SSSS
 *         R  R          S        S
 *         R    R   SSSSS     SSSSS
 *
 * Representa una baza del juego de la Escoba.
 * <p>Una baza es el conjunto de cartas retiradas de la mesa junto con la carta
 * jugada desde la mano del jugador. Puede marcarse como {@code escoba} si al
 * realizarla la mesa queda vacía.</p>
 *
 * <p>Las bazas se utilizan para el recuento final de puntos, ya que en ellas se
 * contabilizan los oros, los sietes y el siete de oros.</p>
 *
 * @author Ricardo Sevilla Soba
 * @version 1.0
 * @since 2025-10-16
 */
public class Baza {

    /** Cartas que forman parte de la baza. */
    private Carta[] cartas;

    /** Indica si la baza fue conseguida con una escoba (mesa vacía). */
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
        Carta[] nuevo = Arrays.copyOf(cartas, cartas.length + 1);
        nuevo[nuevo.length - 1] = carta;
        cartas = nuevo;
    }

    /**
     * Genera un clon profundo de esta baza.
     * <p>Se crea un nuevo objeto {@code Baza} con una copia de las cartas y el
     * mismo estado de {@code fueEscoba}. Esto evita que los cambios en la copia
     * afecten al original.</p>
     *
     * @return una nueva {@code Baza} idéntica a la actual
     */
    public Baza clonar() {
        Baza clon = new Baza();
        clon.cartas = Arrays.copyOf(this.cartas, this.cartas.length);
        clon.fueEscoba = this.fueEscoba;
        return clon;
    }

    /**
     * Devuelve las cartas contenidas en esta baza.
     * <p>Se devuelve una copia del array para proteger la encapsulación
     * del objeto.</p>
     *
     * @return un nuevo array con las cartas de esta baza
     */
    public Carta[] consultarCartas() {
        return Arrays.copyOf(cartas, cartas.length);
    }

    /**
     * Cuenta el número de cartas del palo de oros en la baza.
     * <p>Este valor se utiliza en el recuento de puntos al final del juego.</p>
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
     * Cuenta el número de cartas con valor 7 en la baza.
     * <p>Los sietes son relevantes en la puntuación final del juego.</p>
     *
     * @return número de cartas con valor 7 en la baza
     */
    public int contarSietes() {
        int contador = 0;
        for (Carta c : cartas) {
            if (c.clave() == 7) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Comprueba si la baza contiene el {@code siete de oros}, conocido como
     * «guindis», que otorga un punto directo al jugador.
     *
     * @return {@code true} si contiene el siete de oros, {@code false} en caso contrario
     */
    public boolean tieneSieteOros() {
        for (Carta c : cartas) {
            if (c.palo() == Palo.OROS && c.clave() == 7) {
                return true;
            }
        }
        return false;
    }

    /**
     * Marca esta baza como una escoba, lo que significa que se consiguió
     * dejando la mesa vacía.
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
     * <p>Se basa en las cartas y en el estado {@code fueEscoba}, para garantizar
     * la consistencia con {@link #equals(Object)}.</p>
     *
     * @return el código hash correspondiente a esta baza
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(cartas);
        result = prime * result + Objects.hash(fueEscoba);
        return result;
    }

    /**
     * Compara esta baza con otro objeto para determinar si son iguales.
     * <p>Dos bazas son iguales si contienen las mismas cartas (en el mismo orden)
     * y comparten el mismo estado de {@code fueEscoba}.</p>
     *
     * @param obj el objeto a comparar con esta baza
     * @return {@code true} si ambas bazas son equivalentes, {@code false} en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Baza other = (Baza) obj;
        return Arrays.equals(cartas, other.cartas) && fueEscoba == other.fueEscoba;
    }

    /**
     * Devuelve una representación textual de esta baza, incluyendo las cartas
     * y si fue o no una escoba.
     *
     * @return una cadena de texto descriptiva de la baza
     */
    @Override
    public String toString() {
        return "Baza [cartas=" + Arrays.toString(cartas) + ", fueEscoba=" + fueEscoba + "]";
    }
}
