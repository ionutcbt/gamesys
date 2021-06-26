package main;

import checkout.Checkout;

public class Main {

    public static void main(String[] args) {

        String bookListPath = "checkout/book-list-1.json";
        Checkout checkout = new Checkout(bookListPath);
        checkout.applyDiscount();

        System.out.println("Total price before discount: " + checkout.getTotalPrice());
        System.out.println("Discount: " + checkout.getTotalDiscount());
        System.out.println("Final price: " + checkout.getFinalPrice());
    }

}
