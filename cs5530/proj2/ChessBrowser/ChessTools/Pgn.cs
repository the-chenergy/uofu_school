using System.Collections.Generic;
using System.IO;
using System.Text;

namespace ChessTools {
	/// <summary>
	/// Provides methods to read and parse a PGN (portable game notation) file.
	/// </summary>
	public static class Pgn {
		/// <summary>Reads a series of games from a PGN file.</summary>
		/// <param name="fileName">The path to the PGN file to read in.</param>
		/// <returns>An enumeration of games recorded in the input PGN file.</returns>
		public static IEnumerable<Game> Read(string fileName) {
			bool isReadingMoves = true;
			bool wasPrevLineBlank = true;
			Game currGame = null;
			StringBuilder currGameMoves = new StringBuilder();

			foreach (string line in File.ReadAllLines(fileName)) {
				// A blank line indicates a flip between reading the tags and the moves of a game.
				if (line.Trim().Length == 0) {
					wasPrevLineBlank = true;
					continue;
				}

				// Flipping from reading moves to reading tags marks the start of a new game. Yield
				// that previously ended game.
				if (wasPrevLineBlank) {
					wasPrevLineBlank = false;
					isReadingMoves = !isReadingMoves;
					if (!isReadingMoves) {
						if (!(currGame is null)) {
							currGame.Moves = currGameMoves.ToString().Trim();
							currGameMoves.Clear();
							yield return currGame;
						}
						currGame = new Game();
					}
				}

				// Record the moves or tags of the current game as appropriate.
				if (isReadingMoves) {
					currGameMoves.AppendLine(line);
				} else {
					// Assuming the tag format is [TagName "TagValue"]
					string trimmedLine = line.Substring(1, line.Length - 3);
					string tag = trimmedLine.Substring(0, trimmedLine.IndexOf(' '));
					string value = trimmedLine.Substring(trimmedLine.IndexOf(' ') + 2);
					SetGameTag(currGame, tag, value);
				}
			}

			// The last game
			if (!(currGame is null)) {
				currGame.Moves = currGameMoves.ToString().Trim();
				yield return currGame;
			}
		}

		/// <summary>
		/// Sets a property of a <see cref="Game"/> object with respect to a PGN tag.
		/// </summary>
		private static void SetGameTag(Game game, string tag, string value) {
			switch (tag) {
				case "Event":
					game.Event = value;
					break;
				case "Site":
					game.Site = value;
					break;
				case "EventDate":
					game.EventDate = value;
					break;
				case "Round":
					game.Round = value;
					break;
				case "White":
					game.White = value;
					break;
				case "Black":
					game.Black = value;
					break;
				case "WhiteElo":
					if (uint.TryParse(value, out uint elo)) game.WhiteElo = elo;
					break;
				case "BlackElo":
					if (uint.TryParse(value, out elo)) game.BlackElo = elo;
					break;
				case "Result":
					if (TryParseGameResult(value, out char result)) game.Result = result;
					break;
			}
		}

		/// <summary>
		/// Tries to convert the result tag from a file PGN into a result character (<c>'W'</c>,
		/// <c>'B'</c>, or <c>'D'</c>).
		/// </summary>
		private static bool TryParseGameResult(string s, out char result) {
			switch (s.Trim()) {
				case "1-0":
					result = 'W';
					return true;
				case "0-1":
					result = 'B';
					return true;
				case "1/2-1/2":
					result = 'D';
					return true;
				default:
					result = '\0';
					return false;
			}
		}
	}
}
