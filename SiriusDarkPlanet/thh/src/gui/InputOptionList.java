package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;

import core.GHQ;

public class InputOptionList extends GUIParts{
	private final TitledLabel label;
	private int nowListLength;
	private final int listViewMaxLength;
	private int hoveredPosition = GHQ.NONE;
	private String hoveredString = "";
	private final ArrayList<String> optionList = new ArrayList<String>();
	public InputOptionList(TitledLabel label, int listViewMaxLength) {
		super.setName(label.getGroup() + ">InputOptionList");
		super.setBounds(label.intX(), label.intY() + label.height(), label.width(), label.height());
		this.label = label;
		this.listViewMaxLength = listViewMaxLength;
	}
	public InputOptionList(TitledLabel label) {
		super.setName(label.getGroup() + ">InputOptionList");
		super.setBounds(label.intX(), label.intY() + label.height(), label.width(), label.height());
		this.label = label;
		this.listViewMaxLength = GHQ.MAX;
	}
	//init
	public void addWord(String word) {
		optionList.add(word);
	}
	public void addWord(String... words) {
		for(String word : words)
			optionList.add(word);
	}
	public void addWord(Collection<? extends String> words) {
		optionList.addAll(words);
	}
	//main role
	@Override
	public void idle() {
		if(!label.isInputMode())
			return;
		//listLength update
		final String TEXT = label.getText();
		final String matchedOptions[] = new String[optionList.size()];
		nowListLength = 0;
		for(String option : optionList) {
			if(option.startsWith(TEXT)) {
				matchedOptions[nowListLength++] = option;
				if(nowListLength == listViewMaxLength)
					break;
			}
		}
		//paint
		final Graphics2D G2 = GHQ.getG2D();
		G2.setColor(Color.WHITE);
		final int H = height()*nowListLength;
		G2.fillRect(intX(), intY(), width(), H);
		//hovered position emphasize
		if(hoveredPosition != GHQ.NONE) {
			hoveredString = matchedOptions[hoveredPosition];
			G2.setColor(Color.GRAY);
			G2.fillRect(intX(), intY() + height()*hoveredPosition, width(), height());
		}
		//frame
		G2.setColor(Color.BLACK);
		G2.setStroke(GHQ.stroke3);
		G2.drawRect(intX(), intY(), width(), H);
		//text
		G2.setFont(GHQ.basicFont);
		for(int i = 0;i < nowListLength;i++)
			G2.drawString(matchedOptions[i], intX() + 3, intY() + height()*i + 20);
	}
	@Override
	public boolean clicked(MouseEvent e) {
		super.clicked(e);
		if(hoveredPosition != GHQ.NONE) {
			label.setText(hoveredString);
			label.typeEnded(hoveredString);
		}
		return true;
	}
	@Override
	public void mouseOver() {
		hoveredPosition = (GHQ.mouseScreenY() - intY())/height();
	}
	@Override
	public void mouseOut() {
		hoveredPosition = GHQ.NONE;
	}
	@Override
	public boolean isMouseEntered() {
		final int H = height()*nowListLength;
		return (label.isInputMode() || hoveredPosition != GHQ.NONE) && GHQ.screenMouseInArea(intX(), intY(), width(), H);
	}
	//control
	public void updatePosition() {
		point().setXY(label.intX(), label.intY() + height());
	}
	public void removeWord(String word) {
		optionList.remove(word);
	}
}
