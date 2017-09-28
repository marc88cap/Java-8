# Simpletron Machine-Language Programing
It is a computer that converts instructions into actions, a software based simulation.

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