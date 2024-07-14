package br.com.alura.ForumHub.domain.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Curso(DadosCadastroCurso dados) {
        this.nome = dados.nome();
        this.categoria = dados.categoria();
    }

    public void atualizar(DadosAtualizacaoCurso dados) {

        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if (dados.categoria() != null){
            this.categoria = dados.categoria();
        }

    }
}
