package troubleCrasher.engine;

import java.awt.Color;
import java.util.List;

import java.awt.event.MouseEvent;

import core.GHQ;
import gui.AnimatedGHQTextArea;
import gui.GUIParts;
import gui.GUIPartsSwitcher;
import paint.ImageFrame;
import paint.rect.RectPaint;
import troubleCrasher.jigsaw.Jigsaw;
import troubleCrasher.jigsaw.JigsawEnum;
import troubleCrasher.person.PersonEnum;
import troubleCrasher.person.ResolutionEnum;
import troubleCrasher.person.SceneEnum;
import troubleCrasher.person.VolumeEnum;
import troubleCrasher.resource.Box;

public class GamePageSwitcher extends GUIPartsSwitcher {
	public static final int STARTSCREEN = 0, GAMESCREEN = 1, SETTINGSCREEN = 2,GAMEOVERSCREEN = 3;
	public static final int PROFILE_SESSION = 0, BOX_SESSION = 1, SETTING_SESSION = 2, SAVE_SESSION = 3;
	private static final int SAVE_MAINMENU=0,SAVE_SAVEMENU=1,LOAD_SAVEMENU=2;
	private static final int CHOOSE_SETTINGS = 0, DISPLAY_SETTINGS = 1, MUSIC_SETTINGS=2;
	public static final Color COLOR_BROWN = new Color(35, 12, 2), COLOR_GOLD = new Color(220, 207, 152);
	public static GUIPartsSwitcher leftTab;

	public static GUIParts nextButton, NPC_PART, SCENE_PART;
	private AnimatedGHQTextArea Dialogue, Speaker;

