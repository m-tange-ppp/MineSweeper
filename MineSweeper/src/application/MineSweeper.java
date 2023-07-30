package application;

import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;

public class MineSweeper {
	public static int ROWS = 10;
	public static int COLUMNS = 10;
	public static int NUM_MINES = 20;

	private final boolean[][] mines = new boolean[ROWS][COLUMNS];

	private final boolean[][] isDisable = new boolean[ROWS][COLUMNS];

	private Timer timer = new Timer();

	public static Label timerLabel = new Label("経過時間: 0 秒");

	private final int[] elapsed = { 0 };

	public void leftButtonPress(Button button, int row, int col) {
		if (button.getText() == "○") { //〇の時は何もしない
		} else {
			if (mines[row][col]) { //地雷があった時
				button.setText("×");
				gameOver();
			} else {
				int count = 0;
				for (int r = row - 1; r <= row + 1; r++) {
					for (int c = col - 1; c <= col + 1; c++) {
						if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS && !mines[r][c] && !isDisable[r][c]) {
							/*
							 * 指定したボタンの周りで、地雷がないかつ掘っていない
							 * マスを数える
							 */
							count++;
						}
					}
				}
				if (count > 4) { //上記のマスが４マスを超えた時
					for (int r = row - 1; r <= row + 1; r++) {
						for (int c = col - 1; c <= col + 1; c++) {
							if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS && !mines[r][c] && !isDisable[r][c]) {
								int numMines = countAdjacentMines(row, col);
								button.setText(Integer.toString(numMines));
								button.setDisable(true);
								isDisable[r][c] = true;
								/*
								 * 上記のマスについて再帰的に
								 * leftButtonPressを呼び出す
								 */
								leftButtonPress(GUIController.buttons[r][c], r, c);
							}
						}
					}
				}
				int numMines = countAdjacentMines(row, col);
				button.setText(Integer.toString(numMines));
				button.setDisable(true);
				isDisable[row][col] = true;
			}
			int count = 0;
			for (int i = 0; i < isDisable.length; i++) {
				for (int j = 0; j < isDisable[i].length; j++) {
					if (isDisable[i][j]) {
						count++; //掘ったマスを数える
					}
				}
			}
			if (count == ROWS * COLUMNS - NUM_MINES) {
				win(); //掘ったマスが地雷がないマスと一致した時
			}
		}
	}

	public void rightButtonPress(Button button) {
		if (button.getText() == "") {
			button.setText("○");
		} else {
			button.setText("");
		}
	}

	private int countAdjacentMines(int row, int col) {
		int count = 0;
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1; c <= col + 1; c++) {
				if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS && mines[r][c]) {
					count++;
				}
			}
		}
		return count;
	}

	public void generateBoard() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				mines[i][j] = false;
				isDisable[i][j] = false;
				Button button = GUIController.buttons[i][j];
				button.setText("");
				button.setDisable(false);
			}
		}

		Random random = new Random();
		int count = 0;
		{
			while (count < NUM_MINES) {
				int row = random.nextInt(ROWS);
				int col = random.nextInt(COLUMNS);
				if (!mines[row][col]) {
					mines[row][col] = true;
					count++;
				}
			}
		}
		resetTimer();
	}

	private ChoiceDialog<String> generateFinishDialog(String title) {
		ChoiceDialog<String> dialog = new ChoiceDialog<>("もういちど", "おわり");
		dialog.setTitle(title);
		dialog.setHeaderText(title);
		dialog.setContentText("どうする？");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && result.get().equals("もういちど")) {
			generateBoard();
		} else {
			Platform.exit();
		}
		return dialog;
	}

	private void gameOver() {
		timer.cancel();
		timer = null;
		generateFinishDialog("ざんねんwww");
	}

	private void win() {
		timer.cancel();
		timer = null;
		generateFinishDialog("おめでとう‼");
	}

	private void resetTimer() {
		timerLabel.setText("経過時間: 0 秒");
		elapsed[0] = 0;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					elapsed[0]++;
					timerLabel.setText("経過時間: " + elapsed[0] + " 秒");
				});
			}
		}, 1000, 1000);
	}
}
