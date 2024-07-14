package br.com.alura.ForumHub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroCurso(

        @NotBlank(message = "Nome é obrigatório.")
        String nome,
        @NotNull(message = "Categoria do curso é obrigatória.")
        Categoria categoria
) {
}
