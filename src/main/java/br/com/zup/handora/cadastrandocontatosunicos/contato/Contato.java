package br.com.zup.handora.cadastrandocontatosunicos.contato;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import br.com.zup.handora.cadastrandocontatosunicos.departamento.Departamento;

@Entity
@Table(name = "contatos", uniqueConstraints = {
        @UniqueConstraint(name = "UK_CONTATO_TELEFONE_DEPARTAMENTO", columnNames = {
                "telefone", "departamento_id"})})
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}$")
    private String telefone;

    @Column(nullable = false)
    private String funcionarioResponsavel;

    @Column(nullable = false)
    @Past
    private LocalDate dataCadastro;

    @ManyToOne(optional = false)
    private Departamento departamento;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Contato() {}

    public Contato(@Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}$") String telefone,
                   String funcionarioResponsavel, @Past LocalDate dataCadastro) {
        this.telefone = telefone;
        this.funcionarioResponsavel = funcionarioResponsavel;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return id;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

}
