// Importaciones necesarias

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {

    /**
     * Método que permite leer un mensaje, para ello recibe el flujo de entrada
     *
     * @param entrada flujo de entrada
     */
    public static void leerMensaje(BufferedReader entrada) {
        //crea un nuevo hilo, inicia el hilo de ejecución y constantemente se ejecutará el método run()
        new Thread(new Runnable() {
            //
            @Override
            public void run() {
                //codigo para leer el primer flujo de entrada del socket que pretende obtener el nombre
                try {
                    System.out.print(entrada.readLine());
                } catch (IOException e) {
                }

                while (true) {
                    try {
                        //mostrar la respuesta del servidor en la consola
                        System.out.println(entrada.readLine());

                    } catch (IOException e) {
                        System.out.println("Ha ocurrido un error en la lectura del flujo de entrada...");
                        System.exit(1);
                    }
                }
            }
        }).start();
    }

    /**
     * Método que permite enviar un mensaje a través del flujo de salida
     *
     * @param consola donde se va a escribir
     * @param salida  flujo de salida
     */
    public static void escribirMensaje(Scanner consola, PrintWriter salida) {
        //variable donde se guardara el mensaje escrito en consola
        String lectura;

        while (true) {
            //se lee la consola y lo escrito se guarda en lectura
            lectura = consola.nextLine();

            // a traves del flujo de salida enviamos la lectura de la consola
            salida.println(lectura);
        }
    }

    //método main
    public static void main(String[] args) {

        // numero de puerto del servidor
        int puerto = 1300;

        // arreglo que contiene la ip del servidor
        byte[] ipArray = {(byte) 192, (byte) 168, 1, 70};

        //objeto de tipo InetAddress, permite representar una ip
        InetAddress ip = null;

        //socket que permitirá la conexión con un servidor
        Socket socket = null;

        //flujo de entrada, permite recibir informacion entrante que le llega al socket
        BufferedReader flujoEntrada = null;

        //flujo de salida, permite enviar informacion por medio del socket
        PrintWriter flujoSalida = null;

        //manejar la posible excepcion que arroja el metodo getByAddress
        try {
            // obteniendo un objeto de tipo InetByAddress, es decir una representacion de una ipv4
            ip = InetAddress.getByAddress(ipArray);
        } catch (UnknownHostException e) {
            System.out.println("Ha ocurrido un error al intentar obtener la ip del servidor...");
            System.exit(1);
        }

        //manejar la posble excepcion que arroja el constructor Socket
        try {
            // creando un socket a partir de la direccion ip y el puerto
            socket = new Socket(ip, puerto);

        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al intentar conectarse al socket servidor...");
            System.exit(1);
        }

        //manejar las posibles excepciones que arrojan los metodos Input y Output Stream
        try {
            // objeto que permite leer la respuesta del servidor
            flujoEntrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            //objeto que permite enviar algo al servidor
            flujoSalida = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        //objeto Scanner para leer la consola
        Scanner consola = new Scanner(System.in);

        //llamada a metodo que lee mensajes
        leerMensaje(flujoEntrada);

        //llamada a metodo que envia mensajes
        escribirMensaje(consola, flujoSalida);

    }
}
