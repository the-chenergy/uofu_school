#include <iostream>
#include <cmath>

using namespace std;

int min_block = 4, max_block = 64;
int min_rows = 1, max_rows = 16;
int min_ways = 1, max_ways = 8;
int max_bits = 840;

int addr_size = 16;
int n = 32; // addr count
int addrs[] = {
  4, 8, 12, 16, 20, 32, 36, 40, 44, 20, 32, 36, 40, 44, 64, 68,
  4, 8, 12, 92, 96, 100, 104, 108, 112, 100, 112, 116, 120, 128, 140, 144
};

struct result {
  int hits, pen, cycles, lru_bits, total_bits;
};

result analyze(int block, int rows, int ways) {
  int cpi = 1;
  int const_pen = 10; // constant extra penalty for misses
  
  int cache[rows][ways];
  for (int i = 0; i < rows; i++) {
    for (int j = 0; j < ways; j++) {
      cache[i][j] = -1;
    }
  }
  
  int pen = const_pen + cpi * block;
  
  int hits = 0, cycles = n;
  for (int t = 0; t < 2; t++) {
    // only analyze the second run (when t == 1)
    for (int i = 0; i < n; i++) {
      int addr = addrs[i];
      int offset = addr % block;
      int row = addr / block % rows;
      int tag = addr / block / rows;
      
      bool hit = false;
      for (int j = 0; j < ways; j++) {
	if (cache[row][j] == tag) {
	  hit = true;
	  // put most recently used at the front and shift all
	  int temp = cache[row][j];
	  for (int k = j; k > 0; k--) {
	    cache[row][k] = cache[row][k - 1];
	  }
	  cache[row][0] = temp;
	  break;
	}
      }
      if (hit) {
	if (t == 1) {
	  hits++;
	}
	continue;
      }
      
      // miss
      if (t == 1) {
	cycles += pen;
      }
      
      // put loaded data at the front and shift all
      for (int k = ways - 1; k > 0; k--) {
	cache[row][k] = cache[row][k - 1];
      }
      cache[row][0] = tag;
    }
  }
  
  result res;
  res.hits = hits;
  res.pen = pen;
  res.cycles = cycles;
  res.lru_bits = (int)ceil(log(ways)/ log(2));
  res.total_bits = (int)(rows * ways
			 * (1 + addr_size - log(rows * block) / log(2)
			    + block * 8 + ceil(log(ways) / log(2))));
  
  return res;
}

int main() {
  cout << "------------" << endl;
  
  for (int block = min_block; block <= max_block; block *= 2) {
    cout << block << " bytes per data block" << endl
	 << "ways\\rows..." << endl
	 << "\t";
    for (int rows = min_rows; rows <= max_rows; rows *= 2) {
      cout << rows << "\t";
    }
    cout << endl;
    for (int ways = min_ways; ways <= max_ways; ways++) {
      cout << ways << "\t";
      for (int rows = min_rows; rows <= max_rows; rows *= 2) {
	result res = analyze(block, rows, ways);
	if (res.total_bits <= max_bits) {
	  cout << (double)res.cycles / n << "\t";
	  //cout << res.hits << "\t";
	  //cout << res.total_bits << "\t";
	} else {
	  // too many bits
	  cout << "X\t";
	}
      }
      cout << endl;
    }
    cout << "------------" << endl;
  }
  
  return 0;
}
