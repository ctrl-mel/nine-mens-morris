package GameLogic;

public abstract class Player {
	//public static final int PLAYER_1 = 1;
	//public static final int PLAYER_2 = 2;
	
	protected String name;
	protected int gamesWon;
	protected int numPieces;
	protected int numPiecesOnBoard;
	protected Token playerToken;
	protected boolean canFly;
	
	protected Player() {
		gamesWon = 0;
		numPiecesOnBoard = 0;
		canFly = false;
	}
	
	protected Player(Token player, int numPiecesPerPlayer) throws GameException {
		this();
		if(player != Token.PLAYER_1 && player != Token.PLAYER_2) {
			throw new GameException(""+getClass().getName()+" - Invalid Player Token");
		} else {
			numPieces = numPiecesPerPlayer;
			playerToken = player;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumPieces() {
		return numPieces;
	}
	
	public int getNumPiecesOnBoard() {
		return numPiecesOnBoard;
	}
	
	public int getNumPiecesLeftToPlace() {
		return (numPieces - numPiecesOnBoard);
	}
	
	public int raiseNumPiecesOnBoard() {
		return ++numPiecesOnBoard;
	}
	
	public int lowerNumPiecesOnBoard() {
		if (--numPiecesOnBoard == 3) {
			canFly = true;
		}
		return numPiecesOnBoard;
	}
	
	public Token getPlayerToken() {
		return playerToken;
	}
	
	public boolean canItFly() {
		return canFly;
	}
	
	public abstract boolean isAI();
}
