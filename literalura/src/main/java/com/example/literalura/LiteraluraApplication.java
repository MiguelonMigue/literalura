package com.example.literalura;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.literalura.principal.Principal;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	private Principal principal;

    public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}
    @Override
	public void run(String ...args)throws Exception{
		principal.mostrarMenu();
	}
}
