import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public TCPClient(Socket socket) {
        this.socket = socket;
    }

    public void sendThenReceive() {
        try {
            // Initialisation des flux de communication
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Scanner scanner = new Scanner(System.in);
            String userInput;

            // Boucle pour envoyer des messages au serveur
            while (true) {
                System.out.print("Enter a message to send to the server: ");
                userInput = scanner.nextLine();

                // Envoi du message au serveur
                out.println(userInput);

                // Réception de la réponse du serveur
                String serverResponse = in.readLine();
                System.out.println("Server response: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des flux et du socket à la fin de l'exécution
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", 1234);
        TCPClient tcpClient = new TCPClient(socket);
        tcpClient.sendThenReceive();
    }
}
