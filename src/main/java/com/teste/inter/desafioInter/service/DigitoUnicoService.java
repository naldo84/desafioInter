package com.teste.inter.desafioInter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.teste.inter.desafioInter.model.DigitoUnico;
import com.teste.inter.desafioInter.model.User;
import com.teste.inter.desafioInter.repository.DigitoUnicoRepository;
import com.teste.inter.desafioInter.repository.UserRepository;

@Service
public class DigitoUnicoService {

    private final DigitoUnicoRepository digitoUnicoRepository;
    private final UserRepository userRepository;
    private final List<DigitoUnico> cache = new ArrayList<>();

    public DigitoUnicoService(DigitoUnicoRepository digitoUnicoRepository, UserRepository userRepository){
        this.digitoUnicoRepository = digitoUnicoRepository;
        this.userRepository = userRepository;
    }

    public DigitoUnico calcular(Long userId, Integer numero){
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Integer resultado = buscarNoCache(numero);

        if (resultado == null){
            resultado = calcularDigitoUnico(numero);
            adicionarNoCache(numero, resultado);
        }
        
        DigitoUnico digito = new DigitoUnico();
        digito.setNumero(numero);
        digito.setResultado(resultado);
        digito.setUser(user);

        return digitoUnicoRepository.save(digito);
    }

    private int calcularDigitoUnico(int numero){
        System.out.println("Calculando o digito unico");
        while (numero >=10) {
            int soma = 0;

            while (numero > 0) {
                soma += numero % 10;
                numero /= 10; 
            }

            numero = soma;
        }

        return numero;
    }

    public List<DigitoUnico> listarRessultados(Long userId){
        return digitoUnicoRepository.findByUserId(userId);
    }

    private Integer buscarNoCache(Integer numero){

        for (DigitoUnico digitoUnico : cache){
            if (digitoUnico.getNumero().equals(numero)){
                return digitoUnico.getResultado();
            }
        }

        return null;
    }

    private void adicionarNoCache(Integer numero, Integer resultado){

        if (buscarNoCache(numero) != null){
            return;
        }

        if (cache.size() == 10){
            cache.remove(0);
        }

        DigitoUnico digitoUnico = new DigitoUnico();
        digitoUnico.setNumero(numero);
        digitoUnico.setResultado(resultado);

        cache.add(digitoUnico);

    }
}
