package modelTest;
import com.example.gameuno.model.JuegoModel;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class JuegoModelTest {
/**
 * Clase que se encarga de comprobar si las funciones y clases del modelo del juego funcionan
 */
//@Test
public static void main(String[] args) {
    JuegoModel juego = new JuegoModel(); //instancia la clase que genera las cartas
    ArrayList<JuegoModel.Carta> cartas = juego.CrearCartas();

    //Muestra en consola todas las cartas en orden generado
    for (JuegoModel.Carta c : cartas) {
        System.out.println("Carta con Color: " + c.getColor() + ", Tipo: "
                + c.getTipo() + c.getNumero() + ", Estado: " + c.getEstado());
    }

    juego.Barajar(cartas); //Funcion que baraja las cartas creadas

    //Muestra en consola el orden de las cartas segun el algortimo de baraja
    System.out.println("Después de barajar:");
    for (JuegoModel.Carta c : cartas) {
        System.out.println("Carta barajada con Color: " + c.getColor() + ", Tipo: "
                + c.getTipo() + c.getNumero() + ", Estado: " + c.getEstado());
    }
    juego.ContadorCartas(cartas); //Funcion que cuenta cuantas cartas hay en el arraylist
}
}