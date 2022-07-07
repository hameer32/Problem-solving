package digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestMessage {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String password = "Hameer@123Dhandu";
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        MessageDigest messageDigest1 = MessageDigest.getInstance("SHA-256");
        MessageDigest messageDigest2 = MessageDigest.getInstance("SHA-1");
        messageDigest.update(password.getBytes());
        messageDigest1.update(password.getBytes());
        messageDigest2.update(password.getBytes());
        System.out.println(byteToHexString(messageDigest.digest()));
        System.out.println("--------------------");
        System.out.println(byteToHexString(messageDigest1.digest()));
        System.out.println("--------------------");
        System.out.println(byteToHexString(messageDigest2.digest()));


    }

    static String byteToHexString(byte[] bytes){
        StringBuilder stringBuilder=new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append( String.format("%02X", b));
        }
        return stringBuilder.toString();
    }
}
