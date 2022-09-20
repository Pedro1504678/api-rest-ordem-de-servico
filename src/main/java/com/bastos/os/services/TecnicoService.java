package com.bastos.os.services;
import com.bastos.os.domain.Pessoa;
import com.bastos.os.domain.Tecnico;
import com.bastos.os.dtos.TecnicoDTO;
import com.bastos.os.repositories.PessoaRepository;
import com.bastos.os.repositories.TecnicoRepository;
import com.bastos.os.services.exceptions.DataIntegratyViolationException;
import com.bastos.os.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {


    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = tecnicoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Tecnico não encontrado! Id: "
                + id
                + ", Tipo: "
                + Tecnico.class.getName()));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        if (findByCpf(objDTO) != null) {
            throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
        }
        Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
        return tecnicoRepository.save(newObj);
    }

    public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
        Tecnico oldObj = findById(id);
        if (findByCpf(objDTO) != null && findByCpf(objDTO).getId() != id) {
            throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
        }

        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getCpf());
        oldObj.setTelefone(objDTO.getTelefone());

        return tecnicoRepository.save(oldObj);

    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if(obj.getList().size() > 0){
            throw new DataIntegratyViolationException("Técnico possui Ordens de Serviço, não pode ser deletado");
        }
        tecnicoRepository.deleteById(id);
    }

    private Pessoa findByCpf(TecnicoDTO objDTO) {
        Pessoa obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj != null) {
            return obj;
        }
        return null;
    }


}
