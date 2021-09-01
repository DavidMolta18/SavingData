package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Post;

import java.io.*;

public class MainWindow extends Stage {

	private Button openText;
	private Button openImage;
	private Button saveBtn;

	private ImageView imagePost;
	private TextArea textPost;

	public MainWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			Parent parent = loader.load();

			openImage = (Button) loader.getNamespace().get("openImage");
			imagePost = (ImageView) loader.getNamespace().get("imagePost");

			openText = (Button) loader.getNamespace().get("openText");
			textPost = (TextArea) loader.getNamespace().get("textPost");

			saveBtn = (Button) loader.getNamespace().get("saveBtn");

			Scene scene = new Scene(parent, 600, 400);
			setScene(scene);

			init();
			loadData();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void init() {
		openImage.setOnAction(event -> {
			doOpenImage();
		});

		openText.setOnAction(event -> {
			doOpenText();
		});

		saveBtn.setOnAction(event -> {
			guardarJavaByteCode();
		});

	}

	public void loadData() {
		try {
		File f = new File("jbc.temp");
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Post post =(Post) ois.readObject();
		textPost.setText(post.getText());
		}catch(IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void guardarJavaByteCode() {
		try {
			Post post = new Post(textPost.getText());
			File ref = new File("jbc.temp");
			FileOutputStream fos = new FileOutputStream(ref);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(post);
			oos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void guardarLegible() {
		try {
			String texto = textPost.getText();
			File ref = new File("texto.temp");
			FileOutputStream fos = new FileOutputStream(ref);
			fos.write(texto.getBytes());
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public void doOpenImage() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Abra un imagen");
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"));
		File file = fc.showOpenDialog(this);

		if (file != null) {
			System.out.println(file.getAbsolutePath());
			Image image = new Image("file:" + file.getAbsolutePath());
			imagePost.setImage(image);
		}
	}

	public void doOpenText() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Abra un imagen");
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("ALL", "*.*"));
		File file = fc.showOpenDialog(this);

		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			byte[] buffer = new byte[3];
			int bytesQuePudeLeer = 0;

			while ((bytesQuePudeLeer = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesQuePudeLeer);
			}
			fis.close();
			baos.close();

			String lectura = baos.toString();
			textPost.setText(lectura);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
