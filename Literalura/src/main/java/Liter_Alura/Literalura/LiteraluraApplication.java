package Liter_Alura.Literalura;

import Liter_Alura.Literalura.Principal.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final Menu menu;

    public LiteraluraApplication(Menu menu) {this.menu = menu;}

    public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		menu.exibeMenu();
	}
}