	public GamePageSwitcher() {
		super(4, STARTSCREEN);
		Dialogue = (AnimatedGHQTextArea) new AnimatedGHQTextArea().setTextSpeed(1).setBounds(477, 580, 500, 100)
				.disable();
		Speaker = (AnimatedGHQTextArea) new AnimatedGHQTextArea().setTextColor(Color.WHITE).setBounds(477, 540, 500, 30)
				.setBGImage("thhimage/Name_Tag.png").disable();

		nextButton = new GUIParts() {

			ImageFrame nextBar = ImageFrame.create("thhimage/Next_Bar.png");

			public boolean clicked(MouseEvent event) {
				if (!this.isEnabled)
					return super.clicked(event);
				if(name().equals("先挪开物品")) {
					return super.clicked(event);
				}
				// TODO: select option EDWARD
				System.out.println("Button clicked");
				TCGame.scriptManager.parseLine(TCGame.scriptManager.readLine(TCGame.scriptManager.buffReader));
				return super.clicked(event);
			}

			{
				setBounds(900, 698, 100, 40);
				setName("NEXT>");
			}

			@Override
			public void paint() {
				super.paint();
				nextBar.rectPaint(left(), top(), width(), height());
				GHQ.getG2D(Color.WHITE);
				GHQ.drawString_center(this.name(), this);
			}
			
			@Override
			public GUIParts enable() {
				super.enable();
				setName("NEXT>");
				return this;
			}

		};
		NPC_PART = new GUIParts() {
			@Override
			public boolean clicked(MouseEvent e) {
				final boolean consumed = super.clicked(e);
				if(e.getButton() == MouseEvent.BUTTON1) {
					final Jigsaw hookingJigsaw = TCGame.jigsawViewer.hookingJigsaw();
					if(hookingJigsaw != null) {
						final Box box = (Box)hookingJigsaw;
						System.out.println(TCGame.resource.getCurrentItemName());
						TCGame.resource.setCurrentItemName(box.getBoxName());
						System.out.println(TCGame.resource.getCurrentItemName());
						if(box.usable()) {
							TCGame.scriptManager.currentItemChange();
							TCGame.jigsawViewer.removeHookingJigsaw();
						} else {
							TCGame.jigsawViewer.placeJigsawToLastLocation(TCGame.jigsawViewer.hookingJigsaw());
							TCGame.jigsawViewer.removeHookingJigsaw();
						}
					}
				}
				return consumed;
			}
			@Override
			public void paint() {
				if(super.backGroundPaint instanceof ImageFrame) {
					final ImageFrame paint = (ImageFrame)super.backGroundPaint;
					paint.dotPaint_rate(cx(), bottom() - (int)(paint.height()*0.6)/2, 0.6);
				}
			}
		}.setName("NPC_IMAGE").setBounds(627, 220, 200, 300).setBGImage(PersonEnum.CAPTAIN.personImage);
		SCENE_PART = new GUIParts() {
			@Override
			public boolean clicked(MouseEvent e) {
				final boolean consumed = super.clicked(e);
				if(e.getButton() == MouseEvent.BUTTON1) {
					final Jigsaw hookingJigsaw = TCGame.jigsawViewer.hookingJigsaw();
					if(hookingJigsaw != null) {
						final Box box = (Box)hookingJigsaw;
						System.out.println("----------------------");
						System.out.println(TCGame.resource.getCurrentItemName());
						TCGame.resource.setCurrentItemName("Random");
						System.out.println(TCGame.resource.getCurrentItemName());
						TCGame.resource.setCurrentItemName(box.getBoxName());
						TCGame.scriptManager.currentItemChange();
						System.out.println(TCGame.resource.getCurrentItemName());
						System.out.println("----------------------");
						TCGame.jigsawViewer.removeHookingJigsaw();
					}
				}
				return consumed;
			}
		}.setName("SCENE_PART").setBounds(430, 0, 594, 520).setBGImage(SceneEnum.WORK_DAY.sceneImage);
		final ImageFrame arrowIF = ImageFrame.create("thhimage/Main_Menu_Arrow.png");

		// Start Menu
		set(STARTSCREEN, new GUIParts() {
			{
				this.setBGImage("thhimage/Main_Menu_BG.png");
				setName("STARTSCREEN");
				this.appendLast(new GUIParts() {
					final GUIParts newgameScrBtn = getSwitcherButton(GAMESCREEN)
							.setBGImage("thhimage/Main_Menu_NewGame.png").setName("newgameScrBtn")
							.setBounds(760, 310, 150, 50),
							loadgameScrBtn = getSwitcherButton(SETTINGSCREEN)
									.setBGImage("thhimage/Main_Menu_LoadGame.png").setName("loadgameScrBtn")
									.setBounds(735, 390, 200, 50),
							settingsScrBtn = getSwitcherButton(SETTINGSCREEN)
									.setBGImage("thhimage/Main_Menu_Setting.png").setName("settingScrBtn")
									.setBounds(735, 470, 200, 50),
							exitGameBtn = new GUIParts(){
								{
									setBGImage("thhimage/ExitGameButton.png");
									setBounds(735,550,200,50);
								}
								@Override
								public boolean clicked(MouseEvent e) {
									final boolean consumed = super.clicked(e);
									System.exit(0);
									return consumed;
								}
							};
					{
						this.setName("START_MENU_TABS");
						this.appendFirst(newgameScrBtn);
						this.appendFirst(settingsScrBtn);
						this.appendFirst(loadgameScrBtn);
						this.appendFirst(exitGameBtn);
					}
					@Override
					public void paint() {
						super.paint();
						if(newgameScrBtn.isScreenMouseOvered()) {
							arrowIF.dotPaint(newgameScrBtn.left() - 100, newgameScrBtn.cy());
						}
						if(loadgameScrBtn.isScreenMouseOvered()) {
							arrowIF.dotPaint(loadgameScrBtn.left() - 100, loadgameScrBtn.cy());
						}
						if(settingsScrBtn.isScreenMouseOvered()) {
							arrowIF.dotPaint(settingsScrBtn.left() - 100, settingsScrBtn.cy());
						}if(exitGameBtn.isScreenMouseOvered()) {
							arrowIF.dotPaint(exitGameBtn.left() - 100, exitGameBtn.cy());
						}
					}
				});
			}

			// @Override
			// public void idle() {
			// super.idle();
			// // StartScreen.dotPaint_rate(200, 200, 1.2);
			// }
		});

		// GAME SCREEN
		set(GAMESCREEN, new GUIParts() {
			{
				setName("GAMESCREEN");
				this.setBGColor(Color.GRAY);
				// 游戏功能栏及其展开画面
				this.appendLast(leftTab = new GUIPartsSwitcher(4, PROFILE_SESSION) {
					{
						setName("LEFT_MENU_AND_CONTENT");
						this.setBounds(0, 0, 430, 768);
						this.setBGImage("thhimage/UtilityBar.png");

						this.appendLast(
								getSwitcherButton_selectChangeImage(PROFILE_SESSION, "thhimage/UtilityBar_Profile.png", "thhimage/UtilityBar_Profile_Chosen.png")
										.setName("profileScrBtn").setBounds(10, 10, 50, 50));
						this.appendLast(getSwitcherButton_selectChangeImage(BOX_SESSION, "thhimage/UtilityBar_Resource.png", "thhimage/UtilityBar_Resource_Chosen.png")
								.setName("boxScrBtn").setBounds(10, 80, 50, 50));
						this.appendLast(
								getSwitcherButton_selectChangeImage(SETTING_SESSION, "thhimage/UtilityBar_Settings.png", "thhimage/UtilityBar_Settings_Chosen.png")
										.setName("settingScrBtn").setBounds(10, 160, 50, 50));
						this.appendLast(getSwitcherButton_selectChangeImage(SAVE_SESSION, "thhimage/UtilityBar_Save.png", "thhimage/UtilityBar_Save_Chosen.png")
								.setName("saveScrBtn").setBounds(10, 230, 50, 50));

						set(PROFILE_SESSION, new GUIPartsSwitcher(5, 0) {
							{
								setName("PROFILE_SESSION");
								this.setBounds(70, 0, 70, 768);
								setBGImage("thhimage/Character_Bar.png");
								// 一个人物一个框
								for (int i = 0; i < PersonEnum.values().length - 4; i++) {
									final GUIParts NPC_Button = getSwitcherButton(i)
											.setBGImage(PersonEnum.values()[i].personIcon).setName("NPC_1ScrBtn")
											.setBounds(75, 20 + i * 70, 60, 60);
									this.appendLast(NPC_Button);
									final GUIParts NPC_Profile = new GUIParts().setName("NPC_PROFILE")
											.setBGColor(COLOR_BROWN).setBounds(140, 0, 290, 768);
									set(i, NPC_Profile);
								}

							}
						});

						set(BOX_SESSION, new GUIParts() {
							{
								setName("BOX_SESSION");
								setBounds(70, 0, 360, 768);
								setBGColor(COLOR_BROWN);
								this.addLast(TCGame.jigsawViewer).setBGColor(COLOR_BROWN).setBounds(100, 600,
										JigsawEnum.JIGSAW_GRID_SIZE * 6, JigsawEnum.JIGSAW_GRID_SIZE * 3);
								this.addLast(new GUIParts() {
									{
										this.setBGImage("thhImage/darkArea.png");
										this.setBounds(100, 400, 300, 200);
									}

									@Override
									public boolean clicked(MouseEvent e) {
										final boolean consumed = super.clicked(e);
											if(e.getButton() == MouseEvent.BUTTON1) {
											Jigsaw hookingJigsaw = TCGame.jigsawViewer.hookingJigsaw();
											Jigsaw disposedJigsaw = TCGame.jigsawViewer.disposedJigsaw();
											if (hookingJigsaw != null) { // 拿进
												TCGame.jigsawViewer.disposeHookJigsaw();
												TCGame.resource.delHp(1);
											} else if (disposedJigsaw != null) { // 拿走
												TCGame.jigsawViewer.hookDisposedJigsaw();
											}
										}
										return consumed;
									}
									ImageFrame crackingIF = ImageFrame.create("thhimage/cracking.png");
									@Override
									public void paint() {
										super.paint();
										crackingIF.rectPaint(left(), top(), width(), height());
										GHQ.getG2D(Color.WHITE);
										GHQ.drawString_center("承受", this, 30);
										final Jigsaw disposedJigsaw = TCGame.jigsawViewer.disposedJigsaw();
										if (disposedJigsaw != null) {
											disposedJigsaw.paint(left(), top());
										}
									}
								});
								this.appendLast(new GUIParts() {
									{
										this.setName("waitingBox");
										this.setBGColor(COLOR_GOLD);
										this.setBounds(100, 280, 300, 100);
									}
									@Override
									public boolean clicked(MouseEvent e) {
										final boolean consumed =  super.clicked(e);
										if(TCGame.jigsawViewer.hookingJigsaw() == null) { //只允许拿走
											TCGame.jigsawViewer.hookJigsaw(TCGame.jigsawViewer.waitingJigsaw());
											TCGame.jigsawViewer.setWaitingJigsaw(null);
										}
										return consumed;
									}
									@Override
									public void paint() {
										super.paint();
										if(TCGame.jigsawViewer.waitingJigsaw() != null) {
											TCGame.jigsawViewer.waitingJigsaw().paint(left(), top());
										}
									}
								});
							}
							ImageFrame heartIF = ImageFrame.create("thhimage/Heart.png");
							ImageFrame staminaIF = ImageFrame.create("thhimage/Stamina.png");
							ImageFrame heartGaugeIF = ImageFrame.create("thhimage/Life_Gauge.png");
							ImageFrame staminaGaugeIF = ImageFrame.create("thhimage/Stamina_Gauge.png");

							@Override
							public void paint() {
								super.paint();
								GHQ.getG2D(COLOR_GOLD).setFont(GHQ.initialFont.deriveFont(25F));
								GHQ.getG2D().drawString("金钱$      " + TCGame.resource.getMoney(), 100, 50);
								GHQ.getG2D().drawString("行动点", 100, 100);
								GHQ.getG2D().drawString("生命值", 100, 155);
								for (int i = 0; i < 4; ++i) {
									staminaGaugeIF.dotPaint(220 + i * 55, 93);
									if (i < TCGame.resource.getStamina())
										staminaIF.dotPaint(220 + i * 55, 93);
								}
								for (int i = 0; i < 3; ++i) {
									heartGaugeIF.dotPaint(220 + i * 55, 145);
									if (i < TCGame.resource.getHp())
										heartIF.dotPaint(220 + i * 55, 145);
								}
								GHQ.getG2D().setFont(GHQ.initialFont);
							}
						});
						set(SETTING_SESSION, new GUIPartsSwitcher(3,CHOOSE_SETTINGS) {
							{
								setName("SETTING_SESSION");
//								this.setBGImage("thhimage/Settings_Bar.png");
								setBounds(70, 0, 360, 768);
								set(CHOOSE_SETTINGS,new GUIParts() {
									{
										this.setBGImage("thhimage/Settings_Bar.png");
										setBounds(70, 0, 360, 768);
										this.appendLast(new GUIParts() {
											final GUIParts displayScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
													.setBGImage("thhimage/Settings_Bar_DisplayButton.png").setName("displayScrBtn")
													.setBounds(200, 140, 100, 30),
													musicScrBtn = getSwitcherButton(MUSIC_SETTINGS)
															.setBGImage("thhimage/Settings_Bar_MusicButton.png").setName("musicScrBtn")
															.setBounds(200, 200, 100, 30),
															exitScrBtn = new GUIParts() {
												{
													setBGImage("thhimage/Settings_Bar_ExitButton.png").setName("exitScrBtn");
													setBounds(200, 260, 100, 30);
												}
												@Override
												public boolean clicked(MouseEvent e) {
													final boolean consumed = super.clicked(e);
													System.exit(0);
													return consumed;
												}
											};
																	
											{
												this.setName("SAVE_LOAD_TABS");
												this.appendFirst(displayScrBtn);
												this.appendFirst(musicScrBtn);
												this.appendFirst(exitScrBtn);
											}
											@Override
											public void paint() {
												super.paint();
												if(displayScrBtn.isScreenMouseOvered()) {
													arrowIF.rectPaint(displayScrBtn.left() - 60, displayScrBtn.cy()-10, 40,20);
												}
												if(musicScrBtn.isScreenMouseOvered()) {
													arrowIF.rectPaint(musicScrBtn.left() - 60, musicScrBtn.cy()-10, 40,20);
												}
												if(exitScrBtn.isScreenMouseOvered()) {
													arrowIF.rectPaint(exitScrBtn.left() - 60, exitScrBtn.cy()-10, 40,20);
												}
											}
										});
									}
								});
								set(DISPLAY_SETTINGS,new GUIParts() {
									{
										
										setBounds(70, 0, 360, 768);
										this.setBGImage("thhimage/Display_Settings.png");
										this.appendLast(new GUIParts() {
											final GUIParts resLeftScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
													.setBGImage("thhimage/LeftButton.png").setName("resLeftScrBtn")
													.setBounds(210, 190, 20, 30),
													resRightScrBtn = getSwitcherButton(MUSIC_SETTINGS)
															.setBGImage("thhimage/RightButton.png").setName("resRightScrBtn")
															.setBounds(360, 190, 20, 30),
													resolution = new GUIParts().setBGImage(ResolutionEnum.LARGE.ResolutionImage).setBounds(250,190,100,20).setName("Resolution"),
													AllScrLeftScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
															.setBGImage("thhimage/LeftButton.png").setName("AllScrLeftScrBtn")
															.setBounds(210, 240, 20, 30),
													allScreen = new GUIParts().setBGImage("thhimage/OpenButton.png").setBounds(280,250,40,20),
													AllScrRightScrBtn = getSwitcherButton(MUSIC_SETTINGS)
																	.setBGImage("thhimage/RightButton.png").setName("AllScrRightScrBtn")
																	.setBounds(360, 240, 20, 30),
													returnBtn = getSwitcherButton(CHOOSE_SETTINGS).setBounds(370,710,30,30).setBGImage("thhimage/CancelButton.png");
											{
												this.setName("SAVE_LOAD_TABS");
												this.appendFirst(resLeftScrBtn);
												this.appendFirst(resRightScrBtn);
												this.appendFirst(resolution);
												this.appendFirst(AllScrLeftScrBtn);
												this.appendFirst(allScreen);
												this.appendFirst(AllScrRightScrBtn);
												this.appendFirst(returnBtn);
											}
										});
									}
								});
								set(MUSIC_SETTINGS,new GUIParts() {
									{
										setBounds(70, 0, 360, 768);
										this.setBGImage("thhimage/Music_Settings.png");
										this.appendLast(new GUIParts() {
											final GUIParts volLeftScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
													.setBGImage("thhimage/LeftButton.png").setName("resLeftScrBtn")
													.setBounds(230, 170, 20, 30),
													volRightScrBtn = getSwitcherButton(MUSIC_SETTINGS)
															.setBGImage("thhimage/RightButton.png").setName("resRightScrBtn")
															.setBounds(360, 170, 20, 30),
													volume = new GUIParts().setBGImage(VolumeEnum.LEVEL_5.volumeImage).setBounds(255,170,100,20).setName("Resolution"),
													effectLeftScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
															.setBGImage("thhimage/LeftButton.png").setName("AllScrLeftScrBtn")
															.setBounds(230, 215, 20, 30),
													effect = new GUIParts().setBGImage("thhimage/OpenButton.png").setBounds(280,215,40,20),
													effectScrRightScrBtn = getSwitcherButton(MUSIC_SETTINGS)
																	.setBGImage("thhimage/RightButton.png").setName("AllScrRightScrBtn")
																	.setBounds(360, 215, 20, 30),
																	bgmLeftScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
																	.setBGImage("thhimage/LeftButton.png").setName("AllScrLeftScrBtn")
																	.setBounds(230, 260, 20, 30),
															bgm = new GUIParts().setBGImage("thhimage/OpenButton.png").setBounds(280,260,40,20),
															bgmScrRightScrBtn = getSwitcherButton(MUSIC_SETTINGS)
																			.setBGImage("thhimage/RightButton.png").setName("AllScrRightScrBtn")
																			.setBounds(360, 260, 20, 30),
													returnBtn = getSwitcherButton(CHOOSE_SETTINGS).setBounds(370,710,30,30).setBGImage("thhimage/CancelButton.png");
											{
												this.setName("SAVE_LOAD_TABS");
												this.appendFirst(volLeftScrBtn);
												this.appendFirst(volRightScrBtn);
												this.appendFirst(volume);
												this.appendFirst(effectLeftScrBtn);
												this.appendFirst(effect);
												this.appendFirst(effectScrRightScrBtn);
												this.appendFirst(bgmLeftScrBtn);
												this.appendFirst(bgm);
												this.appendFirst(bgmScrRightScrBtn);
												this.appendFirst(returnBtn);
											}
										});
									}
								});
								
							}
						});
						set(SAVE_SESSION, new GUIPartsSwitcher(3,SAVE_MAINMENU) {
							{
								setName("SAVE_SESSION");
//								setBGImage("thhimage/SaveBar.png");
								setBounds(70, 0, 360, 768);
								set(SAVE_MAINMENU, new GUIParts() {
									{

										setBGImage("thhimage/SaveBar.png");
										setBounds(70, 0, 360, 768);
										this.appendLast(new GUIParts() {
											final GUIParts savegameScrBtn = getSwitcherButton(SAVE_SAVEMENU)
													.setBGImage("thhimage/SaveBar_SaveButton.png").setName("newgameScrBtn")
													.setBounds(220, 160, 60, 30),
													loadgameScrBtn = getSwitcherButton(LOAD_SAVEMENU)
															.setBGImage("thhimage/SaveBar_LoadButton.png").setName("loadgameScrBtn")
															.setBounds(220, 220, 60, 30);
											{
												this.setName("SAVE_LOAD_TABS");
												this.appendFirst(savegameScrBtn);
												this.appendFirst(loadgameScrBtn);
											}
											@Override
											public void paint() {
												super.paint();
												if(savegameScrBtn.isScreenMouseOvered()) {
													arrowIF.rectPaint(savegameScrBtn.left() - 60, savegameScrBtn.cy()-10, 40,20);
												}
												if(loadgameScrBtn.isScreenMouseOvered()) {
													arrowIF.rectPaint(loadgameScrBtn.left() - 60, loadgameScrBtn.cy()-10, 40,20);
												}
											}
										});
									}
								});
								set(SAVE_SAVEMENU,new GUIParts() {
									{
										setBGImage("thhimage/SaveBar_Load.png");
										setBounds(70, 0, 360, 768);
										
										this.appendLast(new GUIParts() {
//											final GUIParts volLeftScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
//													.setBGImage("thhimage/LeftButton.png").setName("resLeftScrBtn")
//													.setBounds(230, 170, 20, 30),
//													volRightScrBtn = getSwitcherButton(MUSIC_SETTINGS)
//															.setBGImage("thhimage/RightButton.png").setName("resRightScrBtn")
//															.setBounds(360, 170, 20, 30),
//													volume = new GUIParts().setBGImage(VolumeEnum.LEVEL_5.volumeImage).setBounds(255,170,100,20).setName("Resolution"),
//													effectLeftScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
//															.setBGImage("thhimage/LeftButton.png").setName("AllScrLeftScrBtn")
//															.setBounds(230, 215, 20, 30),
//													effect = new GUIParts().setBGImage("thhimage/OpenButton.png").setBounds(280,215,40,20),
//													effectScrRightScrBtn = getSwitcherButton(MUSIC_SETTINGS)
//																	.setBGImage("thhimage/RightButton.png").setName("AllScrRightScrBtn")
//																	.setBounds(360, 215, 20, 30),
//																	bgmLeftScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
//																	.setBGImage("thhimage/LeftButton.png").setName("AllScrLeftScrBtn")
//																	.setBounds(230, 260, 20, 30),
//															bgm = new GUIParts().setBGImage("thhimage/OpenButton.png").setBounds(280,260,40,20),
//															bgmScrRightScrBtn = getSwitcherButton(MUSIC_SETTINGS)
//																			.setBGImage("thhimage/RightButton.png").setName("AllScrRightScrBtn")
//																			.setBounds(360, 260, 20, 30),
												final GUIParts returnBtn = getSwitcherButton(SAVE_MAINMENU).setBounds(370,710,30,30).setBGImage("thhimage/CancelButton.png");
											{
//												this.setName("SAVE_LOAD_TABS");
//												this.appendFirst(volLeftScrBtn);
//												this.appendFirst(volRightScrBtn);
//												this.appendFirst(volume);
//												this.appendFirst(effectLeftScrBtn);
//												this.appendFirst(effect);
//												this.appendFirst(effectScrRightScrBtn);
//												this.appendFirst(bgmLeftScrBtn);
//												this.appendFirst(bgm);
//												this.appendFirst(bgmScrRightScrBtn);
												this.appendFirst(returnBtn);
											}
										});
										
									}
								});
								set(LOAD_SAVEMENU,new GUIParts() {
									{
										setBGImage("thhimage/SaveBar_Load.png");
										setBounds(70, 0, 360, 768);
										this.appendLast(new GUIParts() {
//											final GUIParts volLeftScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
//													.setBGImage("thhimage/LeftButton.png").setName("resLeftScrBtn")
//													.setBounds(230, 170, 20, 30),
//													volRightScrBtn = getSwitcherButton(MUSIC_SETTINGS)
//															.setBGImage("thhimage/RightButton.png").setName("resRightScrBtn")
//															.setBounds(360, 170, 20, 30),
//													volume = new GUIParts().setBGImage(VolumeEnum.LEVEL_5.volumeImage).setBounds(255,170,100,20).setName("Resolution"),
//													effectLeftScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
//															.setBGImage("thhimage/LeftButton.png").setName("AllScrLeftScrBtn")
//															.setBounds(230, 215, 20, 30),
//													effect = new GUIParts().setBGImage("thhimage/OpenButton.png").setBounds(280,215,40,20),
//													effectScrRightScrBtn = getSwitcherButton(MUSIC_SETTINGS)
//																	.setBGImage("thhimage/RightButton.png").setName("AllScrRightScrBtn")
//																	.setBounds(360, 215, 20, 30),
//																	bgmLeftScrBtn = getSwitcherButton(DISPLAY_SETTINGS)
//																	.setBGImage("thhimage/LeftButton.png").setName("AllScrLeftScrBtn")
//																	.setBounds(230, 260, 20, 30),
//															bgm = new GUIParts().setBGImage("thhimage/OpenButton.png").setBounds(280,260,40,20),
//															bgmScrRightScrBtn = getSwitcherButton(MUSIC_SETTINGS)
//																			.setBGImage("thhimage/RightButton.png").setName("AllScrRightScrBtn")
//																			.setBounds(360, 260, 20, 30),
												final GUIParts returnBtn = getSwitcherButton(SAVE_MAINMENU).setBounds(370,710,30,30).setBGImage("thhimage/CancelButton.png");
											{
//												this.setName("SAVE_LOAD_TABS");
//												this.appendFirst(volLeftScrBtn);
//												this.appendFirst(volRightScrBtn);
//												this.appendFirst(volume);
//												this.appendFirst(effectLeftScrBtn);
//												this.appendFirst(effect);
//												this.appendFirst(effectScrRightScrBtn);
//												this.appendFirst(bgmLeftScrBtn);
//												this.appendFirst(bgm);
//												this.appendFirst(bgmScrRightScrBtn);
												this.appendFirst(returnBtn);
											}
										});
									}
								});
								
								
							}
						});
					}
				});

				// 场景画面
				// NPC
				this.addLast(NPC_PART);
				this.addLast(SCENE_PART);
				
				this.appendLast(new GUIParts() {{
					setBounds(430,520,594,248);
					setBGImage("thhimage/DialogueBG.png");

					this.appendLast(Speaker.setText("警长").enable());

					this.appendLast(Dialogue.setText("点击开始游戏").enable());

					this.appendLast(nextButton);
				}});
				// if (nextButton.clicked(e)) {

				// }
				// this.appendLast(DIALOGUE_SECTION);
				// System.out.println(this.childList);

			}
		});
		set(SETTINGSCREEN, new GUIParts() {
			{
				setName("SETTINGSCREEN");
				setBGColor(COLOR_BROWN);
			}
		});
		
		set(GAMEOVERSCREEN,new GameOverPage(this) {
		});

		//init options button
		for (int i = 0; i < appendedGUIOption.length; i++) {
			AnimatedGHQTextArea guiOption = new AnimatedGHQTextArea() {
				@Override
				public boolean clicked(MouseEvent event) {
					if(!this.name().startsWith("invalid")) {						
						TCGame.scriptManager.chooseOption(Integer.valueOf(this.name()) + 1);
						nextButton.enable();
						generateOptions(null, null);
					}
					return super.clicked(event);
				}
			};
			guiOption.setName(String.valueOf(i));
			appendedGUIOption[i] = guiOption;
		}

		GHQ.addGUIParts(this);
	}

