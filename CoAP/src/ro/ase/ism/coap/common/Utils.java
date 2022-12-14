package ro.ase.ism.coap.common;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Utils {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private static final String PoC_SALT = "PoCSalt";
    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final IvParameterSpec IV = new IvParameterSpec(new byte[]{0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01});

    public static String encrypt(String input)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException,
            InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException {

        SecretKey key = generateKey(Env.SYMMETRIC_KEY);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, IV);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public static String decrypt(String cipherText)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {

        SecretKey key = generateKey(Env.SYMMETRIC_KEY);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, IV);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    public static SecretKey generateKey(String key) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), PoC_SALT.getBytes(), 65536, 128);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
}
