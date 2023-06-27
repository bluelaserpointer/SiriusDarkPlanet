package troubleCrasher.person;

public enum SceneEnum {
    WORK_DAY("WORK_DAY", "thhimage/OfficeDay.png", "INDOOR"),
	MOUNTAIN_DAY("MOUNTAIN_DAY", "thhimage/MountainDay.png", "OUTDOOR"),
	BANK("BANK", "thhimage/Bank.png", "INDOOR"),
    BAR("BAR", "thhimage/Bar.png", "BAR"),
	
	MOUNTAIN_NIGHT("MOUNTAIN_NIGHT", "thhimage/MountainNight.png", "OUTDOOR"),
	MOUNTAIN_NOON("MOUNTAIN_NOON", "thhimage/MountainNoon.png", "OUTDOOR"),
	WORK_NIGHT("WORK_NIGHT", "thhimage/OfficeNight.png", "INDOOR");


    public final String name;
    public final String sceneImage;
    public final String bgmName;
    
    // public final String description;
    
	//1：办公室
	//2：小山坡
	//3：酒馆
	//4：银行
	//5：小山坡进入战斗

    SceneEnum(final String name, final String sceneImage, final String bgmName) {
        this.name = name;
        this.sceneImage = sceneImage;
        this.bgmName = bgmName;
    }
    
    static SceneEnum getSceneEnumByName(String name)
    {
    	for(SceneEnum scene: SceneEnum.values())
    	{
    		if(scene.name.equals(name))
    			return scene;
    	}
		return null;
    }
}
