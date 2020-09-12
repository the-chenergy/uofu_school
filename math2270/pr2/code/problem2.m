% Qianlang Chen
% Tue, Apr 14, 2020
% 
% Solves Problem #2 from Project #2 with linear programming using the
% simplex method.
% 
% Solution Explanation:
%   Let p1a be the amount of type A blood patient 1 should receive, and p2ab be
% the amount of type AB blood patient 2 should receive, and so on. All of these
% variables must be positive.
% 
%   Each patient can only get the blood types they're compatible with:
%     --------------------------------------------------------------------
%     patient | original type | compatible types | corresponding variables
%     --------------------------------------------------------------------
%     1       | A             | A, O             | p1a, p1o
%     2       | AB            | A, AB, B, O      | p2a, p2ab, p2b, p2o
%     3       | B             | B, O             | p3b, p3o
%     4       | O             | O                | p4o
%     5       | A             | A, O             | p5a, p5o
%     6       | B             | B, O             | p6b, p6o
%     7       | AB            | A, AB, B, O      | p7a, p7ab, p7b, p7o
%     --------------------------------------------------------------------
% 
%   The total cost we're minimizing is the sum of the amount of each type of
% blood each patient gets, multiplied by its unit price:
%     cost = 1*p1a + 1*p2a + 1*p5a + 1*p7a + 2*p2ab + 2*p7ab + 4*p2b
%          + 4*p3b + 4*p6b + 4*p7b + 5*p1o + 5*p2o + 5*p3o + 5*p4o + 5*p5o
%          + 5*p6o + 5*p7o
% 
%   Each patient must get the exact amount they need:
%     p1a              + p1o = 2
%     p2a + p2ab + p2b + p2o = 3
%                  p3b + p3o = 1
%                        p4o = 2
%     p5a              + p5o = 3
%                  p6b + p6o = 2
%     p7a + p7ab + p7b + p7o = 1
% 
%   Finally, the use of each blood type cannot exceed its supply:
%     p1a + p2a             + p5a       + p7a  <= 7
%           p2ab                        + p7ab <= 6
%           p2b + p3b             + p6b + p7b  <= 4
%     p1o + p2o + p3o + p4o + p5o + p6o + p7o  <= 5
% 
% Math Model:
%   Minimize
%     cost = 1*p1a + 1*p2a + 1*p5a + 1*p7a + 2*p2ab + 2*p7ab + 4*p2b
%          + 4*p3b + 4*p6b + 4*p7b + 5*p1o + 5*p2o + 5*p3o + 5*p4o + 5*p5o
%          + 5*p6o + 5*p7o
% 
%   Subjective to
%     p1a + p2a             + p5a       + p7a  <= 7
%           p2ab                        + p7ab <= 6
%           p2b + p3b             + p6b + p7b  <= 4
%     p1o + p2o + p3o + p4o + p5o + p6o + p7o  <= 5
%     p1a              + p1o = 2
%     p2a + p2ab + p2b + p2o = 3
%                  p3b + p3o = 1
%                        p4o = 2
%     p5a              + p5o = 3
%                  p6b + p6o = 2
%     p7a + p7ab + p7b + p7o = 1
%     p1a  >= 0
%     p2a  >= 0
%     p5a  >= 0
%     p7a  >= 0
%     p2ab >= 0
%     p7ab >= 0
%     p2b  >= 0
%     p3b  >= 0
%     p6b  >= 0
%     p7b  >= 0
%     p1o  >= 0
%     p2o  >= 0
%     p3o  >= 0
%     p4o  >= 0
%     p5o  >= 0
%     p6o  >= 0
%     p7o  >= 0

% Initialize the objective function (the function to minimize).
cost = [1  1  1  1  2  2  4  4  4  4  5  5  5  5  5  5  5];

% Initialize the less-than constraints (none for this problem).

% 1  2  5  7  2  7  2  3  6  7  1  2  3  4  5  6  7 <- patient
% a  a  a  a  ab ab b  b  b  b  o  o  o  o  o  o  o <- type
A = [
  1  1  1  1  0  0  0  0  0  0  0  0  0  0  0  0  0;
  0  0  0  0  1  1  0  0  0  0  0  0  0  0  0  0  0;
  0  0  0  0  0  0  1  1  1  1  0  0  0  0  0  0  0;
  0  0  0  0  0  0  0  0  0  0  1  1  1  1  1  1  1;
];
b = [7  6  4  5];

% Initialize the equality constraints.
Aeq =[
  1  0  0  0  0  0  0  0  0  0  1  0  0  0  0  0  0;
  0  1  0  0  1  0  1  0  0  0  0  1  0  0  0  0  0;
  0  0  0  0  0  0  0  1  0  0  0  0  1  0  0  0  0;
  0  0  0  0  0  0  0  0  0  0  0  0  0  1  0  0  0;
  0  0  1  0  0  0  0  0  0  0  0  0  0  0  1  0  0;
  0  0  0  0  0  0  0  0  1  0  0  0  0  0  0  1  0;
  0  0  0  1  0  1  0  0  0  1  0  0  0  0  0  0  1;
];
beq = [2  3  1  2  3  2  1];

% Initialize variable bounds.
lb = [0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0];
ub = [];

% Run the simplex algorithm to solve the model.
[Pints, Cost] = linprog(cost, A, b, Aeq, beq, lb, ub);

% Display the results.
disp("Patient 1 gets type A:  " + Pints(1));
disp("Patient 2 gets type A:  " + Pints(2));
disp("Patient 5 gets type A:  " + Pints(3));
disp("Patient 7 gets type A:  " + Pints(4));
disp("Patient 2 gets type AB: " + Pints(5));
disp("Patient 7 gets type AB: " + Pints(6));
disp("Patient 2 gets type B:  " + Pints(7));
disp("Patient 3 gets type B:  " + Pints(8));
disp("Patient 6 gets type B:  " + Pints(9));
disp("Patient 7 gets type B:  " + Pints(10));
disp("Patient 1 gets type O:  " + Pints(11));
disp("Patient 2 gets type O:  " + Pints(12));
disp("Patient 3 gets type O:  " + Pints(13));
disp("Patient 4 gets type O:  " + Pints(14));
disp("Patient 5 gets type O:  " + Pints(15));
disp("Patient 6 gets type O:  " + Pints(16));
disp("Patient 7 gets type O:  " + Pints(17));
disp("Total cost:             " + Cost);
