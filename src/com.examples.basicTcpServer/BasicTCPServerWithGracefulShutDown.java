import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BasicTCPServerWithGracefulShutDown {
    private static final int PORT = 8080;

  private static volatile boolean running = true;

  public static void main(String[] args) {
    // Add a shutdown hook to gracefully shut down the server when the user hits CTRL+C
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("Shutting down server...");
        stopServer();
      }
    }));

    try {
      // Create a server socket to listen for client connections
      ServerSocket serverSocket = new ServerSocket(PORT);

      // Run the server loop to listen for and accept client connections
      while (running) {
        // Accept a client connection
        Socket clientSocket = serverSocket.accept();

        // Start a new thread to handle the client connection
        Thread thread = new Thread(new ClientHandler(clientSocket));
        thread.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void stopServer() {
    running = false;
  }
}

class ClientHandler implements Runnable {
  private Socket clientSocket;

  public ClientHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  @Override
  public void run() {
    // TODO: Add code to handle the client connection here
  }
}
