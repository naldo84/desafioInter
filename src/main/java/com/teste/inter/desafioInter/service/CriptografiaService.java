package com.teste.inter.desafioInter.service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.stereotype.Service;

@Service
public class CriptografiaService {

    public PublicKey converterChavePublica(String chavePublica) throws Exception{
        byte[] bytes = Base64.getDecoder().decode(chavePublica);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);

        KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePublic(spec);
    }

    public String criptografar(String texto, PublicKey chavePublica) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, chavePublica);

        byte[] critografado = cipher.doFinal(texto.getBytes());

        return Base64.getEncoder().encodeToString(critografado);
    }

    public String descriptografar(String texto, PrivateKey chavePrivada) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        
        cipher.init(Cipher.DECRYPT_MODE, chavePrivada);

        byte[] descriptografado = cipher.doFinal(Base64.getDecoder().decode(texto));

        return new String(descriptografado);
    }

    public KeyPair gerarChaves() throws Exception{
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

        generator.initialize(2048);

        return generator.generateKeyPair();

    }
}
