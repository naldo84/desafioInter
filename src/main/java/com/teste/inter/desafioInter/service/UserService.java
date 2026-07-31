package com.teste.inter.desafioInter.service;

import java.security.PublicKey;
import java.util.List;

import org.springframework.stereotype.Service;

import com.teste.inter.desafioInter.model.User;
import com.teste.inter.desafioInter.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CriptografiaService criptografiaService;

    public UserService(UserRepository userRepository,
            CriptografiaService criptografiaService) {
        this.userRepository = userRepository;
        this.criptografiaService = criptografiaService;
    }

    public User criarUser(User user) throws Exception {

        user.setChavePublica(null);

        return userRepository.save(user);
    }

    public List<User> listarUsers() {
        return userRepository.findAll();
    }

    public User buscarUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public User atualizarUser(Long id, User dados) throws Exception {
        User user = buscarUser(id);

        if (user.getChavePublica() != null && !user.getChavePublica().isBlank()) {
            PublicKey publicKey = criptografiaService.converterChavePublica(
                    user.getChavePublica());

            String nomeCriptografado = criptografiaService.criptografar(
                    dados.getNome(), publicKey);

            String emailCriptografado = criptografiaService.criptografar(
                    dados.getEmail(), publicKey);

            user.setNome(nomeCriptografado);
            user.setEmail(emailCriptografado);
        } else {
            user.setNome(dados.getNome());
            user.setEmail(dados.getEmail());
        }

        return userRepository.save(user);
    }

    public void excluirUser(Long id) {
        User user = buscarUser(id);

        userRepository.delete(user);
    }

    public User salvarChavePublica(Long userId, String chavePublica) {
        User user = buscarUser(userId);
        user.setChavePublica(chavePublica);

        return userRepository.save(user);
    }
}
