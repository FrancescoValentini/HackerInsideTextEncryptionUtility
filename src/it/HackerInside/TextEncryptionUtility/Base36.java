package it.HackerInside.TextEncryptionUtility;

import java.math.BigInteger;

public class Base36 {

    private static final char[] BASE_36_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String encode(byte[] bytes) {
        BigInteger bigInt = new BigInteger(1, bytes);
        StringBuilder sb = new StringBuilder();
        while (bigInt.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] quotientAndRemainder = bigInt.divideAndRemainder(BigInteger.valueOf(36));
            sb.append(BASE_36_ALPHABET[quotientAndRemainder[1].intValue()]);
            bigInt = quotientAndRemainder[0];
        }
        return sb.reverse().toString();
    }

    public static byte[] decode(String base36String) {
        BigInteger bigInt = new BigInteger(base36String, 36);
        byte[] bytes = bigInt.toByteArray();
        if (bytes[0] == 0) {
            byte[] trimmedBytes = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, trimmedBytes, 0, trimmedBytes.length);
            return trimmedBytes;
        }
        return bytes;
    }

}
