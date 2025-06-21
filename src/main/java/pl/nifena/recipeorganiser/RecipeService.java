package pl.nifena.recipeorganiser;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class RecipeService {


    static void suggestRecipe(JdbcTemplate jdbcTemplate, Scanner scanner) {
        System.out.println("Enter the ingredients you have in your fridge (separated by commas):");
        String input = scanner.nextLine();
        String[] fridge = Arrays.stream(input.split(",")).map(String::trim).toArray(String[]::new);

        List<String> recipeNames = new ArrayList<>();

        List<Map<String, Object>> rows = getIngredients(jdbcTemplate, fridge, recipeNames);

        prepareList(recipeNames, scanner, rows);
    }


    static void deleteRecipe(JdbcTemplate jdbcTemplate, Scanner scanner) {
        System.out.println("Enter recipe name you want to delete:");
        String delete = scanner.nextLine();
        String sql = "DELETE FROM recipe WHERE name = ?";
        jdbcTemplate.update(sql,delete);
    }

    static void addRecipe(JdbcTemplate jdbcTemplate, Scanner scanner) {
        System.out.println("Enter recipe name:");
        String name = scanner.nextLine();
        System.out.println("Enter ingredients separated by commas:");
        String ingredients = scanner.nextLine();
        System.out.println("Enter preparation instructions:");
        String how_to_make = scanner.nextLine();

        String sql = "INSERT INTO recipe(name, ingredients, how_to_make) VALUES (?,?,?)";
        jdbcTemplate.update(sql, name, ingredients, how_to_make);
    }

    static void prepareList(List<String> recipeNames, Scanner scanner, List<Map<String, Object>> rows) {
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
    }

    static List<Map<String, Object>> getIngredients(JdbcTemplate jdbcTemplate, String[] fridge, List<String> recipeNames) {
        String sql = "SELECT * FROM recipe";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            String ingredients = (String) row.get("ingredients");
            String[] splitIngredients = Arrays.stream(ingredients.split(",")).map(String::trim).toArray(String[]::new);

            if (linearIn(fridge, splitIngredients)) {
                recipeNames.add(row.get("name").toString());
            }
        }
        return rows;
    }


    static boolean linearIn(String[] outer, String[] inner) {
        return Arrays.asList(outer).containsAll(Arrays.asList(inner)); //check if inner ⊂ outer
    }


}
