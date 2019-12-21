package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

//用来做事件响应的
public class Controller {
    @FXML
    public void onButton(ActionEvent actionEvent){
        System.out.println("hello world");
    }
}
