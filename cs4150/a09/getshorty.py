import heapq

while True:
    n, m = map(int, input().split())
    if not n: break
    g = [{} for _ in range(n)]
    for _ in range(m):
        x, y, f = input().split()
        x, y, f = int(x), int(y), float(f)
        g[x][y] = g[y][x] = f
    v = [False] * n
    d = [int(2e9)] * n
    h = [(-1, 0)]
    while len(h):
        s, x = heapq.heappop(h)
        if x == n - 1:
            print(f"{-s:.4f}")
            break
        if v[x]: continue
        v[x] = True
        for y, f in g[x].items():
            if v[y] or d[y] <= f * s: continue
            d[y] = f * s
            heapq.heappush(h, (d[y], y))
