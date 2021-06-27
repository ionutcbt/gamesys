# Gamesys - Software Engineer coding problem

In order to provide the final amount to be paid when given a book list, 
I chose to use an abstract class to create the discount functionality. 
Therefore, the design is extendable to specific discount types. 
For this case, extending the abstract `Discount` to a `PercentageDiscount` 
type was sufficient. To get the final amount to be paid, in the `Checkout` class I implemented
`applyDiscount` method, which depending on specific conditions decides
how to configure the discount.

In the unit tests, I added content and test cases that match the 
examples given in the technical task and also some other cases 
(no discount to be applied; buying the entire list of books).
