package br.com.alura.ForumHub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {


    Page<Topico> findAllByEstadoTopicoTrue(Pageable paginacao);

}
