import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BasicTCPServer {
    public static void main(String[] args) throws Exception {
        // Create a server socket to listen for client connections on port 9999
        ServerSocket serverSocket = new ServerSocket(9999);

        // Wait for a client to connect
        System.out.println("Wait for a client to connect");
        Socket clientSocket = serverSocket.accept();

        // Get input and output streams to communicate with the client
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Read a line of text from the client
        String inputLine = in.readLine();

        // Send a response back to the client
        out.println("Received: " + inputLine);

        // Close the client socket
        clientSocket.close();

        // Close the server socket
        serverSocket.close();
    }
}
