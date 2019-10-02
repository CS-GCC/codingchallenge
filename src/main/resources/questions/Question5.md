# Challenge 5

You are a security software engineer at Canary Investments. The project your team is working on involves creating an application to mock the response to a potential high severity security threat and prevent it.

The security threat x, will arrive at the company at time t<sub>x</sub>, and in order to maintain the severity of the threat, we assume that data obtained from our company will be sent back to the hackers at frequency f<sub>x</sub>. 

In order to defend the data of your organisation, you must counter the security threats posed by the attack with an instance of an application developed by your team X.  The frequency of X can be modified to match the frequency of the threat. Modifying the initial frequency of X, f<sub>x</sub> to the frequency required to defend from the threat, f<sub>y</sub>, will require \|f<sub>y</sub> - f<sub>x</sub>| units of time.  If two threats of the same frequency arrive at the same time, you can counter them with the same instance of X. 

Find the minimum number of instances of X which are needed, to protect the safety of your organisation.


## Input Format

The input is an `n` Integer array. The first Integer represents the number of security threats posed. This is followed by n pairs of integers t<sub>i</sub> and f<sub>i</sub>, this pair contains the time frequency of the i<sup>th</sup> threat.

## Output Format

One integer which shows the minimum number of instances of X you need to run to defend against the security attack.

## Examples

### Example 1

`Array input = {4,1,1,2,2,3,1,5,1}`

The first Integer is 4, therefore there are 4 security threats posed. X is launched at t=1 with f=1, which successfully counters the threat posed at the pair 1,1. X is then modified to be a frequency of 2 in 1 time unit, and counters the next attempted security breach at t=2. Following this it is then again retuned to a frequency of 1 and counters at t=3, and then counters with the same frequency of 1 at t=5.

Thus, 1 instance of application X is enough to successfully counter this series of security threats.

`Output = 1`

### Example 2

`Array input = {4,1,1,2,3,3,1,5,1}`

The first Integer is 4, therefore there are 4 security threats posed. X is launched at t=1 with f=1, which successfully counters the threat posed at 1,1. At t=2 X needs to be a frequency of 3, this takes 2 time units (as mentioned above, modifying the initial frequency of X, f<sub>x</sub> to the frequency required to defend from the threat, f<sub>y</sub>, will require \|f<sub>y</sub> - f<sub>x</sub>| units of time) therefore, a new instance of X is needed to counter the next attempted security breach at t=2. The first instance of Xis then again can remain at a frequency of 1 and counters at t=3, and then counters with the same frequency of 1 at t=5.

Thus, 2 instances of application X is enough to successfully counter this series of security threats.
 
`Output = 2`






