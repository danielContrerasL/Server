package constant;

public enum MyCommand {
	
	PUBLISH_IMAGE("/publish"), ADD_HASHTAG("/hashtag"), REQUEST_IMAGE("/requestImage"), FINISH("/finish");

	private String command;

	private MyCommand(String command) {
		this.command = command;
	}

	public String getType() {
		return command;
	}

}
