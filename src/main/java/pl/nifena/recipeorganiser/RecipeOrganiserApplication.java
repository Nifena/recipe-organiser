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
                System.out.println("Wybierz jedną z dostępnych opcji");
                System.out.println("1. Zaproponuj przepis na podstawie składników dostępnych w lodówce");
                System.out.println("2. Pokaż dostępne przepisy");
                System.out.println("3. Zakończ program");
                String programmeChoice = scanner.nextLine();

                if (programmeChoice.equals("1")) {

                    System.out.println("Enter the ingredients you have in your fridge (separated by commas):");
                    String input = scanner.nextLine();
                    String[] fridge = Arrays.stream(input.split(",")).map(String::trim).toArray(String[]::new);

                    List<String> recipeNames = new ArrayList<>();

                    String sql = "SELECT * FROM recipe";

                    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

                    for (Map<String, Object> row : rows) {
                        String ingredients = (String) row.get("ingredients");
                        String[] splitIngredients = Arrays.stream(ingredients.split(",")).map(String::trim).toArray(String[]::new);

                        if (linearIn(fridge, splitIngredients)) {
                            recipeNames.add(row.get("name").toString());
                        }
                    }

                    if (!recipeNames.isEmpty()) {
                        System.out.println("You can prepare: " + recipeNames);

                        System.out.println("Select the recipe you want to prepare:");
                        String choice = scanner.nextLine();

                        for (var row : rows) {
                            if (row.get("name").toString().equalsIgnoreCase(choice)) {
                                System.out.println(row.get("name"));
                                System.out.println(row.get("how_to_make"));
                            }
                        }
                    } else {
                        System.out.println("Unfortunately, you cannot prepare anything from the list with the provided ingredients.");
                    }
                } else if (programmeChoice.equals("2")) {
                    //duplikacja kodu
                    String sql = "SELECT * FROM recipe";

                    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

                    for (Map<String, Object> row : rows) {
                        System.out.println(row.get("name").toString());
                    }
                }else {
                    isRunning = false;
                }

            }

        });
    }

    public static boolean linearIn(String[] outer, String[] inner) {
        return Arrays.asList(outer).containsAll(Arrays.asList(inner)); //check if inner c outer
    }



}
//dodawanie/usuwanie/edytowanie przepisow
//opcje do wyboru np 1.sniadania 2.obiady 3.desery 4.kolacje
//opcja do wyboru czy chcemy przepisy wedlug tego co jest w lodowce czy dowolny wybor tj: