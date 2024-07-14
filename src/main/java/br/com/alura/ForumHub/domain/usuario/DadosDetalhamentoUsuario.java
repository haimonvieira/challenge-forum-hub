package br.com.alura.ForumHub.domain.usuario;

import br.com.alura.ForumHub.domain.perfil.Perfil;
import jakarta.persistence.ManyToMany;
import java.util.List;

public record DadosDetalhamentoUsuario(
        Long id,
        String login,
        String senha,
        List<Perfil> perfis

) {
    public DadosDetalhamentoUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getLogin(), usuario.getSenha(), usuario.getPerfis());
    }
}
