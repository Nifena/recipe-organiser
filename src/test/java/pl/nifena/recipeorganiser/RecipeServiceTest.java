package pl.nifena.recipeorganiser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceTest {

    @Test
    void shouldReturnTrueWhenInnerIsSubsetOfOuter() {
        String[] fridge = {"egg", "butter", "salt", "pepper"};
        String[] recipe = {"egg", "butter"};

        boolean result = RecipeService.linearIn(fridge, recipe);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenInnerIsNotSubsetOfOuter() {
        String[] fridge = {"egg", "salt"};
        String[] recipe = {"egg", "butter"};

        boolean result = RecipeService.linearIn(fridge, recipe);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueForEmptyInnerArray() {
        String[] fridge = {"egg", "butter"};
        String[] recipe = {};

        boolean result = RecipeService.linearIn(fridge, recipe);

        assertTrue(result);
    }
}
