package Entities;

import java.util.StringTokenizer;

import org.sikuli.script.*;
public class ActionUnit {

	private double timeout = 10.0;
	private String type;
	private String targetOffset;
	private String target;
	private String inputText;
	private int xOffset;
	private int yOffset;
	private double similarity;
	private Pattern pattern;
	public ActionUnit(String type, String targetOffset, String target, String inputText, 
			Double similarity, String timeout) {
		super();
		this.type = type;
		this.target = target;
		this.targetOffset = targetOffset;
		this.inputText = inputText;
		this.similarity = (similarity == null ? 0.7 : similarity);
		parseOffset();
		if(target != null)
			pattern = new Pattern(target).similar((float) this.similarity).
				targetOffset(xOffset, yOffset);
		if(timeout!=null)
			this.timeout = (new Double(timeout)).doubleValue();
	}
	
	private void parseOffset() {
		if(targetOffset == null) {
			xOffset = 0;
			yOffset = 0;
			return;
		}
		
		StringTokenizer tok = new StringTokenizer(targetOffset, ", ");
		xOffset = new Integer(tok.nextToken());
		yOffset = new Integer(tok.nextToken());
	}
	
	public void execute(Region region) throws FindFailed {
		if(type == null)
			return;
		
		switch (type) {
		case "execute":
			App exe;
			if(inputText != null)
				 exe = App.open(inputText);
			break;
			
		case "click":
			region.wait(pattern, timeout);
			region.click(pattern);
			break;
			
		case "doubleclick":
			
			region.wait(pattern, timeout);
			region.doubleClick(pattern);
			break;
			
		case "type":
			
			region.wait(pattern, timeout);
			if(inputText != null)
				region.type(inputText);
			break;

		default:
			break;
		}
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getxOffset() {
		return xOffset;
	}
	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}
	public int getyOffset() {
		return yOffset;
	}
	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}
	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	
	
}
