package Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerResponder {
    public void respond(String message, SocketChannel socketChannel) throws IOException {
//        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        ByteBuffer buffer = ByteBuffer.allocate(byteArrayOutputStream.size() + 4);
        buffer.putInt(byteArrayOutputStream.size());
        buffer.put(byteArrayOutputStream.toByteArray());
        buffer.flip();

        if (socketChannel.write(buffer) == -1) {
            socketChannel.close();
            throw new IOException();
        }
    }

    public void authorizationRespond(byte b, SocketChannel socketChannel) throws IOException{

        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{b});
        if (socketChannel.write(buffer)== -1){
            socketChannel.close();
            throw new IOException();
        }
    }
}
