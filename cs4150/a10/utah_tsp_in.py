n = int(input())

import random

print(n)
print('\n'.join([
    ' '.join([str(random.randint(1, 501)) for _ in range(n)]) for _ in range(n)
]))
