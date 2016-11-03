package itsecurity;

import java.util.Scanner;

public class RSA {

  private long _p, _q, _n, _d, _e;
  private static final Scanner in = new Scanner(System.in);

  public RSA(long p, long q, long e, long d) {
    _p = p;
    _q = q;
    _e = e;
    _d = d;
    _n = p * q;
  }

  public long encrypt(long plainText) {
    return recursiveRSA(plainText, _e);
  }
  
  public long decrypt(long encrypted) {
    return recursiveRSA(encrypted, _d);
  }
  
  public long getN() {
    return _n;
  }

  private long recursiveRSA(long M, long e) {

    if (e <= 0)
      return 1;
    
    long C = ( recursiveRSA(M, (long) (e/2)) * recursiveRSA(M, (long) (e/2)) ) % _n;

    if (e % 2 == 0) { // e is even
      return C;
    } else { // e is odd
      return (C * M) % _n;
    }

  }

  public static void main(String[] args) {
    long p = 257;
    long q = 337;
    long e = 17;
    long d = 65777;

    RSA rsa = new RSA(p, q, e, d);

    // get plain text from user
    long plainText = 0;
    while (plainText==0) {
      System.out.print("Please type in your plain text (as integer): ");
      if (in.hasNextLong())
        plainText = in.nextLong();
      else
        System.out.println("Error. Please provide an integer!");
      
      if (plainText >= rsa.getN()) {
        System.out.println("Error. Maximum value is "+(rsa.getN()-1));
        plainText = 0;
      }
    }
    
    long encrypted = rsa.encrypt(plainText);
    System.out.println("Your encrypted text is (as integer): " + encrypted);
    
    long decrypted = rsa.decrypt(encrypted);
    System.out.println("The decrypted text is (as integer): " + decrypted);
  }

}
