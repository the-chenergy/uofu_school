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

from tkinter import Widget
from util import manhattanDistance
from game import Actions, AgentState, Directions, GameStateData
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
        # util.raiseNotDefined()
        
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()
        
        # Choose one of the best actions
        scores = [
            self.evaluationFunction(gameState, action) for action in legalMoves
        ]
        bestScore = max(scores)
        bestIndices = [
            index for index in range(len(scores)) if scores[index] == bestScore
        ]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best
        
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
        newScaredTimes = [
            ghostState.scaredTimer for ghostState in newGhostStates
        ]
        
        if action == 'Stop': return -1e50
        
        for i in range(len(newGhostStates)):
            ghostLocation = newGhostStates[i].getPosition()
            if manhattanDistance(newPos, ghostLocation) <= 1:
                return -float('inf')
        
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
                                tempDistanceBetweenFoods = manhattanDistance(
                                    food, otherFood)
                                if tempDistanceBetweenFoods < minDistanceBetweenFoods:
                                    minDistanceBetweenFoods = tempDistanceBetweenFoods
        if minDistanceBetweenFoods == float('inf'): minDistanceBetweenFoods = 0
        if minDistanceToFood == float('inf'): minDistanceToFood = 0
        
        return -minDistanceToFood / (newFood.width + newFood.height) - (
            numFoods - 1) * minDistanceBetweenFoods

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
    
    def __init__(self, evalFn='scoreEvaluationFunction', depth='2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """
    '''
        ONE ghost
        # returns the best evaluation pacman can hope
        evalPacman(currState, agentIndex, numAgents):
            best = -inf
            for each pacman's move:
                nextState = take this move
                best = max(best, evalGhost(nextState, 1, numAgents))
    '''
    
    def evalPacman(self, currState, agentIndex, numAgents, remDepths):
        if currState.isWin() or currState.isLose():
            return self.evaluationFunction(currState)
        if remDepths == 0: return self.evaluationFunction(currState)
        best = -float('inf')
        for action in currState.getLegalActions(agentIndex):
            nextState = currState.generateSuccessor(agentIndex, action)
            nextEval = self.evalGhost(nextState, agentIndex + 1, numAgents,
                                      remDepths)
            best = max(best, nextEval)
        return best
    
    # if win: return inf
    # if loss: return -inf
    def evalGhost(self, currState, agentIndex, numAgents, remDepths):
        best = float('inf')
        if currState.isWin() or currState.isLose():
            return self.evaluationFunction(currState)
        for action in currState.getLegalActions(agentIndex):
            nextState = currState.generateSuccessor(agentIndex, action)
            if agentIndex == numAgents - 1:
                best = min(
                    best, self.evalPacman(nextState, 0, numAgents,
                                          remDepths - 1))
            else:
                best = min(
                    best,
                    self.evalGhost(nextState, agentIndex + 1, numAgents,
                                   remDepths))
        return best
    
    def getAction(self, gameState):
        # util.raiseNotDefined()
        
        bestEval = -float('inf')
        bestAction = None
        for action in gameState.getLegalActions(0):
            nextState = gameState.generateSuccessor(0, action)
            nextEval = self.evalGhost(nextState, 1, gameState.getNumAgents(),
                                      self.depth)
            if nextEval > bestEval:
                bestEval = nextEval
                bestAction = action
        
        return bestAction

class AlphaBetaAgent(MultiAgentSearchAgent):
    
    def evalPacman(self, currState, agentIndex, numAgents, remDepths, alpha,
                   beta):
        if currState.isWin() or currState.isLose():
            return self.evaluationFunction(currState)
        if remDepths == 0: return self.evaluationFunction(currState)
        best = -float('inf')
        for action in currState.getLegalActions(agentIndex):
            nextState = currState.generateSuccessor(agentIndex, action)
            nextEval = self.evalGhost(nextState, agentIndex + 1, numAgents,
                                      remDepths, alpha, beta)
            best = max(best, nextEval)
            if best > beta: return best
            alpha = max(alpha, best)
        return best
    
    # if win: return inf
    # if loss: return -inf
    def evalGhost(self, currState, agentIndex, numAgents, remDepths, alpha,
                  beta):
        best = float('inf')
        if currState.isWin() or currState.isLose():
            return self.evaluationFunction(currState)
        for action in currState.getLegalActions(agentIndex):
            nextState = currState.generateSuccessor(agentIndex, action)
            if agentIndex == numAgents - 1:
                best = min(
                    best,
                    self.evalPacman(nextState, 0, numAgents, remDepths - 1,
                                    alpha, beta))
            else:
                best = min(
                    best,
                    self.evalGhost(nextState, agentIndex + 1, numAgents,
                                   remDepths, alpha, beta))
            if best < alpha: return best
            beta = min(beta, best)
        return best
    
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """
    
    def getAction(self, gameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        # util.raiseNotDefined()
        
        if gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
        if self.depth == 0: return self.evaluationFunction(gameState)
        bestEval = -float('inf')
        bestAction = None
        alpha = -float('inf')
        beta = float('inf')
        for action in gameState.getLegalActions(0):
            nextState = gameState.generateSuccessor(0, action)
            nextEval = self.evalGhost(nextState, 1, gameState.getNumAgents(),
                                      self.depth, alpha, beta)
            if nextEval > bestEval:
                bestEval = nextEval
                bestAction = action
            if bestEval > beta: return bestAction
            alpha = max(alpha, bestEval)
        
        return bestAction

class ExpectimaxAgent(MultiAgentSearchAgent):
    
    def evalPacman(self, currState, agentIndex, numAgents, remDepths):
        if currState.isWin() or currState.isLose():
            return self.evaluationFunction(currState)
        if remDepths == 0: return self.evaluationFunction(currState)
        best = -float('inf')
        for action in currState.getLegalActions(agentIndex):
            nextState = currState.generateSuccessor(agentIndex, action)
            nextEval = self.evalGhost(nextState, agentIndex + 1, numAgents,
                                      remDepths)
            best = max(best, nextEval)
        return best
    
    # if win: return inf
    # if loss: return -inf
    def evalGhost(self, currState, agentIndex, numAgents, remDepths):
        sumEval = 0
        numActions = 0
        if currState.isWin() or currState.isLose():
            return self.evaluationFunction(currState)
        for action in currState.getLegalActions(agentIndex):
            nextState = currState.generateSuccessor(agentIndex, action)
            if agentIndex == numAgents - 1:
                nextEval = self.evalPacman(nextState, 0, numAgents,
                                           remDepths - 1)
            else:
                nextEval = self.evalGhost(nextState, agentIndex + 1, numAgents,
                                          remDepths)
            sumEval += nextEval
            numActions += 1
        return sumEval / numActions
    
    """
      Your expectimax agent (question 4)
    """
    
    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        
        bestEval = -float('inf')
        bestAction = None
        for action in gameState.getLegalActions(0):
            nextState = gameState.generateSuccessor(0, action)
            nextEval = self.evalGhost(nextState, 1, gameState.getNumAgents(),
                                      self.depth)
            if nextEval > bestEval:
                bestEval = nextEval
                bestAction = action
        
        return bestAction

from collections import deque
from game import Grid
from pacman import GameState, nearestPoint, SCARED_TIME
from typing import List

INF = 1e9

def trueDist(wallGrid: Grid, source: tuple, dests: List[tuple]) -> List[int]:
    '''SSSP'''
    source = nearestPoint(source)
    numDests = len(dests)
    dests = list(nearestPoint(dest) for dest in dests)
    destLookup = {dests[i]: i for i in range(numDests)}
    
    vis = wallGrid.deepCopy()
    vis[source[0]][source[1]] = True
    queue = deque()
    queue.append((source, 1))
    dists = [0] * numDests
    while destLookup and queue:
        curr, dist = queue.popleft()
        for dx, dy in ((0, -1), (0, 1), (-1, 0), (1, 0)):
            nx = (curr[0] + dx, curr[1] + dy)
            if vis[nx[0]][nx[1]]: continue
            vis[nx[0]][nx[1]] = True
            if nx in destLookup:
                dists[destLookup[nx]] = dist
                destLookup.pop(nx)
            queue.append((nx, dist + 1))
    
    # Unreachable
    for _, i in destLookup.items():
        dests[i] = INF
    
    return dists

def betterEvaluationFunction(state: GameState) -> float:
    capsules: List[tuple] = state.getCapsules()
    foodGrid: Grid = state.getFood()
    ghosts: List[tuple] = state.getGhostPositions()
    numFoods = state.getNumFood()
    pacman: AgentState = state.getPacmanPosition()
    score: float = state.getScore()
    scared: List[int] = [x.scaredTimer for x in state.getGhostStates()]
    wallGrid: Grid = state.getWalls()
    
    capsulePen = len(capsules) * 100
    foodPen = numFoods * .5
    score -= capsulePen + foodPen
    
    # If there exists chasable ghosts, go for the closest one
    distChasable = INF
    distGhost = INF
    if ghosts:
        for i, dist in enumerate(trueDist(wallGrid, pacman, ghosts)):
            if scared[i] >= dist: distChasable = min(distChasable, dist)
            else: distGhost = min(distGhost, dist)
    if distChasable < INF: return score + capsulePen + 4 * -distChasable
    
    # If getting too close to a ghost
    if distGhost <= 1: return -INF
    
    # If there exists capsules, go for the closest one
    if capsules:
        if distGhost <= SCARED_TIME / 2:
            distCapsule = min(trueDist(wallGrid, pacman, capsules))
            return score + .01 * -distCapsule
        else:
            score -= 20
    
    # Otherwise, go for the closest food
    
    if not numFoods: return score
    
    foods = [(x, y)
             for x in range(foodGrid.width)
             for y in range(foodGrid.height)
             if foodGrid[x][y]]
    distFood = min(trueDist(wallGrid, pacman, foods))
    
    return score + .001 * -distFood

def _betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    
    # util.raiseNotDefined()
    
    # Useful information you can extract from a GameState (pacman.py)
    successorGameState = currentGameState
    # successorGameState = currentGameState.generatePacmanSuccessor(action)
    newPos = successorGameState.getPacmanPosition()
    newFood = successorGameState.getFood()
    newGhostStates = successorGameState.getGhostStates()
    newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]
    # if any(x > 0 for x in newScaredTimes):
    #     print('ready to be scared baby')
    #     print(newScaredTimes)
    # if action == 'Stop': return -1e50
    
    shouldChaseGhosts = any(x > 0 for x in newScaredTimes)
    if shouldChaseGhosts:
        minDistToGhost = float('inf')
        for i in range(len(newGhostStates)):
            ghostLocation = newGhostStates[i].getPosition()
            distToGhost = manhattanDistance(newPos, ghostLocation)
            if distToGhost >= newScaredTimes[i]:
                continue
            minDistToGhost = min(minDistToGhost, distToGhost)
        if minDistToGhost < float('inf'):
            return -minDistToGhost
        else:
            return -1e300
    
    # running away for ghosts
    minDistToGhost = float('inf')
    for i in range(len(newGhostStates)):
        ghostLocation = newGhostStates[i].getPosition()
        distToGhost = manhattanDistance(newPos, ghostLocation)
        if distToGhost <= 1:
            return -1e300
    
    # minDistToGhost = float('inf')
    # for i in range(len(newGhostStates)):
    #     ghostLocation = newGhostStates[i].getPosition()
    #     distToGhost = manhattanDistance(newPos, ghostLocation)
    #     shouldChaseThisGhost = newScaredTimes[i] > distToGhost
    #     if newScaredTimes[i] == distToGhost + 1:
    #         print('so close!' + '!' * 33)
    #     if shouldChaseThisGhost:
    #         minDistToGhost = min(minDistToGhost, distToGhost)
    #     elif distToGhost <= 1:
    #         return -1e300
    # if minDistToGhost < float('inf'): return -minDistToGhost
    
    # going for the closest foods
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
                            tempDistanceBetweenFoods = manhattanDistance(
                                food, otherFood)
                            if tempDistanceBetweenFoods < minDistanceBetweenFoods:
                                minDistanceBetweenFoods = tempDistanceBetweenFoods
    if minDistanceBetweenFoods == float('inf'): minDistanceBetweenFoods = 0
    if minDistanceToFood == float('inf'): minDistanceToFood = 0
    
    return -minDistanceToFood / (newFood.width + newFood.height) - (
        numFoods - 1) * minDistanceBetweenFoods

# Abbreviation
better = betterEvaluationFunction
