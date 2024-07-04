package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.domain.topico.DadosCadastroTopico;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @PostMapping
    @Transactional
    public void cadastrar(DadosCadastroTopico dados){

    }

}
