package escoba.modelo;

import java.util.Arrays;
import java.util.Objects;

public class Baza {

    // Atributos
    private Carta[] cartas;
    private boolean fueEscoba;

    // Constructor
    public Baza() {
        this.cartas = new Carta[0];
        this.fueEscoba = false;
    }

    // Métodos públicos
    public void agregarCarta(Carta carta) {
        Carta[] nuevo = Arrays.copyOf(cartas, cartas.length + 1);
        nuevo[nuevo.length - 1] = carta;
        cartas = nuevo;
    }


    public Baza clonar() {
        Baza clon = new Baza();
        clon.cartas = Arrays.copyOf(this.cartas, this.cartas.length);
        clon.fueEscoba = this.fueEscoba;
        return clon;
    }

    public Carta[] consultarCartas() {
        return Arrays.copyOf(cartas, cartas.length);
    }

    public int contarOros() {
        int contador = 0;
        for (Carta c : cartas) {
            if (c.palo() == Palo.OROS) contador++;
        }
        return contador;
    }

    public int contarSietes() {
        int contador = 0;
        for (Carta c : cartas) {
            if (c.clave() == 7) contador++;
        }
        return contador;
    }

    public boolean tieneSieteOros() {
        for (Carta c : cartas) {
            if (c.palo() == Palo.OROS && c.clave() == 7) return true;
        }
        return false;
    }

    public void marcarEscoba() { this.fueEscoba = true; }

    public boolean fueEscoba() { return fueEscoba; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cartas);
		result = prime * result + Objects.hash(fueEscoba);
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
		Baza other = (Baza) obj;
		return Arrays.equals(cartas, other.cartas) && fueEscoba == other.fueEscoba;
	}

	@Override
	public String toString() {
		return "Baza [cartas=" + Arrays.toString(cartas) + ", fueEscoba=" + fueEscoba + "]";
	}

    // equals(), hashCode(), toString() → generados automáticamente en Eclipse
}
