import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static javax.crypto.Cipher.DECRYPT_MODE;

public class SessionDecrypter {

    private SessionKey sessionKey;
    private Cipher decryptionCipher;

    /**
     * Create a new session encrypter with parameters.
     * @param key The key to use for encrypting the stream.
     * @param iv The initialization vector for the CTR mode.
     */
    public SessionDecrypter(String key, String iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        sessionKey = new SessionKey(key);

        decryptionCipher = Cipher.getInstance("AES/CTR/NoPadding");
        decryptionCipher.init(DECRYPT_MODE, sessionKey.getSecretKey());
    }

    /**
     * @param inputStream The encrypted input stream to read from.
     * @return A cipher input stream that the user can read plaintext data from.
     */
    public CipherInputStream openCipherInputStream(InputStream inputStream) {
        return new CipherInputStream(inputStream, decryptionCipher);
    }
}