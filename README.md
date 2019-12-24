# IART 2018/2019

Curricular Unity: IART - Artificial Intelligence <br>
Lective Year: 2018/2019

Programming Languages used: *Java*

Developed in collaboration with *Juliana Marques* and *António Cruz*

## Unblock Me

We used search algorithms to find solutions to the game *Unblock Me* and compare each of the algorithms' results.

The following algorithms were used:
* **Breath First Search (BFS)**
* **Depth First Search (DFS)**
* **Iterative Deepening**
* **Greedy Search**
* **A\***

We used the following heuristics for the A* algorithm:
* **Distance of the main block to the exit**;
* **Number of blocks blocking the exit**;
* **Sum of the above**.

**Conclusion**: A* presented the best results, by providing an optimal and fast solution. The best heuristic was the sum of the distance of the main block to the exit with the number of blocks blocking the exit.

## Mancala

We used the Minimax algorithm to develop an agent capable of playing the game Mancala.
There are three game modes: *PC vs PC*, *PC vs Human* and *Human vs Human*. When a human plays, there is a system of hints that give advice about what should be the player's next move.
When a PC is chosen as a player, the user gets to choose the difficulty level (*Begginer*, *Medium* or *Professional*), the heuristics used (*Number of pieces in the player area* and *Difference between the Mancalas of the players*) and the Minimax Variant (with or without *Alpha Beta pruning*).

To achieve the different levels of difficulty, different depths are used with the minimax algorithm. The hardest level uses 8 levels of depth. 

**Conclusion**: The Alpha Beta pruning did not alter the results of the game, it only made it more efficient. The PC players with higher levels of difficulty always beat the players with lower levels of difficulty.
