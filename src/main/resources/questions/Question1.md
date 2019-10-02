 # Challenge 1

A common question asked by customers at a firm is about payments on mortgages. Customers would like to know what their total estimated cost will be, before signing the contract. A team has spotted this, and would like to develop a new tool that allows users to calculate this value for a firm’s new mortgage type, “Simply Buy”.
 
Your task is to develop a method calculateTotalPayment, which takes in three integers – the initial level of debt, the percentage interest, and the percentage repayment, and returns a single integer representing the total cost of the mortgage. Interest and Repayment are both a fixed percentage of the original debt
 
For the “Simply Buy” mortgage, there is a fixed 10% deposit, which should be included in the total payment, and each month interest is added before a repayment is made. There is also a minimum repayment of $50 (if the repayment for a month is less than $50, then it will be rounded up to $50).

## Examples

### Example 1

Consider the following:

Input:

`initialLevelOfDebt = 1000`

`interestPercentage = 5`

`repaymentPercentage = 10`

Output:
`1540`