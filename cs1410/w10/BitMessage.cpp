#include <string>

#include "BitMessage.h"

using namespace std;

/**
 * This constructor resets the bit message object so that:
 *   - the current message is blank (an empty string)
 *   - the current bit count is 0
 *   - the temporary character is 0
 *
 * This bit message object will then be ready to accept
 * bits and form message characters.  (See the 'append' function.)
 */
BitMessage::BitMessage() {
  message = "";
  bitCount = 0;
  tempChar = 0;
}

/**
 * This function appends one bit to our message.
 * The bit is moved into position and then combined
 * with the current temporary character.
 * The bit count is increased (for both 1 or 0 bit values).
 *
 * If eight bits have been appended, then the temporary
 * character is complete and it is appended to the message,
 * and then the temporary character and bit count variables are
 * cleared.
 * 
 * Parameters:
 *    int bit - a bit (0 or 1) to combine with the current message
 *
 * Return value:
 *    none
 */
void BitMessage::append(int bit) {
  bit <<= bitCount; // move the bit left to the correct location for combining.
  tempChar |= bit; // add the new bit to the "bit list".
  
  // Check if the tempChar is passed completely.
  if (++bitCount == 8) {
    message.append(1, (char)tempChar); // put the tempChar into the string.
    tempChar = bitCount = 0; // reset them for the next character.
  }
}

/**
 * Returns the currently decoded message (not including any bits in an
 * incomplete temporary character).
 *
 * Return value:
 *   string - the current message
 */
string BitMessage::getMessage() {
  return message;
}
