package troubleCrasher.person;

public enum VolumeEnum {

	LEVEL_1("thhimage/Music1.png",1),	LEVEL_2("thhimage/Music2.png",2),	LEVEL_3("thhimage/Music3.png",3),
	LEVEL_4("thhimage/Music4.png",4),	LEVEL_5("thhimage/Music5.png",5),	LEVEL_6("thhimage/Music6.png",6),
	LEVEL_7("thhimage/Music7.png",7),	LEVEL_8("thhimage/Music8.png",8),	LEVEL_9("thhimage/Music9.png",9),
	LEVEL_10("thhimage/Music10.png",10);


//    public final String name;
    public final String volumeImage;
    public final int volumeLevel;
    // public final String description;
    
	//1：办公室
	//2：小山坡
	//3：酒馆
	//4：银行
	//5：小山坡进入战斗

    VolumeEnum(final String volumeImage, final int volumeLevel) {
        this.volumeImage = volumeImage;
        this.volumeLevel = volumeLevel;
    }
    
	
}
