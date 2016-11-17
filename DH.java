package itsecurity;

import java.util.TreeMap;

public class DH {
  
  private final static int MY_PRIME = 19;
  private final static int MY_PRIMITIVE_ROOT = 2;
  
  private final static int EXPONENT_A = 16;
  private final static int EXPONENT_B = 13;
  

  public static void main(String[] args) {

    if (MY_PRIME<50) displayRootPowers(MY_PRIME, MY_PRIMITIVE_ROOT);
    displayExchange();
    
  }
  
  private static void displayRootPowers (int prime, int root) {
    
    TreeMap<Integer, Integer> rootPowers = new TreeMap<>(); // saves root to the power of i (key) mapped to the number modulo root (value)
    
    for (int i=1; i<prime; i++) {
      String tab = (Math.pow(root, i)<100000) ? "\t\t" : "\t";
      System.out.println(root+"^"+i+"\t= "+(int)Math.pow(root, i)+tab+" ≅ "+(int)(Math.pow(root, i)%prime)+" (mod "+prime+")");
      
      rootPowers.put((int)((Math.pow(root, i)%prime)), i);
    }
    
    System.out.println("\nSorted values:");
    
    for (int i=rootPowers.firstKey(); i<=rootPowers.lastKey(); i++) {
      int potence = rootPowers.get(i);
      System.out.println(root+"^"+potence+"\t ≅ "+(int)(Math.pow(root, potence)%prime)+" (mod "+prime+")");
    }
  }
  
  private static void displayExchange () {
    long ra = recursiveModuloPower(MY_PRIMITIVE_ROOT, EXPONENT_A, MY_PRIME);
    long rb = recursiveModuloPower(MY_PRIMITIVE_ROOT, EXPONENT_B, MY_PRIME);
    long keyA = recursiveModuloPower(rb, EXPONENT_A, MY_PRIME);
    long keyB = recursiveModuloPower(ra, EXPONENT_B, MY_PRIME);
    System.out.println("\nAlice sends to Bob: r^a mod p = "+MY_PRIMITIVE_ROOT+"^"+EXPONENT_A+" mod "+MY_PRIME+" = "+ra);
    System.out.println("\nBob sends to Alice: r^b mod p = "+MY_PRIMITIVE_ROOT+"^"+EXPONENT_B+" mod "+MY_PRIME+" = "+rb);
    System.out.println("\nAlice creates common key: (r^b mod p)^a mod p = ("+rb+")^"+EXPONENT_A+" mod "+MY_PRIME+" = "+keyA);
    System.out.println("\nBob creates common key: (r^a mod p)^b mod p = ("+ra+")^"+EXPONENT_B+" mod "+MY_PRIME+" = "+keyB);

  }
  
  private static long recursiveModuloPower(long base, long exponent, long modulo) {

    if (exponent <= 0)
      return 1;
    
    long C = ( recursiveModuloPower(base, (long) (exponent/2), modulo) * recursiveModuloPower(base, (long) (exponent/2), modulo) ) % modulo;

    if (exponent % 2 == 0) { // e is even
      return C;
    } else { // e is odd
      return (C * base) % modulo;
    }

  }

}
