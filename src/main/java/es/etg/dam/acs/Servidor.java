package es.etg.dam.acs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Servidor implements Constantes {
    public static void main(String[] args) throws Exception {
        ServerSocket servidor = new ServerSocket(PUERTO);
        GestorMensajes gestor = new GestorMensajes();

        Socket[] clientes = new Socket[2];
        Caballo[] caballos = new Caballo[2];

        do {
            
            for (int i = 0; i < caballos.length; i++) {   
                Thread clienThread = new Thread(new Cliente(gestor));
                clienThread.start();
                Socket cliente = servidor.accept();
                clientes[i] = cliente;
                caballos[i] = new Caballo(gestor.recibir(cliente), 0);
            }

            Thread.sleep(2000);

            System.out.println("------------------------------------");

            int id = numRandom(0, caballos.length);
            
            do {
                repartirPuntos(caballos, id, clientes, gestor);

                id = numRandom(0, caballos.length);
            } while (!comprobarPuntos(caballos, id));

            Thread.sleep(2000);
            
            System.out.println("------------------------------------");

            comprobarGanador(caballos, clientes, gestor);
            
            for (Socket cliente : clientes) {
                cliente.close();
            }

            registar();

            servidor.close();

        } while (!servidor.isClosed());
    }

    private static boolean comprobarPuntos(Caballo[] caballos, int ids){
        return caballos[ids].getPuntos() >= 100;
    }

    private static int numRandom(int min, int max){
        Random rdm = new Random();
        return rdm.nextInt(min, max);
    }

    private static void repartirPuntos(Caballo[] caballos, int id, Socket[] clientes, GestorMensajes gMensajes) throws IOException{
        int puntos = numRandom(0, 20);
        caballos[id].setPuntos(puntos);
        gMensajes.enviar(clientes[id], caballos[id].toString());
    }

    private static void comprobarGanador(Caballo[] caballos, Socket[] clientes, GestorMensajes gMensajes) throws IOException{
        for (int i = 0; i < clientes.length; i++) {
            if (caballos[i].getPuntos() >= 100) {
                gMensajes.enviar(clientes[i], "Enhorabuena a " + caballos[i].getNombre());

            } else{
                gMensajes.enviar(clientes[i], "Otra vez será para " + caballos[i].getNombre());
            }
        }
    }


    private static void registar() throws Exception{
        Logger logger = Logger.getLogger("miLog");
        FileHandler fh = new FileHandler("loggeo.log", true);
        SimpleFormatter formatter = new SimpleFormatter();
        logger.addHandler(fh);
        fh.setFormatter(formatter);

        logger.log(Level.INFO, "UwU");
    } 
}
