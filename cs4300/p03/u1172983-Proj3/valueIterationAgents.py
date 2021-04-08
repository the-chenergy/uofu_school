# valueIterationAgents.py
# -----------------------
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

# valueIterationAgents.py
# -----------------------
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

import mdp, util

from learningAgents import ValueEstimationAgent
import collections

class ValueIterationAgent(ValueEstimationAgent):
    """
        * Please read learningAgents.py before reading this.*

        A ValueIterationAgent takes a Markov decision process
        (see mdp.py) on initialization and runs value iteration
        for a given number of iterations using the supplied
        discount factor.
    """
    
    def __init__(self, mdp, discount=0.9, iterations=100):
        """
          Your value iteration agent should take an mdp on
          construction, run the indicated number of iterations
          and then act according to the resulting policy.

          Some useful mdp methods you will use:
              mdp.getStates()
              mdp.getPossibleActions(state)
              mdp.getTransitionStatesAndProbs(state, action)
              mdp.getReward(state, action, nextState)
              mdp.isTerminal(state)
        """
        self.mdp = mdp
        self.discount = discount
        self.iterations = iterations
        self.values = util.Counter() # A Counter is a dict with default 0
        self.runValueIteration()
    
    def runValueIteration(self):
        # Write value iteration code here
        # in each iteration:
        # V_new = new row
        # for each state s in all states:
        #   V_new[s] = the q-value of the best action from s
        # V = V_new
        for i in range(self.iterations):
            v_new = util.Counter()
            states = self.mdp.getStates()
            for state in states:
                action = self.computeActionFromValues(state)
                v_new[state] = self.computeQValueFromValues(state, action)
            self.values = v_new
    
    def getValue(self, state):
        """
          Return the value of the state (computed in __init__).
        """
        return self.values[state]
    
    def computeQValueFromValues(self, state, action):
        """
          Compute the Q-value of action in state from the
          value function stored in self.values.
        """
        if self.mdp.isTerminal(state):
            return 0
        qVal = 0
        nextStates = self.mdp.getTransitionStatesAndProbs(state, action)
        # [(nextState1, prob1), (next2, prob2), ...]
        for nextState, p in nextStates:
            qVal += p * (self.mdp.getReward(state, action, nextState) +
                         self.discount * self.getValue(nextState))
        return qVal
    
    def computeActionFromValues(self, state):
        """
          The policy is the best action in the given state
          according to the values currently stored in self.values.

          You may break ties any way you see fit.  Note that if
          there are no legal actions, which is the case at the
          terminal state, you should return None.
        """
        actions = self.mdp.getPossibleActions(state)
        bestAction = None
        bestQVal = -float('inf')
        for action in actions:
            tempQVal = self.computeQValueFromValues(state, action)
            if tempQVal > bestQVal:
                bestAction = action
                bestQVal = tempQVal
        return bestAction
    
    def getPolicy(self, state):
        return self.computeActionFromValues(state)
    
    def getAction(self, state):
        "Returns the policy at the state (no exploration)."
        return self.computeActionFromValues(state)
    
    def getQValue(self, state, action):
        return self.computeQValueFromValues(state, action)

class AsynchronousValueIterationAgent(ValueIterationAgent):
    """
        * Please read learningAgents.py before reading this.*

        An AsynchronousValueIterationAgent takes a Markov decision process
        (see mdp.py) on initialization and runs cyclic value iteration
        for a given number of iterations using the supplied
        discount factor.
    """
    
    def __init__(self, mdp, discount=0.9, iterations=1000):
        """
          Your cyclic value iteration agent should take an mdp on
          construction, run the indicated number of iterations,
          and then act according to the resulting policy. Each iteration
          updates the value of only one state, which cycles through
          the states list. If the chosen state is terminal, nothing
          happens in that iteration.

          Some useful mdp methods you will use:
              mdp.getStates()
              mdp.getPossibleActions(state)
              mdp.getTransitionStatesAndProbs(state, action)
              mdp.getReward(state)
              mdp.isTerminal(state)
        """
        ValueIterationAgent.__init__(self, mdp, discount, iterations)
    
    def runValueIteration(self):
        for i in range(self.iterations):
            states = self.mdp.getStates()
            state = states[i % len(states)]
            action = self.computeActionFromValues(state)
            self.values[state] = self.computeQValueFromValues(state, action)

class PrioritizedSweepingValueIterationAgent(AsynchronousValueIterationAgent):
    """
        * Please read learningAgents.py before reading this.*

        A PrioritizedSweepingValueIterationAgent takes a Markov decision process
        (see mdp.py) on initialization and runs prioritized sweeping value iteration
        for a given number of iterations using the supplied parameters.
    """
    
    def __init__(self, mdp, discount=0.9, iterations=100, theta=1e-5):
        """
          Your prioritized sweeping value iteration agent should take an mdp on
          construction, run the indicated number of iterations,
          and then act according to the resulting policy.
        """
        self.theta = theta
        ValueIterationAgent.__init__(self, mdp, discount, iterations)
    
    def runValueIteration(self):
        # Compute the predecessors of all states
        '''
              mdp.getStates()
              mdp.getPossibleActions(state)
              mdp.getTransitionStatesAndProbs(state, action)
              mdp.getReward(state)
              mdp.isTerminal(state)
        '''
        predecessors = collections.defaultdict(list)
        states = self.mdp.getStates()
        
        for state in states:
            for action in self.mdp.getPossibleActions(state):
                nextStates = self.mdp.getTransitionStatesAndProbs(state, action)
                for nextState, p in nextStates:
                    if p > 0:
                        predecessors[nextState].append(state)
        pq = util.PriorityQueue()
        
        for state in states:
            if self.mdp.isTerminal(state):
                continue
            currVal = self.values[state]
            action = self.computeActionFromValues(state)
            highQVal = self.computeQValueFromValues(state, action)
            diff = abs(currVal - highQVal)
            pq.push(state, -diff)
        
        for i in range(self.iterations):
            if pq.isEmpty():
                break
            state = pq.pop()
            action = self.computeActionFromValues(state)
            self.values[state] = self.computeQValueFromValues(state, action)
            for p in predecessors[state]:
                currVal = self.values[p]
                action = self.computeActionFromValues(p)
                highQVal = self.computeQValueFromValues(p, action)
                diff = abs(currVal - highQVal)
                if diff > self.theta:
                    pq.update(p, -diff)
