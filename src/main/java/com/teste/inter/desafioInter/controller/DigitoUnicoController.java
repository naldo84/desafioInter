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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Digito Único", description = "Operações relacionadas ao cálculo do dígito único")
@RestController
@RequestMapping("/users/{userId}/digitos")
public class DigitoUnicoController {

    private final DigitoUnicoService digitoUnicoService;

    public DigitoUnicoController(DigitoUnicoService digitoUnicoService) {
        this.digitoUnicoService = digitoUnicoService;
    }

    @Operation(summary = "Calcular dígito único", description = "Calcula o dígito único para um número")
    @PostMapping
    public DigitoUnico calcularDigitoUnico(@PathVariable Long userId, @RequestBody DigitoRequest digitoRequest){

        return digitoUnicoService.calcular(userId, digitoRequest.getNumero());
    }

    @Operation(summary = "Listar resultados", description = "Lista todos os resultados de dígito único calculados por um usuário")
    @GetMapping
    public List<DigitoUnico> listar(@PathVariable Long userId){
        return digitoUnicoService.listarRessultados(userId);
    }
}
