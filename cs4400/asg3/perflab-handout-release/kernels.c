/*******************************************
 * Solutions for the CS:APP Performance Lab
 ********************************************/

#include <stdio.h>
#include <stdlib.h>

#include "defs.h"

student_t student = {
    "Qianlang Chen",          /* Full name */
    "qianlangchen@gmail.com", /* Email address */
};

/***************
 * COMPLEX KERNEL
 ***************/

/******************************************************
 * Your different versions of the complex kernel go here
 ******************************************************/

/*
 * naive_complex - The naive baseline version of complex
 */
char naive_complex_descr[] = "naive_complex: Naive baseline implementation";
void naive_complex(int dim, pixel* src, pixel* dest) {
  int i, j;

  for (i = 0; i < dim; i++)
    for (j = 0; j < dim; j++) {
      dest[RIDX(dim - j - 1, dim - i - 1, dim)].red =
          ((int)src[RIDX(i, j, dim)].red + (int)src[RIDX(i, j, dim)].green +
           (int)src[RIDX(i, j, dim)].blue) /
          3;

      dest[RIDX(dim - j - 1, dim - i - 1, dim)].green =
          ((int)src[RIDX(i, j, dim)].red + (int)src[RIDX(i, j, dim)].green +
           (int)src[RIDX(i, j, dim)].blue) /
          3;

      dest[RIDX(dim - j - 1, dim - i - 1, dim)].blue =
          ((int)src[RIDX(i, j, dim)].red + (int)src[RIDX(i, j, dim)].green +
           (int)src[RIDX(i, j, dim)].blue) /
          3;
    }
}

/*
 * complex - Your current working version of complex
 * IMPORTANT: This is the version you will be graded on
 */
char complex_descr[] = "complex: Current working version";
void complex(int dim, pixel* src, pixel* dest) {
  const int stride = 16;
  for (int i = 0; i < dim; i += stride) {
    for (int j = 0; j < dim; j += stride) {
      for (int ii = i; ii < i + stride; ii++) {
        for (int jj = j; jj < j + stride; jj++) {
          pixel p = src[RIDX(ii, jj, dim)];
          unsigned short gray = ((unsigned int)p.red + p.green + p.blue) / 3;
          p.red = p.green = p.blue = gray;
          dest[RIDX(dim - 1 - jj, dim - 1 - ii, dim)] = p;
        }
      }
    }
  }
}

/*********************************************************************
 * register_complex_functions - Register all of your different versions
 *     of the complex kernel with the driver by calling the
 *     add_complex_function() for each test function. When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.
 *********************************************************************/

void register_complex_functions() {
  add_complex_function(&complex, complex_descr);
  add_complex_function(&naive_complex, naive_complex_descr);
}

/***************
 * MOTION KERNEL
 **************/

/***************************************************************
 * Various helper functions for the motion kernel
 * You may modify these or add new ones any way you like.
 **************************************************************/

/*
 * weighted_combo - Returns new pixel value at (i,j)
 */
static pixel weighted_combo(int dim, int i, int j, pixel* src) {
  int ii, jj;
  pixel current_pixel;

  int red, green, blue;
  red = green = blue = 0;

  int num_neighbors = 0;
  for (ii = 0; ii < 3; ii++)
    for (jj = 0; jj < 3; jj++)
      if ((i + ii < dim) && (j + jj < dim)) {
        num_neighbors++;
        red += (int)src[RIDX(i + ii, j + jj, dim)].red;
        green += (int)src[RIDX(i + ii, j + jj, dim)].green;
        blue += (int)src[RIDX(i + ii, j + jj, dim)].blue;
      }

  current_pixel.red = (unsigned short)(red / num_neighbors);
  current_pixel.green = (unsigned short)(green / num_neighbors);
  current_pixel.blue = (unsigned short)(blue / num_neighbors);

  return current_pixel;
}

/******************************************************
 * Your different versions of the motion kernel go here
 ******************************************************/

/*
 * naive_motion - The naive baseline version of motion
 */
char naive_motion_descr[] = "naive_motion: Naive baseline implementation";
void naive_motion(int dim, pixel* src, pixel* dst) {
  int i, j;

  for (i = 0; i < dim; i++)
    for (j = 0; j < dim; j++) dst[RIDX(i, j, dim)] = weighted_combo(dim, i, j, src);
}

#define CHECK(t, i, j)                                                     \
  {                                                                        \
    naive_motion(n, src, dest);                                            \
    if (dest[RIDX(i, j, n)].red != p.red) {                                \
      printf("\n\n\nFAIL!! t=%i, i=%i, j=%i, e=%i, a=%i\n\n\n\n", t, i, j, \
             dest[RIDX(i, j, n)].red, p.red);                              \
      sr += *(int*)NULL;                                                   \
    }                                                                      \
    /* printf("pass t=%i, i=%i, j=%i\n", t, i, j); */                      \
  }

