package Storage.Client;

import Storage.LightAbstractCommand.LightAbstractCommand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientSender {
    public void send(LightAbstractCommand command, SocketChannel socketChannel) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(command);

        ByteBuffer buffer = ByteBuffer.allocate(byteArrayOutputStream.size() + 4);
        buffer.putInt(byteArrayOutputStream.size());
        buffer.put(byteArrayOutputStream.toByteArray());

        buffer.flip();

        socketChannel.write(buffer);
    }

    public void authorizationSend(String str, SocketChannel socketChannel) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(str);

        ByteBuffer buffer = ByteBuffer.allocate(byteArrayOutputStream.size() + 4);
        buffer.putInt(byteArrayOutputStream.size());
        buffer.put(byteArrayOutputStream.toByteArray());

        buffer.flip();

        socketChannel.write(buffer);
    }
}
