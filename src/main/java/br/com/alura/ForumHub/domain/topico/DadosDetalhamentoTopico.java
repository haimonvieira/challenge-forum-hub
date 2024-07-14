package br.com.alura.ForumHub.domain.topico;

import br.com.alura.ForumHub.domain.curso.Curso;
import br.com.alura.ForumHub.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(

        String titulo,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,
        Boolean estadoTopico,
        String autor,
        String curso) {

    public DadosDetalhamentoTopico(Topico topico){
        this(topico.getTitulo(), topico.getData(), topico.isEstadoTopico(), topico.getAutor().getNome(),
                topico.getCurso().getNome());
    }

}
