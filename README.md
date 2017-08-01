# USU-CS6890-Linear-And-Integer-Programming

[Instructor's Github](https://github.com/VKEDCO/LinearProgramming)

This is the location used to store all assignments and other materials for CS6890 Linear and Integer Programming.

The structure is assignments -> assignment(n) (where (n) is the assignment number) 

Which house all the latex solutions and it's built pdf in folder latex and the base of that directory
is the assignment pdf itself. 

In the directory Linear-And-Integer-Programming house the basic structure for a java project

This is where all the .java files live to which solved the assignments.  
The solution pdf will reference which java file used.

MatrixSimplex uses just doubles whereas TableauSimplex uses a class I wrote called
Ratios.  

Ratios will simplify and perform addition, subtraction, multiplication, and division
using fractions to keep exact numbers.

Out of curiosity, it would be interesting to see at what point does the TableauSimplex
class fall behind MatrixSimplex in speed due to object overhead.  Microbenchmarking 
tests should be created to test this.  Ratios class is need to keep exact solutions, but
is the exact solution not worth the overhead?    