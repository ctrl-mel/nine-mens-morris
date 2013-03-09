package GameLogic;

import java.util.Calendar;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public abstract class NetworkGame extends Game {
	
	static protected final int TPC_PORT = 54555;
	static protected final int UDP_PORT = 54777;
	
	protected Player player;
	protected String otherPlayerName;
	protected boolean isThisPlayerTurn;
	protected Calendar calendar;
	
	public NetworkGame() {
		otherPlayerName = null;
		calendar = Calendar.getInstance();
	}
	
	public void setPlayer(Player p) {
		player = p;
	}
	
	public void setTurn(boolean turn) {
		isThisPlayerTurn = turn;
	}
	
	public boolean isThisPlayerTurn() {
		return isThisPlayerTurn;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public boolean removePiece(int index, int playerId) {
		if(!positionIsAvailable(index) && positionHasPieceOfPlayer(index, playerId)) {
			gameBoard.boardPositions[index].playerOccupying = Position.NO_PLAYER;
			gameBoard.boardPositions[index].isOccupied = false;
			player.removePiece();
			return true;
		}
		return false;
	}
	
	public void checkGameIsOver() {
		if((player.getNumPieces() == Game.MIN_NUM_PIECES)) {
			gameIsOver = true;
		}
	}
	
	public void logThisMessage(String message) {
		System.out.println("["+calendar.get(Calendar.HOUR_OF_DAY)+':'+calendar.get(Calendar.MINUTE)+':'+calendar.get(Calendar.SECOND)+"] "+message);
	}

	public boolean hasConnection() {
		return otherPlayerName != null;
	}
	
	public abstract boolean setPiece(int boardIndex);
	public abstract boolean removePiece(int boardIndex);
	
	// this registers objects that are going to be sent over the network
	static protected void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(JoinGame.class);
		kryo.register(JoinAck.class);
		kryo.register(Place.class);
		kryo.register(Remove.class);
		kryo.register(Move.class);
		kryo.register(GameOver.class);
	}
	
	static protected class JoinGame {
		public String nameOfClientPlayer;
	}
	
	static protected class JoinAck {
		public String nameofServerPlayer;
	}

	static protected class Place {
		public int playerId, boardIndex;
	}
	
	static protected class Remove {
		public int playerId, boardIndex;
	}
	
	static protected class Move {
		public int playerId, srcIndex, destIndex;
	}
	
	static protected class GameOver {}
}
