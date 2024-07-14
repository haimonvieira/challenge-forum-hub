package br.com.alura.ForumHub.controller;

import br.com.alura.ForumHub.domain.topico.Topico;
import br.com.alura.ForumHub.domain.topico.TopicoRepository;
import br.com.alura.ForumHub.domain.usuario.*;
import br.com.alura.ForumHub.service.UsuarioService;
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
@RequestMapping("usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TopicoRepository topicoRepository;

    @Operation(summary = "Cadastrar um novo usuário",
            description = "Este endpoint permite cadastrar um novo usuário.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do usuário que precisa ser cadastrado",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = DadosCadastroUsuario.class),
                            examples = @ExampleObject(value =
                                    "{\"nome\": \"Nome do Usuário\"," +
                                    " \"login\": \"seuusuario\"," +
                                    " \"senha\": \"123\"}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content)
            }
    )
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados){

        var usuario = new Usuario(dados);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));

    }

    @Operation(summary = "Detalhar o usuário logado",
            description = "Este endpoint permite visualizar os detalhes do usuário logado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalhes do usuário retornados com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                            content = @Content)
            }
    )
    //Mostra os dados do usuario logado
    @GetMapping("/detalhar")
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar(){

        var id = usuarioService.getId();

        var usuario = usuarioRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));

    }

    @Operation(summary = "Atualizar um usuário",
            description = "Este endpoint permite atualizar as informações de um usuário específico.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do usuário",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = DadosAtualizacaoUsuario.class),
                            examples = @ExampleObject(value =
                                    "{\"nome\": \"Novo Nome\"," +
                                    " \"login\": \"novousuario\"}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida",
                            content = @Content)
            }
    )
    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dados,
                                    @PathVariable Long id){

        var usuario = usuarioRepository.getReferenceById(id);
        usuario.atualizar(dados);

        return ResponseEntity.ok(new DadosAtualizacaoUsuario(usuario));

    }

    @Operation(summary = "Excluir um usuário",
            description = "Este endpoint permite excluir um usuário específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                            content = @Content)
            }
    )
    @DeleteMapping("/excluir/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){

        //Deletar topico
        Optional<Usuario> usuarioExiste = usuarioRepository.findById(id);

        if(usuarioExiste.isPresent()){

            var idTopico = topicoRepository.buscarIdDoTopicoDoUsuario(id);
            topicoRepository.deleteById(idTopico);
            usuarioRepository.deleteById(id);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();

    }


}
