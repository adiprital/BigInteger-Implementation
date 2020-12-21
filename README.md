# BigInteger-Implementation
A program that supports plus, minus, multiplication and division arithmetic operations for unlimited integers.<br />
That is, the limit that exists in java for the maximum value of integers (2^31-1 = 2147483647) does not exist for this class.<br /><br />

The program get as input 2 strings of infinite numbers, and presents to the user the result of using all the arithmetic operations for these 2 BigInts.<br /><br />

The BIGINT class was written entirely without the use of the parseInt function of the String class,<br />
and no built-in arithmetic operations of Integer class were used at all except for a single digit (between 0-9).<br />
In addition, it implements the 'Comparable' interface, so 2 isntances of BigInt can be compared.<br /><br />

Execution example:<br />
First input: "45646561321216546"<br />
Second input: "256168165131321325"<br />
Output:<br /><br />
45646561321216546 + 256168165131321325 = 301814726452537871<br />
256168165131321325 + 45646561321216546 = 301814726452537871<br />
45646561321216546 - 256168165131321325 = -210521603810104779<br />
256168165131321325 - 45646561321216546 = 210521603810104779<br />
45646561321216546 * 256168165131321325 = 11693195858210385070853997432643450<br />
256168165131321325 * 45646561321216546 = 11693195858210385070853997432643450<br />
45646561321216546 / 256168165131321325 = 0<br />
256168165131321325 / 45646561321216546 = 5<br />
the result of compereTo is: -1, it means that 256168165131321325 is bigger then 45646561321216546<br />
256168165131321325 and 45646561321216546 are NOT equals.<br />



