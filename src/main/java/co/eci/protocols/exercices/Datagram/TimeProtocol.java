package co.eci.protocols.exercices.Datagram;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

/**
 * Constantes del "protocolo" UDP de hora
 */
public final class TimeProtocol {

    public static final int DEFAULT_PORT = 45000;
    public static final String DEFAULT_HOST = "127.0.0.1";

    public static final int BUFFER_SIZE = 256;
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    public static final String REQUEST_MESSAGE = "TIME";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static final int DEFAULT_INTERVAL_MS = 5000;
    public static final int DEFAULT_TIMEOUT_MS = 2000;

    private TimeProtocol() { }
}