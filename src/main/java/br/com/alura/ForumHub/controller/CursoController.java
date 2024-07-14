package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.domain.curso.*;
import br.com.alura.ForumHub.domain.topico.TopicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Operation(summary = "Cadastrar um novo curso",
            description = "Este endpoint permite cadastrar um novo curso.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do curso que precisa ser cadastrado",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = DadosCadastroCurso.class),
                            examples = @ExampleObject(value =
                                    "{\"nome\": \"Curso de Java\", " +
                                            "\"categoria\": \"PROGRAMACAO\"}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content)
            }
    )
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCurso dados){

        var curso = new Curso(dados);
        cursoRepository.save(curso);
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));

    }

    @Operation(summary = "Listar todos os cursos",
            description = "Este endpoint permite listar todos os cursos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de cursos retornada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity listar(){

        var cursos = cursoRepository.findAll()
                .stream()
                .map(DadosListagemCurso::new);

        return ResponseEntity.ok(cursos);

    }

    @Operation(summary = "Atualizar um curso",
            description = "Este endpoint permite atualizar as informações de um curso específico.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do curso",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = DadosAtualizacaoCurso.class),
                            examples = @ExampleObject(value =
                                    "{\"nome\": \"Curso de Java Avançado\"," +
                                            " \"categoria\": \"Programação\"}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content)
            }
    )
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoCurso dados,
                                    @PathVariable Long id){

        var curso = cursoRepository.getReferenceById(id);
        curso.atualizar(dados);

        return ResponseEntity.ok(new DadosAtualizacaoCurso(curso));

    }

    @Operation(summary = "Excluir um curso",
            description = "Este endpoint permite excluir um curso específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Curso não encontrado",
                            content = @Content)
            }
    )
    //Deletar curso
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        //Curso nao pode ser deletado pois esta relacionado com um tópico
        Optional<Curso> curso = cursoRepository.findById(id);

        if (curso.isPresent()){

            var idTopico = topicoRepository.buscarIdDoTopicoDoCurso(id);
            topicoRepository.deleteById(idTopico);
            cursoRepository.deleteById(id);
            return ResponseEntity.ok().build();

        }

        return ResponseEntity.noContent().build();

    }

}
