package ro.ase.ism.mqtt.common;

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

    public static String decrypt(byte[] payload)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException {

        Cipher AESCipher = Cipher.getInstance(ALGORITHM);
        AESCipher.init(Cipher.DECRYPT_MODE, Utils.generateKey(Env.SYMMETRIC_KEY), Utils.IV);
        byte[] plainText = AESCipher.doFinal(payload);
        return new String(plainText);
    }

    public static SecretKey generateKey(String key) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), PoC_SALT.getBytes(), 65536, 128);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
}
