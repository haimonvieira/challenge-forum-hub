package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.domain.topico.DadosCadastroTopico;
import br.com.alura.ForumHub.domain.topico.DadosDetalhamentoTopico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//Fazer teste de unidade na classe
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TopicoControllerTest {

    @Autowired
    private MockMvc mvc;//Simula requisicoes http

    @Autowired
    private JacksonTester<DadosCadastroTopico> dadosCadastroTopicoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoTopico> dadosDetalhamentoTopicoJson;

    @MockBean
    private TopicoController topicoController;

    private static final String ENDERECO = "/topicos";


    //Testes de cadastro
    @Test
    @DisplayName("Devolver código 400 quando ter dados inválidos")
    @WithMockUser//Simular usuario logado
    void cadastrar_cenario1() throws Exception{

        //Disparando requisicao
       var response = mvc.perform(post("/topicos"))
                .andReturn().getResponse();

       //Verificar se o status é 400
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());


    }

    @Test
    @DisplayName("Devolver código 200 quando ter dados validos")
    @WithMockUser//Simular usuario logado
    void cadastrar_cenario2() throws Exception{

        var data = LocalDateTime.now();
        var dadosDetalhamentoTopico = new DadosDetalhamentoTopico("Teste", data, true,
                "Eu", "POO");

       var dadosCadastroTopico = new DadosCadastroTopico("Teste", "Teste",
                "Eu", "POO");

       when(topicoController.cadastrar(any(), any())).thenReturn(ResponseEntity.ok(dadosDetalhamentoTopico));


        //Disparando requisicao
        var response = mvc.perform(
                post(ENDERECO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroTopicoJson.write(dadosCadastroTopico).getJson())
                )
                .andReturn().getResponse();

        //Verificar se o status é 400
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoTopicoJson.write(dadosDetalhamentoTopico).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);


    }

    @Test
    @DisplayName("Devolver código 400 quando ter dados invalidos")
    @WithMockUser//Simular usuario logado
    void listar_cenario1() throws Exception{

        //Disparando requisicao
        var response = mvc.perform(post(ENDERECO))
                .andReturn().getResponse();

        //Verificar se o status é 400
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());


    }
}