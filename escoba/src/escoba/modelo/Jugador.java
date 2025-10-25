package escoba.modelo;

/**
 * Representa a un jugador del juego de la Escoba.
 * <p>Gestiona su nombre, la mano de cartas y las bazas ganadas.
 * Salvo error los arrays se manipulan sin utilizar clases del paquete {@code java.util}.</p>
 *
 * @author Ricardo Sevilla Soba
 * @version 1.3
 * @since 2025-10-16
 */
public class Jugador {

    /** Nombre del jugador. */
    private String nombre;

    /** Cartas que el jugador tiene actualmente en su mano. */
    private Carta[] mano;

    /** Conjunto de bazas ganadas por el jugador. */
    private Baza[] bazas;

    /**
     * Crea un nuevo jugador con el nombre indicado.
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
     * @return nombre del jugador
     */
    public String consultarNombre() {
        return nombre;
    }

    /**
     * Devuelve una copia de las cartas que el jugador tiene en la mano.
     *
     *
     * @return copia de la mano
     */
    public Carta[] consultarMano() {
        Carta[] copia = new Carta[mano.length];
        for (int i = 0; i < mano.length; i++) {
            copia[i] = mano[i];
        }
        return copia;
    }

    /**
     * Devuelve una copia profunda de las bazas ganadas por el jugador.
     *<p> Con clonar, no utilizamos ni clone ni cloneable.
     *
     * @return copia de las bazas
     */
    public Baza[] consultarBazas() {
        Baza[] copia = new Baza[bazas.length];
        for (int i = 0; i < bazas.length; i++) {
            copia[i] = bazas[i].clonar();
        }
        return copia;
    }

    /**
     * Añade una nueva baza ganada al jugador.
     *
     * @param baza baza que se va a agregar
     */
    public void agregarBaza(Baza baza) {
        Baza[] nuevo = new Baza[bazas.length + 1];
        for (int i = 0; i < bazas.length; i++) {
            nuevo[i] = bazas[i];
        }
        nuevo[nuevo.length - 1] = baza.clonar();
        bazas = nuevo;
    }

    /**
     * Cuenta las escobas realizadas por el jugador.
     *
     * @return número total de escobas, se muestran al final de la partida
     */
    public int consultarEscobas() {
        int contador = 0;
        for (int i = 0; i < bazas.length; i++) {
            if (bazas[i].fueEscoba()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Cuenta el total de cartas ganadas, no de los puntos, si no de la cantidad total.
     *
     * @return número total de cartas
     */
    public int contarCartas() {
        int contador = 0;
        for (int i = 0; i < bazas.length; i++) {
            contador += bazas[i].consultarCartas().length;
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
        for (int i = 0; i < bazas.length; i++) {
            contador += bazas[i].contarOros();
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
        for (int i = 0; i < bazas.length; i++) {
            contador += bazas[i].contarSietes();
        }
        return contador;
    }

    /**
     * Nos informa si el jugador tiene el siete de oros.
     *
     * @return {@code true} si posee el siete de oros
     */
    public boolean tieneSieteOros() {
        for (int i = 0; i < bazas.length; i++) {
            if (bazas[i].tieneSieteOros()) {
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
        return mano.length == 0;
    }

    /**
     * Añade una carta a la mano del jugador.
     *
     * @param carta carta a añadir
     */
    public void recibirCarta(Carta carta) {
        Carta[] nuevo = new Carta[mano.length + 1];
        for (int i = 0; i < mano.length; i++) {
            nuevo[i] = mano[i];
        }
        nuevo[nuevo.length - 1] = carta;
        mano = nuevo;
    }

    /**
     * Elimina la carta jugada de la mano, el jugador seleeciona una para hacer baza o dejar en la mesa.
     *
     * @param carta carta que se ha jugado
     */
    public void jugarCarta(Carta carta) {
        Carta[] nuevo = new Carta[mano.length - 1];
        int j = 0;
        for (int i = 0; i < mano.length; i++) {
            if (!mano[i].equals(carta)) {
                nuevo[j] = mano[i];
                j++;
            }
        }
        mano = nuevo;
    }

    /**
     * Calcula el código hash del jugador sin usar {@code java.util}.
     *
     * @return código hash del jugador
     */
    @Override
    public int hashCode() {
        int resultado = 1;
        resultado = 31 * resultado + (nombre != null ? nombre.hashCode() : 0);
        for (int i = 0; i < mano.length; i++) {
            if (mano[i] != null) {
                resultado = 31 * resultado + mano[i].hashCode();
            }
        }
        for (int i = 0; i < bazas.length; i++) {
            if (bazas[i] != null) {
                resultado = 31 * resultado + bazas[i].hashCode();
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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
            
        }
        Jugador otro = (Jugador) obj;

        if (nombre == null) {
            if (otro.nombre != null) {
                return false;
            }
            
        } else if (!nombre.equals(otro.nombre)) {
            return false;
        }

        if (mano.length != otro.mano.length || bazas.length != otro.bazas.length) {
            return false;
        }

        for (int i = 0; i < mano.length; i++) {
            if (!mano[i].equals(otro.mano[i])) {
                return false;
            }
        }
        
        for (int i = 0; i < bazas.length; i++) {
            if (!bazas[i].equals(otro.bazas[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Devuelve una representación textual del jugador.
     *
     * @return texto descriptivo del jugador
     */
    @Override
    public String toString() {
        String s = "Jugador [nombre=" + nombre + ", mano=[";
        for (int i = 0; i < mano.length; i++) {
            s += String.valueOf(mano[i]);
            if (i < mano.length - 1) {
                s += ", ";
            }
        }
        s += "], bazas=[";
        for (int i = 0; i < bazas.length; i++) {
            s += String.valueOf(bazas[i]);
            if (i < bazas.length - 1) {
                s += ", ";
            }
        }
        s += "]]";
        return s;
    }
}
