# Challenge 6

You are a developer at Canary Investments, and are currently working on a project which involves handling an highly confidential portfolio of emerging market, real estate and technology stocks  S. S[i] has value i. 

You are given the task of evaluating the portfolio, and finding the stock with maximum value which has the provided starting and ending in order to make further decisions about this portfolio. If such a stock does not exist in your portfolio, then return -1 in order to indicate this. 

Your method of evaluating a portfolio must be able to efficiently evaluate portfolios of varying values and stocks.

## Input Formats

The input is an array of stocks S, such that S[i] has value i

Your method to evaluate will take as parameters the starting and ending which has been provided

## Output Format

The return value should be an integer n, which denotes the index of highest valued stock with the given starting and ending. 

## Examples

### Example 1

<b>Input</b>

Array portfolio s: {VPAI, HRLG, AIGHRW, DBPF, AJWW}

The given starting and ending are "A", and "W" respectively

<b>Output</b>

class.yourMethod(“A”,”W”) should return the following :

4

As the stock AJWW, has a higher value than the stock AIGHRW, even though they both have the  same starting and ending characters