package br.com.alura.ForumHub.domain.usuario;

public record DadosAtualizacaoUsuario(
        String nome,
        String login,
        String senha
) {
    public DadosAtualizacaoUsuario(Usuario usuario){
        this(usuario.getNome(), usuario.getLogin(), usuario.getSenha());
    }
}
