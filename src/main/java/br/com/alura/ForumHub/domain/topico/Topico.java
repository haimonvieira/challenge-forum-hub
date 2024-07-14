package br.com.alura.ForumHub.domain.topico;

import br.com.alura.ForumHub.domain.curso.Curso;
import br.com.alura.ForumHub.domain.resposta.Resposta;
import br.com.alura.ForumHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime data;
    private boolean estadoTopico;

    @Column(name = "autor")
    private String nomeAutor;
    @Column(name = "curso")
    private String nomeCurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico")
    private List<Resposta> respostas;


    public Topico(DadosCadastroTopico dados, Usuario autor, Curso curso){

        //Quando iniciar um topico ele estara ativo
        this.estadoTopico = true;
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.data = LocalDateTime.now();
        this.autor = autor;
        this.curso = curso;
        this.nomeAutor = autor.getNome();
        this.nomeCurso = curso.getNome();

    }

    public void atualizarInformacoes(DadosAtualizacaoTopico dados){

        if(dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if(dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
        if(dados.autor() != null){
            this.autor = dados.autor();
        }
        if(dados.curso() != null){
            this.curso = dados.curso();
        }

        this.data = LocalDateTime.now();

    }

    public void desativarTopico(){
        this.estadoTopico = false;
    }

}
