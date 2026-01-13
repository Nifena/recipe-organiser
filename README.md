# Recipe Organiser

## Project Overview

A Spring Boot command-line application that helps you organize recipes and discover what you can cook based on available ingredients. The application uses a MySQL database to store recipes and provides an interactive console interface for managing your recipe collection.

## Features

1. **Smart Recipe Suggestions**:
   - Enter ingredients you have in your fridge
   - Get instant recipe suggestions based on available ingredients
   - View full preparation instructions for suggested recipes

2. **Recipe Management**:
   - View all recipes in your collection
   - Display detailed recipe information (ingredients and instructions)
   - Add new recipes with custom ingredients and step-by-step instructions
   - Delete recipes from your collection

3. **Database Integration**:
   - Persistent storage using MySQL database
   - Pre-loaded with sample recipes (Pancakes, Scrambled Eggs)
   - Automatic database initialization on first run

4. **Interactive Console Interface**:
   - User-friendly menu system
   - Clear prompts and feedback
   - Easy navigation between features

## System Requirements

- **Java Development Kit (JDK) 21** or higher
- **Apache Maven** 3.9.9 or higher (included via Maven Wrapper)
- **Docker** (for running MySQL database)
- **Docker Compose** (optional, for easier setup)
- Supported operating systems:
  - Windows
  - macOS
  - Linux

## Dependencies

### Core Dependencies
- **Spring Boot 3.4.5** - Application framework
- **Spring Boot JDBC Starter** - Database connectivity
- **MySQL Connector/J** - MySQL database driver

### Development Dependencies
- **Spring Boot Test Starter** - Testing framework
- **JUnit Jupiter** - Unit testing
- **Mockito** - Mocking framework for tests

## Installation

### 1. Clone the Repository

```bash
git clone <repository_url>
cd recipe-organiser
```

### 2. Set Up MySQL Database

The project includes a Dockerfile for easy MySQL setup.

**Build and run the MySQL container**:

```bash
docker build -t recipe-mysql .
docker run -d \
  --name recipe-db \
  -p 3307:3306 \
  -e MYSQL_ROOT_PASSWORD=root \
  recipe-mysql
```

**Alternative: Using docker-compose** (create `docker-compose.yml`):

```yaml
version: '3.8'
services:
  mysql:
    build: .
    container_name: recipe-db
    ports:
      - "3307:3306"
    environment:
      MYSQL_ROOT_PASSWORD: root
```

Then run:
```bash
docker-compose up -d
```

### 3. Verify Database Setup

The database will be automatically initialized with:
- Database name: `recipes`
- Table: `recipe` (with columns: id, name, ingredients, how_to_make)
- Sample recipes: Pancakes and Scrambled Eggs

### 4. Build the Project

Using Maven Wrapper (recommended):

```bash
./mvnw clean package
```

Or on Windows:
```cmd
mvnw.cmd clean package
```

## Running the Application

### Using Maven Wrapper

**macOS/Linux**:
```bash
./mvnw spring-boot:run
```

**Windows**:
```cmd
mvnw.cmd spring-boot:run
```

### Using Compiled JAR

```bash
java -jar target/recipe-organiser-0.0.1-SNAPSHOT.jar
```

## Usage Guide

Once the application starts, you'll see a menu with the following options:

```
Choose one of the available options:
1. Suggest recipe based on ingredients
2. Show all recipes
3. Show a recipe
4. Add a new recipe
5. Delete a recipe
6. Exit
```

### 1. Suggest Recipe Based on Ingredients

- Select option `1`
- Enter ingredients you have, separated by commas (e.g., `egg, butter, salt`)
- The app will show recipes you can make with those ingredients
- If multiple recipes match, you can choose which one to view
- Full preparation instructions will be displayed

**Example**:
```
Enter the ingredients you have in your fridge (separated by commas):
egg, butter, salt, pepper

You can prepare following recipes:
Scrambled Eggs

Select the recipe you want to prepare:
Scrambled Eggs
```

### 2. Show All Recipes

- Select option `2`
- View a list of all recipe names in the database

### 3. Show a Recipe

- Select option `3`
- Enter the exact recipe name
- View full recipe details (name, ingredients, instructions)

### 4. Add a New Recipe

- Select option `4`
- Enter recipe name
- Enter ingredients separated by commas
- Enter preparation steps (one per line)
- Type `.` on a new line to finish entering steps

**Example**:
```
Enter recipe name:
Omelette

Enter ingredients separated by commas:
egg, butter, cheese, salt, pepper

Enter preparation instructions in points - new line for each step. Press '.' in separate line to finish:
1. Beat eggs with salt and pepper
2. Heat butter in a pan
3. Pour eggs into the pan
4. Add cheese when eggs start to set
5. Fold and serve
.

Recipe added successfully!
```

### 5. Delete a Recipe

- Select option `5`
- Enter the exact name of the recipe to delete
- Confirmation message will be displayed

### 6. Exit

- Select option `6` to close the application

## Configuration

### Database Connection

