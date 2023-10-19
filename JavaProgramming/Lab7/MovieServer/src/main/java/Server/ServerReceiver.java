package Server;

import Storage.LightAbstractCommand.LightAbstractCommand;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ServerReceiver {
    public LightAbstractCommand receive(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        try {

            if (socketChannel.read(buffer) == -1) {
                throw new IOException();
            }

            buffer.flip();

            int sizeMessage = buffer.getInt();
            byte[] realMessage = new byte[buffer.remaining()];
            buffer.get(realMessage);
            buffer.flip();

            while (sizeMessage != realMessage.length) {
                if (socketChannel.read(buffer) == -1) {
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
            return (LightAbstractCommand) objectInputStream.readObject();

        } catch (IOException ex) {
            String nameConn = socketChannel.getRemoteAddress().toString();
            socketChannel.close();
            throw new IOException("Connection is lost from " + nameConn);
        }
    }

    public String authorizationReceive(SocketChannel socketChannel) throws IOException, ClassNotFoundException {

        try {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            if (socketChannel.read(buffer) == -1) {
                socketChannel.close();
                throw new IOException();
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

        } catch (IOException ex) {
            throw new IOException("Connection is lost from " + socketChannel.getRemoteAddress());
        }
    }
}
