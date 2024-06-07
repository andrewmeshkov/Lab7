package client.forms;




import client.utility.UserInputManager;
import common.models.*;
import common.exceptions.*;
import common.utility.Console;
import common.utility.FileInputManager;
import common.utility.Input;

import java.util.NoSuchElementException;

public class GovernmentForm implements Form<Government> {

    private final Console console;
    private final Input input;

    public GovernmentForm(Console console) {
        this.console = console;
        this.input = (UserInputManager.fileMode()) ? (new FileInputManager()) : new UserInputManager();
    }


    @Override
    public Government build(boolean flag) throws InvalidFormException {
        var fileMode = UserInputManager.fileMode();
        String strGovernment;
        Government government;
        while (true) {
            try {
                this.console.println("Список видов форм власти - " + Government.types());
                this.console.println("Введите вид формы власти (может быть null, тогда оставьте ввод пустым):");

                strGovernment = this.input.getInputString();
                if (fileMode) this.console.println(strGovernment);
                if(strGovernment.equals("")) return null;
                government = Government.valueOf(strGovernment.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                this.console.printError("Тип формы власти не распознан!");
                if (fileMode) throw new InvalidFormException();
            } catch (IllegalArgumentException exception) {
                this.console.printError("Такого вида формы власти нет в списке!");
            } catch (IllegalStateException exception) {
                this.console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return government;
    }
}
