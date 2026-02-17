package co.eci.protocols.exercices.RPC;

/**
 * Interfaz del servicio de calculadora para RPC.
 * Define operaciones matematicas basicas que pueden ser invocadas remotamente.
 */
public interface CalculatorService {
    /**
     * Suma dos numeros enteros.
     * @param a Primer operando
     * @param b Segundo operando
     * @return Resultado de la suma
     */
    int add(int a, int b);

    /**
     * Calcula el cuadrado de un numero.
     * @param n Numero a elevar al cuadrado
     * @return Cuadrado del numero
     */
    int square(int n);
}