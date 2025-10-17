package escoba.vista.util;

import escoba.modelo.Carta;

/**
 * Clase de utilidad para manejar arrays de cartas.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
public class ManejadorArray {
	
	/**
	 * Constructor privado en clase de utilidades.
	 */
	private ManejadorArray() {		
	}
	
	/**
	 * Compacta un array elimando nulos al final del mismo
	 * redimensionando un nuevo array sin nulos.
	 * 
	 * @param cartas cartas
	 * @return array compactado
	 */
	public static Carta[] compactar(Carta[] cartas) {
		int posicion = 0;
		boolean localizadoHueco = false;
		for (int i = 0; i < cartas.length && !localizadoHueco; i++) {
			if (cartas[i]!=null) {
				posicion++;				
			}
			else {
				localizadoHueco = true;
			}
		}
		Carta[] cartasNuevas = new Carta[posicion];
		for (int i = 0; i < posicion; i++) {
			cartasNuevas[i] = cartas[i];
		}
		return cartasNuevas;
	}
	
	/**
	 * Comprueba si está contenida la carta.
	 * 
	 * @param cartaABuscar carta a buscar
	 * @param array array de cartas
	 * @return true si está contenida
	 */
	public static boolean contiene(Carta cartaABuscar, Carta[] array) {
		for (Carta carta : array) {
			if (carta != null && carta.equals(cartaABuscar)) {
				return true;
			}
		}
		return false;
	}
}
