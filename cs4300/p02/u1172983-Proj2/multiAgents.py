# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Actions, AgentState, Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"
        
        # print('start of printing')

        # print(f'self: {self}')

        # if True or any(x == -float('inf') for x in scores):
        #     print('*' * 30)
        #     print('game state')
        #     print(gameState)
        #     print('legal', legalMoves)
        #     print('scores', scores)
        #     print('bs', bestScore)
        # print('bi', bestIndices)
        # print('ci', chosenIndex)
        
        # print('end of printing')
        # None.haha()


        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]
        
        "*** YOUR CODE HERE ***"
        print(newFood)
        # if newScaredTimes[0] > 0:
        #     print('newScaredTimes')
        #     print(newScaredTimes)
        # print('current game state:')
        # print(currentGameState)
        # print('action:')
        # print(action)
        # print('successor game state:')
        # print(successorGameState)
        # print('new ghost states:')
        # newGhostState:AgentState = newGhostStates[0]
        # print('new pos')
        # print('pm', newPos)
        # print('g', newGhostState.getPosition())
        if action == 'Stop': return -1e50
        '''
          *   4
         ***  3
        **G** 2
         ***  1
          *   0
        
        if np.y - g.y < 0: disable N
        else: disable S
        if np.x - g.x < 0: disable E
        else: disable W
        '''
        for i in range(len(newGhostStates)):
            ghostLocation = newGhostStates[i].getPosition()
            if manhattanDistance(newPos, ghostLocation) <= 1:
                return -float('inf')
                # npx, npy = newPos
                # gx, gy = ghostLocation
                # print(successorGameState)
                # print('pacman:', npx, npy, 'ghost:', gx, gy, 'a:', action)
                # # if npy - gy < 0 and action == 'North': print('disabling N')
                # # if npy - gy > 0 and action == 'South': print('disabling S')
                # # if npx - gx < 0 and action == 'East': print('disabling E')
                # # if npx - gx > 0 and action == 'West': print('disabling W')
                # if npy - gy < 0 and action == 'North': return -float('inf')
                # if npy - gy > 0 and action == 'South': return -float('inf')
                # if npx - gx < 0 and action == 'East': return -float('inf')
                # if npx - gx > 0 and action == 'West': return -float('inf')
                # haha()
                

# %%%%%%%%%%%%%%%%%%%%%%%%%
# %..    .         .      %
# %..            .        %
# %..                     %
# %..              ...    %
# %..            ...  ?>  %
# %..         .  ...  ... %
# %..              .. G  o%
# %%%%%%%%%%%%%%%%%%%%%%%%%
            
        # ghostStateLocation = []
        # minGhostDistance = float('inf')
        # for i in range(len(ghostStateLocation)):            
        #     ghostDistance = manhattanDistance(newPos, ghostStateLocation[i])
        #     if ghostDistance < minGhostDistance:
        #         minGhostDistance = ghostDistance
        # if minGhostDistance == float('inf'):
        #     minGhostDistance = 0
        # if newScaredTimes[0] > 0:
        #     minGhostDistance *= -1

        minDistanceToFood = float('inf')
        minDistanceBetweenFoods = float('inf')
        numFoods = 0
        for i in range(newFood.width):
            for j in range(newFood.height):
                if newFood[i][j]:
                    food = (i, j)
                    numFoods += 1
                    tempDistanceToFood = manhattanDistance(newPos, food)
                    # print(action, food, newPos, tempDistanceToFood)
                    if tempDistanceToFood < minDistanceToFood:
                        minDistanceToFood = tempDistanceToFood
                    
                    for k in range(newFood.width):
                        for l in range(newFood.height):
                            if k != i or l != j:
                                otherFood = (k, l)
                                tempDistanceBetweenFoods = manhattanDistance(food, otherFood)
                                if tempDistanceBetweenFoods < minDistanceBetweenFoods:
                                    minDistanceBetweenFoods = tempDistanceBetweenFoods
        if minDistanceBetweenFoods == float('inf'): minDistanceBetweenFoods = 0
        if minDistanceToFood == float('inf'): minDistanceToFood = 0

        # print(sum)
        # print('new food')
        # print(newFood[1][1])
        # haha()
        # minDistanceToScaredGhost = float('inf')
        
        # .< .
        # print(action, minDistanceToFood, numFoods, minDistanceBetweenFoods)
        return -minDistanceToFood / (newFood.width + newFood.height) - (numFoods - 1) * minDistanceBetweenFoods
        # return successorGameState.getScore()
        
        '''
        N: -2 - (3 - 1) * 10 = -22
        W: -10 - (2 - 1) * 10 = -20
        '''

def scoreEvaluationFunction(currentGameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"
        util.raiseNotDefined()

class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        util.raiseNotDefined()

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** YOUR CODE HERE ***"
        util.raiseNotDefined()

def betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()

# Abbreviation
better = betterEvaluationFunction
