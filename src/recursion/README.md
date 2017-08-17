<h1>Recursion</h1><h5>from Deitel Java 8 exercises</h5>

<p>In this section I was working on challenges:</p>
<h3><a title="Recursion Main" href="Recursion.java">Recursion.java</h3>
<ol>
<li><a title="Power of X" href="Power.java">Power of X</a>: First argument raised to the power of second argument</li>
<li><a title="Palindromes" href="Palindrome.java">Palindromes:</a> Is the string a palindrome? Ignores case, white space and punctation.</li>
<li><a title="Eight Queens " href="EightQueens.java">Eight Queens:</a> Can eight queens be placed into a 8x8 field without attacking each other?</li>
<li><a title="Print an array" href="PrintAnArray.java">Print an array:</a> Prints integer array as-is, minimum value of integer array, and reversed string input</li>
</ol>
<h3><a title="Maze Traversal with Recursive Backtracking" href="MazeMain.java">MazeMain.java</a></h3>
<ul>
<li><a title="Maze Traversal with Recursive Backtracking" href="MazeTraversalBacktracking.java">Maze Traversal with Recursive Backtracking</a>: Maze generator with solution</li>
</ul>
<h2>Demonstrational images</h2>
<p>File Recursion.java:<br /><img alt="terminal output" src="images/Screenshot from 2017-08-17 10-53-23.png"/><br />Output is self-explanatory</p>
<p>File MazeMain.java:<br /><img title="Maze of size 7x7 animation" alt="terminal output 7x7 maze" src="images/MazeSolving.gif"/><br />
<img title="Maze of size 12x12" alt="terminal output 12x12 maze" src="images/Screenshot from 2017-08-17 10-48-40.png"/><img alt="terminal output 12x12 maze" src="images/Screenshot from 2017-08-17 10-48-50.png"/><br /><br />
LEGEND:
<ol>
<li><b>#</b> represents a Wall</li>
<li><b>Empty field</b> represents a Corridor</li>
<li><b>+</b> represents a active path</li>
<li><b>-</b> represents a backtracked path</li>
</ol></p>

<h3>Future work</h3>
<p>There is still alot of possibilities to improve the performance and functionality of the program.<br />
For now known issues:
<ul>
<li>Maze is sometimes generated in many diagonal corridors, I prefer horizontal and vertical corridors,</li>
<li>Smallest maze that can be generated with this logic is of size 7x7,</li>
<li>Direction of movement is always decided in the order Up, Right, Down and Left,</li>
<li>It would be better if it would not run into a blind corridor which is smaller than 3 in-a-row fields.</li>
</ul>
<br />
<img alt="Example of non-diagonal maze" src="images/Screenshot from 2017-08-17 12-17-12.png"/>Example of a maze with only horizontal and vertical corridors
</p>
