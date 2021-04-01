import numpy
from scipy import linalg

def freq_dir(A, l):
    n, d = A.shape
    for a_i in A:
        # TODO
        pass

if __name__ == '__main__':
    A = numpy.loadtxt('data/A.csv', delimiter=',')
    freq_dir(A, 3)