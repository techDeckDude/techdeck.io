import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionPool {
  // A queue to store the available connections
  private ConcurrentLinkedQueue<Socket> availableConnections;

  // A flag to indicate if the pool is closed
  private AtomicBoolean closed;

  public ConnectionPool() {
    availableConnections = new ConcurrentLinkedQueue<>();
    closed = new AtomicBoolean(false);
  }

  // Checkout a connection from the pool
  public Socket checkout() {
    // Return the first available connection if the pool is not closed
    if (!closed.get()) {
      Socket connection = availableConnections.poll();
      if (connection != null) {
        return connection;
      }
    }
    // Otherwise, return null
    return null;
  }

  // Return a connection to the pool
  public void checkin(Socket connection) {
    // Add the connection to the queue if the pool is not closed
    if (!closed.get()) {
      availableConnections.offer(connection);
    }
  }

  // Close the pool
  public void close() {
    closed.set(true);
    while (!availableConnections.isEmpty()) {
      Socket connection = availableConnections.poll();
      if (connection != null) {
        connection.close();
      }
    }
  }
}
