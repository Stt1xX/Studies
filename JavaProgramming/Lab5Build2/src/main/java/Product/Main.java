package Product;

import Product.Receivers.ReceiverStorage;

import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;


/**
 * It's the Main.java file contains Main class
 * I don't know what else to say  :)
 */
public class Main {
    public static void main(String[] args){
        TerminalInterpreter terminalInterpreter = new TerminalInterpreter(new Scanner(System.in));
        terminalInterpreter.printPoster();

        ReceiverStorage receiverStorage = new ReceiverStorage("CommandReceiversStorage");
        terminalInterpreter.goAction(receiverStorage);
    }
}