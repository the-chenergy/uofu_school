# search.py
# ---------
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
"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util
from util import Stack
from util import Queue
from util import PriorityQueue

class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """
    
    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()
    
    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()
    
    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()
    
    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()

def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return [s, s, w, s, w, w, s, w]

def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:

    
    """
    """
    print("Start:", problem.getStartState())
    print("Is the start a goal?", problem.isGoalState(problem.getStartState()))
    print("Start's successors:", problem.getSuccessors(problem.getStartState()))"""
    '''
    stack = Stack()
    stack.push(problem.getStartState())
    prev = {}
    visited = set()
    while not stack.isEmpty():
        currState = stack.pop()
        visited.add(currState)
        if problem.isGoalState(currState):
            path = []
            prevState = currState  
            while prevState in prev:
                prevState,prevAction = prev[prevState]
                path.append(prevAction)                    
            path.reverse()
            return path
        for neighbour, action, _ in problem.getSuccessors(currState):
            if neighbour in visited:
                continue
            prev[neighbour] = (currState, action)
            stack.push(neighbour)'''
    
    stack = Stack()
    stack.push((problem.getStartState(), None, None))
    prev = {}
    visited = set()
    while not stack.isEmpty():
        currState, prevState, prevAction = stack.pop()
        
        if currState in visited:
            continue
        prev[currState] = (prevState, prevAction)
        visited.add(currState)
        if problem.isGoalState(currState):
            path = []
            prevState, prevAction = prev[currState]
            while prevState:
                path.append(prevAction)
                prevState, prevAction = prev[prevState]
            path.reverse()
            return path
        for neighbour, action, _ in problem.getSuccessors(currState):
            stack.push((neighbour, currState, action))
    '''
    print('-----------------------------------DFS-------------------------------------')
    print('-----------------------------------DFS-------------------------------------')
    print('-----------------------------------DFS-------------------------------------')
    print('-----------------------------------DFS-------------------------------------')
    '''
    '''util.raiseNotDefined()'''

def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""
    queue = Queue()
    queue.push((problem.getStartState(), None, None))
    prev = {}
    visited = set()
    while not queue.isEmpty():
        currState, prevState, prevAction = queue.pop()
        if currState in visited:
            continue
        prev[currState] = (prevState, prevAction)
        visited.add(currState)
        if problem.isGoalState(currState):
            path = []
            prevState, prevAction = prev[currState]
            while prevState:
                path.append(prevAction)
                prevState, prevAction = prev[prevState]
            path.reverse()
            return path
        for neighbour, action, _ in problem.getSuccessors(currState):
            queue.push((neighbour, currState, action))
    print(
        '-----------------------------------BFS-------------------------------------'
    )
    print(
        '-----------------------------------BFS-------------------------------------'
    )
    print(
        '-----------------------------------BFS-------------------------------------'
    )
    print(
        '-----------------------------------BFS-------------------------------------'
    )

def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    pq = PriorityQueue()
    pq.push((problem.getStartState(), None, None, 0), 0)
    prev = {}
    visited = set()
    while not pq.isEmpty():
        currState, prevState, prevAction, totalCost = pq.pop()
        if currState in visited:
            continue
        prev[currState] = (prevState, prevAction)
        visited.add(currState)
        if problem.isGoalState(currState):
            path = []
            prevState, prevAction = prev[currState]
            while prevState:
                path.append(prevAction)
                prevState, prevAction = prev[prevState]
            path.reverse()
            return path
        for neighbour, action, cost in problem.getSuccessors(currState):
            pq.push((neighbour, currState, action, totalCost + cost),
                    totalCost + cost)

def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0

def print_state(pos, foods, walls):
    s = []
    for y in range(foods.height):
        for x in range(foods.width):
            if (x, y) == pos: s.append('<')
            elif foods[x][y]: s.append('•')
            elif walls[x][y]: s.append('@')
            else: s.append(' ')
        s.append('\n')
    print(''.join(s))

def aStarSearch(problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    pq = PriorityQueue()
    startState = problem.getStartState()
    if not isinstance(startState, str) and not isinstance(
            startState[1], (int, tuple)):
        print('a-star print start')
        print_state(startState[0], startState[1], problem.walls)
        print('a-star print end')
    pq.push((startState, None, None, 0), 0 + heuristic(startState, problem))
    prev = {}
    visited = set()
    while not pq.isEmpty():
        currState, prevState, prevAction, totalCost = pq.pop()
        if currState in visited:
            continue
        prev[currState] = (prevState, prevAction)
        visited.add(currState)
        if problem.isGoalState(currState):
            path = []
            prevState, prevAction = prev[currState]
            while prevState:
                path.append(prevAction)
                prevState, prevAction = prev[prevState]
            path.reverse()
            return path
        for neighbour, action, cost in problem.getSuccessors(currState):
            pq.push((neighbour, currState, action, totalCost + cost),
                    totalCost + cost + heuristic(neighbour, problem))

# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
