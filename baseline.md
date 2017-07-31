## Composition
Programming is easier if we can compose applications out of small building blocks. This as opposed to building one application without functions at al. 

To achieve this, the Scala applies algebraic structures that involve: 

| |Closure|Associativity|Identity|Invertibility|Communativity|
|-|---|---|---|---|---|
||∀ (a, b) ∈ M: a ○ b ∈ M|∀ (a, b, c) ∈ M:  (a ○ b) ○ c = a ○ (b ○ c)|∀ (a) ∈ M: a ○ e = e ○ a = a|∀ (a, b) ∈ M: a ○ b = b ○ a = e|∀ (a, b) ∈ M: a ○ b = b ○ a|
|example ○ = addition| 1 + 1 = 2|(3+4)+5 = 3 + (4 + 5)| 6 + 0 = 0 + 6 = 6|7 + -7 = -7 + 7 = 0|8 + 9 = 9 + 8|
|example ○ = product| 1 * 1 = 2|(3 * 4) * 5 = 3 * (4 * 5)| 6 * 1 = 1 * 6 = 6|7 * 1/7 = 1/7 * 7 = 1|8 * 9 = 9 * 8|

M is a collection of elements, whereby an element is an object
a and b are identifyable elements
○ is a mapping, function, morphism or operation.
e is an identity element that has no effect when ○ applies. E.g. 0 with addition; 1 with multiplication; empty string with concatation; Nil with lists.

###Category
A category is an algebraic structure that follows Identity and Associativity laws. It is comprised of a collection of elements with morphisms or arrows between them.

For example: an arrow named 'f' connecting two elements is f: A->B
And g: B->C.
Composition is to combine the arrows as in g ○ f.
Implicitly, this means there is a implicit arrow h: A->C where h = g ○ f

For example, in the category of vector spaces in lineair algebra the elements are vectors (points in space) and the arrows do adding, substracting or multiplying vectors.

In programming (e.g. Scala) the elements are Types and the arrows one-argument functions 

increment: T => T 
where we just add one
increment(T: a):T = a + 1



####Algebraic laws
The category has laws about the behaviour how the elements and arrows behave

|Algebraic structure|Closure|Associativity|Identity|Invertibility|Communativity|
|--||--||--||--||--||--|
Semicategory|No|Yes|No|No|No|
Category|No|Yes|Yes|No|No|
Monoid|Yes|Yes|Yes|No|No|
Semigroup|Yes|Yes|No|No|No|
Abelian Group|Yes|Yes|Yes|Yes|Yes|