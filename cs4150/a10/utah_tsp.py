n = int(input())
A = [list(map(int, input().split())) for _ in range(n)]

import heapq

Mi = [min([A[j][i] for j in range(n) if j != i]) for i in range(n)]
Mo = [min([A[i][j] for j in range(n) if j != i]) for i in range(n)]
H = [(n - 1, sum(Mi) + sum(Mo), 2**n - 2, 0)]
b = 2**30
while len(H):
    (t, w, U, i) = heapq.heappop(H)
    if w >= b: continue
    for j in (range(1, n) if t > 0 else range(1)):
        if t > 0 and not (U >> j & 1): continue
        x = w - Mo[i] - Mi[j] + 2 * A[i][j]
        if x < b:
            if t == 0: b = x
            else: heapq.heappush(H, (t - 1, x, U - 2**j, j))

if b < 2**30: print(b // 2)