	// 改变说话人物图片以及对话，讲话的人
	public void setDialogue(String text, PersonEnum Speaker) {
		System.out.println(text);
		System.out.println(Speaker.name);
		this.Dialogue.setText(text);
		this.Speaker.setText("   " + Speaker.name);
		if(Speaker.personImage != null)
			this.NPC_PART.setBGImage(Speaker.personImage);
		else
			this.NPC_PART.setBGPaint(RectPaint.BLANK_SCRIPT);
	}

	public void setNPCImage(String img) {
		this.NPC_PART.setBGImage(img);
	}

	// 调换场景图片
	public void setSceneImage(SceneEnum Scene) {
		this.SCENE_PART.setBGImage(Scene.sceneImage);
	}
	
	public void setSceneImageMusic(SceneEnum Scene) {
		this.SCENE_PART.setBGImage(Scene.sceneImage);
		TCGame.setSoundBgm(Scene.bgmName);
	}

	private final AnimatedGHQTextArea[] appendedGUIOption = new AnimatedGHQTextArea[4];
	
	public void generateOptions(List<String> options, List<Boolean> optionStatus) {
		for (GUIParts parts : appendedGUIOption)
			parts.disable();
		if (options == null) {
			nextButton.enable();
			return;
		}
		nextButton.disable();
		for (int i = 0; i < options.size(); i++) {
			final AnimatedGHQTextArea guiOption = appendedGUIOption[i];
			guiOption.enable();
			guiOption.setText("       " + options.get(i));
			guiOption.setTextColor(optionStatus.get(i) ? Color.WHITE : Color.GRAY);
			if(!optionStatus.get(i)) {
				if(!guiOption.name().startsWith("invalid"))
					guiOption.setName("invalid" + guiOption.name());
			} else {
				if(guiOption.name().startsWith("invalid"))
					guiOption.setName(guiOption.name().substring(7));
			}
			switch (i) {
				case 0:
					guiOption.setBGImage("thhimage/Option1.png");
					guiOption.setBounds(477, 690, 200, 30);
					break;
				case 1:
					guiOption.setBGImage("thhimage/Option2.png");
					guiOption.setBounds(690, 690, 200, 30);
					break;
				case 2:
					guiOption.setBGImage("thhimage/Option3.png");
					guiOption.setBounds(477, 720, 200, 30);
					break;
				case 3:
					guiOption.setBGImage("thhimage/Option4.png");
					guiOption.setBounds(690, 720, 200, 30);
					break;
				default:
					break;
			}
			// System.out.println(this);
			this.get(GAMESCREEN).appendFirst(guiOption);
			// this.childList.get(0).getChildren().get(GAMESCREEN).appendLast(guiOption);
		}
	}
	
	public void gameOver() {
		this.switchTo(GAMEOVERSCREEN);
	}

}
