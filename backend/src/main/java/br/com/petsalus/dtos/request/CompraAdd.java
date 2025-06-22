package br.com.petsalus.dtos.request;

public record CompraAdd (
        boolean retirarNaLoja,
        boolean receberNoMeuEndereco,
        EnderecoAdd endereco
){
}
