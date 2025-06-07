USE recipes;

CREATE TABLE IF NOT EXISTS recipes.recipe (
                                              id INT AUTO_INCREMENT PRIMARY KEY,
                                              name VARCHAR(255),
    ingredients TEXT,
    how_to_make TEXT
    );

INSERT INTO recipes.recipe (name, ingredients, how_to_make)
VALUES (
           'Pancakes',
           'flour,baking powder,sugar,salt,milk,butter,egg',
           '1. Sift the dry ingredients together.\n2. Make a well, then add the wet ingredients. Stir to combine.\n3. Scoop the batter onto a hot griddle or pan.\n4. Cook for two to three minutes, then flip.\n5. Continue cooking until brown on both sides.'
       );

INSERT INTO recipes.recipe (name, ingredients, how_to_make)
VALUES (
           'Scrambled Eggs',
           'egg,butter,pepper,salt',
           '1. Crack the eggs into a bowl.\n2. Add a pinch of salt and pepper.\n3. Beat the eggs with a fork or whisk until well mixed.\n4. Heat a non-stick pan over medium-low heat.\n5. Add the butter and let it melt.\n6. Pour the eggs into the pan.\n7. Stir slowly with a spatula, moving eggs from the edges to the center.\n8. Cook gently until the eggs are just set and still a bit creamy.\n9. Remove from heat immediately.\n10. Serve warm with optional toppings.'
       );