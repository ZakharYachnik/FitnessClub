package by.zakharyachnik.fitnessclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FitnessClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessClubApplication.class, args);
		System.out.println("http://localhost:8080/");
	}

}
