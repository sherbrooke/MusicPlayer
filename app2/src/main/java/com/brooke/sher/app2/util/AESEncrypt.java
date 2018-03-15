package com.brooke.sher.app2.util;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public final class AESEncrypt {

    private static final String TAG = AESEncrypt.class.getName();

    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    public AESEncrypt(String keyvalue) {
        SecretKeySpec key = new SecretKeySpec(keyvalue.getBytes(), "AES");
        try {
            encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);

            decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getLocalizedMessage());
        } catch (NoSuchPaddingException e) {
            Log.e("", e.getLocalizedMessage());
        } catch (InvalidKeyException e) {
            Log.e("", e.getLocalizedMessage());
        }
    }

    public byte[] encrytor(String str, String charsetName) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        byte[] src = null;
        try {
            src = str.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return encryptCipher.doFinal(src);
    }

    public String encrytorAsString(String str, String charsetName) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] srcBytes = encrytor(str, charsetName);
        return EncryptUtils.byteArr2HexStr(srcBytes);
    }

    public String encrytorAsString(byte[] srcBytes) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        byte[] destBytes = encryptCipher.doFinal(srcBytes);
        return EncryptUtils.byteArr2HexStr(destBytes);
    }

    public String decryptorFromString(String str, String charsetName) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] resultBytes = EncryptUtils.hexStr2ByteArr(str);
        byte[] jsonBytes = decryptCipher.doFinal(resultBytes);

        return new String(jsonBytes, charsetName);
    }
}
