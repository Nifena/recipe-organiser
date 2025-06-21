package pl.nifena.recipeorganiser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

@SpringBootApplication
public class RecipeOrganiserApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeOrganiserApplication.class, args);
    }

    @Bean
    public CommandLineRunner runApp(JdbcTemplate jdbcTemplate) {
        return (args -> {
            boolean isRunning = true;
            Scanner scanner = new Scanner(System.in);

            while (isRunning) {
                OutputHandler.outputOptions();
                String programmeChoice = scanner.nextLine();

                if (programmeChoice.equals("1")) {
                    RecipeService.suggestRecipe(jdbcTemplate, scanner);
                } else if (programmeChoice.equals("2")) {
                    OutputHandler.showRecipes(jdbcTemplate);
                } else if (programmeChoice.equals("3")) {
                    RecipeService.addRecipe(jdbcTemplate, scanner);
                } else if (programmeChoice.equals("4")) {
                    RecipeService.deleteRecipe(jdbcTemplate, scanner);
                } else isRunning = false;
            }
        });
    }



}