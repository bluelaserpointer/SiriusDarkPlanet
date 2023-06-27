package core;

import java.io.Serializable;

public class SaveData implements Serializable{
	private static final long serialVersionUID = 8084358389955267688L;
}
final class SaveHolder implements Serializable{
	private static final long serialVersionUID = 8376623130315069281L;
	private final SaveData saveData;
	SaveHolder(SaveData saveData){
		this.saveData = saveData;
	}
	SaveData getData(){
		return saveData;
	}
}
