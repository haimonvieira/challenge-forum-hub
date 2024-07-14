package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.domain.topico.TopicoRepository;
import br.com.alura.ForumHub.domain.usuario.*;
import br.com.alura.ForumHub.service.UsuarioService;
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
@RequestMapping("usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados){

        var usuario = new Usuario(dados);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));

    }

    //Mostra os dados do usuario logado
    @GetMapping("/detalhar")
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar(){

        var id = usuarioService.getId();

        if( id == null){

            return ResponseEntity.notFound().build();

        }

        var usuario = usuarioRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));

    }

    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dados,
                                    @PathVariable Long id){

        var usuario = usuarioRepository.getReferenceById(id);
        usuario.atualizar(dados);

        return ResponseEntity.ok(new DadosAtualizacaoUsuario(usuario));

    }

    @DeleteMapping("/excluir/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){

        Optional<Usuario> usuarioExiste = usuarioRepository.findById(id);

        if(usuarioExiste.isPresent()){

            usuarioRepository.deleteById(id);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();

    }



}
