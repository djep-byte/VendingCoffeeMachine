import java.util.ArrayList;

public class TransactionLog {
    private ArrayList<String> transactions;

    public TransactionLog() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(String transaction) {
        transactions.add(transaction);
    }

    public void displayLog() {
        System.out.println("Transaction Log:");
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }
}