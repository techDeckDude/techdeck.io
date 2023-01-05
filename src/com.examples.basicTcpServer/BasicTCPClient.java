import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BasicTCPClient {
    public static void main(String[] args) throws Exception {
        // Connect to the server on port 9999
        Socket socket = new Socket("localhost", 9999);
        socket.setReceiveBufferSize(1);

        // Get input and output streams to communicate with the server
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Send a message to the server
        out.println("shutdown");

        // Read a response from the server
        String response = in.readLine();
        System.out.println(response);

        // Close the socket
        socket.close();
    }
}
