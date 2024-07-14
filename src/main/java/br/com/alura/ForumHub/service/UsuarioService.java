package br.com.alura.ForumHub.service;

import br.com.alura.ForumHub.domain.usuario.Usuario;
import br.com.alura.ForumHub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Long getId(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = ((UserDetails) authentication.getPrincipal()).getUsername();

        var usuario = usuarioRepository.findByLogin(login);

        return usuario.getId();

    }

}
