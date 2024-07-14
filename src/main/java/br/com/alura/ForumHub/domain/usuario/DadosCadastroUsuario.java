package br.com.alura.ForumHub.domain.usuario;

import br.com.alura.ForumHub.domain.perfil.Perfil;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DadosCadastroUsuario(

        @NotBlank
        String nome,
        @NotBlank
        String login,
        @NotBlank
        String senha,
        List<Perfil> perfis
) {
    public DadosCadastroUsuario(Usuario usuario){
        this(usuario.getUsername(), usuario.getLogin(), usuario.getSenha(), usuario.getPerfis());
    }
}
