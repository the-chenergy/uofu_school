#include <iostream>
#include <cmath>

using namespace std;

int main() {
  int addr_size = 16;
  int block = 8; // block size (maximum offset)
  int rows = 4;
  int ways = 2; // num of mini fully assoc maps (in a set assoc map)
  
  int cpi = 1;
  int const_pen = 10; // constant extra penalty for misses
  
  int n = 32; // addr count
  int addrs[] = {
    4, 8, 12, 16, 20, 32, 36, 40, 44, 20, 32, 36, 40, 44, 64, 68,
    4, 8, 12, 92, 96, 100, 104, 108, 112, 100, 112, 116, 120, 128, 140, 144
  };
  
  cout << "------------" << endl
       << "address size: " << addr_size << endl
       << "block size: " << block << endl
       << "rows: " << rows << endl
       << "ways: " << ways << endl
       << "normal cpi: " << cpi << endl
       << "extra miss penalty: " << const_pen << endl
       << "addresses: ";
  for (int i = 0; i < n; i++) {
    cout << addrs[i] << " ";
  }
  cout << endl;
  
  int cache[rows][ways];
  for (int i = 0; i < rows; i++) {
    for (int j = 0; j < ways; j++) {
      cache[i][j] = -1;
    }
  }
  
  int pen = const_pen + cpi * block;
  
  cout << "------------" << endl
       << "addr\ttag\trow" << endl;
  
  int hits = 0, cycles = n;
  for (int t = 0; t < 2; t++) {
    // only analyze the second run (when t == 1)
    
    for (int i = 0; i < n; i++) {
      int addr = addrs[i];
      int offset = addr % block;
      int row = addr / block % rows;
      int tag = addr / block / rows;
      
      if (t == 1) {
	cout << addr << "\t" << tag << "\t" << row << "\t";
      }
      
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
	  cout << "hit" << endl;
	  hits++;
	}
	continue;
      }
      
      // miss
      if (t == 1) {
	cout << "miss" << endl;
	cycles += pen;
      }
      
      // put loaded data at the front and shift all
      for (int k = ways - 1; k > 0; k--) {
	cache[row][k] = cache[row][k - 1];
      }
      cache[row][0] = tag;
    }
  }
  
  cout << "------------" << endl
       << "contents after simulation:" << endl
       << "row\t(v) tags in ways..." << endl;
    
  for (int i = 0; i < rows; i++) {
    cout << i << "\t";
    for (int j = 0; j < ways; j++) {
      if (cache[i][j] < 0) {
	cout << "(0)" << "\t";
      } else {
	cout << "(1) " << cache[i][j] << "\t";
      }
    }
    cout << endl;
  }
  
  cout << "------------" << endl
       << "hits: " << hits << endl
       << "misses: " << (n - hits) << endl
       << "hit rate: " << (double)hits / n << endl
       << "panelty for each miss: " << pen << endl
       << "total miss penalty: " << cycles - n << endl
       << "total cycles: " << cycles << endl
       << "cpi: " << (double)cycles / n << endl
       << "lru bits used: " << (int)ceil(log(ways) / log(2)) << endl
       << "total bits used: " << (int)(rows * ways
				 * (1 + addr_size - log(rows * block) / log(2)
				    + block * 8 + ceil(log(ways) / log(2)))) 
       << endl
       << "------------" << endl;
  
  return 0;
}
