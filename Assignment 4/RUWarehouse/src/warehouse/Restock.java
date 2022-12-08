package warehouse;

import java.time.Year;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);

        int loop = StdIn.readInt();

        Warehouse warehouse = new Warehouse();

        for (int i = 0; i < loop; i++)
        {
            String s = StdIn.readString();
            
            if (s.equals("restock"))
            {
                int id = StdIn.readInt();
                int restock = StdIn.readInt();

                warehouse.restockProduct(id, restock);
            } 
            else if (s.equals("add"))
            {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();

                warehouse.addProduct(id, name, stock, day, demand);
            }
        }

        StdOut.setFile(args[1]);

        StdOut.print(warehouse);

	// Uset his file to test restock
    }
}