The database configuration is in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/recipes
spring.datasource.username=root
spring.datasource.password=root
```

**To modify database settings**:

1. Change the port number (default: `3307`)
2. Update username/password if using different credentials
3. Restart the application

### Docker MySQL Configuration

The `Dockerfile` sets up MySQL with:
- Database name: `recipes` (set via `MYSQL_DATABASE` environment variable)
- Initialization script: `scripts/create_table.sql`
- Port mapping: `3307:3306` (host:container)

## Project Structure

```
recipe-organiser/
├── Dockerfile                          # MySQL container configuration
├── scripts/
│   └── create_table.sql               # Database initialization script
├── src/
│   ├── main/
│   │   ├── java/pl/nifena/recipeorganiser/
│   │   │   ├── RecipeOrganiserApplication.java  # Main application
│   │   │   ├── RecipeService.java               # Business logic
│   │   │   └── OutputHandler.java               # Console output handling
│   │   └── resources/
│   │       └── application.properties   # Database configuration
│   └── test/
│       └── java/pl/nifena/recipeorganiser/
│           ├── RecipeOrganiserApplicationTests.java
│           ├── RecipeServiceTest.java
│           └── RecipeServiceGetIngredientsTest.java
├── .mvn/                               # Maven wrapper configuration
├── mvnw                                # Maven wrapper script (Unix)
├── mvnw.cmd                            # Maven wrapper script (Windows)
├── pom.xml                             # Maven project configuration
└── README.md
```

## Testing

The project includes comprehensive unit tests using JUnit and Mockito.

### Run All Tests

```bash
./mvnw test
```

### Test Coverage

- **RecipeServiceTest**: Tests the ingredient matching logic
- **RecipeServiceGetIngredientsTest**: Tests recipe retrieval based on ingredients
- **RecipeOrganiserApplicationTests**: Tests database operations (delete recipe)

### Example Test Cases

- Verifying ingredient subset matching
- Testing recipe suggestions with various ingredient combinations
- Validating database delete operations
- Edge cases (empty ingredients, no matches)

## Troubleshooting

### Database Connection Failed

**Error**: `Communications link failure`

**Solutions**:
- Verify MySQL container is running: `docker ps`
- Check if port 3307 is available: `netstat -an | grep 3307`
- Restart the container: `docker restart recipe-db`
- Verify database credentials in `application.properties`

### Container Already Exists

**Error**: `The container name "/recipe-db" is already in use`

**Solutions**:
```bash
# Remove existing container
docker rm -f recipe-db

# Start a new container
docker run -d --name recipe-db -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root recipe-mysql
```

### Application Can't Find Recipes Table

**Error**: `Table 'recipes.recipe' doesn't exist`

**Solutions**:
1. Stop the container: `docker stop recipe-db`
2. Remove the container: `docker rm recipe-db`
3. Rebuild the image: `docker build -t recipe-mysql .`
4. Start fresh: `docker run -d --name recipe-db -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root recipe-mysql`
5. Wait 10-15 seconds for initialization, then restart the application

### Invalid Option Error

**Issue**: Entering non-numeric input causes "Invalid option" message

**Solution**: Enter only numbers 1-6 as shown in the menu

### Recipe Not Found When Viewing

**Issue**: Recipe exists but shows no results

**Solution**: Ensure you enter the exact recipe name (case-insensitive matching is applied)

## Database Schema

### Recipe Table Structure

```sql
CREATE TABLE recipe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    ingredients TEXT,
    how_to_make TEXT
);
```

### Sample Data

The database is pre-loaded with:

1. **Pancakes**
   - Ingredients: flour, baking powder, sugar, salt, milk, butter, egg
   - 5 preparation steps

2. **Scrambled Eggs**
   - Ingredients: egg, butter, pepper, salt
   - 10 preparation steps

## How the Recipe Suggestion Works

The application uses a subset matching algorithm:

1. You enter ingredients you have (e.g., `egg, butter, salt`)
2. The app queries all recipes from the database
3. For each recipe, it checks if all recipe ingredients are in your list
4. If your ingredients include ALL recipe ingredients, it's a match
5. Matched recipes are displayed for selection

**Example**:
- Your fridge: `egg, butter, salt, pepper, milk`
- Recipe needs: `egg, butter, salt, pepper`
- ✅ Match! (recipe ingredients ⊂ your ingredients)

## Security Considerations

- **Database Credentials**: Default credentials (`root/root`) are for development only
- **Production Deployment**: 
  - Use strong, unique passwords
  - Store credentials in environment variables
  - Use Docker secrets for sensitive data
  - Implement proper user authentication

## Future Enhancements

- Web-based user interface (REST API + frontend)
- Recipe categories and tags (breakfast, lunch, dinner, dessert)
- Nutritional information tracking
- Recipe ratings and reviews
- Shopping list generation based on missing ingredients
- Recipe import/export functionality (JSON, CSV)
- Image upload for recipes
- Serving size calculator
- Recipe search by name, ingredient, or category
- User accounts with personal recipe collections
- Recipe sharing between users
- Dietary restriction filters (vegetarian, vegan, gluten-free)
- Cooking time and difficulty level indicators

## Docker Commands Reference

### Basic Container Management

```bash
# Start container
docker start recipe-db

# Stop container
docker stop recipe-db

# View container logs
docker logs recipe-db

# Access MySQL shell
docker exec -it recipe-db mysql -uroot -proot recipes

# Remove container
docker rm -f recipe-db

# View running containers
docker ps

# View all containers (including stopped)
docker ps -a
```

### Database Management

```bash
# Backup database
docker exec recipe-db mysqldump -uroot -proot recipes > backup.sql

# Restore database
docker exec -i recipe-db mysql -uroot -proot recipes < backup.sql
```

## License

This project is for educational and experimental purposes only. No license is specified — all rights reserved unless otherwise stated.

## Support

For issues and questions:
- Check the troubleshooting section above
- Review Docker documentation: [docs.docker.com](https://docs.docker.com)
- Review Spring Boot JDBC documentation: [spring.io/guides/gs/relational-data-access](https://spring.io/guides/gs/relational-data-access)

## Acknowledgments

- Built with Spring Boot framework
- Database powered by MySQL
- Containerized with Docker
