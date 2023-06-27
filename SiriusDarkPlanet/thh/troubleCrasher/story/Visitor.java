package troubleCrasher.story;

public class Visitor {
	private String visitorName;
	private int visitDate;
	private String visitScript;
	
	public Visitor() {}
	
	public Visitor(String visitorName, int visitDate, String visitScript)
	{
		this.visitDate = visitDate;
		this.visitorName = visitorName;
		this.visitScript = visitScript;
	}
	
	public String getVisitorName() {
		return visitorName;
	}
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	public int getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(int visitDate) {
		this.visitDate = visitDate;
	}
	public String getVisitScript() {
		return visitScript;
	}
	public void setVisitScript(String visitScript) {
		this.visitScript = visitScript;
	}
	
	
}
