package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);

        int loop = StdIn.readInt();

        Warehouse warehouse = new Warehouse();

        for (int i = 0; i < loop; i++)
        {
            String s = StdIn.readString();
            
            if (s.equals("add"))
            {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();

                warehouse.addProduct(id, name, stock, day, demand);
            } 
            else if (s.equals("restock"))
            {
                int id = StdIn.readInt();
                int restock = StdIn.readInt();

                warehouse.restockProduct(id, restock);
            } 
            else if (s.equals("purchase"))
            {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();

                warehouse.purchaseProduct(id, day, amount);
            } 
            else if (s.equals("delete"))
            {
                int id = StdIn.readInt();

                warehouse.deleteProduct(id);
            }
        }

        StdOut.setFile(args[1]);

        StdOut.print(warehouse);

	// Use this file to test all methods
    }
}
