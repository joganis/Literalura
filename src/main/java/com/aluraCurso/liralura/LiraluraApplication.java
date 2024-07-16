package com.aluraCurso.liralura;

import com.aluraCurso.liralura.principal.Principal;
import com.aluraCurso.liralura.repository.AutorRepository;
import com.aluraCurso.liralura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LiraluraApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepository, libroRepository);
		principal.muestramenu();
	}


}