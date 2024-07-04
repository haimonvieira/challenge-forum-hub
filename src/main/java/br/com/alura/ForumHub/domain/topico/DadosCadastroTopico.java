package br.com.alura.ForumHub.domain.topico;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record DadosCadastroTopico(

        @NotBlank
        String titulo,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,

        @NotNull
        Boolean estadoTopico,

        @NotBlank
        String autor,

        @NotBlank
        String curso
) {
}
