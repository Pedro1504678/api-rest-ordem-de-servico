package com.bastos.os.services;
import com.bastos.os.domain.Cliente;
import com.bastos.os.domain.OS;
import com.bastos.os.domain.Tecnico;
import com.bastos.os.domain.enuns.Prioridade;
import com.bastos.os.domain.enuns.Status;
import com.bastos.os.repositories.ClienteRepository;
import com.bastos.os.repositories.OSRepository;
import com.bastos.os.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OSRepository osRepository;

    public void instanciaDB() {

        Tecnico t1 = new Tecnico(null, "Pedro Bastos", "498.608.710-00", "(88) 98888-8888");
        Tecnico t2 = new Tecnico(null, "José Bastos", "281.001.020-07", "(88) 98888-8545");
        Cliente c1 = new Cliente(null, "Betina Campos", "157.717.860-20", "(88) 98888-8888");
        OS os1 = new OS(null, Prioridade.ALTA, "Testeeeee create OD", Status.ANDAMENTO, t1, c1);

        t1.getList().add(os1);
        c1.getList().add(os1);

        tecnicoRepository.saveAll(Arrays.asList(t1, t2));
        clienteRepository.saveAll(Arrays.asList(c1));
        osRepository.saveAll(Arrays.asList(os1));
    }

}
