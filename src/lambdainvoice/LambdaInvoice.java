import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LambdaInvoice 
{
   public static void main(String[] args)
   {
      Invoice[] invoices = new Invoice[10];
      
      // populate array with objects
      invoices[0] = new Invoice(01234, "seat", 2, 375.00);
      invoices[1] = new Invoice(56789, "tire", 4, 79.95);
      invoices[2] = new Invoice(83, "Electric sander", 7, 57.98);
      invoices[3] = new Invoice(24, "Power saw", 18, 99.99);
      invoices[4] = new Invoice(7, "Sledge hammer", 11, 21.50);
      invoices[5] = new Invoice(77, "Hammer", 76, 11.99);
      invoices[6] = new Invoice(39, "Lawn mower", 3, 79.50);
      invoices[7] = new Invoice(68, "Screwdriver", 106, 6.99);
      invoices[8] = new Invoice(56, "Jig saw", 21, 11.00);
      invoices[9] = new Invoice(3, "Wrench", 34, 7.50);
      
      List<Invoice> invoice = Arrays.asList(invoices); //create list from array

      System.out.println(
         "Sorting Invoice objects by PartDescription using lambdas:"); 
      System.out.printf("%-7s %-20s %8s %12s%n",
              "#","DESC","QUANTITY","PRICE");
      // sort collection case insensitive by its partDescription 
      invoice.stream()
              .sorted(Comparator.comparing(Invoice::getPartDescription, String.CASE_INSENSITIVE_ORDER))
              .forEach(System.out::println);
      
      System.out.println(
         "\nSorting Invoice objects by Price using lambdas:"); 
      System.out.printf("%-7s %-20s %8s %12s%n",
              "#","DESC","QUANTITY","PRICE");
      invoice.stream()
              .sorted(Comparator.comparing(Invoice::getPricePerItem))
              .forEach(System.out::println);
      
      System.out.println(
         "\nEach Invoice mapped to its PartDescription and Quantity,\n"
                 + "sorted by Quantity using lambdas:"); 
      System.out.printf("%-20s %8s%n",
              "DESC","QUANTITY");
      invoice.stream()
              .collect(Collectors.toMap(Invoice::getPartDescription,Invoice::getQuantity)) //create new map collector
              .entrySet() //get a set of map
              .stream() //create a stream from a set of map
              .sorted(Map.Entry.comparingByValue()) //sort a collection by its value
              .forEach(e -> System.out.printf("%-20s %8d%n", e.getKey(), e.getValue()));
      
      System.out.println(
         "\nEach Invoice mapped to its PartDescription and the value of\n"
                 + "the Invoice (Quantity*ItemPrice) sorted by Invoice value using lambdas:"); 
      System.out.printf("%-20s %10s%n",
              "DESC","VALUE");
      invoice.stream()
              .collect(Collectors.toMap(Invoice::getPartDescription,e -> e.getQuantity()*e.getPricePerItem()))
              .entrySet()
              .stream()
              .sorted(Map.Entry.comparingByValue())
              .forEach(e -> System.out.printf("%-20s %8.2f $%n", e.getKey(), e.getValue()));
      
      System.out.println(
         "\nEach Invoice mapped to its PartDescription and the value of\n"
                 + "the Invoice (Quantity*ItemPrice), sorted by Invoice value\n"
                 + "ranging from 200$ to 400$ using lambdas:"); 
      System.out.printf("%-20s %10s%n",
              "DESC","VALUE");
      invoice.stream()
              .collect(Collectors.toMap(Invoice::getPartDescription,e -> e.getQuantity()*e.getPricePerItem()))
              .entrySet()
              .stream()
              .filter(e -> e.getValue() >= 200 && e.getValue() <= 500) //filter the vaules
              .sorted(Map.Entry.comparingByValue())
              .forEach(e -> System.out.printf("%-20s %8.2f $%n", e.getKey(), e.getValue()));
   } // end main
} // end class
