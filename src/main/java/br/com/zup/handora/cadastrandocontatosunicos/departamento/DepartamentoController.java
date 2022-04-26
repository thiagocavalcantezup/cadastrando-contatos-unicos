package br.com.zup.handora.cadastrandocontatosunicos.departamento;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.handora.cadastrandocontatosunicos.contato.Contato;
import br.com.zup.handora.cadastrandocontatosunicos.contato.ContatoRepository;
import br.com.zup.handora.cadastrandocontatosunicos.contato.ContatoRequest;

@RestController
@RequestMapping(DepartamentoController.BASE_URI)
public class DepartamentoController {

    public final static String BASE_URI = "/departamentos";

    private final DepartamentoRepository departamentoRepository;
    private final ContatoRepository contatoRepository;

    public DepartamentoController(DepartamentoRepository departamentoRepository,
                                  ContatoRepository contatoRepository) {
        this.departamentoRepository = departamentoRepository;
        this.contatoRepository = contatoRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid DepartamentoRequest departamentoRequest,
                                    UriComponentsBuilder ucb) {
        if (departamentoRepository.existsBySigla(departamentoRequest.getSigla())) {
            throw new ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Já existe um departamento cadastrado com essa sigla."
            );
        }

        Departamento departamento = departamentoRepository.save(departamentoRequest.toModel());

        URI location = ucb.path(BASE_URI + "/{id}").buildAndExpand(departamento.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Transactional
    @PostMapping("/{departamentoId}/contatos")
    public ResponseEntity<?> createContato(@RequestBody @Valid ContatoRequest contatoRequest,
                                           @PathVariable Long departamentoId,
                                           UriComponentsBuilder ucb) {
        Departamento departamento = departamentoRepository.findById(departamentoId)
                                                          .orElseThrow(
                                                              () -> new ResponseStatusException(
                                                                  HttpStatus.NOT_FOUND,
                                                                  "Não existe um departamento com o id fornecido."
                                                              )
                                                          );

        if (contatoRepository.existsByTelefoneAndDepartamentoId(
            contatoRequest.getTelefone(), departamentoId
        )) {
            throw new ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Já existe um contato cadastrado com esse telefone nesse departamento."
            );
        }

        Contato contato = contatoRequest.toModel();
        departamento.adicionar(contato);

        departamentoRepository.flush();

        URI location = ucb.path(DepartamentoController.BASE_URI + "/{departamentoId}/contatos/{id}")
                          .buildAndExpand(departamentoId, contato.getId())
                          .toUri();

        return ResponseEntity.created(location).build();
    }

}
