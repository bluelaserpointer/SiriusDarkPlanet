package troubleCrasher.person;

import sound.SoundClip;



public enum SoundEnum {
    GUN_SHOT("GUN_SHOT", "../thhmusic/GunShot.mp3"), 
    PUNCH("PUNCH", "../thhmusic/Punch.mp3"),
    TYPING("TYPING", "../thhmusic/Typing.mp3"),
    USING_BOTTLE("USING_BOTTLE", "../thhmusic/UsingBottle.mp3"),
    WOLF_CLAW("WOLF_CLAW", "../thhmusic/WolfClaw.mp3"),
    
    
    BAR("BAR", "../thhmusic/Bar.mp3"),
    INDOOR("INDOOR", "../thhmusic/Indoor.mp3"),
    OUTDOOR("OUTDOOR", "../thhmusic/Outdoor.mp3"),
    BATTLE("BATTLE", "../thhmusic/Battle.mp3");

    public final String name;
    public final String soundUrl;
    public final SoundClip soundClip;
    
    SoundEnum(final String name, final String soundUrl) {
        this.name = name;
        this.soundUrl = soundUrl;
        this.soundClip = new SoundClip(soundUrl);
    }
}
