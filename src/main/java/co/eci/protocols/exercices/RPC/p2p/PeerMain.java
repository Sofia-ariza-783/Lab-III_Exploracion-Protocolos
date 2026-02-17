package co.eci.protocols.exercices.RPC.p2p;

/**
 * Clase principal para iniciar un nodo peer P2P.
 * Configura y lanza un nodo peer con los parametros proporcionados.
 */
public class PeerMain {

    /**
     * Metodo principal que inicia un nodo peer.
     * @param args Argumentos: peerId listenPort trackerHost
     * @throws Exception Si ocurre un error al iniciar el peer
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("Usage: PeerMain <peerId> <listenPort> <trackerHost>");
            System.out.println("Example: PeerMain peerA 7001 127.0.0.1");
            return;
        }
        String peerId = args[0];
        int listenPort = Integer.parseInt(args[1]);
        String trackerHost = args[2];
        TrackerClient tracker = new TrackerClient(trackerHost, 6000);
        new PeerNode(peerId, listenPort, tracker).start();
    }
}