package br.com.alura.ForumHub.domain.usuario;

import br.com.alura.ForumHub.domain.perfil.Perfil;
import java.util.List;

public record DadosListagemUsuario(
        Long id,
        String nome,
        String login,
        String senha,
        List<Perfil> perfis
) {

}
