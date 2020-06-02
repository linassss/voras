import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

class Connector{

    //Method for uploading crawled recipes
    public void upload(ArrayList<Recipe> recipes){

        String recipeTable = "recipe";
        String ingredientTable = "ingredient";
        String recipeId = new String();

        try{

            //Connecting to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/database?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","user","password");
            Statement stmt = con.createStatement();

            //Looping through all collected recipes
            for (Recipe recipe : recipes) {

                //Title and url of recipe is inserted in the recipe table
                String updateRecipes = String.format("INSERT INTO %s VALUES (NULL, '%s', '%s')", recipeTable, recipe.url, recipe.title);
                System.out.println(updateRecipes);
                stmt.executeUpdate(updateRecipes);

                //Getting unique recipe id from the recipe table
                ResultSet rsId = stmt.executeQuery("(SELECT id FROM recipe WHERE url = '" + recipe.url + "')");
                while(rsId.next()) recipeId = rsId.getString(1);

                //Inserting all the ingredients to the ingredient table with their corresponding recipe ids
                for (String ingredient : recipe.ingredients) {
                    String updateIngredients = String.format("INSERT INTO %s VALUES (NULL, '%s', '%s')", ingredientTable, recipeId, ingredient);
                    System.out.println(updateIngredients);
                    stmt.executeUpdate(updateIngredients);
                }
            }

            //Disconnecting
            con.close();

        }catch(Exception e){
            System.out.println(e);
        }
    }

    //Method for searching for recipes
    public Hashtable<String,String> searchForRecipe(ArrayList<String> keywords){

        int keywordCount = keywords.size();
        Hashtable<String,String> foundRecipes = new Hashtable<>();

        //Constructing part of SQL query
        String queryKeywords = String.join("\", \"", keywords);

        try{

            //Connecting to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/database?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","user","password");
            Statement stmt = con.createStatement();

            //Constructing SQL query string
            String query = "SELECT title, url FROM recipe JOIN ingredient ON recipe.id = ingredient.recipe_id WHERE ingredient in (\"" + queryKeywords + "\") GROUP BY title, url HAVING COUNT(ingredient) = " + keywordCount + ";";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                foundRecipes.put(rs.getString(1), rs.getString(2));
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
            }

            //Disconnecting
            con.close();

        }catch(Exception e){
            System.out.println(e);
        }
        return foundRecipes;
    }

    //Method for checking the most recent recipe in database
    public String checkNewest() {

        String newestRecipe = "empty";

        try{

            //Connecting to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/database?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","user","password");
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            //Getting url of the most recently uploaded recipe
            String query = "SELECT url FROM recipe";
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
                rs.last();
                newestRecipe = rs.getString("url");
                System.out.println(("newest recipe in db: " + newestRecipe));
            }

            //Disconnecting
            con.close();

        }catch(Exception e){
            System.out.println(e);
        }
        return newestRecipe;
    }
}