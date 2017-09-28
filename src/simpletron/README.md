# Simpletron Machine-Language Programing
It is a computer that converts instructions into actions, a software based simulation.
## Operation codes
<table>
    <th>
        <td>Operation code</td>
        <td>Operation</td>
        <td>Description</td>
    </th>
    <tr>
        <td></td>
        <td>10</td>
        <td>READ</td>
        <td>Read a word from the keyboard into a specific location in memory.</td>
    </tr>
    <tr>
        <td></td>
        <td>11</td>
        <td>WRITE</td>
        <td>Write a word from a specific location in memory to the screen.</td>
    </tr>
    <tr>
        <td></td>
        <td>20</td>
        <td>LOAD</td>
        <td>Load a word from a specific location in memory into the accumulator.</td>
    </tr>
    <tr>
        <td></td>
        <td>21</td>
        <td>STORE</td>
        <td>Store a word from the accumulator into a specific location in memory</td>
    </tr>
    <tr>
        <td></td>
        <td>30</td>
        <td>ADD</td>
        <td>Add a word from a specific location in memory to the word in the accumulator (leave the result in the accumulator).</td>
    </tr>
    <tr>
        <td></td>
        <td>31</td>
        <td>SUBTRACT</td>
        <td>Subtract a word from a specific location in memory from the word in the accumulator (leave the result in the accumulator).</td>
    </tr>
    <tr>
        <td></td>
        <td>32</td>
        <td>DIVIDE</td>
        <td>Divide a word from a specific location in memory into the word in the accumulator (leave result in the accumulator).</td>
    </tr>
    <tr>
        <td></td>
        <td>33</td>
        <td>MULTIPLY</td>
        <td>Multiply a word from a specific location in memory by the word in the accumulator (leave the result in the accumulator).</td>
    </tr>
    <tr>
        <td></td>
        <td>40</td>
        <td>BRANCH</td>
        <td>Branch to a specific location in memory.</td>
    </tr>
    <tr>
        <td></td>
        <td>41</td>
        <td>BRANCHNEG</td>
        <td>Branch to a specific location in memory if the accumulator is negative.</td>
    </tr>
    <tr>
        <td></td>
        <td>42</td>
        <td>BRANCHZERO</td>
        <td>Branch to a specific location in memory if the accumulator is zero.</td>
    </tr>
    <tr>
        <td></td>
        <td>43</td>
        <td>HALT</td>
        <td>Halt. The program has completed its task.</td>
    </tr>
</table>

## Summing up 2 numbers
First we feed the computer the instructions which lead it to run an application that sums up two numbers.
<table>
    <th>
        <td>Location</td>
        <td>Number</td>
        <td>Instruction</td>
    </th>
    <tr>
        <td></td>
        <td>00</td>
        <td>+1007</td>
        <td>(Read A into memory 07)</td>
    </tr>
    <tr>
        <td></td>
        <td>01</td>
        <td>+1008</td>
        <td>(Read B into memory 08)</td>
    </tr>
    <tr>
        <td></td>
        <td>02</td>
        <td>+2007</td>
        <td>(Load A from memory 07)</td>
    </tr>
    <tr>
        <td></td>
        <td>03</td>
        <td>+3008</td>
        <td>(Add B)</td>
    </tr>
    <tr>
        <td></td>
        <td>04</td>
        <td>+2109</td>
        <td>(Store C to memory 09)</td>
    </tr>
    <tr>
        <td></td>
        <td>05</td>
        <td>+1109</td>
        <td>(Write to line from memory 09)</td>
    </tr>
    <tr>
        <td></td>
        <td>06</td>
        <td>+4300</td>
        <td>(Halt execution)</td>
    </tr>
    <tr>
        <td></td>
        <td>07</td>
        <td>+0000</td>
        <td>(Reserved memory for A)</td>
    </tr>
    <tr>
        <td></td>
        <td>08</td>
        <td>+0000</td>
        <td>(Reserved memory for B)</td>
    </tr>
    <tr>
        <td></td>
        <td>09</td>
        <td>+0000</td>
        <td>(Reserved memory for C)</td>
    </tr>
</table>

After computation is finished and application is halted it prints or dumps its register and memory to display.
![Simpletron](graphics/Simpletron.png)