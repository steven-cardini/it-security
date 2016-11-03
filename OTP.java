package itsecurity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class OTP {

  private final static SecureRandom random = new SecureRandom();
  private final static Scanner in = new Scanner(System.in);

  private static byte[] inputBytes;
  private static byte[] key;
  private static byte[] encrypted;

  public static void main(String[] args) throws IOException {
    
    System.out.println("Please input plain text or path to txt file:");
    String userInput = "";
    
    if (in.hasNextLine()) userInput = in.nextLine();
    
    processUserInput(userInput);
    
    // command loop
    
    displayTextHelp();
    boolean exit = false;

    while (!exit) {

      switch (in.nextLine()) {
      case "e": // encrypt (OTP procedure)
        for (int i = 0; i < inputBytes.length; i++)
          encrypted[i] = (byte) (encrypted[i] ^ key[i]);
        break;
      case "sp": // show plain text
        String plain = new String(inputBytes);
        System.out.println(plain);
        break;
      case "sk": // show key
        for (byte b : key)
          System.out.print("|" + b);
        System.out.println("|");
        break;
      case "se": // show encrypted text
        String enc = new String(encrypted);
        System.out.println(enc);
        break;
      case "n": // new user input
        System.out.print("enter text: ");
        if (in.hasNextLine()) userInput = in.nextLine();
        processUserInput(userInput);
        break;
      case "nk": // new key
        key = generateKey();
        break;
      case "help":
        displayTextHelp();
        break;
      case "exit":
        exit = true;
        break;
      default:
        System.out.println("command unknown");
      }

    }



  }

  private static byte[] getTxtBytes(String txtFile) throws IOException {
    String str = new String(Files.readAllBytes(Paths.get(txtFile)));
    return str.getBytes();
  }

  private static void processUserInput(String input) throws IOException {
    int i = 0;
    
    if ((i = input.lastIndexOf('.')) > 0) { // it is a file
      String extension = input.substring(i + 1);
      switch (extension) {
      case "txt":
        inputBytes = getTxtBytes(input);
        break;
      default:
        System.out.println("file type not supported!");
        return;
      }
    } else { // it is text
      inputBytes = input.getBytes();
    }
    
    key = generateKey();
    encrypted = Arrays.copyOf(inputBytes, inputBytes.length); // deep copy of input bytes
  }

  private static byte[] generateKey() {
    byte key[] = new byte[inputBytes.length];
    random.nextBytes(key);
    return key;
  }

  private static void displayTextHelp() {
    System.out.println("---------------");
    System.out.println("Program commands:");
    System.out.println("e - encrypt (OTP procedure)");
    System.out.println("sp - show plain text");
    System.out.println("sk - show key");
    System.out.println("se - show encrypted text");
    System.out.println("n - new input data (plain text or txt file path)");
    System.out.println("nk - generate new key");
    System.out.println("help - display commands");
    System.out.println("exit - exit program");
    System.out.println("---------------");
  }

}
