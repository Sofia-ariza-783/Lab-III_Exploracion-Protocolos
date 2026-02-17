package co.eci.protocols.exercices.RPC;

/**
 * Cliente principal para demostrar el uso del servicio RPC.
 * Conecta con el servidor y ejecuta operaciones de calculadora.
 */
public class RpcClientMain {

    /**
     * Metodo principal que inicia el cliente RPC.
     * Crea un stub y ejecuta operaciones de ejemplo.
     * @param args Argumentos de linea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        CalculatorService calc = new CalculatorClientStub("127.0.0.1", 5000);
        System.out.println("add(2,3) = " + calc.add(2, 3));
        System.out.println("square(9) = " + calc.square(9));
    }
}