package br.com.alura.ForumHub.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoTopico(

        @NotBlank(message = "{titulo.obrigatorio}")
        String titulo,

        @NotBlank(message = "{mensagem.obrigatorio}")
        String mensagem,

        @NotBlank(message = "{autor.obrigatorio}")
        String autor,

        @NotBlank(message = "{curso.obrigatorio}")
        String curso
) {
    public DadosAtualizacaoTopico(Topico topico){
        this(topico.getTitulo(), topico.getMensagem(), topico.getAutor(),
                topico.getCurso());
    }
}
