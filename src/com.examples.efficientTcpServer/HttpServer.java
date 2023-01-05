import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
  // Create a connection pool
  private static ConnectionPool pool = new ConnectionPool();

  public static void main(String[] args) throws Exception {
    // Create a ServerSocket
    ServerSocket serverSocket = new ServerSocket(8080);

    while (true) {
      // Accept a connection from a client
      Socket clientSocket = serverSocket.accept();

      // Checkout a connection from the pool
      Socket connection = pool.checkout();
      if (connection == null) {
        // Create a new connection if none is available in the pool
        connection = clientSocket;
      } else {
        // Use the available connection if one is available in the pool
        connection.setInputStream(clientSocket.getInputStream());
        connection.setOutputStream(clientSocket.getOutputStream());
      }

      // Get the input and output streams of the connection
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      PrintWriter out = new PrintWriter(connection.getOutputStream(), true);

      // Read the request from the client
      String request = in.readLine();
      System.out.println("Received request: " + request);

      // Send the response to the client
      out.println("HTTP/1.1 200 OK");
      out.println("Content-Type: text/plain");
      out.println("Connection: keep-alive");
      out.println();
      out.println("Hello, World!");
      out.flush();

      // Check the "Connection" request header to determine if the connection should be kept open
      String connectionHeader = "";
      while ((request = in.readLine()) != null) {
        if (request.length() == 0) {
          break;
        }
        if (request.startsWith("Connection:")) {
          connectionHeader = request;
        }
      }
      if (connectionHeader.equals("Connection: keep-alive")) {
        // Return the connection to the pool if the client wants to use a persistent connection
        pool.checkin(connection);
      } else {
        // Close the connection if the client does not want to use a persistent connection
        connection.close();
      }
    }
  }
}
