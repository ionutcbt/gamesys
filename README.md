# Gamesys - Software Engineer coding problem

In order to provide the final amount to be paid when given a list of books, 
I chose to use an abstract class (`Discount`) to create the discount functionality. 
It has a `discountCondition` member, that is used in the `calculateDiscount()` method to 
verify if our defined condition is met or not. Only when the condition is accomplished, 
the logic will return the discounted amount.

The design is extendable for different discount types. For this case, I extended two types of discounts:
- `PercentageDiscountPerBook` - used for calculating the discount for books that have been published after a specified date;
- `TotalPriceDiscount` - used for calculating the discount applied for the total price, when a threshold is met;

Using the `DiscountLoader` we can define different discounts that will be checked when buying a list of books.
To get the final amount that needs to be paid, in the `Checkout` class I implemented
`processPrice()` method, which takes into consideration all the discounts available 
in the `DiscountLoader` class. 

In the unit tests, I added content and test cases that match the 
examples given in the technical task and also some other cases 
(no discount to be applied; buying the entire list of books).
