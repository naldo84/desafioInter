package com.teste.inter.desafioInter.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.teste.inter.desafioInter.model.User;
import com.teste.inter.desafioInter.service.CriptografiaService;
import com.teste.inter.desafioInter.service.UserService;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private CriptografiaService criptografiaService;

    @Test
    void deveListarTodosUsuarios() throws Exception {

        User user = new User();
        user.setNome("Erinaldo");
        user.setEmail("erinaldo@teste.com.br");

        when(userService.listarUsers())
                .thenReturn(List.of(user));

        mockMvc.perform(get("/users/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Erinaldo"))
                .andExpect(jsonPath("$[0].email").value("erinaldo@teste.com.br"));

    }

    @Test
    void deveBuscarUsuarioPorId() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setNome("Erinaldo");
        user.setEmail("erinaldo@teste.com.br");

        when(userService.buscarUser(1L))
                .thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Erinaldo"))
                .andExpect(jsonPath("$.email").value("erinaldo@teste.com.br"));
    }

    @Test
    void deveCriarUsuario() throws Exception {

        User user = new User();
        user.setNome("Erinaldo");
        user.setEmail("erinaldo@teste.com.br");

        when(userService.criarUser(any(User.class)))
                .thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content("{ \"nome\": \"Erinaldo\", \"email\": \"erinaldo@teste.com.br\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Erinaldo"))
                .andExpect(jsonPath("$.email").value("erinaldo@teste.com.br"));
    }

    @Test
    void deveAtualizarUsuario() throws Exception {

        User userAtualizado = new User();
        userAtualizado.setId(1L);
        userAtualizado.setNome("Erinaldo Atualizado");
        userAtualizado.setEmail("erinaldo.atualizado@teste.com.br");

        when(userService.atualizarUser(any(Long.class), any(User.class)))
                .thenReturn(userAtualizado);

        mockMvc.perform(put("/users/1")
                .contentType("application/json")
                .content("{ \"nome\": \"Erinaldo Atualizado\", \"email\": \"erinaldo.atualizado@teste.com.br\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Erinaldo Atualizado"))
                .andExpect(jsonPath("$.email").value("erinaldo.atualizado@teste.com.br"));
    }

    @Test
    void deveDeletarUsuario() throws Exception {

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userService).excluirUser(1L);
    }

    @Test
    void deveSalvarChavePublica() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setNome("Erinaldo");
        user.setEmail("erinaldo@teste.com.br");
        user.setChavePublica("chavePublica123");

        when(userService.salvarChavePublica(any(Long.class), any(String.class)))
                .thenReturn(user);

        mockMvc.perform(post("/users/1/chave-publica")
                .contentType("application/json")
                .content("{ \"chavePublica\": \"chave123\" }"))
                .andExpect(status().isOk());
    }

    @Test
    void deveGerarChaves() throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair keyPair = generator.generateKeyPair();

        when(criptografiaService.gerarChaves())
                .thenReturn(keyPair);

        mockMvc.perform(get("/users/gerar-chaves"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chavePublica").isNotEmpty())
                .andExpect(jsonPath("$.chavePrivada").isNotEmpty());

        verify(criptografiaService).gerarChaves();
    }

}