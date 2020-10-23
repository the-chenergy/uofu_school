/**
 * Provides utility functions for this project.
 *
 * The State of the State of the States
 * Qianlang Chen
 * W 10/21/20
 */

/**
 * Converts a string into title case.
 * @param {string} text The string to convert.
 * @return {string} The input string in title case.
 */
function toTitleCase(text) {
  return text.split(/([^0-9A-Za-z]+)/)
      .map(s => s[0].toUpperCase() + s.substr(1))
      .join('');
}

/**
 * Truncates a string and put dots at the end, if necessary.
 * @param {string} text The string to truncate.
 * @param {number} maxLength The maximum length of the string before the
 *     truncate-point.
 */
function truncateAt(text, maxLength) {
  if (text.length <= maxLength) return text;
  return text.substr(0, maxLength - 1) + '...';
}

/**
 * Returns the name of the party according to the sign of the data
 *     value.
 * @param {number} margin The margin data value.
 * @returns {string} The name of the party corresponding to the margin.
 */
function partyClassOf(margin) {
  if (margin < 0) return 'democrat';
  if (margin == 0) return 'neutral';
  return 'republican';
}

/**
 * Returns the proper category class code for a dot.
 * @param {string} category The category of the dot.
 * @returns {string} The class code.
 */
function categoryClassOf(category) {
  return category.split(/[^0-9A-Za-z]/)[0].toLowerCase();
}
