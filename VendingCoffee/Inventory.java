import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Integer> stock;

    public Inventory() {
        stock = new HashMap<>();
        stock.put("Coffee", 200);
        stock.put("Sugar", 100);
        stock.put("Milk", 200);
        stock.put("Water", 400);
    }

    public boolean hasEnoughIngredients(Map<String, Integer> requiredIngredients) {
        for (Map.Entry<String, Integer> entry : requiredIngredients.entrySet()) {
            String ingredient = entry.getKey();
            int required = entry.getValue();
            if (!stock.containsKey(ingredient) || stock.get(ingredient) < required) {
                return false;
            }
        }
        return true;
    }

    public void reduceStock(Map<String, Integer> ingredients) {
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            String ingredient = entry.getKey();
            int amount = entry.getValue();
            stock.put(ingredient, stock.get(ingredient) - amount);
        }
    }

    public void refillStock() {
        stock.put("Coffee", 200);
        stock.put("Sugar", 100);
        stock.put("Milk", 200);
        stock.put("Water", 400);
        System.out.println("Inventory refilled!");
    }

    public void displayStock() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}