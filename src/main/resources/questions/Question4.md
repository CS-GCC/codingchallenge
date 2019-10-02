# Challenge 4

Lisa has a maximum total complexity of trades that she is able to fulfill in one day. In the morning, she is given a list of trades to choose from. Each one has a value, `v` and a complexity, `c`. What is the maximum value of trades that she can execute, given her complexity constraint?

## Input Format

The inputs are a pair of arrays, `v` and `c`, and a maximum complexity, `mc`. The elements of `v` represent trade value in millions of dollars and is any positive integer. The complexity is between 0-100 and represents a percentage. The maximum complexity is a scalar integer.

## Output Format

The value returned should be the maximum value of trades that Lisa can execute.

## Examples

### Example 1

For the following inputs

`v = [6, 10, 12]`
`c = [10, 20, 30]`
`mc = 50`

The output should be `22`. This is because the maximum value elements that Lisa has capacity for is at index 1 and 2, which have value 10 and 12.