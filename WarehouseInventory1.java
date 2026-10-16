import java.util.Scanner;
import java.util.ArrayList;

class Product {
    int id, qty, reorderLevel, reorderQty;
    String name;
    double price;

    Product(int id, String name, int qty, int reorderLevel, int reorderQty, double price) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.reorderLevel = reorderLevel;
        this.reorderQty = reorderQty;
        this.price = price;
    }

    boolean low() { return qty <= reorderLevel; }
}

public class WarehouseInventory1 {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Product> list = new ArrayList<>();

    public static void main(String[] args) {

        list.add(new Product(101, "Bolt",    40,  50, 200,   2.50));
        list.add(new Product(102, "Wire",   500, 100, 500,  12.75));
        list.add(new Product(103, "Helmet",  15,  20, 100, 350.00));

        while (true) {
            System.out.println("\n1.Add  2.Receive  3.Issue  4.View  5.Reorder  6.Exit");
            System.out.print("Choice: ");
            int c = sc.nextInt(); sc.nextLine();

            if      (c == 1) add();
            else if (c == 2) receive();
            else if (c == 3) issue();
            else if (c == 4) view();
            else if (c == 5) report();
            else if (c == 6) break;
            else             System.out.println("Invalid choice");
        }
    }

    static Product find(int id) {
        for (Product p : list)
            if (p.id == id) return p;
        return null;
    }

    static void add() {
        System.out.print("ID: ");              int id = sc.nextInt(); sc.nextLine();
        if (find(id) != null) { System.out.println("ID already exists"); return; }

        System.out.print("Name: ");            String n  = sc.nextLine();
        System.out.print("Quantity: ");        int q     = sc.nextInt();
        System.out.print("Reorder level: ");   int rl    = sc.nextInt();
        System.out.print("Reorder quantity: ");int rq    = sc.nextInt();
        System.out.print("Unit price: ");      double p  = sc.nextDouble(); sc.nextLine();

        list.add(new Product(id, n, q, rl, rq, p));
        System.out.println("Added.");
    }

    static void receive() {
        System.out.print("ID: ");  int id = sc.nextInt(); sc.nextLine();
        Product p = find(id);
        if (p == null) { System.out.println("Not found"); return; }

        System.out.print("Quantity received: ");
        int q = sc.nextInt(); sc.nextLine();

        p.qty += q;
        System.out.println("New quantity: " + p.qty);
    }

    static void issue() {
        System.out.print("ID: ");  int id = sc.nextInt(); sc.nextLine();
        Product p = find(id);
        if (p == null) { System.out.println("Not found"); return; }

        System.out.println("Available: " + p.qty);
        System.out.print("Quantity to issue: ");
        int q = sc.nextInt(); sc.nextLine();

        if (q <= 0 || q > p.qty) { System.out.println("Invalid quantity"); return; }

        p.qty -= q;
        System.out.println("Remaining: " + p.qty);

        if (p.low())
            System.out.println("*** REORDER ALERT: order " + p.reorderQty + " of " + p.name);
    }

    static void view() {
        for (Product p : list)
            System.out.println(p.id + "  " + p.name + "  qty=" + p.qty
                    + "  reorder=" + p.reorderLevel + "  price=" + p.price
                    + (p.low() ? "  [LOW]" : ""));
    }

    static void report() {
        double total = 0;
        for (Product p : list) {
            if (p.low()) {
                double cost = p.reorderQty * p.price;
                total += cost;
                System.out.println(p.name + " -> order " + p.reorderQty + " = " + cost);
            }
        }
        System.out.println("Total purchase cost: " + total);
    }
}
