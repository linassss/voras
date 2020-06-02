import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


public class Voras {

    static String baseUrl = "https://www.beatosvirtuve.lt/receptai/page/";

    //Method for collecting new links
    public LinkedList<String> collectLinks(int pageNumber) {

        LinkedList<String> listOfURLs = new LinkedList<>();
        LinkedList<String> newList = new LinkedList<>();

        Connector conn = new Connector();
        String newestRecipeInDB = conn.checkNewest();
        System.out.println(newestRecipeInDB);


        try {

            //Creating a connection
            Document doc = Jsoup.connect(baseUrl + pageNumber + "/").get();

            //Selecting the first recipe in a page
            org.jsoup.select.Elements firstLink = doc.select("div.nr_informacija > a:nth-child(1)");
            String firstURL = firstLink.attr("href");
            if (firstURL.equals(newestRecipeInDB)) return listOfURLs;
            System.out.println("added first url: " + firstURL);
            listOfURLs.addFirst(firstURL);


            //Selecting remaining recipes in a page
            org.jsoup.select.Elements links = doc.select("a.ir_nuotrauka");
            for(Element e: links) {
                String link = e.attr("href");
                if (link.equals(newestRecipeInDB)) return listOfURLs;
                System.out.println(e.attr("href"));
                listOfURLs.addFirst(e.attr("href"));
            }
            org.jsoup.select.Elements nextPage = doc.select("div.irasai_toliau > a:nth-child(1)");
            if (!nextPage.attr("href").equals("")) {
                newList = collectLinks(pageNumber+1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(listOfURLs);
        listOfURLs.addAll(0, newList);
        return listOfURLs;
    }

    //Method for collecting new recipes
    public ArrayList<Recipe> collectRecipes() {

        Voras spider = new Voras();
        Ingredient ingredient = new Ingredient();

        //Specify page number from which to start crawling
        int pageToStartFrom = 1;

        //Acquire a list of links to crawl
        LinkedList<String> links = spider.collectLinks(pageToStartFrom);
        int counter = 0;
        ArrayList<Recipe> newRecipes = new ArrayList<>();

        for (String link: links) {
            ArrayList<String> ingredients = new ArrayList<>();

            try {

                //Scrape recipe web page
                Document doc = Jsoup.connect(link).get();

                //Collect ingredients
                org.jsoup.select.Elements recipeIngredients = doc.select("div.ingridentu_informacija > ul > li");
                for (Element i: recipeIngredients) {
                    //Try to recognize ingredient
                    String newIngredient = ingredient.recognize((i.text()));
                    //If new ingredient is recognized, add to the list
                    if ((!newIngredient.equals("")) && (!ingredients.contains(newIngredient))) ingredients.add(newIngredient);
                }

                //Collect recipe title
                org.jsoup.select.Elements nameOfRecipe = doc.select("div.iraso_antraste > h2:nth-child(1)");

                //Add to recipe list
                newRecipes.add(new Recipe(counter, nameOfRecipe.text(), ingredients, link));

            } catch (IOException e) {
                e.printStackTrace();
            }
            counter++;
        }
        return newRecipes;
    }

    public static void main(String[] args) {

        Connector conn = new Connector();

        //Method for collecting and adding new recipes to the database. Must be executed regularly to have the newest recipes.
        //Uploading a lot of recipes at once takes some time...
        //Voras voras = new Voras();
        //conn.upload(voras.collectRecipes());

        //For testing purposes until front-end is created.
        ArrayList<String> whatIHave = new ArrayList<>();
        whatIHave.add("sūris");
        whatIHave.add("kumpis");
        System.out.println(conn.searchForRecipe(whatIHave));
    }
}