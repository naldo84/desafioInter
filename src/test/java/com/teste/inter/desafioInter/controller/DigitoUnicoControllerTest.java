package com.teste.inter.desafioInter.controller;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.teste.inter.desafioInter.model.DigitoUnico;
import com.teste.inter.desafioInter.service.DigitoUnicoService;

@WebMvcTest(DigitoUnicoController.class)
public class DigitoUnicoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DigitoUnicoService digitoUnicoService;

    @Test
    void deveCalcularDigitoUnico() throws Exception {
        DigitoUnico digitoUnico = new DigitoUnico();

        digitoUnico.setNumero(141);
        digitoUnico.setResultado(6);

        when(digitoUnicoService.calcular(1L, 141)).thenReturn(digitoUnico); 

        mockMvc.perform(post("/users/1/digitos")
                .contentType("application/json")
                .content("{\"numero\": 141}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value(141))
                .andExpect(jsonPath("$.resultado").value(6));

        verify(digitoUnicoService).calcular(1L, 141);
    }

    @Test
    void deveListarDigitosUnicos() throws Exception {
                DigitoUnico digito1 = new DigitoUnico();
        digito1.setNumero(141);
        digito1.setResultado(6);

        DigitoUnico digito2 = new DigitoUnico();
        digito2.setNumero(123);
        digito2.setResultado(6);

        when(digitoUnicoService.listarRessultados(1L))
                .thenReturn(List.of(digito1, digito2));

        mockMvc.perform(get("/users/1/digitos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].numero").value(141))
                .andExpect(jsonPath("$[0].resultado").value(6))
                .andExpect(jsonPath("$[1].numero").value(123))
                .andExpect(jsonPath("$[1].resultado").value(6));

        verify(digitoUnicoService).listarRessultados(1L);

    }
}
