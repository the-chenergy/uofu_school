using ChessTools;

using MySql.Data.MySqlClient;

using System.Collections.Generic;

namespace ChessBrowser {
	/// <summary>
	/// Provides methods to create and prepare <see cref="MySqlCommand"/>s that manipulate the
	/// chess game database.
	/// </summary>
	public static class CommandManager {
		/// <summary>
		/// Creates and prepares a <see cref="MySqlCommand"/> that can be reused to upload a chess
		/// game to the database.
		/// </summary>
		/// <param name="conn">An <i>opened</i> connection to create a command from.</param>
		public static MySqlCommand CreateUploadGameCommand(MySqlConnection conn) {
			MySqlCommand command = conn.CreateCommand();
			PrepareCommand(command, uploadGameCommandText, uploadGameCommandParams);

			return command;
		}

		/// <summary>
		/// Plugs in a chess game into a prepared upload-game command and executes it to upload a
		/// game to the database.
		/// </summary>
		/// <param name="command">The command to parametrize and execute.</param>
		/// <param name="game">The game to upload.</param>
		public static int ExecuteUploadGameCommand(MySqlCommand command, Game game) {
			ParametrizeCommand(
				command,
				uploadGameCommandParams,
				// Parameter values:
				game.Event,
				game.Site,
				game.EventDate,
				game.Round,
				game.White,
				game.WhiteElo,
				game.Black,
				game.BlackElo,
				game.Result,
				game.Moves
			);

			return command.ExecuteNonQuery();
		}

		/// <summary>The command used to upload one game to the database.</summary>
		private static readonly string uploadGameCommandText = string.Join(
			"\r\n",
			"insert into Players (Name, Elo)",
			"	values (@white, @whiteElo)",
			"		on duplicate key update Elo=greatest(Elo, @whiteElo);",
			"insert into Players (Name, Elo)",
			"	values (@black, @blackElo)",
			"		on duplicate key update Elo=greatest(Elo, @blackElo);",
			"insert ignore into Events (Name, Site, Date)",
			"	values (@event, @site, @date);",
			"insert ignore into Games (Round, Result, Moves, BlackPlayer, WhitePlayer, eID)",
			"	values (",
			"		@round,",
			"		@result,",
			"		@moves,",
			"		(select pID from Players where Name=@black),",
			"		(select pID from Players where Name=@white),",
			"		(select eID from Events where name=@event and site=@site and date=@date)",
			"	);"
		);

		/// <summary>The parameters used in an upload-game command.</summary>
		private static readonly string[] uploadGameCommandParams =
			"@event @site @date @round @white @whiteElo @black @blackElo @result @moves"
				.Split(' ');

		/// <summary>
		/// Creates and prepares a <see cref="MySqlCommand"/> that can be reused search chess
		/// games in the database according to some query parameters.
		/// </summary>
		/// <param name="conn">An <i>opened</i> connection to create a command from.</param>
		/// <param name="searchParams">The search parameters.</param>
		public static MySqlCommand CreateSearchGamesCommand(
				MySqlConnection conn, SearchGamesParams searchParams) {
			MySqlCommand command = conn.CreateCommand();
			PrepareCommand(
				command, GetSearchGamesCommand(searchParams), searchGamesCommandParams);

			return command;
		}

		/// <summary>
		/// Executes a search-games command and returns the games in the search results.
		/// </summary>
		/// <param name="command">The command to parametrize and execute.</param>
		/// <param name="searchParams">The search parameters.</param>
		public static IEnumerable<Game> ExecuteSearchGamesCommand(
				MySqlCommand command, SearchGamesParams searchParams) {
			ParametrizeCommand(
				command,
				searchGamesCommandParams,
				// Parameter values:
				searchParams.White,
				searchParams.Black,
				searchParams.OpeningMoves + '%',
				searchParams.Result,
				searchParams.DateRangeStart,
				searchParams.DateRangeEnd
			);

			// Read and parse each row in the result into a Game object.
			using (MySqlDataReader reader = command.ExecuteReader()) {
				while (reader.Read()) {
					Game game = new Game()
					{
						Event = reader.GetString("Event"),
						Site = reader.GetString("Site"),
						EventDate = reader.GetMySqlDateTime("EventDate").ToString(),
						White = reader.GetString("White"),
						WhiteElo = reader.GetUInt32("WhiteElo"),
						Black = reader.GetString("Black"),
						BlackElo = reader.GetUInt32("BlackElo"),
						Result = reader.GetChar("Result")
					};
					if (searchParams.ShowMoves) game.Moves = reader.GetString("Moves");

					yield return game;
				}
			}
		}

		/// <summary>
		/// Returns a command used to search games according to some query parameters.
		/// </summary>
		private static string GetSearchGamesCommand(SearchGamesParams p) {
			return string.Join(
				"\r\n",
				"select",
				$"		{(p.ShowMoves ? "Moves," : "")}",
				"		Events.Name as Event,",
				"		Site,",
				"		Date as EventDate,",
				"		WhitePlayers.Name as White,",
				"		WhitePlayers.Elo as WhiteElo,",
				"		BlackPlayers.Name as Black,",
				"		BlackPlayers.Elo as BlackElo,",
				"		Result",
				"	from Games",
				"		natural join Events",
				"		join Players as WhitePlayers on WhitePlayer = WhitePlayers.pID",
				"		join Players as BlackPlayers on BlackPlayer = BlackPlayers.pID",
				"	where true",
				$"		{(p.White.Length > 0 ? "and WhitePlayers.Name=@white" : "")}",
				$"		{(p.Black.Length > 0 ? "and BlackPlayers.Name=@black" : "")}",
				$"		{(p.OpeningMoves.Length > 0 ? "and Moves like @moves" : "")}",
				$"		{(p.Result > 0 ? "and Result=@result" : "")}",
				$"		{(p.FilterDateRange ? "and Date between @start and @end" : "")}"
			);
		}

		/// <summary>The parameters used in a search-games command.</summary>
		private static readonly string[] searchGamesCommandParams =
			"@white @black @moves @result @start @end".Split(' ');

		/// <summary>
		/// Prepares a command and assigns it default values for its parameters.
		/// </summary>
		private static void PrepareCommand(
				MySqlCommand command, string commandText, string[] commandParams) {
			command.CommandText = commandText;
			// MySQL requires all parameters to be added with initial values to be set in the future.
			foreach (string param in commandParams) command.Parameters.AddWithValue(param, null);
			command.Prepare();
		}

		/// <summary>
		/// Parametrizes a command and assigns it <paramref name="values"/> for its parameters.
		/// </summary>
		private static void ParametrizeCommand(
				MySqlCommand command, string[] commandParams, params object[] values) {
			for (int i = 0; i < values.Length; i++)
				command.Parameters[commandParams[i]].Value = values[i];
		}
	}
}
