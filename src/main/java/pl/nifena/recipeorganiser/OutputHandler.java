package pl.nifena.recipeorganiser;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class OutputHandler {

    public static void outputOptions(){
        System.out.println();
        System.out.println("""
                Choose one of the available options:
                1. Suggest recipe
                2. Show all recipes
                3. Add a new recipe
                4. Delete a recipe
                5. Exit""");
    }

    public static void showRecipes(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT * FROM recipe";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> row : rows) {
            System.out.println(row.get("name").toString());
        }
    }

}
