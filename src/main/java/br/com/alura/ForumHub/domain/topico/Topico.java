package br.com.alura.ForumHub.domain.topico;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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
    private String autor;
    private String curso;

    public Topico(DadosCadastroTopico dados){

        //Quando iniciar um topico ele estara ativo
        this.estadoTopico = true;
        this.titulo = dados.titulo();;
        this.mensagem = dados.mensagem();
        this.data = LocalDateTime.now();
        this.autor = dados.autor();
        this.curso = dados.curso();

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
