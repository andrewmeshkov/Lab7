package client.forms;





import client.utility.UserInputManager;
import common.models.*;
import common.exceptions.*;
import common.utility.Console;
import common.utility.FileInputManager;
import common.utility.Input;

import java.util.NoSuchElementException;

public class StandardOfLivingForm implements Form<StandardOfLiving> {

    private final Console console;
    private final Input input;

    public StandardOfLivingForm(Console console) {
        this.console = console;
        this.input = (UserInputManager.fileMode()) ? (new FileInputManager()) : new UserInputManager();
    }


    @Override
    public StandardOfLiving build(boolean flag) throws InvalidFormException {
        var fileMode = UserInputManager.fileMode();
        String strStandardOfLiving;
        StandardOfLiving standardOfLiving;
        while (true) {
            try {
                this.console.println("Список уровней жизни - " + StandardOfLiving.types());
                this.console.println("Введите уровень жизни  (может быть null, тогда оставьте ввод пустым)::");

                strStandardOfLiving = this.input.getInputString().trim();
                if (fileMode) this.console.println(strStandardOfLiving);
                if(strStandardOfLiving.equals("")) return null;
                standardOfLiving = StandardOfLiving.valueOf(strStandardOfLiving.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                this.console.printError("Уровень жизни не распознан!");
                if (fileMode) throw new InvalidFormException();
            } catch (IllegalArgumentException exception) {
                this.console.printError("Такого уровня жизни нет в списке!");
            } catch (IllegalStateException exception) {
                this.console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return standardOfLiving;
    }
}
