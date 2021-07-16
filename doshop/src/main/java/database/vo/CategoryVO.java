package database.vo;

public class CategoryVO {
	private int selfId;
	private int parentId;
	private String name;
	private String url;
	private int sequence;
	
	public int getSelfId() {
		return selfId;
	}
	public void setSelfId(int selfId) {
		this.selfId = selfId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