/*
 * motion - Your current working version of motion.
 * IMPORTANT: This is the version you will be graded on
 */
char motion_descr[] = "motion: Current working version";
void motion(int n, pixel* src, pixel* dest) {
  const int d = 16;
  unsigned int sr, sg, sb;
  pixel p;
  for (int i = 0; i < n - d; i += d) {
    for (int j = 0; j < n - d; j += d) {
      sr = sg = sb = 0;
      for (int ii = i; ii < i + 2; ii++) {
        for (int jj = j; jj < j + 3; jj++) {
          p = src[RIDX(ii, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
        }
      }
      for (int ii = i; ii < i + d; ii += 2) {
        for (int jj = j; jj < j + 3; jj++) {
          p = src[RIDX(ii + 2, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
        }
        p.red = sr / 9, p.green = sg / 9, p.blue = sb / 9;
        // CHECK(0, ii, j);
        dest[RIDX(ii, j, n)] = p;
        for (int jj = j + 1; jj < j + d; jj++) {
          for (int iii = ii; iii < ii + 3; iii++) {
            p = src[RIDX(iii, jj - 1, n)];
            sr -= p.red, sg -= p.green, sb -= p.blue;
            p = src[RIDX(iii, jj + 2, n)];
            sr += p.red, sg += p.green, sb += p.blue;
          }
          p.red = sr / 9, p.green = sg / 9, p.blue = sb / 9;
          // CHECK(1, ii, jj);
          dest[RIDX(ii, jj, n)] = p;
        }
        for (int jj = j + d - 1; jj < j + d + 2; jj++) {
          p = src[RIDX(ii, jj, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
          p = src[RIDX(ii + 3, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
        }
        p.red = sr / 9, p.green = sg / 9, p.blue = sb / 9;
        // CHECK(2, ii + 1, j + d - 1);
        dest[RIDX(ii + 1, j + d - 1, n)] = p;
        for (int jj = j + d - 2; jj >= j; jj--) {
          for (int iii = ii + 1; iii < ii + 4; iii++) {
            p = src[RIDX(iii, jj + 3, n)];
            sr -= p.red, sg -= p.green, sb -= p.blue;
            p = src[RIDX(iii, jj, n)];
            sr += p.red, sg += p.green, sb += p.blue;
          }
          p.red = sr / 9, p.green = sg / 9, p.blue = sb / 9;
          // CHECK(3, ii + 1, jj);
          dest[RIDX(ii + 1, jj, n)] = p;
        }
        for (int jj = j; jj < j + 3; jj++) {
          p = src[RIDX(ii + 1, jj, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
        }
      }
    }
  }
  {
    int i = n - d;
    for (int j = 0; j < n - d; j += d) {
      sr = sg = sb = 0;
      for (int ii = i - 1; ii < i + 2; ii++) {
        for (int jj = j; jj < j + 3; jj++) {
          p = src[RIDX(ii, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
        }
      }
      for (int ii = i; ii < i + d - 2; ii += 2) {
        for (int jj = j; jj < j + 3; jj++) {
          p = src[RIDX(ii - 1, jj, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
          p = src[RIDX(ii + 2, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
        }
        p.red = sr / 9, p.green = sg / 9, p.blue = sb / 9;
        // CHECK(5, ii, j);
        dest[RIDX(ii, j, n)] = p;
        for (int jj = j + 1; jj < j + d; jj++) {
          for (int iii = ii; iii < ii + 3; iii++) {
            p = src[RIDX(iii, jj - 1, n)];
            sr -= p.red, sg -= p.green, sb -= p.blue;
            p = src[RIDX(iii, jj + 2, n)];
            sr += p.red, sg += p.green, sb += p.blue;
          }
          p.red = sr / 9, p.green = sg / 9, p.blue = sb / 9;
          // CHECK(6, ii, jj);
          dest[RIDX(ii, jj, n)] = p;
        }
        for (int jj = j + d - 1; jj < j + d + 2; jj++) {
          p = src[RIDX(ii, jj, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
          p = src[RIDX(ii + 3, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
        }
        p.red = sr / 9, p.green = sg / 9, p.blue = sb / 9;
        // CHECK(7, ii + 1, j + d - 1);
        dest[RIDX(ii + 1, j + d - 1, n)] = p;
        for (int jj = j + d - 2; jj >= j; jj--) {
          for (int iii = ii + 1; iii < ii + 4; iii++) {
            p = src[RIDX(iii, jj + 3, n)];
            sr -= p.red, sg -= p.green, sb -= p.blue;
            p = src[RIDX(iii, jj, n)];
            sr += p.red, sg += p.green, sb += p.blue;
          }
          p.red = sr / 9, p.green = sg / 9, p.blue = sb / 9;
          // CHECK(8, ii + 1, jj);
          dest[RIDX(ii + 1, jj, n)] = p;
        }
      }
      {
        int ii = i + d - 2;
        for (int jj = j; jj < j + 3; jj++) {
          p = src[RIDX(ii - 1, jj, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
        }
        p.red = sr / 6, p.green = sg / 6, p.blue = sb / 6;
        // CHECK(9, ii, j);
        dest[RIDX(ii, j, n)] = p;
        for (int jj = j + 1; jj < j + d; jj++) {
          for (int iii = ii; iii < ii + 2; iii++) {
            p = src[RIDX(iii, jj - 1, n)];
            sr -= p.red, sg -= p.green, sb -= p.blue;
            p = src[RIDX(iii, jj + 2, n)];
            sr += p.red, sg += p.green, sb += p.blue;
          }
          p.red = sr / 6, p.green = sg / 6, p.blue = sb / 6;
          // CHECK(10, ii, jj);
          dest[RIDX(ii, jj, n)] = p;
        }
        for (int jj = j + d - 1; jj < j + d + 2; jj++) {
          p = src[RIDX(ii, jj, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
        }
        p.red = sr / 3, p.green = sg / 3, p.blue = sb / 3;
        // CHECK(11, ii + 1, j + d - 1);
        dest[RIDX(ii + 1, j + d - 1, n)] = p;
        for (int jj = j + d - 2; jj >= j; jj--) {
          p = src[RIDX(ii + 1, jj + 3, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
          p = src[RIDX(ii + 1, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
          p.red = sr / 3, p.green = sg / 3, p.blue = sb / 3;
          // CHECK(12, ii + 1, jj);
          dest[RIDX(ii + 1, jj, n)] = p;
        }
      }
    }
  }
  for (int i = 0; i < n - d; i += d) {
    int j = n - d;
    sr = sg = sb = 0;
    for (int ii = i; ii < i + 2; ii++) {
      for (int jj = j; jj < j + 3; jj++) {
        p = src[RIDX(ii, jj, n)];
        sr += p.red, sg += p.green, sb += p.blue;
      }
    }
    for (int ii = i; ii < i + d; ii += 2) {
      for (int jj = j; jj < j + 3; jj++) {
        p = src[RIDX(ii + 2, jj, n)];
        sr += p.red, sg += p.green, sb += p.blue;
      }
      p.red = sr / 9, p.green = sg / 9, p.blue = sb / 9;
      // CHECK(13, ii, j);
      dest[RIDX(ii, j, n)] = p;
      for (int jj = j + 1; jj < j + d - 2; jj++) {
        for (int iii = ii; iii < ii + 3; iii++) {
          p = src[RIDX(iii, jj - 1, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
          p = src[RIDX(iii, jj + 2, n)];
          sr += p.red, sg += p.green, sb += p.blue;
        }
        p.red = sr / 9, p.green = sg / 9, p.blue = sb / 9;
        // CHECK(14, ii, jj);
        dest[RIDX(ii, jj, n)] = p;
      }
      for (int jj = j + d - 2, jjx = 6; jjx >= 3; jj++, jjx -= 3) {
        for (int iii = ii; iii < ii + 3; iii++) {
          p = src[RIDX(iii, jj - 1, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
        }
        p.red = sr / jjx, p.green = sg / jjx, p.blue = sb / jjx;
        // CHECK(15, ii, jj);
        dest[RIDX(ii, jj, n)] = p;
      }
      {
        int jj = j + d - 1;
        p = src[RIDX(ii, jj, n)];
        sr -= p.red, sg -= p.green, sb -= p.blue;
        p = src[RIDX(ii + 3, jj, n)];
        sr += p.red, sg += p.green, sb += p.blue;
      }
      p.red = sr / 3, p.green = sg / 3, p.blue = sb / 3;
      // CHECK(16, ii + 1, j + d - 1);
      dest[RIDX(ii + 1, j + d - 1, n)] = p;
      for (int jj = j + d - 2, jjx = 6; jjx <= 9; jj--, jjx += 3) {
        for (int iii = ii + 1; iii < ii + 4; iii++) {
          p = src[RIDX(iii, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
        }
        p.red = sr / jjx, p.green = sg / jjx, p.blue = sb / jjx;
        // CHECK(17, ii + 1, jj);
        dest[RIDX(ii + 1, jj, n)] = p;
      }
      for (int jj = j + d - 4; jj >= j; jj--) {
        for (int iii = ii + 1; iii < ii + 4; iii++) {
          p = src[RIDX(iii, jj + 3, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
          p = src[RIDX(iii, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
        }
        p.red = sr / 9, p.green = sg / 9, p.blue = sb / 9;
        // CHECK(18, ii + 1, jj);
        dest[RIDX(ii + 1, jj, n)] = p;
      }
      for (int jj = j; jj < j + 3; jj++) {
        p = src[RIDX(ii + 1, jj, n)];
        sr -= p.red, sg -= p.green, sb -= p.blue;
      }
    }
  }
  {
    int i = n - d;
    int j = n - d;
    int c = 0;
    sr = sg = sb = 0;
    for (int ii = i; ii < i + 2; ii++) {
      for (int jj = j; jj < j + 3; jj++) {
        p = src[RIDX(ii, jj, n)];
        sr += p.red, sg += p.green, sb += p.blue;
        c++;
      }
    }
    for (int ii = i; ii < i + d; ii += 2) {
      if (ii + 2 < n) {
        for (int jj = j; jj < j + 3; jj++) {
          p = src[RIDX(ii + 2, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
          c++;
        }
      }
      p.red = sr / c, p.green = sg / c, p.blue = sb / c;
      // CHECK(19, ii, j);
      dest[RIDX(ii, j, n)] = p;
      for (int jj = j + 1; jj < j + d - 2; jj++) {
        for (int iii = ii, iiil = ii + 3 < n ? ii + 3 : n; iii < iiil; iii++) {
          p = src[RIDX(iii, jj - 1, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
          p = src[RIDX(iii, jj + 2, n)];
          sr += p.red, sg += p.green, sb += p.blue;
        }
        p.red = sr / c, p.green = sg / c, p.blue = sb / c;
        // CHECK(20, ii, jj);
        dest[RIDX(ii, jj, n)] = p;
      }
      for (int jj = j + d - 2, jjx = 6; jjx >= 3; jj++, jjx -= 3) {
        for (int iii = ii, iiil = ii + 3 < n ? ii + 3 : n; iii < iiil; iii++) {
          p = src[RIDX(iii, jj - 1, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
          c--;
        }
        p.red = sr / c, p.green = sg / c, p.blue = sb / c;
        // CHECK(21, ii, jj);
        dest[RIDX(ii, jj, n)] = p;
      }
      {
        int jj = j + d - 1;
        p = src[RIDX(ii, jj, n)];
        sr -= p.red, sg -= p.green, sb -= p.blue;
        c--;
        if (ii + 3 < n) {
          p = src[RIDX(ii + 3, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
          c++;
        }
      }
      p.red = sr / c, p.green = sg / c, p.blue = sb / c;
      // CHECK(22, ii + 1, j + d - 1);
      dest[RIDX(ii + 1, j + d - 1, n)] = p;
      for (int jj = j + d - 2, jjx = 6; jjx <= 9; jj--, jjx += 3) {
        for (int iii = ii + 1, iiil = ii + 4 < n ? ii + 4 : n; iii < iiil; iii++) {
          p = src[RIDX(iii, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
          c++;
        }
        p.red = sr / c, p.green = sg / c, p.blue = sb / c;
        // CHECK(23, ii + 1, jj);
        dest[RIDX(ii + 1, jj, n)] = p;
      }
      for (int jj = j + d - 4; jj >= j; jj--) {
        for (int iii = ii + 1, iiil = ii + 4 < n ? ii + 4 : n; iii < iiil; iii++) {
          p = src[RIDX(iii, jj + 3, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
          p = src[RIDX(iii, jj, n)];
          sr += p.red, sg += p.green, sb += p.blue;
        }
        p.red = sr / c, p.green = sg / c, p.blue = sb / c;
        // CHECK(24, ii + 1, jj);
        dest[RIDX(ii + 1, jj, n)] = p;
      }
      if (ii + 1 < n) {
        for (int jj = j; jj < j + 3; jj++) {
          p = src[RIDX(ii + 1, jj, n)];
          sr -= p.red, sg -= p.green, sb -= p.blue;
          c--;
        }
      }
    }
  }
  // printf("\n\n######## END ########\n\n\n");
  // dest[0].red += *(int*)NULL;
}

/*********************************************************************
 * register_motion_functions - Register all of your different versions
 *     of the motion kernel with the driver by calling the
 *     add_motion_function() for each test function.  When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.
 *********************************************************************/

void register_motion_functions() {
  add_motion_function(&motion, motion_descr);
  add_motion_function(&naive_motion, naive_motion_descr);
}
