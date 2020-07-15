package by.training.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CryptographyManagerTest {

    @Test
    public void shouldEncryptThanDecryptCorrectly() {
        CryptographyManager cryptographyManager = new CryptographyManagerImpl();

        String inputString = "some very long text from 2019@";
        String encrypted = cryptographyManager.encrypt(inputString);
        String decrypted = cryptographyManager.decrypt(encrypted);

        Assert.assertFalse(inputString.equals(encrypted));
        Assert.assertEquals(inputString, decrypted);
    }
}
