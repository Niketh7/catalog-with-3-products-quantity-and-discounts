package niketh.bean.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import niketh.bean.Product;

class ShoppingCart {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Catalog of products
        List<Product> catalog = new ArrayList<>();
        catalog.add(new Product("Product A", 20.0));
        catalog.add(new Product("Product B", 40.0));
        catalog.add(new Product("Product C", 50.0));

        // Discounts
        Set<String> discounts = new HashSet<>();
        discounts.add("flat_10_discount");
        discounts.add("bulk_5_discount");
        discounts.add("bulk_10_discount");
        discounts.add("tiered_50_discount");

        // Fees
        Map<String, Double> fees = new HashMap<>();
        fees.put("gift_wrap_fee_per_unit", 1.0);
        fees.put("shipping_fee_per_package", 5.0);
        int maxUnitsPerPackage = 10;

        // Input quantity and gift wrap for each product
        Map<Product, Integer> quantities = new HashMap<>();
        Map<Product, Boolean> isGiftWrapped = new HashMap<>();

        for (Product product : catalog) {
            System.out.print("Enter the Quantity of " + product.getName() + ": ");
            quantities.put(product, scanner.nextInt());
            System.out.print("Is " + product.getName() + " wrapped as a gift? (true/false): ");
            isGiftWrapped.put(product, scanner.nextBoolean());
        }

        // Calculate total amount, discounts, and fees
        double subtotal = 0.0;
        double discountAmount = 0.0;
        String discountName = "";
        int totalQuantity = 0;

        for (Product product : catalog) {
            int quantity = quantities.get(product);
            double price = product.getPrice();
            double productTotal = quantity * price;
            subtotal += productTotal;
            totalQuantity += quantity;

            if (quantity > 10) {
                double bulkItemDiscount = productTotal * 0.05;
                if (bulkItemDiscount > discountAmount && discounts.contains("bulk_5_discount")) {
                    discountAmount = bulkItemDiscount;
                    discountName = "bulk_5_discount";
                }
            }

            if (totalQuantity > 20) {
                double totalQuantityDiscount = subtotal * 0.1;
                if (totalQuantityDiscount > discountAmount && discounts.contains("bulk_10_discount")) {
                    discountAmount = totalQuantityDiscount;
                    discountName = "bulk_10_discount";
                }
            }

            if (totalQuantity > 30 && quantity > 15) {
                double tieredDiscount = (quantity - 15) * price * 0.5;
                if (tieredDiscount > discountAmount && discounts.contains("tiered_50_discount")) {
                    discountAmount = tieredDiscount;
                    discountName = "tiered_50_discount";
                }
            }
        }

        double totalAmount = subtotal - discountAmount;
        int totalPackages = (int) Math.ceil((double) totalQuantity / maxUnitsPerPackage);
        double shippingFee = totalPackages * fees.get("shipping_fee_per_package");
        double giftWrapFee = 0.0;

        for (Product product : catalog) {
            if (isGiftWrapped.get(product)) {
                giftWrapFee += quantities.get(product) * fees.get("gift_wrap_fee_per_unit");
            }
        }

        // Output the details
        System.out.println("\n--- Order Summary ---");
        for (Product product : catalog) {
            int quantity = quantities.get(product);
            double price = product.getPrice();
            double productTotal = quantity * price;
            System.out.println(product.getName() + " - Quantity: " + quantity + " - Total: $" + productTotal);
        }
        System.out.println("\nSubtotal: $" + subtotal);
        System.out.println("Discount Applied: " + discountName + " - Amount: $" + discountAmount);
        System.out.println("Shipping Fee: $" + shippingFee);
        System.out.println("Gift Wrap Fee: $" + giftWrapFee);
        System.out.println("---------------------");
        System.out.println("Total: $" + totalAmount);

        scanner.close();
    }
}
