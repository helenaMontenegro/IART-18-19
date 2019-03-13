
public class Block {
	int id;
	int column;
	int line;
	int length;
	String direction;
	
	Block(int id, int column, int line) {
		this.length=1;
		this.column = column;
		this.line = line;
		this.id = id;
		if(this.id%2 == 0)
			this.direction = "horizontal";
		else
			this.direction = "vertical";
	}
	
	public void increase_length() {
		this.length++;
	}
	
	public int get_id() {
		return this.id;
	}
	
	public int get_line() {
		return this.line;
	}
	
	public int get_column() {
		return this.column;
	}
	
	public int get_length() {
		return this.length;
	}
	
	public String get_direction() {
		return this.direction;
	}
}
