import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingCoffeeMachine {
    private ArrayList<Coffee> menu;
    private Inventory inventory;
    private TransactionLog log;
    private Scanner scanner;

    public VendingCoffeeMachine() {
        menu = new ArrayList<>();
        inventory = new Inventory();
        log = new TransactionLog();
        scanner = new Scanner(System.in);
        initializeMenu();
    }

    private void initializeMenu() {
        Map<String, Integer> espressoIngredients = new HashMap<>();
        espressoIngredients.put("Coffee", 10);
        espressoIngredients.put("Water", 20);
        menu.add(new Coffee("Espresso", 37500, espressoIngredients));

        Map<String, Integer> latteIngredients = new HashMap<>();
        latteIngredients.put("Coffee", 10);
        latteIngredients.put("Milk", 20);
        latteIngredients.put("Water", 30);
        menu.add(new Coffee("Latte", 52500, latteIngredients));

        Map<String, Integer> cappuccinoIngredients = new HashMap<>();
        cappuccinoIngredients.put("Coffee", 10);
        cappuccinoIngredients.put("Milk", 30);
        cappuccinoIngredients.put("Water", 20);
        menu.add(new Coffee("Cappuccino", 55000, cappuccinoIngredients));

        Map<String, Integer> americanoIngredients = new HashMap<>();
        americanoIngredients.put("Coffee", 10);
        americanoIngredients.put("Water", 40);
        menu.add(new Coffee("Americano", 40000, americanoIngredients));
    }

    public void start() {
        while (true) {
            displayMenu();
            System.out.print("Pilih kopi (1-" + menu.size() + ") atau 0 untuk keluar: ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                inventory.displayStock();
                log.displayLog();
                break;
            }
            if (choice < 1 || choice > menu.size()) {
                System.out.println("Pilihan tidak valid!");
                continue;
            }

            Coffee selectedCoffee = menu.get(choice - 1);
            System.out.println("Pilih ukuran:");
            System.out.println("1. Kecil");
            System.out.println("2. Normal");
            System.out.println("3. Besar");
            System.out.print("Masukkan pilihan (1-3): ");
            int sizeChoice = scanner.nextInt();
            double sizeMultiplier;
            String sizeLabel;
            if (sizeChoice == 1) {
                sizeMultiplier = 1.0;
                sizeLabel = "Kecil";
            } else if (sizeChoice == 2) {
                sizeMultiplier = 1.5;
                sizeLabel = "Normal";
            } else if (sizeChoice == 3) {
                sizeMultiplier = 2.0;
                sizeLabel = "Besar";
            } else {
                System.out.println("Ukuran tidak valid! Gunakan 1, 2, atau 3.");
                continue;
            }

            System.out.println("Tambah gula?");
            System.out.println("1. Iya");
            System.out.println("2. Tidak");
            System.out.print("Masukkan pilihan (1-2): ");
            int sugarChoice = scanner.nextInt();
            System.out.println("Tambah susu?");
            System.out.println("1. Iya");
            System.out.println("2. Tidak");
            System.out.print("Masukkan pilihan (1-2): ");
            int milkChoice = scanner.nextInt();

            if (sugarChoice != 1 && sugarChoice != 2 || milkChoice != 1 && milkChoice != 2) {
                System.out.println("Pilihan gula atau susu tidak valid! Gunakan 1 atau 2.");
                continue;
            }

            Map<String, Integer> orderIngredients = new HashMap<>();
            for (Map.Entry<String, Integer> entry : selectedCoffee.getIngredients().entrySet()) {
                orderIngredients.put(entry.getKey(), (int)(entry.getValue() * sizeMultiplier));
            }
            if (sugarChoice == 1) {
                orderIngredients.put("Sugar", (int)(5 * sizeMultiplier));
            }
            if (milkChoice == 1 && !selectedCoffee.getName().equals("Latte") && !selectedCoffee.getName().equals("Cappuccino")) {
                orderIngredients.put("Milk", (int)(10 * sizeMultiplier));
            }

            if (!inventory.hasEnoughIngredients(orderIngredients)) {
                System.out.println("Maaf, bahan tidak cukup! Admin, silakan isi ulang.");
                inventory.displayStock();
                System.out.println("Isi ulang sekarang?");
                System.out.println("1. Iya");
                System.out.println("2. Tidak");
                System.out.print("Masukkan pilihan (1-2): ");
                int refillChoice = scanner.nextInt();
                if (refillChoice == 1) {
                    inventory.refillStock();
                }
                continue;
            }

            double totalPrice = selectedCoffee.getPrice() * sizeMultiplier;
            if (sugarChoice == 1) totalPrice += 7500 * sizeMultiplier;
            if (milkChoice == 1 && !selectedCoffee.getName().equals("Latte") && !selectedCoffee.getName().equals("Cappuccino")) totalPrice += 15000 * sizeMultiplier;

            System.out.println("Total harga: Rp" + (int)totalPrice);
            System.out.print("Masukkan jumlah pembayaran: Rp");
            double payment = scanner.nextDouble();

            if (payment < totalPrice) {
                System.out.println("Pembayaran kurang!");
                continue;
            }

            inventory.reduceStock(orderIngredients);
            System.out.println("Membuat " + selectedCoffee.getName() + " (" + sizeLabel + ")...");
            System.out.println("Kopi disajikan! Kembalian: Rp" + (int)(payment - totalPrice));

            String transaction = "Pesanan: " + selectedCoffee.getName() + " (" + sizeLabel + ")" +
                                ", Gula: " + (sugarChoice == 1 ? "Iya" : "Tidak") +
                                ", Susu: " + (milkChoice == 1 ? "Iya" : "Tidak") +
                                ", Total: Rp" + (int)totalPrice;
            log.addTransaction(transaction);
        }
    }

    private void displayMenu() {
        System.out.println("=== Mesin Penjual Kopi ===");
        for (int i = 0; i < menu.size(); i++) {
            Coffee coffee = menu.get(i);
            System.out.println((i + 1) + ". " + coffee.getName() + " - Rp" + (int)coffee.getPrice() + " (Kecil)");
        }
        System.out.println("Ukuran: 1. Kecil (1x), 2. Normal (1.5x), 3. Besar (2x)");
    }

    public static void main(String[] args) {
        VendingCoffeeMachine machine = new VendingCoffeeMachine();
        machine.start();
    }
}