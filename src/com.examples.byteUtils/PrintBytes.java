import java.nio.ByteBuffer;

public class PrintBytes {
    public static void main(String[] args) {
        int value = 12345;
        //value = 10;

        // Allocate a ByteBuffer with enough space to hold the integer value
        ByteBuffer buffer = ByteBuffer.allocate(4);

        // Put the integer value into the buffer
        buffer.putInt(value);

        // Flip the buffer to set the position to 0
        buffer.flip();

        // Get the raw bytes of the integer value
        byte[] bytes = buffer.array();

        // Print the raw bytes
        for (byte b : bytes) {
            // System.out.print(b + " ");
            //System.out.print(Integer.toBinaryString(b & 0xff)+" ");
            System.out.print(Integer.toBinaryString(b)+" ");
        }
    }
}
