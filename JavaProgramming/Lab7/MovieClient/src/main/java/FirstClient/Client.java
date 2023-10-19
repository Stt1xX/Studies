package FirstClient;

import Storage.Client.ClientStorage;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        Storage.Client.Client client = new Storage.Client.Client(new Scanner(System.in));
        client.goAction(new ClientStorage(), "localhost", 8081);

    }
}
