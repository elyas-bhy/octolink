package octolink.gameframework.game;

import gameframework.base.ObservableValue;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameLevel;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * Create a basic game application with menus and displays of lives and score
 */
public class OctolinkGame implements Game, Observer {
	protected static final int NB_ROWS = 31;
	protected static final int NB_COLUMNS = 28;
	protected static final int SPRITE_SIZE = 24;
	public static final int MAX_NUMBER_OF_PLAYER = 4;
	public static final int NUMBER_OF_LIVES = 3;
	public static final int ZELDA_LIVES = 3;

	protected CanvasDefaultImpl defaultCanvas = null;
	protected ObservableValue<Integer> score[] = new ObservableValue[MAX_NUMBER_OF_PLAYER];
	protected ObservableValue<Integer> life[] = new ObservableValue[MAX_NUMBER_OF_PLAYER];
	protected ObservableValue<Integer> lifeZelda[] = new ObservableValue[MAX_NUMBER_OF_PLAYER];

	// initialized before each level
	protected ObservableValue<Boolean> endOfGame = null;
	protected ObservableValue<String> state = null;

	private Frame f;
	private OctolinkGameLevel currentPlayedLevel = null;

	protected int levelNumber;
	protected ArrayList<GameLevel> gameLevels;

	protected Label lifeText, lifeZeldaText, scoreText, currentLevel, information;
	protected Label lifeValue, lifeZeldaValue, scoreValue, currentLevelValue, informationValue;

	public OctolinkGame() {
		for (int i = 0; i < MAX_NUMBER_OF_PLAYER; ++i) {
			score[i] = new ObservableValue<Integer>(0);
			life[i] = new ObservableValue<Integer>(0);
			lifeZelda[i] = new ObservableValue<Integer>(0);
		}
		state = new ObservableValue<String>("Playing");
		lifeText = new Label("Link Lives:");
		lifeZeldaText = new Label("Zelda Lives:");
		scoreText = new Label("Score:");
		information = new Label("State:");
		currentLevel = new Label("Level:");
		createGUI();
	}

	public void createGUI() {
		f = new Frame("Octolink");
		f.dispose();

		createMenuBar();
		Container c = createStatusBar();

		defaultCanvas = new CanvasDefaultImpl();
		defaultCanvas.setSize(SPRITE_SIZE * NB_COLUMNS, SPRITE_SIZE * NB_ROWS);
		f.add(defaultCanvas);
		f.add(c, BorderLayout.NORTH);
		f.pack();
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	private void createMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("file");
		MenuItem start = new MenuItem("new game");
		MenuItem save = new MenuItem("save");
		MenuItem restore = new MenuItem("load");
		MenuItem quit = new MenuItem("quit");
		Menu game = new Menu("game");
		MenuItem pause = new MenuItem("pause");
		MenuItem resume = new MenuItem("resume");
		menuBar.add(file);
		menuBar.add(game);
		f.setMenuBar(menuBar);

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		restore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restore();
			}
		});
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		resume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resume();
			}
		});

		file.add(start);
		file.add(save);
		file.add(restore);
		file.add(quit);
		game.add(pause);
		game.add(resume);
	}

	private Container createStatusBar() {
		JPanel c = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		c.setLayout(layout);
		lifeValue = new Label(Integer.toString(life[0].getValue()));
		lifeZeldaValue = new Label(Integer.toString(lifeZelda[0].getValue()));
		scoreValue = new Label(Integer.toString(score[0].getValue()));
		currentLevelValue = new Label(Integer.toString(levelNumber));
		informationValue = new Label(state.getValue());
		c.setBackground(new Color(50, 50, 50));
		c.setForeground(Color.WHITE);
		c.add(lifeText);
		c.add(lifeValue);
		c.add(lifeZeldaText);
		c.add(lifeZeldaValue);
		c.add(scoreText);
		c.add(scoreValue);
		c.add(currentLevel);
		c.add(currentLevelValue);
		c.add(information);
		c.add(informationValue);
		return c;
	}

	public Canvas getCanvas() {
		return defaultCanvas;
	}

	public void start() {
		for (int i = 0; i < MAX_NUMBER_OF_PLAYER; ++i) {
			score[i].addObserver(this);
			life[i].addObserver(this);
			life[i].setValue(NUMBER_OF_LIVES);
			lifeZelda[i].addObserver(this);
			lifeZelda[i].setValue(ZELDA_LIVES);
			score[i].setValue(0);
		}
		levelNumber = 0;
		for (GameLevel level : gameLevels) {
			endOfGame = new ObservableValue<Boolean>(false);
			endOfGame.addObserver(this);
			state = new ObservableValue<String>("Playing");
			state.addObserver(this);
			try {
				if (currentPlayedLevel != null && currentPlayedLevel.isAlive()) {
					synchronized (currentPlayedLevel) {
						currentPlayedLevel.notifyAll();
					}
					currentPlayedLevel = null;
				}
				currentPlayedLevel = (OctolinkGameLevel) level;
				levelNumber++;
				currentLevelValue.setText(Integer.toString(levelNumber));

				currentPlayedLevel.start();
				currentPlayedLevel.join();
			} catch (Exception e) {
			}
		}

	}

	public void restore() {
		System.out.println("restore(): Unimplemented operation");
	}

	public void save() {
		System.out.println("save(): Unimplemented operation");
	}

	public void pause() {
		state.setValue("Paused");
		synchronized (currentPlayedLevel) {
			if (currentPlayedLevel.getState() != Thread.State.WAITING) {
				currentPlayedLevel.interrupt();
			}
		}
	}

	public void resume() {
		if (!endOfGame.getValue()) {
			state.setValue("Playing");
			synchronized (currentPlayedLevel) {
				currentPlayedLevel.notifyAll();
			}
		}
	}

	public ObservableValue<Integer>[] score() {
		return score;
	}

	public ObservableValue<Integer>[] life() {
		return life;
	}

	public ObservableValue<Integer>[] lifeZelda() {
		return lifeZelda;
	}

	public ObservableValue<String> state() {
		return state;
	}

	public ObservableValue<Boolean> endOfGame() {
		return endOfGame;
	}

	public void setLevels(ArrayList<GameLevel> levels) {
		gameLevels = levels;
	}

	public void update(Observable o, Object arg) {
		if (o == endOfGame) {
			if (endOfGame.getValue()) {
				informationValue.setText("Victory");
				currentPlayedLevel.interrupt();
				currentPlayedLevel.end();
			}
		} else if (o == state) {
			String state = ((ObservableValue<String>) o).getValue();
			informationValue.setText(state);
		} else {
			for (ObservableValue<Integer> lifeObservable : life) {
				if (o == lifeObservable) {
					int lives = ((ObservableValue<Integer>) o).getValue();
					lifeValue.setText(Integer.toString(lives));
					if (lives == 0) {
						informationValue.setText("Defeat");
						currentPlayedLevel.interrupt();
						currentPlayedLevel.end();
					}
				}
			}
			for (ObservableValue<Integer> lifeZeldaObservable : lifeZelda) {
				if (o == lifeZeldaObservable) {
					int zeldaLives = ((ObservableValue<Integer>) o).getValue();
					lifeZeldaValue.setText(Integer.toString(zeldaLives));
					if (zeldaLives == 0) {
						informationValue.setText("Defeat");
						currentPlayedLevel.interrupt();
						currentPlayedLevel.end();
					}
				}
			}
			for (ObservableValue<Integer> scoreObservable : score) {
				if (o == scoreObservable) {
					int score = ((ObservableValue<Integer>) o).getValue();
					scoreValue.setText(Integer.toString(score));
				}
			}
		}
	}
}
