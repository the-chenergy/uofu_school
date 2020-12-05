n = int(input())
A = [list(map(int, input().split())) for _ in range(n)]
for i in range(n):
    A[i][i] = 2**30

import heapq

h0 = sum(min(A[i][j] for j in range(n)) for i in range(n)) + sum(
    min(A[i][j] for i in range(n)) for j in range(n))
P = [(n - 1, h0, 0, 0, 2**n - 1)]
b = 2**30
while len(P):
    (r, hi, ti, i, U) = heapq.heappop(P)
    if hi >= b: continue
    for j in range(1, n):
        if not U >> j & 1: continue
        tj = ti + 2 * A[i][j]
        if r > 1:
            aj0 = A[j][0]
            A[j][0] = 2**30
        hj = tj + sum(
            min(A[k][l] for l in range(n) if U - 2**j >> l & 1)
            for k in range(1, n) if U >> k & 1) + sum(
                min(A[k][l] for k in range(1, n) if U >> k & 1)
                for l in range(n) if U - 2**j >> l & 1)
        if r > 1: A[j][0] = aj0
        if hj < b:
            if r > 1: heapq.heappush(P, (r - 1, hj, tj, j, U - 2**j))
            else: b = hj

print(b // 2)
