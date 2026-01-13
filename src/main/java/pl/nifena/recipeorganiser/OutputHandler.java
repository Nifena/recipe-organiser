package pl.nifena.recipeorganiser;

import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

public class OutputHandler {

    public static void outputOptions(){
        System.out.println();
        System.out.println("""
                Choose one of the available options:
                1. Suggest recipe based on ingredients
                2. Show all recipes
                3. Show a recipe
                4. Add a new recipe
                5. Delete a recipe
                6. Exit""");
    }

    public static void showRecipes(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT * FROM recipe";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> row : rows) {
            System.out.println(row.get("name").toString());
        }
    }

    public static void showRecipe(JdbcTemplate jdbcTemplate, String name) {
        String sql = "SELECT * FROM recipe WHERE name = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, name);
        for (Map<String, Object> row : rows) {
            System.out.println(row.get("name"));
            System.out.println(row.get("ingredients"));
            System.out.println(row.get("how to make"));
        }
    }
}
