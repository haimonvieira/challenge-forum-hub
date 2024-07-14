package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.domain.curso.*;
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

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCurso dados){

        var curso = new Curso(dados);
        cursoRepository.save(curso);
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));

    }

    @GetMapping
    public ResponseEntity listar(){

        var cursos = cursoRepository.findAll()
                .stream()
                .map(DadosListagemCurso::new);

        return ResponseEntity.ok(cursos);

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoCurso dados,
                                    @PathVariable Long id){

        var curso = cursoRepository.getReferenceById(id);
        curso.atualizar(dados);

        return ResponseEntity.ok(new DadosAtualizacaoCurso(curso));

    }

    //Deletar curso
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){

        Optional<Curso> curso = cursoRepository.findById(id);

        if (curso.isPresent()){

            cursoRepository.deleteById(id);

            return ResponseEntity.ok().build();

        }

        return ResponseEntity.noContent().build();

    }

}
