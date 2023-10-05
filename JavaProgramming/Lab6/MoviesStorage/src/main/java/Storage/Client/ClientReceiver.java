package Storage.Client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class ClientReceiver {
    int counterOfConnecting = 0;

    public String receive(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 1000L) {
                if (socketChannel.read(buffer) == -1) {
                    socketChannel.close();
                    throw new IOException();
                }
            }

            if (buffer.position() == 0) {
                throw new NotYetConnectedException();
            }


            buffer.flip();
            int sizeMessage = buffer.getInt();
            byte[] realMessage = new byte[buffer.remaining()];
            buffer.get(realMessage);
            buffer.flip();

            while (sizeMessage != realMessage.length) {

                if (socketChannel.read(buffer) == -1) {
                    socketChannel.close();
                    throw new IOException();
                }
                buffer.flip();

                byte[] pieceOfMessage = new byte[buffer.remaining()];
                buffer.get(pieceOfMessage);
                buffer.flip();

                byte[] sumOfArrays = new byte[realMessage.length + pieceOfMessage.length];

                for (int i = 0; i < realMessage.length; i++) {
                    sumOfArrays[i] = realMessage[i];
                }

                buffer.get(sumOfArrays, realMessage.length, pieceOfMessage.length);
                buffer.flip();

                realMessage = sumOfArrays;
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(realMessage);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (String) objectInputStream.readObject();
        } catch (NotYetConnectedException ex) {
            System.out.println("The server haven't given a response yet, we will try again in a few seconds");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counterOfConnecting++;
            if (counterOfConnecting > 5) {
                System.out.println("It looks like the server is unavailable. Try connect another time");
                System.exit(0);
            }
            receive(socketChannel);
            return null;
        }
    }
}
