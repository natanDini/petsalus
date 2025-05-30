package br.com.petsalus.services;

import br.com.petsalus.dtos.request.EnderecoAdd;
import br.com.petsalus.entities.Endereco;
import br.com.petsalus.enums.Estados;
import br.com.petsalus.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public Endereco salvar(EnderecoAdd enderecoAdd) {

        Endereco endereco = new Endereco();

        endereco.setCep(enderecoAdd.cep());
        endereco.setBairro(enderecoAdd.bairro());
        endereco.setCidade(enderecoAdd.cidade());
        endereco.setNumero(enderecoAdd.numero());
        endereco.setEndereco(enderecoAdd.endereco());
        endereco.setEstado(Estados.valueOf(enderecoAdd.estado()));
        endereco.setComplemento(enderecoAdd.complemento() != null ? enderecoAdd.complemento() : null);

        return enderecoRepository.save(endereco);
    }
}