import java.util.HashMap;
import java.util.Map;

public class Ingredient {

    //Creating an empty dictionary for ingredients
    private Map<String,String> dictionary = new HashMap<String,String>();

    public Ingredient() {

        //Filling dictionary with ingredients
        dictionary.put("jautien", "jautiena");
        dictionary.put("kiaul", "kiauliena");
        dictionary.put("višt", "vištiena");
        dictionary.put("višč", "vištiena");
        dictionary.put("antien", "antiena");
        dictionary.put("kalakut", "kalakutiena");
        dictionary.put("avien", "aviena");
        dictionary.put("kiaušin", "kiaušiniai");
        dictionary.put("svogūn", "svogūnai");
        dictionary.put("mork", "morkos");
        dictionary.put("bulv", "bulvės");
        dictionary.put("ryž", "ryžiai");
        dictionary.put("gryb", "grybai");
        dictionary.put("grietinėl", "grietinėlė");
        dictionary.put("grietin", "grietinė");
        dictionary.put("pien", "pienas");
        dictionary.put("sūri", "sūris");
        dictionary.put("šoninė", "šoninė");
        dictionary.put("kump", "kumpis");
        dictionary.put("žirn", "žirniai");
        dictionary.put("pomidor", "pomidorai");
        dictionary.put("agurk", "agurkai");
        dictionary.put("cukinij", "cukinija");
        dictionary.put("moliūg", "moliūgas");
        dictionary.put("milt", "miltai");
        dictionary.put("varšk", "varškė");
        dictionary.put("kakav", "kakava");
        dictionary.put("sviest", "sviestas");
        dictionary.put("cukr", "cukrus");
        dictionary.put("šokolad", "šokoladas");
    }

    //Method for ingredient recognition
    public String recognize(String ingredient) {

        String result = "";
        ingredient = ingredient.toLowerCase();

        for (Map.Entry<String,String> entry : dictionary.entrySet()) {
            if (ingredient.indexOf(entry.getKey()) != -1) {
                result = entry.getValue();
            }
        }
        return result;
    }
}
