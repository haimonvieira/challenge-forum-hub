package br.com.alura.ForumHub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Cadastro do topico
public record DadosCadastroTopico(

        @NotBlank(message = "{titulo.obrigatorio}")
        String titulo,

        @NotBlank(message = "{mensagem.obrigatorio}")
        String mensagem,
        @NotNull
        Long idCurso
) {
        public DadosCadastroTopico(Topico topico){
                this(topico.getTitulo(), topico.getMensagem(), topico.getCurso().getId());
        }
}
