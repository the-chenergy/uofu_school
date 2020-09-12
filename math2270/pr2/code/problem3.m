% Qianlang Chen
% Tue, Apr 14, 2020
% 
% Solves an example linear programming problem on our own using the simplex
% method.
% 
% Problem Text:
%   A company makes two products, X and Y, using two machines, A and B.
% Producing each unit of product X requires 15 minutes processing time on
% machine A followed by 10 minutes on machine B. Producing each unit of Y
% requires 5 minutes on machine A followed by 10 minutes on machine B. The
% available processing time is 72 hours on machine A and 60 hours on machine B.
% 
%   There are 24 units of X and 84 units of Y currently in stock. The demand for
% the coming week is forecast to be 96 units of product X and 120 units of
% product Y. The company would like to maximize the combined total of the units 
% X and Y in stock at the end of the week. Using a linear program, find out how 
% much of each product the company should make for the current week.
% 
% Solution:
%   Let x be the number of X units to produce for the week and y be the number
% of Y units to produce for the week.
% 
%   The units of products that'd remain in stock at the end of the week would be
% the units currently in stock, plus the units to produce, minus the units to be
% sold:
%     total = (24 + x - 96) + (84 + y - 120) = x + y - 108
% 
%   The company must have enough product made for the demands:
%     x >= 96 - 24  =>  x >= 72
%     y >= 120 - 84  =>  y >= 36
% 
%   The total time used by the machines cannot exceed their available times:
%     15*x + 10*y <= 72*60  =>  15*x + 10*y <= 4,320
%     5*x + 10*y <= 60*60  =>  5*x + 10*y <= 3,600
% 
% Math Model:
%   Maximize
%     total = x + y - 108
% 
%   Subjective to
%     15*x + 10*y <= 4320
%     5*x + 10*y <= 3600
%     x >= 72
%     y >= 36

% Initialize the objective function (the function to minimize).
% 
% Since the simplex method always minimizes the system, we take the opposite
% values for the coefficients for the algorithm, then we'll take the opposite of
% the result for the maximized value.
total = [-1  -1];

% Initialize the integer constraint so we only produce integer units of either
% product.
intcon = [1  2];

% Initialize the less-than constraints.
A = [
  15  10;
  5   10;
];
b = [4320  3600];

% Initialize the equality constraints (none for this problem).
Aeq = [];
beq = [];

% Initialize variable bounds.
lb = [72  36];
ub = [];

% Run the simplex algorithm to solve the model.
[Prod, Total] = intlinprog(total, intcon, A, b, Aeq, beq, lb, ub);

% Take the opposite of the result to get the maximized value and subtract back
% 108 like in the original maximizing equation.
Total = -Total - 108;

% Display the results.
disp("Units of product X to produce: " + Prod(1));
disp("Units of product Y to produce: " + Prod(2));
disp("Units remain in stock after the week: " + Total);
