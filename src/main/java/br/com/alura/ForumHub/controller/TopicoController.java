package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.domain.topico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    //Cadastrar tópico
    //RequestBody e Valid para pegar o JSON e validar os campos
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados,
                                    UriComponentsBuilder uriBuilder){

        var topico = new Topico(dados);
        repository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));

    }

    //Mostrar todos os tópicos
    //JSON que retornara os dados de todos os topicos com maximo de 10 elementos
    //ordenando pelo titulo
    //GET para todos os topicos
    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"titulo"})
                                                            Pageable paginacao){

        var page = repository.findAllByEstadoTopicoTrue(paginacao)
                .map(DadosListagemTopico::new);

        return ResponseEntity.ok(page);

    }

    //Detalhamento de tópicos
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){


        var topico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));

    }

    //Atualizar tópico
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados){

        var topico = repository.getReferenceById(dados.id());
        topico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));

    }

    //Deletar topico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){

        Optional<Topico> topico = repository.findById(id);

        if (topico.isPresent()){

            repository.deleteById(id);

            return ResponseEntity.ok().build();

        }

        return ResponseEntity.noContent().build();

    }

}
