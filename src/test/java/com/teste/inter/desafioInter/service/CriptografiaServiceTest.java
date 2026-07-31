package com.teste.inter.desafioInter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.Base64;

import org.junit.jupiter.api.Test;


public class CriptografiaServiceTest {

    private CriptografiaService criptografiaService = new CriptografiaService();

    @Test
    void deveConverterChavePublica() throws Exception {
        KeyPairGenerator generatorKeys = KeyPairGenerator.getInstance("RSA");
        generatorKeys.initialize(2048);

        KeyPair KeyPair = generatorKeys.generateKeyPair();

        String chaveBase64 = Base64.getEncoder()
                .encodeToString(KeyPair.getPublic().getEncoded());

        PublicKey chave = criptografiaService.converterChavePublica(chaveBase64);

        assertNotNull(chave);
        assertEquals("RSA", chave.getAlgorithm());

    }

    @Test
    void deveLancarErroAoConverterChaveInvalida(){
        assertThrows(Exception.class, () -> criptografiaService.converterChavePublica("Naldo"));
    }

    @Test
    void deveCriptografarTexto() throws Exception {
        KeyPairGenerator gerador = KeyPairGenerator.getInstance("RSA");
        gerador.initialize(2048);

        KeyPair keyPair = gerador.generateKeyPair();

        String resultado = criptografiaService.criptografar("Erinaldo", keyPair.getPublic());

        assertNotNull(resultado);
        assertNotEquals("Erinaldo", resultado);
    }

    @Test
    void deveDescriptografarTexto() throws Exception {
        KeyPairGenerator gerador = KeyPairGenerator.getInstance("RSA");
        gerador.initialize(2048);

        KeyPair keyPair = gerador.generateKeyPair();

        String textoCriptografado = criptografiaService.criptografar("Erinaldo", keyPair.getPublic());

        String textoDescriptografado = criptografiaService.descriptografar(textoCriptografado, keyPair.getPrivate());

        assertEquals("Erinaldo", textoDescriptografado);

    }

    @Test
    void deveGerarChaves() throws Exception {

        KeyPair keyPair = criptografiaService.gerarChaves();

        assertNotNull(keyPair);
        assertNotNull(keyPair.getPublic());
        assertNotNull(keyPair.getPrivate());

        assertEquals("RSA", keyPair.getPublic().getAlgorithm());
        assertEquals("RSA", keyPair.getPrivate().getAlgorithm());

    }
}
