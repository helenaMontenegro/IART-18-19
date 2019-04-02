package logic;

/**
 * Class that defines a block.
 */
public class Block {
	int id;
	int column;
	int line;
	int length;
	String direction;
	/**
	 * Constructor of the class.
	 * @param id
	 * @param column
	 * @param line
	 */
	Block(int id, int column, int line) {
		this.length=1;
		this.column = column;
		this.line = line;
		this.id = id;
		if(this.id%2 == 0)
			this.direction = "horizontal";
		else
			this.direction = "vertical";
		if(id == 1)
			this.direction = "horizontal";
	}
	
	/**
	 * Function that increases the length of the block.
	 */
	public void increase_length() {
		this.length++;
	}
	
	/**
	 * Function that returns the id of the block.
	 * @return
	 */
	public int get_id() {
		return this.id;
	}
	
	/**
	 * Function that returns the initial line of the block.
	 * @return
	 */
	public int get_line() {
		return this.line;
	}
	
	/**
	 * Function that returns the initial column of the block.
	 * @return
	 */
	public int get_column() {
		return this.column;
	}
	
	/**
	 * Function that returns the length of the block.
	 * @return
	 */
	public int get_length() {
		return this.length;
	}
	
	/**
	 * Function that returns the direction of the block.
	 * @return
	 */
	public String get_direction() {
		return this.direction;
	}
}
