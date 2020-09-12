#include <string>

/**
 * This class represents a partially (or compelety) collected message.
 */
class BitMessage {
 private:
  std::string message;
  int tempChar;
  int bitCount;
  
 public:
  BitMessage(); // constructor
  void append(int bit);
  std::string getMessage();
};
