package br.com.alura.ForumHub.domain.topico;

import br.com.alura.ForumHub.domain.curso.Curso;
import br.com.alura.ForumHub.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoTopico(

        @NotBlank(message = "{titulo.obrigatorio}")
        String titulo,

        @NotBlank(message = "{mensagem.obrigatorio}")
        String mensagem,

        @NotBlank(message = "{autor.obrigatorio}")
        Usuario autor,

        @NotBlank(message = "{curso.obrigatorio}")
        Curso curso
) {
    public DadosAtualizacaoTopico(Topico topico){
        this(topico.getTitulo(), topico.getMensagem(), topico.getAutor(),
                topico.getCurso());
    }
}
