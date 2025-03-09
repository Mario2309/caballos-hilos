package es.etg.dam.acs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class GestorMensajes {

    public void enviar(Socket cliente, String mensaje) throws IOException{
        OutputStream aux = cliente.getOutputStream();
        DataOutputStream output = new DataOutputStream(aux);
        if (!mensaje.isEmpty()) {
            output.writeUTF(mensaje);
            output.flush();
        }
    }

    public String recibir(Socket cliente) throws IOException{
        InputStream aux = cliente.getInputStream();
        DataInputStream input = new DataInputStream(aux);
        return input.readUTF();
    }

}
