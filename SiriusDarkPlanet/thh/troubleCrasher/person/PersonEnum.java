package troubleCrasher.person;

public enum PersonEnum {
	CAPTAIN("警长", "thhimage/Captain.png", "代填", "thhimage/Captain_Icon.png"),
	YOUNGMAN("年轻人", "thhimage/YoungMan.png", "代填", "thhimage/YoungMan_Icon.png"),
	PRIEST("神父", "thhimage/Priest.png", "代填", "thhimage/Priest_Icon.png"),
	BARMANAGER("酒馆老板", "thhimage/BarManager.png", "代填", "thhimage/BarManager_Icon.png"),
	BANKER("银行家", "thhimage/Focus.png", "没胡子。", "thhimage/Unknown_Icon.png"),
	FARMER("农夫", "thhimage/Farmer.png", "代填", "thhimage/Unknown_Icon.png"),
	DOCTOR("医生", "thhimage/Doctor.png", "代填", "thhimage/Unknown_Icon.png"),
	ANNOUNCER("播报员", "thhimage/Announcer.png", "代填", "thhimage/Unknown_Icon.png"),
	NARRATAGE("旁白", null, "代填", "thhimage/Unknown_Icon.png");

	public final String name;
	public final String personImage;
	public final String description;
	public final String personIcon;

	PersonEnum(final String name, final String personImage, final String description, final String personIcon) {
		this.name = name;
		this.personImage = personImage;
		this.description = description;
		this.personIcon = personIcon;
	}
}
