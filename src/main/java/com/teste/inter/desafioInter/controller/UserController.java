package com.teste.inter.desafioInter.controller;

import java.security.KeyPair;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.inter.desafioInter.dto.ChavePublicaRequest;
import com.teste.inter.desafioInter.model.User;
import com.teste.inter.desafioInter.service.CriptografiaService;
import com.teste.inter.desafioInter.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CriptografiaService criptografiaService;

    public UserController(UserService userService, CriptografiaService criptografiaService) {
        this.userService = userService;
        this.criptografiaService = criptografiaService;
    }

    @PostMapping
    public User criaUser(@RequestBody User user) throws Exception {
        return userService.criarUser(user);
    }

    @GetMapping("/{id}")
    public User buscarUserById(@PathVariable Long id) {
        return userService.buscarUser(id);
    }

    @GetMapping("/listar")
    public List<User> listarUsers() {
        return userService.listarUsers();
    }

    @PutMapping("/{id}")
    public User atualizarUser(@PathVariable Long id, @RequestBody User userAtualizado) throws Exception {
        return userService.atualizarUser(id, userAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletarUSer(@PathVariable Long id) {
        userService.excluirUser(id);
    }

    @PostMapping("/{id}/chave-publica")
    public User salvarChave(@PathVariable("id") Long userId,
            @RequestBody ChavePublicaRequest chavePublicaRequest) {
        return userService.salvarChavePublica(userId, chavePublicaRequest.getChavePublica());
    }

    @GetMapping("/gerar-chaves")
    public Map<String, String> gerarchaves() throws Exception {
        KeyPair keyPair = criptografiaService.gerarChaves();

        Map<String, String> resposta = new HashMap<>();

        resposta.put("chavePublica", Base64.getEncoder().encodeToString(
                keyPair.getPublic().getEncoded()));

        resposta.put("chavePrivada", Base64.getEncoder().encodeToString(
            keyPair.getPrivate().getEncoded()));

        return resposta;
    }

}