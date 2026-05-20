package br.com.haroldo.sgm.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    private Long id;
    private String rua;
    private int numero;
    private String cidade;
    private String uf;
    private String cep;

}