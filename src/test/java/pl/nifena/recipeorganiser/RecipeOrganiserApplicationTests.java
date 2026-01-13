package pl.nifena.recipeorganiser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Scanner;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeOrganiserApplicationTests {

    @Mock
    JdbcTemplate jdbcTemplate;

    @Test
    void shouldDeleteRecipeFromDatabase() {
        // given
        String recipeName = "Pizza";
        Scanner scanner = new Scanner(recipeName + "\n");

        // when
        RecipeService.deleteRecipe(jdbcTemplate, scanner);

        // then
        verify(jdbcTemplate, times(1))
                .update("DELETE FROM recipe WHERE name = ?", recipeName);
    }

}
