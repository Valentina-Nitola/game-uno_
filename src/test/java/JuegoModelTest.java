import java.util.ArrayList;

import com.example.gameuno.model.JuegoModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase que se encarga de comprobar si las funciones y clases del modelo del juego funcionan
 */
class JuegoModelTest {
    @Test
    void testCrearCartas() {
        JuegoModel juego = new JuegoModel();
        ArrayList<JuegoModel.Carta> mazo = juego.CrearCartas();

        // Verificamos que el mazo tenga el número correcto de cartas
        // 10 números por 4 colores = 40
        // 4 MasCuatro + 4 Comodin = 8
        // 4 MasDos + 4 Skip = 8
        int esperadas = 40 + 8 + 8;
        int contadorColorR = 0;
        int contadorColorG = 0;
        int contadorColorB = 0;
        int contadorColorY = 0;
        int contadorMas4 = 0;
        int contadorMas2 = 0;
        int contadorComodin = 0;
        int contadorSkip = 0;

        assertEquals(esperadas, mazo.size(), "El número de cartas generadas no es el esperado");

        //Si las cartas pasan la condicion, son validas y cuentan como esperadas
        for (JuegoModel.Carta carta : mazo) { //Recorre todas las cartas que hay en el mazo
            //por color y numero
            if (carta.getColor() == JuegoModel.Color._red && carta.getTipo() == JuegoModel.Tipo.numero)
                contadorColorR++;
            if (carta.getColor() == JuegoModel.Color._yellow && carta.getTipo() == JuegoModel.Tipo.numero)
                contadorColorY++;
            if (carta.getColor() == JuegoModel.Color._green && carta.getTipo() == JuegoModel.Tipo.numero)
                contadorColorG++;
            if (carta.getColor() == JuegoModel.Color._blue && carta.getTipo() == JuegoModel.Tipo.numero)
                contadorColorB++;

            //Por color y tipo Comodin
            if (carta.getColor() == JuegoModel.Color.negro && carta.getTipo() == JuegoModel.Tipo.mascuatro)
                contadorMas4++;
            if (carta.getColor() != JuegoModel.Color.negro && carta.getTipo() == JuegoModel.Tipo.masdos)
                contadorMas2++;
            if (carta.getColor() == JuegoModel.Color.negro && carta.getTipo() == JuegoModel.Tipo.comodin)
                contadorComodin++;
            if (carta.getColor() != JuegoModel.Color.negro && carta.getTipo() == JuegoModel.Tipo.skip)
                contadorSkip++;
        }
        assertEquals(10, contadorColorR, "No hay 10 cartas numéricas rojas");
        assertEquals(10, contadorColorG, "No hay 10 cartas numéricas Verdes");
        assertEquals(10, contadorColorY, "No hay 10 cartas numéricas Amarillas");
        assertEquals(10, contadorColorB, "No hay 10 cartas numéricas Azules");
        assertEquals(4, contadorMas4, "No hay 4 cartas MasCuatro negras");
        assertEquals(4, contadorMas2, "No hay 4 cartas MasDos de colores");
        assertEquals(4, contadorComodin, "No hay 4 cartas Comodin negras");
        assertEquals(4, contadorSkip, "No hay 4 cartas Skip de colores");
    }
}