import java.util.Map;

public class Coffee {
    private String name;
    private double price;
    private Map<String, Integer> ingredients;

    public Coffee(String name, double price, Map<String, Integer> ingredients) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }
}