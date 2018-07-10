import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class AesTest {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        String content = "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";
        String key = "befa9057379a8c02d46141aa327cdf48";
        String iv = "befa9057379a8c02";

        //System.out.println("加密前：" + byteToHexString(content.getBytes()));
        //byte[] encrypted = AES_CBC_Encrypt(content.getBytes(), key.getBytes(), iv.getBytes());
        //System.out.println("加密后：" + byteToHexString(encrypted));
        String decrypted = AES_cbc_decode(content, key, iv);
        System.out.println("解密后：" + (decrypted));
    }

    public static String AES_cbc_decode(String bytes, String key, String iv) {
        String result = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES"); //生成加密解密需要的Key
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            byte[] decoded = cipher.doFinal(Base64.decode(bytes));
            result = new String(decoded, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String decrypt(String cryptograph, String password, String iv) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom securerandom = new SecureRandom(password.getBytes());
            kgen.init(256, securerandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv.getBytes()));
            byte[] content = cipher.doFinal(Base64.decode(cryptograph));
            return new String(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

	/*private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length()/2];
		for (int i = 0;i< hexStr.length()/2; i++) {
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}*/

    private static byte[] tohash256Deal(String datastr) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            digester.update(datastr.getBytes());
            byte[] hex = digester.digest();
            return hex;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}

