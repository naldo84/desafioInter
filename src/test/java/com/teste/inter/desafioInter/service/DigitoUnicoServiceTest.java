package com.teste.inter.desafioInter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.teste.inter.desafioInter.model.DigitoUnico;
import com.teste.inter.desafioInter.model.User;
import com.teste.inter.desafioInter.repository.DigitoUnicoRepository;
import com.teste.inter.desafioInter.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class DigitoUnicoServiceTest {

    @Mock
    private DigitoUnicoRepository digitoUnicoRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DigitoUnicoService digitoUnicoService;

    @Test
    void deveCalcularDigitoUnico() {
        User user = new User();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(digitoUnicoRepository.save(any(DigitoUnico.class)))
                .thenAnswer(digitoUnico -> digitoUnico.getArgument(0));

        DigitoUnico resultado = digitoUnicoService.calcular(1L, 141);

        assertNotNull(resultado);
        assertEquals(6, resultado.getResultado());

        verify(digitoUnicoRepository).save(any(DigitoUnico.class));
    }

    @Test
    void deveListarRessultados() {
        List<DigitoUnico> lista = List.of(new DigitoUnico(), new DigitoUnico());

        when(digitoUnicoRepository.findByUserId(1L))
                .thenReturn(lista);

        List<DigitoUnico> resultado = digitoUnicoService.listarRessultados(1L);

        assertEquals(2, resultado.size());

        verify(digitoUnicoRepository).findByUserId(1L);
    }


    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado(){
        when(userRepository.findById(1L))
        .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> digitoUnicoService.calcular(1L, 125));

        assertEquals("Usuário não encontrado", exception.getMessage());
    }
}
