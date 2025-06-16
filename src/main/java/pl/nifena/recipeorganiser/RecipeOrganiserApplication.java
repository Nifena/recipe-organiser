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
                System.out.println("Choose one of the available options");
                System.out.println("1. Suggest a recipe based on the ingredients available in the fridge");
                System.out.println("2. Show available recipes");
                System.out.println("3. Add a recipe");
                System.out.println("4. Delete a recipe");
                System.out.println("5. Exit program");

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
                    //duplicate code
                    String sql = "SELECT * FROM recipe";

                    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

                    for (Map<String, Object> row : rows) {
                        System.out.println(row.get("name").toString());
                    }
                } else if (programmeChoice.equals("3")) {

                    System.out.println("Enter recipe name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter ingredients separated by commas:");
                    String ingredients = scanner.nextLine();
                    System.out.println("Enter preparation instructions:");
                    String how_to_make = scanner.nextLine();

                    String sql = "INSERT INTO recipe(name, ingredients, how_to_make)" +
                            "VALUES ('" + name + "', '" + ingredients + "', '" + how_to_make + "')";

                    jdbcTemplate.update(sql);

                } else if (programmeChoice.equals("4")) {
                    System.out.println();
                } else isRunning = false;

            }

        });
    }

    public static boolean linearIn(String[] outer, String[] inner) {
        return Arrays.asList(outer).containsAll(Arrays.asList(inner)); //check if inner ⊂ outer
    }

}

//adding/removing/editing recipes
//options to choose e.g. 1.breakfasts 2.lunches 3.desserts 4.dinners
//option to choose whether we want recipes based on what's in the fridge or any recipe:
//improve recipe entry
