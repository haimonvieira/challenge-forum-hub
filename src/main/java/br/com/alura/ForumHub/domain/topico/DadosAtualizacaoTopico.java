package br.com.alura.ForumHub.domain.topico;

public record DadosAtualizacaoTopico(
        Long id,
        String titulo,
        String mensagem,
        String autor,
        String curso
) {
    public DadosAtualizacaoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getAutor(),
                topico.getCurso());
    }
}
