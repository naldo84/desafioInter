package com.teste.inter.desafioInter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.inter.desafioInter.dto.DigitoRequest;
import com.teste.inter.desafioInter.model.DigitoUnico;
import com.teste.inter.desafioInter.service.DigitoUnicoService;

@RestController
@RequestMapping("/users/{userId}/digitos")
public class DigitoUnicoController {

    private final DigitoUnicoService digitoUnicoService;

    public DigitoUnicoController(DigitoUnicoService digitoUnicoService) {
        this.digitoUnicoService = digitoUnicoService;
    }

    @PostMapping
    public DigitoUnico calcularDigitoUnico(@PathVariable Long userId, @RequestBody DigitoRequest digitoRequest){

        return digitoUnicoService.calcular(userId, digitoRequest.getNumero());
    }

    @GetMapping
    public List<DigitoUnico> listar(@PathVariable Long userId){
        return digitoUnicoService.listarRessultados(userId);
    }
}
