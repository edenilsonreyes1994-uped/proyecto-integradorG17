package semana17.ejercicios;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import semana17.web.RepositorioSocios;
import semana17.web.ServidorCooperativa;

public class ServidorTotal {
    public static void main(String[] args) throws IOException {
        RepositorioSocios repo = new RepositorioSocios();
        HttpServer servidor = HttpServer.create(new InetSocketAddress(8080), 0);

        servidor.createContext("/total", ex -> {
            String json = "{\"socios\": 3, \"total\": " + repo.saldoTotal() + "}";
            ServidorCooperativa.responder(ex, 200, json);
        });

        servidor.setExecutor(Executors.newFixedThreadPool(4));
        servidor.start();
        System.out.println("Servidor en http://localhost:8080/total");
    }
}