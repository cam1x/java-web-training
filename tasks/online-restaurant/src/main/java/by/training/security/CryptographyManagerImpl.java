package by.training.security;

import by.training.core.Bean;

import java.util.Base64;

@Bean
public class CryptographyManagerImpl implements CryptographyManager {
    @Override
    public String encrypt(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }

    @Override
    public String decrypt(String string) {
        byte[] decodedBytes = Base64.getDecoder().decode(string);
        return new String(decodedBytes);
    }
}
