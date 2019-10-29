package cu.home.appfirststeps;

public class Example {
	
	private String title;
	private String subtitle;
	private String group;
	
	public Example(String tit, String sub, String gro){
		title = tit;
		subtitle = sub;
		group = gro;
	}
	public String getTitle(){
	return title;
	}
	public String getSubtitle(){
		return subtitle;
	}
	public String getGroup(){
	return group;
	}

}
