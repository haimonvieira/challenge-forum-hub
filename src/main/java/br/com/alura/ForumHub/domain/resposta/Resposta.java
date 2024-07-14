package br.com.alura.ForumHub.domain.resposta;

import br.com.alura.ForumHub.domain.topico.Topico;
import br.com.alura.ForumHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respostas")
@Entity(name = "Resposta")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;
    private LocalDateTime data = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario autor;
    private String solucao;

}
