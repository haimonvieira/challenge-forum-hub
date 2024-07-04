package br.com.alura.ForumHub.domain.topico;


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
    private LocalDateTime data;
    private boolean estadoTopico;
    private String autor;
    private String curso;

    public Topico(DadosCadastroTopico dados){

        //Quando iniciar um topico ele estara ativo
        this.estadoTopico = true;
        this.titulo = dados.titulo();;
        this.data = dados.data();
        this.autor = dados.autor();
        this.curso = dados.curso();

    }

    public void desativarTopico(){
        this.estadoTopico = false;
    }

}
