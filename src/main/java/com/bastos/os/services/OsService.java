package com.bastos.os.services;
import com.bastos.os.domain.Cliente;
import com.bastos.os.domain.OS;
import com.bastos.os.domain.Tecnico;
import com.bastos.os.domain.enuns.Prioridade;
import com.bastos.os.domain.enuns.Status;
import com.bastos.os.dtos.OSDTO;
import com.bastos.os.repositories.OSRepository;
import com.bastos.os.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OsService {

    @Autowired
    private OSRepository osRepository;

    @Autowired
    private TecnicoService tecnicoService;

    @Autowired
    private ClienteService clienteService;


    public OS findById(Integer id) {
        Optional<OS> obj = osRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "
                + id + ", Tipo: "
                + OS.class.getName()));

    }

    public List<OS> findAll() {
        return osRepository.findAll();
    }


    public OS create(OSDTO obj) {
        return fromDTO(obj);
    }

    public OS update(OSDTO obj) {
        findById(obj.getId());
        return fromDTO(obj);
    }

    private OS fromDTO(OSDTO obj) {
        OS newObj = new OS();
        newObj.setId(obj.getId());
        newObj.setObservacoes(obj.getObservacoes());
        newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        newObj.setStatus(Status.toEnum(obj.getStatus()));

        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());

        newObj.setTecnico(tecnico);
        newObj.setCliente(cliente);

        if (newObj.getStatus().getCod().equals(2)) {
            newObj.setDataFechamento(LocalDateTime.now());
        }

        return osRepository.save(newObj);
    }


}
