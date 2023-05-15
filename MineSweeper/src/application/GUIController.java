package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class GUIController implements Initializable {
	MineSweeper mineSweeper;

	@FXML
	private GridPane gridPane;

	static public Button[][] buttons = new Button[MineSweeper.ROWS][MineSweeper.COLUMNS];

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.mineSweeper = new MineSweeper();
		for (int i = 0; i < MineSweeper.ROWS; i++) {
			for (int j = 0; j < MineSweeper.COLUMNS; j++) {
				Button button = new Button();
				button.setFont(new Font(8));
				button.setPrefSize(25, 25); // ボタンのサイズを設定
				gridPane.add(button, j, i); // GridPaneにボタンを配置
				buttons[i][j] = button; // ボタンを配列に格納
				int row = i;
				int column = j;
				button.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY) {
							Button bt = (Button) event.getSource();
							mineSweeper.leftButtonPress(bt, row, column);
						} else if (event.getButton() == MouseButton.SECONDARY) {
							Button bt = (Button) event.getSource();
							mineSweeper.rightButtonPress(bt);
						}
					}
				});
			}
		}
		mineSweeper.generateBoard();
	}
}