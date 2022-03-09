namespace ChessTools {
	/// <summary>Represents and stores relevant information of a tournament chess game.</summary>
	public class Game {
		/// <summary>The name of the event where this game took place.</summary>
		public string Event { get; set; }

		/// <summary>The name of the site/location where the event took place.</summary>
		public string Site { get; set; }

		/// <summary>A string encoding the date when the event took place.</summary>
		public string EventDate { get; set; }

		/// <summary>The name of the round.</summary>
		public string Round { get; set; }

		/// <summary>The name of the white player.</summary>
		public string White { get; set; }

		/// <summary>The rating of the white player (or zero for unrated).</summary>
		public uint WhiteElo { get; set; }

		/// <summary>The name of the black player.</summary>
		public string Black { get; set; }

		/// <summary>The rating of the black player (or zero for unrated).</summary>
		public uint BlackElo { get; set; }

		/// <summary>
		/// The result of the game (<c>'W'</c> for white won, <c>'B'</c> for black won,
		/// <c>'D'</c> for drawn, or zero for unknown).
		/// </summary>
		public char Result { get; set; }

		/// <summary>The moves of the game in algebraic notation.</summary>
		public string Moves { get; set; }
	}
}
