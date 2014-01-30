package octolink.util;

public class Sprite {
	
	private String image;
	private int width;
	private int height;
	private float scale;
	private int[] rows;
	
	public Sprite(String image, int width, int height, float scale, int[] rows) {
		this.image = image;
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.rows = rows;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public int[] getRows() {
		return rows;
	}
	
	public void setRows(int[] rows) {
		this.rows = rows;
	}
	
}
