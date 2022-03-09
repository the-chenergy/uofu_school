namespace ChessBrowser {
	/// <summary>Stores the parameters of a search-games query.</summary>
	public struct SearchGamesParams {
		/// <summary>The name of the white player. Set to empty to disable this filter.</summary>
		public string White { get; set; }

		/// <summary>The name of the black player. Set to empty to disable this filter.</summary>
		public string Black { get; set; }

		/// <summary>The game's opening moves. Set to empty to disable this filter.</summary>
		public string OpeningMoves { get; set; }

		/// <summary>
		/// The game's result. Set to zero to disable this filter.
		/// </summary>
		public char Result { get; set; }

		/// <summary>Whether to enable the date range filter.</summary>
		public bool FilterDateRange { get; set; }

		/// <summary>
		/// A string encoding the start date set for the date range filter (only meaningful if
		/// <see cref="UseDateRange"/> is set to <c>true</c>.
		/// </summary>
		public string DateRangeStart { get; set; }

		/// <summary>
		/// A string encoding the end date set for the date range filter (only meaningful if
		/// <see cref="UseDateRange"/> is set to <c>true</c>.
		/// </summary>
		public string DateRangeEnd { get; set; }

		/// <summary>Whether to include the game's moves in the response.</summary>
		public bool ShowMoves { get; set; }
	}
}
