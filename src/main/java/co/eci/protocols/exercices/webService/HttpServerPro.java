package co.eci.protocols.exercices.webService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServerPro {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
        String inputLine, outputLine;
        String body =
                "<!DOCTYPE html>"
                        + "<html lang=\"en\">"
                        + "<head>"
                        + "  <meta charset=\"UTF-8\">"
                        + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                        + "  <title>My Web Site</title>"
                        + "  <style>"
                        + "    body{margin:0;font-family:Arial,Helvetica,sans-serif;background:#0b1220;color:#e7eefc;}"
                        + "    .wrap{max-width:980px;margin:0 auto;padding:28px;}"
                        + "    header{display:flex;align-items:center;justify-content:space-between;gap:16px;margin-bottom:18px;}"
                        + "    .brand{display:flex;align-items:center;gap:12px;}"
                        + "    .brand img{width:44px;height:44px;border-radius:10px;object-fit:cover;}"
                        + "    h1{font-size:22px;margin:0;}"
                        + "    .subtitle{opacity:.85;margin:6px 0 0 0;font-size:14px;}"
                        + "    .hero{background:linear-gradient(135deg,#1f2a44,#0f172a);border:1px solid rgba(255,255,255,.08);"
                        + "          border-radius:16px;padding:22px;margin:16px 0;}"
                        + "    .grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:14px;margin-top:16px;}"
                        + "    .card{display:flex;align-items:center;gap:12px;padding:14px;border-radius:14px;"
                        + "          background:rgba(255,255,255,.04);border:1px solid rgba(255,255,255,.08);"
                        + "          text-decoration:none;color:inherit;transition:transform .12s ease, background .12s ease;}"
                        + "    .card:hover{transform:translateY(-2px);background:rgba(255,255,255,.06);}"
                        + "    .card img{width:44px;height:44px;border-radius:12px;object-fit:cover;flex:0 0 auto;}"
                        + "    .card .title{font-weight:700;margin:0;font-size:15px;}"
                        + "    .card .desc{margin:4px 0 0 0;opacity:.85;font-size:13px;line-height:1.2;}"
                        + "    footer{margin-top:18px;opacity:.7;font-size:12px;}"
                        + "    .pill{display:inline-block;padding:6px 10px;border-radius:999px;background:rgba(255,255,255,.06);"
                        + "          border:1px solid rgba(255,255,255,.08);font-size:12px;}"
                        + "  </style>"
                        + "</head>"
                        + "<body>"
                        + "  <div class=\"wrap\">"
                        + "    <header>"
                        + "      <div class=\"brand\">"
                        + "        <img src=\"https://images.unsplash.com/photo-1527430253228-e93688616381?auto=format&fit=crop&w=96&q=60\" alt=\"Logo\">"
                        + "        <div>"
                        + "          <h1>My Web Site</h1>"
                        + "          <p class=\"subtitle\">A simple Java HTTP server page with image links.</p>"
                        + "        </div>"
                        + "      </div>"
                        + "      <span class=\"pill\">Status: Online</span>"
                        + "    </header>"
                        + ""
                        + "    <section class=\"hero\">"
                        + "      <h2 style=\"margin:0 0 8px 0;font-size:18px;\">Welcome</h2>"
                        + "      <p style=\"margin:0;opacity:.9;line-height:1.4;\">"
                        + "        Choose a destination below. Each link is a clickable card with an image."
                        + "      </p>"
                        + ""
                        + "      <div class=\"grid\">"
                        + "        <a class=\"card\" href=\"/hello\">"
                        + "          <img src=\"https://images.unsplash.com/photo-1555066931-4365d14bab8c?auto=format&fit=crop&w=96&q=60\" alt=\"Hello\">"
                        + "          <div>"
                        + "            <p class=\"title\">Hello</p>"
                        + "            <p class=\"desc\">Test endpoint (e.g., /hello).</p>"
                        + "          </div>"
                        + "        </a>"
                        + ""
                        + "        <a class=\"card\" href=\"/about\">"
                        + "          <img src=\"https://images.unsplash.com/photo-1522071820081-009f0129c71c?auto=format&fit=crop&w=96&q=60\" alt=\"About\">"
                        + "          <div>"
                        + "            <p class=\"title\">About</p>"
                        + "            <p class=\"desc\">Learn what this server does.</p>"
                        + "          </div>"
                        + "        </a>"
                        + ""
                        + "        <a class=\"card\" href=\"https://github.com/\" target=\"_blank\" rel=\"noreferrer\">"
                        + "          <img src=\"https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png\" alt=\"GitHub\">"
                        + "          <div>"
                        + "            <p class=\"title\">GitHub</p>"
                        + "            <p class=\"desc\">Open an external link in a new tab.</p>"
                        + "          </div>"
                        + "        </a>"
                        + "      </div>"
                        + "    </section>"
                        + ""
                        + "    <footer>"
                        + "      <div>Tip: If you prefer local images, serve them from something like <code>/assets/logo.png</code>.</div>"
                        + "    </footer>"
                        + "  </div>"
                        + "</body>"
                        + "</html>";

        String response =
                "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html; charset=UTF-8\r\n"
                        + "Content-Length: " + body.getBytes("UTF-8").length + "\r\n"
                        + "Connection: close\r\n"
                        + "\r\n"
                        + body;


        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);

        }

        out.print(response);
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}