package com.bastos.os;

import com.bastos.os.domain.Cliente;
import com.bastos.os.domain.OS;
import com.bastos.os.domain.Tecnico;
import com.bastos.os.domain.enuns.Prioridade;
import com.bastos.os.domain.enuns.Status;
import com.bastos.os.repositories.ClienteRepository;
import com.bastos.os.repositories.OSRepository;
import com.bastos.os.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class OsApplication {


    public static void main(String[] args) {
        SpringApplication.run(OsApplication.class, args);
    }


}
