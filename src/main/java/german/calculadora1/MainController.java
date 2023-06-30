package german.calculadora1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private String ANSWER, EXPRESSION;
    private String ANG_UNIT = "RAD";
    private int DEC_PRECISION = 8;
    private HashMap<String, String> VARIABLES = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        angleUnitL.setText(ANG_UNIT);
    }

    private void insertText(String txt) {
        expressionTF.insertText(expressionTF.getCaretPosition(), txt);
    }

    private void replace() {
        EXPRESSION = expressionTF.getText();

        VARIABLES.forEach((exp, val) -> {
            EXPRESSION = EXPRESSION.replaceAll(exp, val);
        });
    }

    private String calculate() {
        try {
            VARIABLES.put("ANS", ANSWER);

            replace();
            visorL.setText(EXPRESSION);

            Expression exp = new ExpressionBuilder(EXPRESSION).build();
            DecimalFormat df = new DecimalFormat("#." + "#".repeat(DEC_PRECISION));
            return df.format(exp.evaluate());
        } catch (Exception e) {
            return "ERR";
        }
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