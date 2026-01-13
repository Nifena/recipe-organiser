package pl.nifena.recipeorganiser;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RecipeServiceGetIngredientsTest {

    @Test
    void shouldReturnMatchingRecipeNamesBasedOnIngredients() {
        // given
        JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);

        Map<String, Object> pancake = new HashMap<>();
        pancake.put("name", "Pancakes");
        pancake.put("ingredients", "flour,egg,milk");

        Map<String, Object> eggs = new HashMap<>();
        eggs.put("name", "Scrambled Eggs");
        eggs.put("ingredients", "egg,butter");

        List<Map<String, Object>> dbRows = List.of(pancake, eggs);

        when(jdbcTemplate.queryForList("SELECT * FROM recipe"))
                .thenReturn(dbRows);

        String[] fridge = {"flour", "egg", "milk", "butter"};
        List<String> recipeNames = new ArrayList<>();

        // when
        List<Map<String, Object>> result =
                RecipeService.getIngredients(jdbcTemplate, fridge, recipeNames);

        // then
        assertEquals(2, result.size());
        assertEquals(2, recipeNames.size());
        assertTrue(recipeNames.contains("Pancakes"));
        assertTrue(recipeNames.contains("Scrambled Eggs"));
    }

    @Test
    void shouldReturnEmptyRecipeNamesWhenNothingMatches() {
        JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);

        Map<String, Object> recipe = new HashMap<>();
        recipe.put("name", "Pancakes");
        recipe.put("ingredients", "flour,egg");

        when(jdbcTemplate.queryForList("SELECT * FROM recipe"))
                .thenReturn(List.of(recipe));

        String[] fridge = {"butter", "milk"};
        List<String> recipeNames = new ArrayList<>();

        RecipeService.getIngredients(jdbcTemplate, fridge, recipeNames);

        assertTrue(recipeNames.isEmpty());
    }
}
