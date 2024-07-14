package br.com.alura.ForumHub.service;

import br.com.alura.ForumHub.domain.curso.CursoRepository;
import br.com.alura.ForumHub.domain.topico.DadosCadastroTopico;
import br.com.alura.ForumHub.domain.topico.DadosDetalhamentoTopico;
import br.com.alura.ForumHub.domain.topico.Topico;
import br.com.alura.ForumHub.domain.topico.TopicoRepository;
import br.com.alura.ForumHub.domain.usuario.Usuario;
import br.com.alura.ForumHub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public Topico criarTopico(DadosCadastroTopico dados){

        //Pegando o usuario logado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = ((UserDetails) authentication.getPrincipal()).getUsername();

        var usuario = usuarioRepository.findByLogin(login);
        System.out.println(usuario.getNome());
        var curso = cursoRepository.getReferenceById(dados.idCurso());
        System.out.println(curso.getNome());

        var topico = new Topico(dados, usuario, curso);

        topicoRepository.save(topico);

        return topico;

    }

}
