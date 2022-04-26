package br.com.zup.handora.cadastrandocontatosunicos.departamento;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.zup.handora.cadastrandocontatosunicos.contato.Contato;

@Entity
@Table(name = "departamentos", uniqueConstraints = {
        @UniqueConstraint(name = "UK_DEPARTAMENTO_SIGLA", columnNames = {"sigla"})})
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 120)
    private String nome;

    @Column(nullable = false)
    @Size(min = 1, max = 3)
    @Pattern(regexp = "^[A-Z]{1,3}$")
    private String sigla;

    @OneToMany(mappedBy = "departamento", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Contato> contatos = new HashSet<>();

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Departamento() {}

    public Departamento(@Size(max = 120) String nome,
                        @Size(max = 3) @Pattern(regexp = "^[A-Z]{1, 3}$") String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public void adicionar(Contato contato) {
        contato.setDepartamento(this);
        contatos.add(contato);
    }

    public Long getId() {
        return id;
    }

}
