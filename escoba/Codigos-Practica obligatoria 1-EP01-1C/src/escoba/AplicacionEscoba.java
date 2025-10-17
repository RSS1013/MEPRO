package escoba;

import escoba.control.Controlador;
import escoba.modelo.Baraja;
import escoba.modelo.Jugador;
import escoba.modelo.Mesa;
import escoba.modelo.Partida;
import escoba.vista.VistaConsola;

/**
 * Clase raíz.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 * @since JDK 24.0.2
 */
public class AplicacionEscoba {
	
	/**
	 * Constructor privado para impedir instanciaciones.
	 */
	private AplicacionEscoba() {		
	}
	
	/**
	 * Métod principal.
	 * 
	 * @param args argumentos en línea de comandos (sin uso)
	 */
    public static void main(String[] args) {
        Jugador jugador1 = new Jugador("Juan");
        Jugador jugador2 = new Jugador("María");
        Mesa mesa = new Mesa();
        Baraja baraja = new Baraja();

        Partida partida = new Partida(jugador1, jugador2, mesa, baraja);
        VistaConsola vista = new VistaConsola();
        Controlador controlador = new Controlador(partida, vista);

        controlador.ejecutarPartida();
    }
}
