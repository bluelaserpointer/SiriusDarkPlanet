package troubleCrasher.person;

public enum ResolutionEnum {
	LARGE("1920*1024","thhimage/Resulution1920.png",1920,1080),
	MEDIUM("1024*768","thhimage/Resolution1024.png",1024,768),
	SMALL("800*600","thhimage/Resolution800.png",800,600);
	
	
	
	public final String name;
	public final String ResolutionImage;
	public final int width;
	public final int height;
	
	
	ResolutionEnum(final String name, final String ResolutionImage, final int width, final int height) {
        this.name = name;
        this.ResolutionImage = ResolutionImage;
        this.width = width;
        this.height = height;
        }
}
