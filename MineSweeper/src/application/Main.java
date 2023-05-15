package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		// マスの数を設定するダイアログを表示
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("マスの数を設定");
		dialog.setHeaderText("マスの数を入力してください");
		dialog.setContentText("行数:");

		// OKボタンがクリックされたら、入力された値を取得する
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			try {
				MineSweeper.ROWS = Integer.parseInt(result.get());
			} catch (NumberFormatException e) {
				// 入力が不正な場合はデフォルト値を使用する
			}
		} else {
			// キャンセルがクリックされた場合はデフォルト値を使用する
		}

		// 列数の入力ダイアログを表示
		dialog = new TextInputDialog();
		dialog.setTitle("マスの数を設定");
		dialog.setHeaderText("マスの数を入力してください");
		dialog.setContentText("列数:");

		// OKボタンがクリックされたら、入力された値を取得する
		result = dialog.showAndWait();
		if (result.isPresent()) {
			try {
				MineSweeper.COLUMNS = Integer.parseInt(result.get());
			} catch (NumberFormatException e) {
				// 入力が不正な場合はデフォルト値を使用する
			}
		} else {
			// キャンセルがクリックされた場合はデフォルト値を使用する
		}

		// 地雷の数の入力ダイアログを表示
		dialog = new TextInputDialog();
		dialog.setTitle("地雷の数を設定");
		dialog.setHeaderText("地雷の数を入力してください");
		dialog.setContentText("地雷の数:");

		// OKボタンがクリックされたら、入力された値を取得する
		result = dialog.showAndWait();
		if (result.isPresent()) {
			try {
				MineSweeper.NUM_MINES = Integer.parseInt(result.get());
			} catch (NumberFormatException e) {
				// 入力が不正な場合はデフォルト値を使用する
			}
		} else {
			// キャンセルがクリックされた場合はデフォルト値を使用する
		}

		//メイン画面の起動
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("GUI.fxml"));
			root.getChildren().add(MineSweeper.timerLabel);
			Scene scene = new Scene(root, 500, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("まいんすいーぱー");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
