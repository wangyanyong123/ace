package com.github.wxiaoqi.security.common.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;



public class RSAUtil {
	public static final String KEY_ALGORITHM = "RSA";
	
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";

    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    public static final int KEY_SIZE = 2048;
    
	/**
     * RSA生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
     * 
     * @return
     */
    @SuppressWarnings("resource")
	public static Map<String, byte[]> generateKeyBytes() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            FileOutputStream fos;
            ObjectOutputStream os;
			try {
				fos = new FileOutputStream("C:/Users/Administrator/Desktop/publicKey.pem");
				os = new ObjectOutputStream(fos);
	            os.writeObject(publicKey.getEncoded());
	            os.flush();
	            fos.flush(); 
	            fos = new FileOutputStream("C:/Users/Administrator/Desktop/privateKey.pem");
				os = new ObjectOutputStream(fos);
	            os.writeObject(privateKey.getEncoded());
	            os.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
            
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     * 
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 
     * 从字符串中加载公钥 
     * 
     * @param publicKeyStr 
     *            公钥数据字符串 
     * @throws Exception 
     *             加载公钥时产生的异常 
     */  
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {  
        try  
        {  
            byte[] buffer = Base64Utils.decode(publicKeyStr);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e)  
        {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e)  
        {  
            throw new Exception("公钥非法");  
        } catch (NullPointerException e)  
        {  
            throw new Exception("公钥数据为空");  
        }  
    }  
    
    /** 
     * 从文件中输入流中加载公钥 
     * 
     * @param in 
     *            公钥输入流 
     * @throws Exception 
     *             加载公钥时产生的异常 
     */  
    public static PublicKey loadPublicKey(InputStream in) throws Exception  
    {  
        try  
        {  
            return loadPublicKey(readKey(in));  
        } catch (IOException e)  
        {  
            throw new Exception("公钥数据流读取错误");  
        } catch (NullPointerException e)  
        {  
            throw new Exception("公钥输入流为空");  
        }  
    }  
    
    /** 
     * 读取密钥信息 
     * 
     * @param in 
     * @return 
     * @throws IOException 
     */  
    private static String readKey(InputStream in) throws IOException  
    {  
        BufferedReader br = new BufferedReader(new InputStreamReader(in));  
        String readLine = null;  
        StringBuilder sb = new StringBuilder();  
        while ((readLine = br.readLine()) != null)  
        {  
            if (readLine.charAt(0) == '-')  
            {  
                continue;  
            } else  
            {  
                sb.append(readLine);  
                sb.append('\r');  
            }  
        }  
  
        return sb.toString();  
    }     
    
    /** 
     * 从文件中加载私钥 
     * 
     * @param in 
     *            私钥文件名 
     * @return 是否成功 
     * @throws Exception 
     */  
    public static PrivateKey loadPrivateKey(InputStream in) throws Exception  
    {  
        try  
        {  
            return loadPrivateKey(readKey(in));  
        } catch (IOException e)  
        {  
            throw new Exception("私钥数据读取错误");  
        } catch (NullPointerException e)  
        {  
            throw new Exception("私钥输入流为空");  
        }  
    }  
    
    /** 
     * 从字符串中加载私钥<br> 
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。 
     * 
     * @param privateKeyStr 
     * @return 
     * @throws Exception 
     */  
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception  
    {  
        try  
        {  
            byte[] buffer = Base64Utils.decode(privateKeyStr);  
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e)  
        {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e)  
        {  
            throw new Exception("私钥非法");  
        } catch (NullPointerException e)  
        {  
            throw new Exception("私钥数据为空");  
        }  
    }  
    
    /**
     * RSA还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     * 
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA加密
     * 
     * @param key
     * @param plainText
     * @return
     */
    public static byte[] RSAEncode(PublicKey key, byte[] plainText) {
        try {
        	KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA解密
     * 
     * @param key
     * @param encodedText
     * @return
     */
    public static String RSADecode(PrivateKey key, byte[] encodedText) {
        try {
        	KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(encodedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
