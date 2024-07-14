package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.domain.curso.CursoRepository;
import br.com.alura.ForumHub.domain.topico.*;
import br.com.alura.ForumHub.domain.usuario.Usuario;
import br.com.alura.ForumHub.domain.usuario.UsuarioRepository;
import br.com.alura.ForumHub.service.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService topicoService;

    //Cadastrar tópico
    //RequestBody e Valid para pegar o JSON e validar os campos
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados,
                                    UriComponentsBuilder uriBuilder){

        var topico = topicoService.criarTopico(dados);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));

    }

    //Mostrar todos os tópicos
    //JSON que retornara os dados de todos os topicos com maximo de 10 elementos
    //ordenando pelo titulo e id
    //GET para todos os topicos
    @GetMapping("/listar")
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10,
            sort = {"id", "titulo"})
                                                            Pageable paginacao){

        var page = topicoRepository.findAllByEstadoTopicoTrue(paginacao)
                .map(DadosListagemTopico::new);

        return ResponseEntity.ok(page);

    }

    //Detalhamento de tópicos
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){

        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));

    }

    //Atualizar tópico
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados,
                                    @PathVariable Long id){

        var topico = topicoRepository.getReferenceById(id);
        topico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosAtualizacaoTopico(topico));

    }

    //Deletar topico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){

        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()){

            topicoRepository.deleteById(id);

            return ResponseEntity.ok().build();

        }

        return ResponseEntity.noContent().build();

    }

}