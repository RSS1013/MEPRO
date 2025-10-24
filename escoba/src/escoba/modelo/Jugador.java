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
 * Representa a un jugador del juego de la Escoba.
 * <p>Gestiona su nombre, las cartas que tiene en la mano y las bazas ganadas
 * durante la partida. Proporciona métodos para consultar estadísticas del
 * juego, como el número de escobas, oros o sietes obtenidos.</p>
 *
 * <p>Los métodos que devuelven arrays siempre retornan copias para evitar
 * modificaciones externas del estado interno.</p>
 *
 * @author Ricardo Sevilla Soba
 * @version 1.0
 * @since 2025-10-16
 */
public class Jugador {

    /** Nombre del jugador (por ejemplo, "María" o "Juan"). */
    private String nombre;

    /** Cartas que el jugador tiene actualmente en la mano. */
    private Carta[] mano;

    /** Conjunto de bazas ganadas por el jugador. */
    private Baza[] bazas;

    /**
     * Crea un nuevo jugador con el nombre indicado.
     * La mano y las bazas comienzan vacías.
     *
     * @param nombre nombre del jugador
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new Carta[0];
        this.bazas = new Baza[0];
    }

    /**
     * Devuelve el nombre del jugador.
     *
     * @return el nombre del jugador
     */
    public String consultarNombre() {
        return nombre;
    }

    /**
     * Devuelve una copia de las cartas que el jugador tiene en la mano.
     * <p>Se devuelve una copia del array para mantener la encapsulación
     * y evitar modificaciones externas.</p>
     *
     * @return un nuevo array con las cartas de la mano
     */
    public Carta[] consultarMano() {
        return Arrays.copyOf(mano, mano.length);
    }

    /**
     * Devuelve las bazas ganadas por el jugador.
     * <p>Realiza una copia profunda: se copia el array y se clona
     * cada {@link Baza} individualmente.</p>
     *
     * @return un nuevo array con las bazas ganadas
     */
    public Baza[] consultarBazas() {
        Baza[] copia = new Baza[bazas.length];
        for (int i = 0; i < bazas.length; i++) {
            copia[i] = bazas[i].clonar();
        }
        return copia;
    }

    /**
     * Agrega una nueva baza ganada al historial del jugador.
     * <p>Se guarda una copia de la baza para evitar modificaciones
     * externas.</p>
     *
     * @param baza la baza ganada que se desea agregar
     */
    public void agregarBaza(Baza baza) {
        Baza[] nuevo = Arrays.copyOf(bazas, bazas.length + 1);
        nuevo[nuevo.length - 1] = baza.clonar();
        bazas = nuevo;
    }

    /**
     * Devuelve el número de bazas que fueron conseguidas con escoba.
     *
     * @return cantidad de escobas ganadas por el jugador
     */
    public int consultarEscobas() {
        int contador = 0;
        for (Baza b : bazas) {
            if (b.fueEscoba()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Cuenta el número total de cartas que ha ganado el jugador
     * en todas sus bazas.
     *
     * @return total de cartas ganadas
     */
    public int contarCartas() {
        int contador = 0;
        for (Baza b : bazas) {
            contador += b.consultarCartas().length;
        }
        return contador;
    }

    /**
     * Cuenta el número total de cartas del palo de oros
     * que ha ganado el jugador.
     *
     * @return número total de oros ganados
     */
    public int contarOros() {
        int contador = 0;
        for (Baza b : bazas) {
            contador += b.contarOros();
        }
        return contador;
    }

    /**
     * Cuenta el número total de cartas con valor 7
     * que ha ganado el jugador.
     *
     * @return número total de sietes ganados
     */
    public int contarSietes() {
        int contador = 0;
        for (Baza b : bazas) {
            contador += b.contarSietes();
        }
        return contador;
    }

    /**
     * Comprueba si el jugador ha conseguido el {@code siete de oros},
     * conocido como "guindis", que otorga un punto directo.
     *
     * @return {@code true} si el jugador tiene el siete de oros,
     *         {@code false} en caso contrario
     */
    public boolean tieneSieteOros() {
        for (Baza b : bazas) {
            if (b.tieneSieteOros()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indica si el jugador se ha quedado sin cartas en la mano.
     *
     * @return {@code true} si la mano está vacía, {@code false} en caso contrario
     */
    public boolean estaSinCartas() {
        return mano.length == 0;
    }

    /**
     * Añade una carta a la mano del jugador, por ejemplo durante el reparto.
     *
     * @param carta carta a añadir a la mano
     */
    public void recibirCarta(Carta carta) {
        Carta[] nuevo = Arrays.copyOf(mano, mano.length + 1);
        nuevo[nuevo.length - 1] = carta;
        mano = nuevo;
    }

    /**
     * Elimina de la mano la carta jugada por el jugador.
     *
     * @param carta carta que se ha jugado y debe retirarse de la mano
     */
    public void jugarCarta(Carta carta) {
        Carta[] nuevo = new Carta[mano.length - 1];
        int j = 0;
        for (Carta c : mano) {
            if (!c.equals(carta)) {
                nuevo[j++] = c;
            }
        }
        mano = nuevo;
    }

    /**
     * Calcula el código hash del jugador, basado en su nombre,
     * mano y bazas ganadas.
     *
     * @return el código hash correspondiente al jugador
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(bazas);
        result = prime * result + Arrays.hashCode(mano);
        result = prime * result + Objects.hash(nombre);
        return result;
    }

    /**
     * Compara este jugador con otro objeto para determinar si son iguales.
     * <p>Dos jugadores son iguales si tienen el mismo nombre,
     * las mismas cartas en mano y las mismas bazas ganadas.</p>
     *
     * @param obj el objeto a comparar
     * @return {@code true} si ambos jugadores son equivalentes,
     *         {@code false} en caso contrario
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
        Jugador other = (Jugador) obj;
        return Arrays.equals(bazas, other.bazas)
                && Arrays.equals(mano, other.mano)
                && Objects.equals(nombre, other.nombre);
    }

    /**
     * Devuelve una representación textual del jugador,
     * incluyendo su nombre, mano actual y bazas ganadas.
     *
     * @return una cadena descriptiva del jugador
     */
    @Override
    public String toString() {
        return "Jugador [nombre=" + nombre + ", mano=" + Arrays.toString(mano)
                + ", bazas=" + Arrays.toString(bazas) + "]";
    }
}
