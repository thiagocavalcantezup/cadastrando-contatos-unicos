package br.com.zup.handora.cadastrandocontatosunicos.contato;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ContatoRequest {

    @NotBlank
    @Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}$")
    private String telefone;

    @NotBlank
    private String funcionarioResponsavel;

    @NotNull
    @Past
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;

    public ContatoRequest() {}

    public ContatoRequest(@NotBlank @Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}$") String telefone,
                          @NotBlank String funcionarioResponsavel,
                          @NotNull @Past LocalDate dataCadastro) {
        this.telefone = telefone;
        this.funcionarioResponsavel = funcionarioResponsavel;
        this.dataCadastro = dataCadastro;
    }

    public Contato toModel() {
        return new Contato(telefone, funcionarioResponsavel, dataCadastro);
    }

    public String getTelefone() {
        return telefone;
    }

    public String getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

}
