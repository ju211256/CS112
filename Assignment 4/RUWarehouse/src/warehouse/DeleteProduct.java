package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        
        int loop = StdIn.readInt();

        Warehouse warehouse = new Warehouse();

        for (int i = 0; i < loop; i++)
        {
            String s = StdIn.readString();
            
            if (s.equals("delete"))
            {
                int id = StdIn.readInt();

                warehouse.deleteProduct(id);
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


	// Use this file to test deleteProduct
    }
}
