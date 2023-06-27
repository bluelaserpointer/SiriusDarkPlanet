package loading;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class MyDataSaver {
	public abstract void output(ObjectOutputStream oos) throws IOException ;
	public abstract void input(ObjectInputStream ois) throws IOException ;
	public final boolean doSave(File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		try(final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){ //データの書き込み
			this.output(oos);
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public final boolean doLoad(File file) {
		if(!file.exists()) {
			System.out.println("File doesn't exist.");
			return false;
		}
		try(final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
			this.input(ois);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}