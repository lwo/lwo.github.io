## Algebraic structure
An algebraic structure is a set with some operation(s) on its elements. In what follows we name:

|symbol|meaning|
|--|--|
|M|collection of elements, whereby an element is an object|
|a, b, c| identifyable elements|
|○|an operation, or mapping, function, morphism.|
|e|an identity element that has no effect when ○ applies it to an element. E.g. 0 with addition; 1 with multiplication; empty string with concatation; Nil with lists.|

## Algebraic laws

| |Closure|Associativity|Identity|Invertibility|Communativity|
|-|---|---|---|---|---|
||∀ (a, b) ∈ M: a ○ b ∈ M|∀ (a, b, c) ∈ M:  (a ○ b) ○ c = a ○ (b ○ c)|∀ (a) ∈ M: a ○ e = e ○ a = a|∀ (a, b) ∈ M: a ○ b = b ○ a = e|∀ (a, b) ∈ M: a ○ b = b ○ a|
|In scala notation|def op(a: T, b: T): T|(a op b) op c == a op (b op c)|e op a== a op e == a|a op b == b op a == e |a op b == b op a|
|example ○ = addition| 1 + 1 = 2|(3+4)+5 = 3 + (4 + 5)| 6 + 0 = 0 + 6 = 6|7 + -7 = -7 + 7 = 0|8 + 9 = 9 + 8|
|example ○ = product| 1 * 1 = 1|(3 * 4) * 5 = 3 * (4 * 5)| 6 * 1 = 1 * 6 = 6|7 * 1/7 = 1/7 * 7 = 1|8 * 9 = 9 * 8|


## Algebraic structures and the laws that apply

|Algebraic structure|Closure|Associativity|Identity|Invertibility|Communativity|
|--||--||--||--||--||--|
Semicategory|No|Yes|No|No|No|
Category|No|Yes|Yes|No|No|
Groupiod|No|Yes|Yes|Yes|No|
|Quasigroup|Yes|No|No|Yes|No|
|Loop|Yes|No|Yes|Yes|No|
|Magma|Yes|No|No|No|No|
|Semigroup|Yes|Yes|No|No|No|
|Monoid|Yes|Yes|Yes|No|No|
|Group|Yes|Yes|Yes|Yes|No|
|Abelian Group|Yes|Yes|Yes|Yes|Yes|


## Focus in on the algebraic Category structure
A category is an algebraic structure that follows Identity and Associativity laws. Its elements are **arrows** and the operation is arrow **composition**.

|Category|elements are arrows|composition of arrows|
|--|--|--|
| |a->b and b->c and d|a->b ○ b->c|
|A programming language|Types and one-argument functions|(Int=>Int) ○ (Int => Int)|


### Composition
For example: an arrow named 'f' is f: A->B

And a second arrow g: B->C.

Composition is to combine the arrows as in g ○ f. Implicitly, this means there is a arrow h: A->C where h = g ○ f

For example, in the category of vector spaces in lineair algebra the elements are vectors (points in space) and the arrows do adding, substracting or multiplying vectors.

### Monoid
Like the Category plus a closure law. Example are Numbers.

But a Monoid can also be seen as a Category with just the one object rather than a set.
![](/home/lwo/scala/notation/assets/images/Elements_as_functions.svg) 
Source: https://en.wikiversity.org/wiki/Introduction_to_Category_Theory/Monoids#From_Binary_Operators_to_Arrows

|Number as example|Elements|Identity element|Operator|Associativity|Closure|
|--|--|--|--|--|--|
| Monoid as a set of elements|Numbers: 1, 2, 3, etc|0|binary: +, -|1+(2+3)|[n]|
| Monoid as Category|Arrows: Add1, Add2, Add2, etc |Add0|unary composition: ○|Add1 ○ (Add2 ○ Add3)|Add[n]

No information is lost !!!

http://www.michael-noll.com/blog/2013/12/02/twitter-algebird-monoid-monad-for-large-scala-data-analytics/