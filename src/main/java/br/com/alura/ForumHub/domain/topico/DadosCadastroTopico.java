package br.com.alura.ForumHub.domain.topico;

import jakarta.validation.constraints.NotBlank;

//Cadastro do topico
public record DadosCadastroTopico(

        @NotBlank(message = "{titulo.obrigatorio}")
        String titulo,

        @NotBlank(message = "{mensagem.obrigatorio}")
        String mensagem,

        @NotBlank(message = "{autor.obrigatorio}")
        String autor,

        @NotBlank(message = "{curso.obrigatorio}")
        String curso
) {
}
