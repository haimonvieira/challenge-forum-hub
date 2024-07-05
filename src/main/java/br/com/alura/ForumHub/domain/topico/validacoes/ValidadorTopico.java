package br.com.alura.ForumHub.domain.topico.validacoes;

import br.com.alura.ForumHub.domain.topico.DadosDetalhamentoTopico;

public interface ValidadorTopico {
    void validar(DadosDetalhamentoTopico dados);
}
