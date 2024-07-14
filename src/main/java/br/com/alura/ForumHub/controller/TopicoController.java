package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.domain.topico.*;
import br.com.alura.ForumHub.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Optional;

@RestController
@RequestMapping("topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService topicoService;

    @Operation(summary = "Cadastrar um novo tópico",
            description = "Este endpoint permite cadastrar um novo tópico no fórum.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do tópico que precisa ser cadastrado",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = DadosCadastroTopico.class),
                            examples = @ExampleObject(value = "{\"titulo\": \"Título do Tópico\", " +
                                    "\"mensagem\": \"Mensagem do tópico\"}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tópico cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content)
            }
    )

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

    @Operation(summary = "Listar todos os tópicos",
            description = "Este endpoint permite listar todos os tópicos do fórum.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de tópicos retornada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content)
            }
    )
    @GetMapping("/listar")
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10,
            sort = {"id", "titulo"})
                                                            Pageable paginacao){

        var page = topicoRepository.findAllByEstadoTopicoTrue(paginacao)
                .map(DadosListagemTopico::new);

        return ResponseEntity.ok(page);

    }

    @Operation(summary = "Detalhar um tópico",
            description = "Este endpoint permite visualizar os detalhes de um tópico específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalhes do tópico retornados com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Tópico não encontrado",
                            content = @Content)
            }
    )
    //Detalhamento de tópicos
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){

        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));

    }

    @Operation(summary = "Atualizar um tópico",
            description = "Este endpoint permite atualizar as informações de um tópico específico.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do tópico",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = DadosAtualizacaoTopico.class),
                            examples = @ExampleObject(value = "{\"titulo\": \"Título Atualizado\", \"mensagem\": \"Mensagem Atualizada\"}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tópico atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content)
            }
    )
    //Atualizar tópico
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados,
                                    @PathVariable Long id){

        var topico = topicoRepository.getReferenceById(id);
        topico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosAtualizacaoTopico(topico));

    }

    @Operation(summary = "Excluir um tópico",
            description = "Este endpoint permite excluir um tópico específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tópico excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Tópico não encontrado",
                            content = @Content)
            }
    )
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