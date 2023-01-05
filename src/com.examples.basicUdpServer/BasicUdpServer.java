import java.io.*;
import java.net.*;

public class BasicUdpServer {
    static final int BUFFER_SIZE = 1024;
    public static void main(String[] args) throws IOException {
        // Create a datagram socket and bind it to a specific port
        DatagramSocket socket = new DatagramSocket(8888);
        System.out.println("Listening on port: 8888");

        // Create a buffer to hold the incoming datagrams (data)
        byte[] buffer = new byte[BUFFER_SIZE];
        boolean shutDown = false;

        
        do {
            // wait for an incoming datagram from the socket
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println(message);
            
            if(message.equals("shutDown")) {
                shutDown = true;   
            }

            // Send a response back to the client
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            byte[] response = "ACK".getBytes();
            packet = new DatagramPacket(response, response.length, address, port);
            socket.send(packet);

        }
        while(!shutDown);


        // Close the socket when you are finished
        socket.close();
    }

    public static void main1(String[] args) throws IOException {
        // Create a datagram socket and bind it to a specific port
        DatagramSocket socket = new DatagramSocket(8888);
        System.out.println("Listening on port: 8888");

        // Create a buffer to hold the incoming datagrams (data)
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        // wait for an incoming datagram from the socket
        socket.receive(packet);

        // Get the data from the datagram and print it to the console
        String message = new String(packet.getData(), 0, packet.getLength());
        System.out.println(message);

        // Send a response back to the client
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        byte[] response = "Hello Client!".getBytes();
        packet = new DatagramPacket(response, response.length, address, port);
        socket.send(packet);

        // Close the socket when you are finished
        socket.close();
    }
}
