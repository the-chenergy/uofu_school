% Qianlang Chen
% Sat, Apr 11, 2020
% 
% Solves Problem #1 from Project #2 with linear programming using the
% simplex method.
% 
% Problem model:
% 
%   Maximize
%     z = 20*x1 + 10*x2 + 15*x3;
% 
%   Subjective to
%     3*x1 + 2*x2 + 5*x3 <= 55;
%     2*x1 +   x2 +   x3 <= 26;
%       x1 +   x2 + 3*x3 <= 30;
%     5*x1 + 2*x2 + 4*x3 <= 57;
%       x1               >=  0;
%              x2        >=  0;
%                     x3 >=  0;

% Initialize the objective function (the function to minimize).
% 
% Since the simplex method always minimizes the system, we take the
% opposite values for the coefficients for the algorithm, then we'll take
% the opposite of the result for the maximized value.
z = [-20  -10  -15];

% Initialize the less-than constraints.
A = [
  3  2  5;
  2  1  1;
  1  1  3;
  5  2  4;
];
b = [55  26  30  57];

% Initialize the equality constraints (none for this problem).
Aeq = [];
beq = [];

% Initialize variable bounds.
lb = [0  0  0];
ub = [];

% Run the simplex algorithm to solve the model.
[X, Z] = linprog(z, A, b, Aeq, beq, lb, ub);

% Take the opposite of the result to get the maximized value.
Z = -Z;

% Display the results.
disp("x1 = " + X(1));
disp("x2 = " + X(2));
disp("x3 = " + X(3));
disp("z  = " + Z);
