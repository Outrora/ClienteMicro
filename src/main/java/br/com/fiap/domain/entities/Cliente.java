package br.com.fiap.domain.entities;

import lombok.*;

import java.util.Optional;

@Getter()
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Cliente {

    private String nome;
    private String email;
    private String cpf;
    private Optional<Long> id;


    public String getCpf() {
        return cpf.replaceAll("[.-]", "");
    }
}
