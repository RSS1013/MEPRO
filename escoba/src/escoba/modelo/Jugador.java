package escoba.modelo;

import java.util.Arrays;
import java.util.Objects;

public class Jugador {

    // Atributos
    private String nombre;
    private Carta[] mano;
    private Baza[] bazas;

    // Constructor
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new Carta[0];
        this.bazas = new Baza[0];
    }

    // Métodos públicos
    public String consultarNombre() {
        return nombre;
    }

    public Carta[] consultarMano() {
        return Arrays.copyOf(mano, mano.length);
    }

    public Baza[] consultarBazas() {
        // Clon profundo de cada Baza
        Baza[] copia = new Baza[bazas.length];
        for (int i = 0; i < bazas.length; i++) {
            copia[i] = bazas[i].clonar();
        }
        return copia;
    }

    public void agregarBaza(Baza baza) {
        Baza[] nuevo = Arrays.copyOf(bazas, bazas.length + 1);
        nuevo[nuevo.length - 1] = baza.clonar();
        bazas = nuevo;
    }

    public int consultarEscobas() {
        int contador = 0;
        for (Baza b : bazas) {
            if (b.fueEscoba()) contador++;
        }
        return contador;
    }

    public int contarCartas() {
        int contador = 0;
        for (Baza b : bazas) contador += b.consultarCartas().length;
        return contador;
    }

    public int contarOros() {
        int contador = 0;
        for (Baza b : bazas) contador += b.contarOros();
        return contador;
    }

    public int contarSietes() {
        int contador = 0;
        for (Baza b : bazas) contador += b.contarSietes();
        return contador;
    }

    public boolean tieneSieteOros() {
        for (Baza b : bazas) {
            if (b.tieneSieteOros()) return true;
        }
        return false;
    }

    public boolean estaSinCartas() {
        return mano.length == 0;
    }

    public void recibirCarta(Carta carta) {
        Carta[] nuevo = Arrays.copyOf(mano, mano.length + 1);
        nuevo[nuevo.length - 1] = carta;
        mano = nuevo;
    }

    public void jugarCarta(Carta carta) {
        Carta[] nuevo = new Carta[mano.length - 1];
        int j = 0;
        for (Carta c : mano) {
            if (!c.equals(carta)) nuevo[j++] = c;
        }
        mano = nuevo;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bazas);
		result = prime * result + Arrays.hashCode(mano);
		result = prime * result + Objects.hash(nombre);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		return Arrays.equals(bazas, other.bazas) && Arrays.equals(mano, other.mano)
				&& Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Jugador [nombre=" + nombre + ", mano=" + Arrays.toString(mano) + ", bazas=" + Arrays.toString(bazas)
				+ "]";
	}

    // equals(), hashCode(), toString() → generados automáticamente
}
