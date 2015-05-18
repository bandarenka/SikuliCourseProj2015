package Entities;
import org.sikuli.script.*;

public class If {

	private String targetCondition;
	private Pattern pattern;
	private double timeout = 10.0;

	public If(String targetCondition) {
		super();
		this.targetCondition = targetCondition;
		this.pattern = new Pattern(targetCondition);
	}

	public boolean executeCondition(Region region) {
		try {
			Thread.sleep((long) (1000 * timeout / 2));
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
			return false;
		}
		try {
		
			region.wait(pattern,timeout / 2);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			return false;
			//e.printStackTrace();
		}
		
		return true;
	}
	public String getTargetCondition() {
		return targetCondition;
	}

	public void setTargetCondition(String targetCondition) {
		this.targetCondition = targetCondition;
	}
}
