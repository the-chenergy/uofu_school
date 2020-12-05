inf = float('inf')

n = int(input())
A = [list(map(int, input().split())) for _ in range(n)]
for i in range(n):
    A[i][i] = inf

import heapq

h0 = 0
for i in range(n):
    m = min(A[i])
    if m > 0:
        h0 += m
        for j in range(n):
            A[i][j] -= m
for j in range(n):
    m = min(A[:][j])
    if m > 0:
        h0 += m
        for i in range(n):
            A[i][j] -= m
P = [(h0, n - 1, 0, 2**n - 1, A)]
b = inf
while len(P):
    (hi, r, i, U, Ai) = heapq.heappop(P)
    if hi >= b: break
    for j in range(1, n):
        if not U >> j & 1: continue
        Aj = [Aik[:] for Aik in Ai]
        hj = hi + Aj[i][j]
        for k in range(n):
            Aj[i][k] = inf
            Aj[k][j] = inf
        if r > 1: Aj[j][0] = inf
        for k in range(n):
            m = min(Aj[k])
            if 0 < m < inf:
                hj += m
                for l in range(n):
                    Aj[k][l] -= m
        for l in range(n):
            m = min(Aj[:][l])
            if 0 < m < inf:
                hj += m
                for k in range(n):
                    Aj[k][l] -= m
        if hj < b:
            if r > 1: heapq.heappush(P, (hj, r - 1, j, U - 2**j, Aj))
            else: b = hj

print(b)
