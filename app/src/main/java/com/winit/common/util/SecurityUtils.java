package com.winit.common.util;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Girish Velivela on 12/6/2016.
 */

public class SecurityUtils {

    public static final String SECRET_KEY = "N@pC0SFA";

    public static String encryptText(String text)     {
        String strEnc = "";
        try         {
            if(text!=null && !text.equalsIgnoreCase("")){
                byte[] textByte =text.getBytes("UTF-8");
                byte[] keyByte = SECRET_KEY.getBytes("UTF-16LE");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyByte, "AES"),new IvParameterSpec(keyByte));
                strEnc = Base64.encodeToString(cipher.doFinal(textByte), Base64.DEFAULT);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return strEnc;
    }

    public static String decrypt(String encryptedText)     {
        String strDec="";
        try{
            if(encryptedText!=null && !encryptedText.equalsIgnoreCase("")){
                byte[] keyByte, decryptedData ;
                keyByte = SECRET_KEY.getBytes("UTF-16LE");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyByte, "AES"),new IvParameterSpec(keyByte));
                decryptedData = cipher.doFinal(Base64.decode(encryptedText, Base64.DEFAULT));
                strDec = new String(decryptedData, "UTF-8");
            }
        }
        catch (Exception e)         {
            e.printStackTrace();
        }
        return strDec;
    }

}
