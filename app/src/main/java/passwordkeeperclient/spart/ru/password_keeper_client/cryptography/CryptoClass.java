package passwordkeeperclient.spart.ru.password_keeper_client.cryptography;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class CryptoClass {
    public static String encrypt(String strClearText, String strKey) throws Exception {
        String strData = "";

        try {
            SecretKeySpec skeyspec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
            byte[] encrypted = cipher.doFinal(strClearText.getBytes());
            strData = new String(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return strData;
    }

    public static String decrypt(String strEncrypted, String strKey) throws Exception {
        String strData = "";

        try {
            SecretKeySpec skeyspec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            byte[] decrypted = cipher.doFinal(strEncrypted.getBytes());
            strData = new String(decrypted);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return strData;
    }


//    public static String base64Encode(String token) {
//        String originalInput = "test input";
//        String encodedString = new String(org.springframework.security.crypto.codec.Base64.encodeBase64(originalInput.getBytes()));
//        String decodedString = new String(java.util.Base64.decodeBase64(encodedString.getBytes()));
//
//        String originalInput = "test input";
//        android.util.Base64 base64 = new org.springframework.security.crypto.codec.Base64();
//        String encodedString = new String(base64.encode(originalInput.getBytes()));
//
//        String originalInput = "test input";
//        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
//
//        String encodedString = Base64.encodeToString(token.getBytes());//.getEncoder().encodeToString(originalInput.getBytes());
//        String s  = Base64.encode(token);
//        return new String(encodedBytes, Charset.forName("UTF-8"));
//    }


//    public static String base64Decode(String token) {
//        byte[] decodedBytes = Base64.decode(token.getBytes());
//        return new String(decodedBytes, Charset.forName("UTF-8"));
//    }

    public static String fileProcessor(int cipherMode, String key, String string) {

        String specKey = String.valueOf(key.hashCode());

        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKey);

            byte[] inputBytes = string.getBytes();
            byte[] outputBytes = cipher.doFinal(inputBytes);
            return outputBytes.toString();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "Error";
    }

}


