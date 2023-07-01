package german.calculadora1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private String ANSWER;
    private String ANG_UNIT = "RAD";
    private int DEC_PRECISION = 5;
    private Evaluator evaluator = new Evaluator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        angleUnitL.setText(ANG_UNIT);
    }

    private void insertText(String txt) {
        expressionTF.insertText(expressionTF.getCaretPosition(), txt);
    }

    private String calculate() {
        String exp = delSpaces(expressionTF.getText());
        visorL.setText(exp);
        DecimalFormat df = new DecimalFormat("#"+"#".repeat(DEC_PRECISION));
        return evaluator.eval(exp)+"";
    }

    private String delSpaces(String exp) {
        String rtn = "";
        for(char c : exp.toCharArray()) {
            if(!(c == ' ')) rtn += c;
        }
        return rtn;
    }

    @FXML
    private Label angleUnitL, visorL;
    @FXML
    private TextField expressionTF, resultTF;

    @FXML
    protected void expresionTFAP() {
        ANSWER = calculate();
        resultTF.setText(ANSWER);
    }

    @FXML
    protected void ansBAP() {
        insertText("ANS");
    }
}