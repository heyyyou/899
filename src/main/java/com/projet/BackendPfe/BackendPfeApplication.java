package com.projet.BackendPfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.projet.BackendPfe.Controller.ConsulationController;

import java.io.File;
@SpringBootApplication
@ComponentScan({"com.projet.BackendPfe","com.projet.BackendPfe.Controller"})
public class BackendPfeApplication {

	public static void main(String[] args) {
		new File(ConsulationController.uploadDirectory).mkdir();
		SpringApplication.run(BackendPfeApplication.class, args);
	}
}
	