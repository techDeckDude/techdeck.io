import java.io.*;
import java.net.*;
import java.util.Arrays;

public class BasicUdpClient {
    final int BUFFER_SIZE = 1024;
    int port;
    String host = null;
    String messageToServer = null;
    InetAddress address = null;
    DatagramSocket socket;
    
    /**
     * BasicUdpClient Constructor
     * @param messageToServer
     * @param host
     * @param port
     * @param messageBufferSize
     * @throws UnknownHostException
     * @throws SocketException
     */
    BasicUdpClient (String host, int port) throws UnknownHostException, SocketException {
        this.host = host;
        this.port = port;
        this.address = InetAddress.getByName(this.host);
        socket = new DatagramSocket(9999);
    }

    /**
     * Builds a new DatagramPacket containing the provided string
     * and send the packet using the underlying socket
     * @param messageToServer
     * @throws IOException
     */
    void sendMessage(String messageToServer) throws IOException {
        byte[] messagetToServerInBytes = messageToServer.getBytes();
        DatagramPacket sendingPacket = new DatagramPacket(messagetToServerInBytes, messagetToServerInBytes.length, address, port);
        socket.send(sendingPacket);
    }

    /**
     * Wait for a message from the server
     * @throws IOException
     */
    void receiveMessage() throws IOException {
        byte[] inputByteArray = new byte[BUFFER_SIZE];
        DatagramPacket receivingPacket = new DatagramPacket(inputByteArray, inputByteArray.length);
        socket.receive(receivingPacket);
        parseResponseDataBuffer(receivingPacket);
    }

    /*
     * Close the socket used to communicate with the host
     */
    void closeSocket() {
        socket.close();
    }

    /**
     * Extract the response from the packet
     * @param inputPacket
     */
    void parseResponseDataBuffer(DatagramPacket inputPacket) {
        String response = new String(inputPacket.getData(), 0, inputPacket.getLength());
        
        System.out.println("Stringified packet byte array: " + response);
        // System.out.println("packet length (bytes): "+inputPacket.getLength());
        // System.out.println("packet byte array: "+Arrays.toString(inputPacket.getData()));
    }

    public static void main(String[] args) throws IOException {
        // Create a message to send to the server
        String messageToServer = "shutDown";

        //instantiate a new upd client
        BasicUdpClient basicUdpClient = new BasicUdpClient("localhost", 8888);
        basicUdpClient.sendMessage(messageToServer);
        basicUdpClient.receiveMessage();
        basicUdpClient.closeSocket();
    }

    public static void main1(String[] args) throws IOException {
        
        // Create a datagram socket and bind it to a specific port (required for receving repsonses)
        DatagramSocket socket = new DatagramSocket(9999);

        // Create a message to send to the server
        String message = "Hello Server!";
        byte[] data = message.getBytes();

        // Create a datagram packet to send to the server
        InetAddress address = InetAddress.getByName("localhost");
        int port = 8888;
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

        // Send the packet to the server
        socket.send(packet);

        // Create a buffer to store the response from the server
        byte[] buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);

        // Wait for a response from the server
        socket.receive(packet);

        // Extract the response from the packet
        String response = new String(packet.getData(), 0, packet.getLength());
        
        System.out.println("MAX packet size: "+buffer.length);
        System.out.println("packet length (bytes): "+packet.getLength());
        System.out.println("packet byte array: "+Arrays.toString(packet.getData()));
        System.out.println("Stringified packet byte array: " + response);

        // Close the socket when you are finished
        socket.close();
    }
}
