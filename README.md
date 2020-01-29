# AprioriAlgoAssignment2
Apriori algo for finding frequent itemsets and generating association rules.

Below is the output of my project:

Please enter min support : 
0.4

Please enter min confidence : 
0.6

Please enter the desired data : 

1 3 4

2 3 5

1 2 3 5

2 5

1 3 5


Minimum support: 0.4

Minimum confidence: 0.6


1 Frequent ItemSet :

List(Item(Set(5),4), Item(Set(1),3), Item(Set(2),3), Item(Set(3),4))

2 Frequent ItemSets :

List(Item(Set(5, 1),2), Item(Set(5, 2),3), Item(Set(5, 3),3), Item(Set(1, 3),3), Item(Set(2, 3),2))

3 Frequent ItemSets :

List(Item(Set(5, 1, 3),2), Item(Set(5, 2, 3),2))


Association rules:

( 1 ) --> ( 5 ) = 0.6666666666666666

( 5 ) --> ( 2 ) = 0.75

( 2 ) --> ( 5 ) = 1.0

( 5 ) --> ( 3 ) = 0.75

( 3 ) --> ( 5 ) = 0.75

( 1 ) --> ( 3 ) = 1.0

( 3 ) --> ( 1 ) = 0.75

( 2 ) --> ( 3 ) = 0.6666666666666666

( 1 ) --> ( 5 3 ) = 0.6666666666666666

( 5 1 ) --> ( 3 ) = 1.0

( 5 3 ) --> ( 1 ) = 0.6666666666666666

( 1 3 ) --> ( 5 ) = 0.6666666666666666

( 2 ) --> ( 5 3 ) = 0.6666666666666666

( 5 2 ) --> ( 3 ) = 0.6666666666666666

( 5 3 ) --> ( 2 ) = 0.6666666666666666

( 2 3 ) --> ( 5 ) = 1.0
