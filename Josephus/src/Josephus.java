import java.util.ArrayList;
import java.util.List;

public class Josephus {

	/**
	 * <h6><b>THE JOSEPHUS PROBLEM</b></h6> Using a singular-circularly-linked
	 * list to loop through removing all players and output the last player.
	 * This program is intended to be run from the command line using command
	 * line arguments. First argument is players. Second is passes between
	 * eliminations. The third argument randomly eliminates players.
	 * 
	 * @author Alex Place
	 * */
	private static class PlayerList {
		// Eventually this returns the winner...
		private PlayerLink dynamicPlayer;
		// The number of passes before elimination and the number of players in
		// the game.
		private int passes, players;
		// Option to turn random on.
		public boolean randomPass = false;

		/**
		 * @see main
		 * 
		 * @param arg0
		 *            The number of players in the game.
		 * @param arg1
		 *            The number of passes before elimination.
		 * @param arg2
		 *            Option to randomly pass to any player. Leave blank or
		 *            don't put "randomize" to turn off.
		 */
		public PlayerList(String arg0, String arg1, String arg2) {
			try {

				// Convert the command line arguments into integers.
				players = Integer.parseInt(arg0);
				passes = Integer.parseInt(arg1);

				// In the examples passes begin at 1.
				int beginAt = 1;

				// Output the first line of the program.
				System.out.println("Starting Hot Potato game with " + players
						+ " players.");

				// Turn the random feature on if desired. Output the second
				// line.
				if (arg2.equalsIgnoreCase("randomize")) {
					System.out.println("Random elimination is ENABLED.");
					randomPass = true;
				} else {
					System.out.println("Players are eliminated every " + passes
							+ " passes.");
					randomPass = false;
				}

				PlayerLink previous = null;
				PlayerLink head = null;

				// Circle through the list until one remaining link is declared
				// winner. This also reduces the list after players have been
				// removed.
				for (int ID = 1; ID <= players; ID++) {
					// Create a playerlink for every player.
					PlayerLink pLink = new PlayerLink(ID);

					// If there is no playerlink before this one, this link is
					// the head.
					if (previous == null) {
						head = pLink;
					} else {
						// If there is a previous link than the one after it is
						// this one.
						previous.next = pLink;
					}
					// Move to the next link.
					previous = pLink;

					if (ID == beginAt) {
						dynamicPlayer = pLink;
					}
				}
				previous.next = head; // circle the list
			} catch (Exception e) {
				// Output the usage error message for invalid arguments.
				System.err
						.println("Usage java Josephus players passes [randomize]");

			}
		}

		/**
		 * Loop through the list removing players, and determine the last player
		 * to be WINNNER.
		 * 
		 * @return PlayerLink
		 */
		private PlayerLink removePlayers() {
			//
			PlayerLink current = dynamicPlayer;

			PlayerLink previous = null;

			int testPos = passes;

			// Loop through all remaining players
			while (--testPos >= 1) {

				// If random feature is on...
				if (randomPass == true) {
					int a = (int) (Math.random() * passes + 1);
					// Randomly move around
					for (int i = 0; i <= a; i++) {
						previous = current;
						current = current.next;
					}
				} else {

					previous = current;

					current = current.next;
				}

			}
			if (previous == current.next) {
				previous.next = null;
			} else {
				previous.next = current.next;
			}

			dynamicPlayer = current.next;

			return current;
		}

		/**
		 * Single out and return the last player.
		 * 
		 * @return PlayerLink
		 */
		public PlayerLink run() {
			List<PlayerLink> killed = new ArrayList<PlayerLink>();
			while (dynamicPlayer != null && dynamicPlayer.next != null) {
				killed.add(removePlayers());
			}

			for (int i = 0; i < killed.size(); i++) {
				PlayerLink kill = killed.get(i);
				// System.out.println("Killing: " + kill);
			}
			return dynamicPlayer;
		}
	}

	/**
	 * A relatively empty PlayerLink class. Tracks a players ID and converts it
	 * from a String to an integer.
	 */
	private static class PlayerLink {
		private int ID;
		public PlayerLink next;

		/**
		 * Override toString to allow the integer to be converted to a String.
		 * 
		 * @return String Converts number into string and returns.
		 */
		@Override
		public String toString() {
			return String.valueOf(ID);
		}

		/**
		 * @param number
		 */
		public PlayerLink(int ID) {
			this.ID = ID;
		}

	}

	/**
	 * @param arg0
	 *            The number of players in the game.
	 * @param arg1
	 *            The number of passes before elimination.
	 * @param arg2
	 *            Option to randomly pass to any player. Leave blank or don't
	 *            put "randomize" to turn off.
	 */
	public static void main(String[] args) {
		try {

			// Create a new PlayerList using command line arguments.
			if (args.length > 2) {
				PlayerList playerList = new PlayerList(args[0], args[1],
						args[2]);

				// Enter program through the PlayerLink method. Returns the
				// winner or the last player.
				PlayerLink lastPlayer = playerList.run();
				if (lastPlayer != null) {
					System.out.println("WINNER: " + lastPlayer);
				}

			} else if (args.length == 2) {
				PlayerList playerList = new PlayerList(args[0], args[1],
						"false");

				// Enter program through the PlayerLink method. Returns the
				// winner or the last player.
				PlayerLink lastPlayer = playerList.run();
				if (lastPlayer != null) {
					System.out.println("WINNER: " + lastPlayer);
				}
			} else {
				System.err
						.println("Usage java Josephus players passes [randomize]");

			}

		} catch (Exception e) {
			System.err
					.println("Usage java Josephus players passes [randomize]");
		}
	}
}