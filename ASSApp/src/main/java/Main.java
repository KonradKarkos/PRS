import controllers.general.LoginScreenController;
//import entities.Address;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import parameters.ParameterProvider;

import javax.smartcardio.*;
import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
//
//public class Main {
//    public static void main(String[] args) {
//
//        RestTemplate restTemplate = new RestTemplate();
//        Address test = restTemplate.getForObject("http://localhost:8080/test", Address.class);
//
//        Address testowy = new Address();
//        testowy.setCity("Poznań");
//        testowy.setStreet("Królowej Jadwigi");
//        testowy.setHouseNumber("25");
//        testowy.setLocalNumber("5");
//        testowy.setPostCode("60-811");
//
//        restTemplate.postForObject("http://localhost:8080/testb",testowy,Address.class);
//    }
//}

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws CardException {
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = factory.terminals().list();
        System.out.println("Terminals: " + terminals);

        CardTerminal reader = terminals.get(0);
        Card card = reader.connect("*");
        System.out.println(card.getATR().toString());

        card.beginExclusive();
        CardChannel channel = card.getBasicChannel();
        ResponseAPDU resp = channel.transmit(new CommandAPDU(128, 202, 0x9f, 0x7f, 256));
        byte[] data = resp.getData();
        byte[] ICSerialNumber = Arrays.copyOfRange(data,15,19);
        System.out.println(hexToString(ICSerialNumber));
        Locale.setDefault(new Locale("en", "GB"));

        FXMLLoader innerLoader = new FXMLLoader(getClass().getResource("views/general/LoginScreen.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("lang");
        innerLoader.setResources(bundle);
        try {
            Stage stage = new Stage();
            Parent innerRoot = innerLoader.load();
            //TODO AS - SETTING ALL REQUIRED OBJECTS
            LoginScreenController loginScreenController = innerLoader.getController();
//            loginScreenController.setClient(client);

            stage.setTitle(ParameterProvider.WINDOW_TITLE);
            Scene scene = new Scene(innerRoot, 800, 500);
            stage.setScene(scene);
            stage.setMaximized(true);
            loginScreenController.setStage(stage);
            loginScreenController.setScene(scene);
            stage.show();

//            Platform.runLater(loginScreenController.thread::start);
//            loginScreenController.thread.start();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.out.println("Stage is closing2");
//                    loginScreenController.checkConnection=Boolean.FALSE;
                }
            });

            primaryStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Main(String[] args) {
        launch(args);
    }
    public static String hexToString(final byte[] data) {
        char[] hexValue = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int length = data.length;
        char[] out = new char[length*2];
        for (int i = 0, j = 0; i < length; i++) {
            out[j++] = hexValue[(0xF0 & data[i]) >>> 4];
            out[j++] = hexValue[0x0F & data[i]];
        }
        return new String(out);
    }
}
