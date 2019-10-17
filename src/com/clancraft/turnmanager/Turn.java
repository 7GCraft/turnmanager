import org.bukkit.Bukkit;

public class Turn {
    private int currPlayerIndex;

    public void announceTurn() {
        String turnSequence = TurnManager.cycle.toString();   
    
        Bukkit.broadcastMessage(String.format(TMStrings.CURRENT_PLAYER_ANNOUNCE, TurnManager.cycle.getPlayerName(currPlayerIndex), String.format(TMStrings.PLAYER_LIST, turnSequence)));
    }
    
    //TODO what does this do? Put the last person back in the queue?
    public boolean reinstatePlayer() {
    	return false;
    }

    public void nextTurn() {
        currPlayerIndex = (currPlayerIndex + 1) % TurnManager.cycle.size();
        announceTurn();
    }

    /**
     * NOTICE: Swap does NOT validate this inside Cycle.
     * Code from swapping:
     *  if (index1 < currPlayerIndex && index2 > currPlayerIndex || 
            index2 < currPlayerIndex && index1 > currPlayerIndex) {
            return false;
        }
     */

    //TODO timer functionality
    
    //TODO add functionality where it automatically skips players who's not present
}