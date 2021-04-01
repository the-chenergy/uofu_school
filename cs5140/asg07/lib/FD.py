import numpy
from scipy import linalg

def freq_dir(A, l):
    n, d = A.shape
    B = numpy.zeros((2 * l, d))
    zero_index = 0
    for i in range(n):
        B[zero_index] = A[i]
        zero_index += 1
        if zero_index == 2 * l: # full
            U, S, Vt = linalg.svd(B)
            delta = S[-1]**2
            S_prime = numpy.zeros((2 * l, d))
            numpy.fill_diagonal(S_prime,
                                [(sigma**2 - delta)**.5 for sigma in S])
            B = S_prime @ Vt
            for zero_index in range(2 * l):
                if not B[zero_index].any(): break # the first empty row
    return B

if __name__ == '__main__':
    A = numpy.loadtxt('data/A.csv', delimiter=',')
    B = freq_dir(A, 24)
    print(linalg.norm(A)**2)
    print(linalg.norm(A.T @ A - B.T @ B))
