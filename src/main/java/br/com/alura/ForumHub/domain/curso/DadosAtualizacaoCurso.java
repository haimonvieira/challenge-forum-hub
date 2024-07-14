package br.com.alura.ForumHub.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoCurso(
        @NotBlank
        String nome,
        Categoria categoria
) {

    public DadosAtualizacaoCurso(Curso curso){
        this(curso.getNome(), curso.getCategoria());
    }
}
