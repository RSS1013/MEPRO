package escoba.modelo;

import java.util.ArrayList;
import java.util.List;

import escoba.modelo.comparador.Comparador;
import escoba.modelo.excepcion.CartaExistenteException;
import escoba.modelo.excepcion.CartaNoEncontradaException;

/**
 * Representa a un jugador del juego de la Escoba.
 * <p>
 * Gestiona su nombre, la mano de cartas y las bazas ganadas a lo largo de la
 * partida.
 * </p>
 *
 * @author Ricardo Sevilla Soba
 * @version 2.0
 * @since 2025-11-24
 */
public class Jugador {

    /** Nombre del jugador. */
    private final String nombre;

    /** Cartas que el jugador tiene actualmente en su mano. */
    private final List<Carta> mano;

    /** Conjunto de bazas ganadas por el jugador. */
    private final List<Baza> bazas;

    /** Comparador de cartas asociado al jugador (puede ser {@code null}). */
    private final Comparador<Carta> comparador;

    /**
     * Crea un nuevo jugador con el nombre indicado y un comparador asociado.
     *
     * @param nombre            nombre del jugador
     * @param comparadorCartas  comparador de cartas asociado al jugador
     * @throws IllegalArgumentException si el nombre o el comparador son nulos
     */
    public Jugador(final String nombre, final Comparador<Carta> comparadorCartas) {
        if (nombre == null || comparadorCartas == null) {
            throw new IllegalArgumentException("Nombre o comparador nulos");
        }
        this.nombre = nombre;
        this.comparador = comparadorCartas;
        this.mano = new ArrayList<>();
        this.bazas = new ArrayList<>();
    }

    /**
     * Crea un nuevo jugador con el nombre indicado.
     *
     * @param nombre nombre del jugador
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.bazas = new ArrayList<>();
        this.comparador = null;
    }

    /**
     * Devuelve el nombre del jugador.
     *
     * @return nombre del jugador
     */
    public String consultarNombre() {
        return nombre;
    }

    /**
     * Devuelve una copia de las cartas que el jugador tiene en la mano.
     *
     * @return copia de la mano
     */
    public List<Carta> consultarMano() {
        return new ArrayList<>(mano);
    }

    /**
     * Devuelve una copia profunda de las bazas ganadas por el jugador.
     *
     * @return copia de las bazas
     */
    public List<Baza> consultarBazas() {
        List<Baza> copia = new ArrayList<>();
        for (Baza b : bazas) {
            copia.add(b.clonar());
        }
        return copia;
    }

    /**
     * Añade una nueva baza ganada al jugador.
     *
     * @param baza baza que se va a agregar
     * @throws IllegalArgumentException si la baza es {@code null}
     */
    public void agregarBaza(Baza baza) {
        if (baza == null) {
            throw new IllegalArgumentException("La baza no puede ser nula");
        }
        bazas.add(baza.clonar());
    }

    /**
     * Cuenta las escobas realizadas por el jugador.
     *
     * @return número total de escobas, se muestran al final de la partida
     */
    public int consultarEscobas() {
        int contador = 0;
        for (Baza element : bazas) {
            if (element.fueEscoba()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Cuenta el total de cartas ganadas, no los puntos, sino la cantidad total.
     *
     * @return número total de cartas
     */
    public int contarCartas() {
        int contador = 0;
        for (Baza element : bazas) {
            contador += element.consultarCartas().size();
        }
        return contador;
    }

    /**
     * Cuenta el número total de cartas del palo de oros ganadas.
     *
     * @return número de oros
     */
    public int contarOros() {
        int contador = 0;
        for (Baza element : bazas) {
            contador += element.contarOros();
        }
        return contador;
    }

    /**
     * Cuenta el número de cartas con puntuación 7 ganadas.
     *
     * @return número de sietes
     */
    public int contarSietes() {
        int contador = 0;
        for (Baza element : bazas) {
            contador += element.contarSietes();
        }
        return contador;
    }

    /**
     * Informa si el jugador tiene el siete de oros.
     *
     * @return {@code true} si posee el siete de oros
     */
    public boolean tieneSieteOros() {
        for (Baza element : bazas) {
            if (element.tieneSieteOros()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Comprueba si el jugador no tiene ninguna carta en la mano.
     *
     * @return {@code true} si la mano está vacía
     */
    public boolean estaSinCartas() {
        return mano.isEmpty();
    }

    /**
     * Añade una carta a la mano del jugador.
     *
     * @param carta carta a añadir
     * @throws IllegalArgumentException si la carta es {@code null}
     * @throws CartaExistenteException  si la carta ya está en la mano
     */
    public void recibirCarta(Carta carta) throws CartaExistenteException {
        if (carta == null) {
            throw new IllegalArgumentException("La carta no puede ser nula");
        }
        if (mano.contains(carta)) {
            throw new CartaExistenteException("La carta ya está en la mano");
        }
        mano.add(carta);
    }

    /**
     * Elimina la carta jugada de la mano. El jugador selecciona una para hacer
     * baza o dejarla en la mesa.
     *
     * @param carta carta que se ha jugado
     * @throws IllegalArgumentException   si la carta es {@code null}
     * @throws CartaNoEncontradaException si la carta no está en la mano
     */
    public void jugarCarta(Carta carta) throws CartaNoEncontradaException {
        if (carta == null) {
            throw new IllegalArgumentException("La carta no puede ser nula");
        }
        if (!mano.contains(carta)) {
            throw new CartaNoEncontradaException("La carta no está en la mano");
        }
        mano.remove(carta);
    }

    /**
     * Calcula el código hash del jugador.
     *
     * @return código hash del jugador
     */
    @Override
    public int hashCode() {
        int resultado = 1;
        resultado = 31 * resultado + (nombre != null ? nombre.hashCode() : 0);
        for (Carta element : mano) {
            if (element != null) {
                resultado = 31 * resultado + element.hashCode();
            }
        }
        for (Baza element : bazas) {
            if (element != null) {
                resultado = 31 * resultado + element.hashCode();
            }
        }
        return resultado;
    }

    /**
     * Compara este jugador con otro objeto.
     *
     * @param obj objeto a comparar
     * @return {@code true} si ambos jugadores son iguales
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
            Jugador otro = (Jugador) obj;

            if (nombre == null) {
                if (otro.nombre != null) {
                    iguales = false;
                }
            } else if (!nombre.equals(otro.nombre)) {
                iguales = false;
            }

            if (iguales && (mano.size() != otro.mano.size() || bazas.size() != otro.bazas.size())) {
                iguales = false;
            }

            for (int i = 0; iguales && i < mano.size(); i++) {
                if (!mano.get(i).equals(otro.mano.get(i))) {
                    iguales = false;
                }
            }

            for (int i = 0; iguales && i < bazas.size(); i++) {
                if (!bazas.get(i).equals(otro.bazas.get(i))) {
                    iguales = false;
                }
            }
        }

        return iguales;
    }

    /**
     * Devuelve una representación textual del jugador.
     *
     * @return texto descriptivo del jugador
     */
    @Override
    public String toString() {
        String s = "Jugador [nombre=" + nombre + ", mano=[";
        for (int i = 0; i < mano.size(); i++) {
            s += String.valueOf(mano.get(i));
            if (i < mano.size() - 1) {
                s += ", ";
            }
        }
        s += "], bazas=[";
        for (int i = 0; i < bazas.size(); i++) {
            s += String.valueOf(bazas.get(i));
            if (i < bazas.size() - 1) {
                s += ", ";
            }
        }
        s += "]]";
        return s;
    }
}
