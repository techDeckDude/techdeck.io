/**
 * 1) The current implementation can handle an unlimited number of connections. 
 * When a new client connection is accepted by the server, a new thread is created to handle the connection. 
 * This allows the server to handle multiple connections concurrently.
 * 2) However, keep in mind that creating a new thread for each client connection can be resource-intensive, 
 * especially if the server receives a large number of connections. In such cases, 
 * it may be more efficient to use a thread pool to manage a fixed number of threads 
 * and reuse them to handle client connections as they come in.
 * 
 * 3) With this code, the server uses a thread pool with a fixed number of threads to handle client connections. 
 * This allows the server to handle a large number of connections efficiently, 
 * while also limiting the resource usage of the server.
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
  private static final int PORT = 8080;
  private static final int NUM_THREADS = 10; // Number of threads in the thread pool

  private static volatile boolean running = true;

  public static void main(String[] args) {
    // Create a thread pool with NUM_THREADS threads
    ExecutorService threadPool = Executors.newFixedThreadPool(NUM_THREADS);

    // Add a shutdown hook to gracefully shut down the server
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Shutting down server...");
      running = false;
      threadPool.shutdown();
    }));

    try {
      // Create a server socket to listen for client connections
      ServerSocket serverSocket = new ServerSocket(PORT);

      // Run the server loop to listen for and accept client connections
      while (running) {
        // Accept a client connection
        Socket clientSocket = serverSocket.accept();

        // Submit a new task to the thread pool to handle the client connection
        threadPool.submit(new ClientHandler(clientSocket));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
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
