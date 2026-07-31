package com.teste.inter.desafioInter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.teste.inter.desafioInter.model.User;
import com.teste.inter.desafioInter.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private CriptografiaService criptografiaService;

    @InjectMocks
    private UserService service;


    @Test
    void deveCriarUsuario() throws Exception {
        User user = new User();
        user.setNome("Erinaldo");
        user.setEmail("erinaldo@test.com.br");

        when(repository.save(any(User.class))).thenReturn(user);

        User resultado = service.criarUser(user);

        assertNotNull(resultado);
        assertEquals("Erinaldo", resultado.getNome());

        verify(repository).save(any(User.class));

    }

    @Test
    void deveBuscarUsuario() throws Exception{

        User user = new User();
        user.setNome("Erinaldo");

        when(repository.findById(1L))
        .thenReturn(Optional.of(user));

        User resultado = service.buscarUser(1L);

        assertNotNull(resultado);
        assertEquals("Erinaldo", resultado.getNome());

        verify(repository).findById(1L);

    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado(){
        when(repository.findById(1L))
        .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> service.buscarUser(1L));

        assertEquals("Usuário não encontrado!", exception.getMessage());
    }

    @Test
    void deveListarUsuarios() {
        User user1 = new User();
        user1.setNome("Erinaldo");
        user1.setEmail("erinaldo1@teste.com.br");

        User user2 = new User();
        user2.setNome("Erinaldo2");
        user2.setEmail("erinaldo2@teste.com.br");

        List<User> listUsers = List.of(user1, user2);

        when(repository.findAll()).thenReturn(listUsers);

        List<User> resultado = service.listarUsers();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(repository).findAll();

    }

    @Test
    void deveAtualizarUsuarioSemCriptografia() throws Exception {
        User user = new User();
        user.setNome("Erinaldo");
        user.setEmail("erinaldo@teste.com.br");

        
        User userAtualizado = new User();
        userAtualizado.setNome("Erinaldo Atualizado");
        userAtualizado.setEmail("erinaldo.atualizado@teste.com.br");
        
        when(repository.findById(1L))
        .thenReturn(Optional.of(user));

        when(repository.save(any(User.class)))
        .thenAnswer(usuario -> usuario.getArgument(0));

        User resultado = service.atualizarUser(1L, userAtualizado);

        assertNotNull(resultado);
        assertEquals("Erinaldo Atualizado", resultado.getNome());
        assertEquals("erinaldo.atualizado@teste.com.br", resultado.getEmail());


        verify(repository).save(any(User.class));
    }

    @Test
    void deveAtualizarUsuarioComCriptografia() throws Exception {
        User user = new User();
        user.setNome("Erinaldo");
        user.setEmail("erinaldo@teste.com.br");
        user.setChavePublica("Chave-Publica");


        when(repository.findById(1L))
        .thenReturn(Optional.of(user));

        User userAtualizado = new User();
        userAtualizado.setNome("Erinaldo Atualizado");
        userAtualizado.setEmail("erinaldo.atualizado@teste.com.br");


        PublicKey publicKey = mock(PublicKey.class);

        when(criptografiaService.converterChavePublica("Chave-Publica"))
        .thenReturn(publicKey);

        when(criptografiaService.criptografar("Erinaldo Atualizado", publicKey))
        .thenReturn("Nome_Criptografado");

        when(criptografiaService.criptografar("erinaldo.atualizado@teste.com.br", publicKey))
        .thenReturn("Email_Criptografado");

        when(repository.save(any(User.class)))
        .thenAnswer(usuario -> usuario.getArgument(0));

        User resultado = service.atualizarUser(1L, userAtualizado);

        assertEquals("Nome_Criptografado", resultado.getNome());
        assertEquals("Email_Criptografado", resultado.getEmail());

        verify(repository).save(any(User.class));
    }



    @Test
    void deveExcluirUsuario() {
        User user = new User();

        when(repository.findById(1L))
        .thenReturn(Optional.of(user));

        service.excluirUser(1L);

        verify(repository).delete(user);
    }


    @Test
    void deveSalvarChavePublica() {
        User user = new User();
        
        when(repository.findById(1L))
        .thenReturn(Optional.of(user));

        when(repository.save(any(User.class)))
        .thenAnswer(usuario -> usuario.getArgument(0));

        User resultado = service.salvarChavePublica(1L, "CHAVE-PUBLICA");

        assertNotNull(resultado);
        assertEquals("CHAVE-PUBLICA", resultado.getChavePublica());

        verify(repository).save(any(User.class));
    }
}
