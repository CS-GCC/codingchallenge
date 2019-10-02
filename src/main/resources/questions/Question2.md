# Challenge 2

A team of traders have a series of trades, numbered 0-i, that they can fulfill for a client. 
- We have three arrays: `trader`, `risk`, and `bonus`.
- `Trader[i]` gives the skill level of the ith trader. 
- `Risk[i]` gives the difficulty of the ith trade. 
Only traders with a skill level at or above `risk[i]` can complete `trade[i]`. Completion of a `trade[i]` gives the team a bonus of `bonus[i]`.

What is the biggest total bonus the traders can make?

## Constraints
- trader.length, risk.length, bonus.length >= 1 and <= 10,000
- The arrays are not necessarily in ascending order (although bonus[i] and risk[i] will always correspond to the bonus and risk of trade i)
- Each trader is only able to take on at most one trade, but multiple traders can complete the same trade.

## Output Format

The value returned should be the maximum profit that can be made from your trading stategy.

## Examples

### Example 1

For the following input
`trader = [6, 7, 2, 8, 1]`
`risk = [5, 4, 3, 1, 8]`
`bonus = [9, 9, 1, 9, 4]`

Output:
45

### Example 2

For the following input
`trader = [2, 10, 9, 10, 10]`
`risk = [9, 1, 1, 6, 1]`
`bonus = [9, 9, 8, 10, 10]`

Output:
50