package com.bastos.os.services;
import com.bastos.os.domain.Cliente;
import com.bastos.os.domain.Pessoa;
import com.bastos.os.dtos.ClienteDTO;
import com.bastos.os.repositories.ClienteRepository;
import com.bastos.os.repositories.PessoaRepository;
import com.bastos.os.services.exceptions.DataIntegratyViolationException;
import com.bastos.os.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Cliente findById(Integer id) {

        Optional<Cliente> obj = clienteRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id: "
                + id
                + ", Tipo: "
                + Cliente.class.getName()));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente create(ClienteDTO clienteDTO) {
        if (findByCpf(clienteDTO) != null) {
            throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
        }
        Cliente newObj = new Cliente(null, clienteDTO.getNome(), clienteDTO.getCpf(), clienteDTO.getTelefone());
        return clienteRepository.save(newObj);
    }

    private Pessoa findByCpf(ClienteDTO objDTO) {
        Pessoa obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj != null) {
            return obj;
        }
        return null;
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getList().size() > 0) {
            throw new DataIntegratyViolationException("Cliente possui ordem de serviço, não pode ser deletado");

        }
        clienteRepository.deleteById(id);
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        Cliente oldObj = findById(id);
        if (findByCpf(objDTO) != null && findByCpf(objDTO).getId() != id) {
            throw new DataIntegratyViolationException("CPF já cadastrado na base de dados");
        }

        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getCpf());
        oldObj.setTelefone(objDTO.getTelefone());

        return clienteRepository.save(oldObj);

    }
}
