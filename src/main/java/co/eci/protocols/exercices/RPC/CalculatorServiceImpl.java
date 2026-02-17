package co.eci.protocols.exercices.RPC;

/**
 * Implementacion del servicio de calculadora para RPC.
 * Proporciona operaciones matematicas basicas.
 */
public class CalculatorServiceImpl implements CalculatorService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int square(int n) {
        return n * n;
    }
}