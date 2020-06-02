import java.util.ArrayList;

public class Recipe {

    int nr;
    ArrayList<String> ingredients = new ArrayList<String>();
    String title;
    String url;

    public Recipe (int nr, String title, ArrayList<String> ingredients, String url) {
        this.nr = nr;
        this.title = title;
        this.ingredients = ingredients;
        this.url = url;
    }
}
