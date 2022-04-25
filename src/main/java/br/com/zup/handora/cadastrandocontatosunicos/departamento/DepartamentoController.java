package br.com.zup.handora.cadastrandocontatosunicos.departamento;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(DepartamentoController.BASE_URI)
public class DepartamentoController {

    public final static String BASE_URI = "/departamentos";

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoController(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
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

}
