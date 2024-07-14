package br.com.alura.ForumHub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TopicoRepository extends JpaRepository<Topico, Long> {


    Page<Topico> findAllByEstadoTopicoTrue(Pageable paginacao);

    @Query("SELECT t.id FROM Topico t WHERE t.autor.id = :id")
    Long buscarIdDoTopicoDoUsuario(Long id);

    @Query("SELECT t.id FROM Topico t WHERE t.curso.id = :id")
    Long buscarIdDoTopicoDoCurso(Long id);

}
