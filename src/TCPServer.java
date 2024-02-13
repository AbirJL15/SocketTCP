import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public TCPServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void acceptAndHandle() {
        try {
            // Attente de la connexion d'un client
            clientSocket = serverSocket.accept();

            // Initialisation des flux de communication
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;

            // Boucle pour gérer les messages du client
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Message from client: " + inputLine);

                // Réponse au client en renvoyant le même message
                out.println("Server says: " + inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des flux et du socket à la fin de l'exécution
            try {
                in.close();
                out.close();
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        TCPServer tcpServer = new TCPServer(serverSocket);
        tcpServer.acceptAndHandle();
    }
}
