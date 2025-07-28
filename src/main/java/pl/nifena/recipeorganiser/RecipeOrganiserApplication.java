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

                if(programmeChoice.matches("[1-6]")){
                    switch (programmeChoice) {
                        case "1" -> RecipeService.suggestRecipe(jdbcTemplate, scanner);
                        case "2" -> OutputHandler.showRecipes(jdbcTemplate);
                        case "3" -> {
                            System.out.println("Enter recipe name:");
                            OutputHandler.showRecipe(jdbcTemplate, scanner.nextLine());
                        }
                        case "4" -> RecipeService.addRecipe(jdbcTemplate, scanner);
                        case "5" -> RecipeService.deleteRecipe(jdbcTemplate, scanner);
                        default -> isRunning = false;
                    }
                }else System.out.println("Invalid option, please enter a valid option");
            }
        });
    }
}