## Kevin Song (u1211977) and Qianlang Chen (u1172983)

### Written Answers

**3.1:**

  * We never considered "Stop" to be a good option unless all other options are bad.
  
  * If the Pacman ever gets too close (<= 1 unit away) from any ghost, we will reject whatever action Pacman took to reach such a state.
  
  * Otherwise, we're going for the closest food combined with the number of foods left:
  
    * *-minDistanceToFood / (newFood.width + newFood.height) - ( numFoods - 1) * minDistanceBetweenFoods*

**3.2:**

  When Pacman realizes that his death is unavoidable, he will try to maximize his score, which in this case, is to rush to the closest ghost so that score penalty is the lowest:
  
  * Pacman dies after 1 move: (-500) + 1 * (-1) = -501 points
  
  * Pacman dies after 2 moves: (-500) + 2 * (-1) = -502 points
  
  So, it is better to die sooner than later if death is inevitable.

**3.4:**

  The Expectimax algorithm is not compatible with the concept of pruning from the *AlphaBetaAgent*. The pruning of the *ExpectimaxAgent* will reduce the valuable information for the agent, since the algorithm cannot be certain about what happens exactly in the pruned paths. And pruning without understanding the gravity of reducing such information can create negative outcome.

**3.5:**

  * If there's a ghost that's "chasable" (having a longer scared-time than the time Pacman needs to catch it), then go for such a ghost with the minimum distance to the Pacman.
  
  * If the Pacman ever gets too close (<= 1 unit away) from any "un-chasable" ghost, we will reject whatever action Pacman took to reach such a state.
  
  * Otherwise, we're going for the closest food combined with the number of foods left, as in *Problem 1*:
  
    * *-minDistanceToFood / (newFood.width + newFood.height) - ( numFoods - 1) * minDistanceBetweenFoods*

### Kevin's Answers:

**4.1:**

  The hardest part of the assignment for me is to figure out what the context of the project is. Specifically, I was trying to understand the problem, what each field does. Given the little instruction we received, we had trouble understanding what functions we should call and what type of fields there are available to us. 

**4.2:**

  The easiest part of the assignment is to implement the Expectimax and Alpha-Beta Pruning. These two code implementations are easy because we can just use Minimax and added some extension and edge cases to make them work. 

**4.3:**

  All of the problems are highly effective at helping me understand the material. The trial and errors and discussions I had with my partner definitely strengthened my understanding of the course materials. 

**4.4:**

  No, all of problems are very interesting and helpful to my understanding of the material. 

**4.5:**

  I would say that the project is good overall but need some clarifications on the instructions. 

### Qianlang's Answers:

**4.1:**

  The hardest part was to know what to do from the vague instructions given in the code. Especially for *Problem 2*, where the comments was obscuring which agent's Minimax action we're supposed to return. Writing print-statements to try and investigate what exactly was going on in the background was fun, though.

**4.2:**

  Getting the Alpha-Beta and Expectimax to work after finishing Minimax.

**4.3:**

  All of them. These problems all demonstrated how to put theory into practice since being able to program these clever algorithms was quite different than simply knowing the concepts behind them.

**4.4:**

  No.

**4.5:**

  Just more instructions, please.
