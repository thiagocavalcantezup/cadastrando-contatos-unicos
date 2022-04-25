package br.com.zup.handora.cadastrandocontatosunicos.departamento;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DepartamentoRequest {

    @NotBlank
    @Size(max = 120)
    private String nome;

    @NotBlank
    @Size(min = 1, max = 3)
    @Pattern(regexp = "^[A-Z]{1,3}$")
    private String sigla;

    public DepartamentoRequest() {}

    public DepartamentoRequest(@NotBlank @Size(max = 120) String nome,
                               @NotBlank @Size(max = 3) @Pattern(regexp = "^[A-Z]{1, 3}$") String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public Departamento toModel() {
        return new Departamento(nome, sigla);
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

}
