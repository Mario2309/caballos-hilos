package es.etg.dam.acs;

import java.net.Socket;
import java.util.Scanner;

public class Cliente implements Runnable, Constantes {
    Scanner sc = new Scanner(System.in);

    private final GestorMensajes gestor;

    public Cliente(GestorMensajes gestorMensajes){
        this.gestor = gestorMensajes;
    }

    @Override
    public void run() {
        try {
            Socket cliente = new Socket(HOST, PUERTO);

            System.out.println(DETERMINAR_NOMBRE);
            gestor.enviar(cliente, sc.next());

            String msg = "";
            do {
                
                msg = gestor.recibir(cliente);
                System.out.println(msg);

                if (msg.toLowerCase().contains("enhorabuena") || msg.toLowerCase().contains("otra vez será")) {
                    cliente.close();
                }
                
                
            } while (!cliente.isClosed());


        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
