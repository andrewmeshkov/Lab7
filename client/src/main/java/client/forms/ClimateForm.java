package client.forms;

import client.utility.UserInputManager;
import common.models.*;
import common.utility.Console;
import common.utility.FileInputManager;
import common.utility.Input;

import java.util.NoSuchElementException;

public class ClimateForm implements Form<Climate>{


    private final Console console;
    private final Input input;

    public ClimateForm(Console console) {
        this.console = console;
        this.input = (UserInputManager.fileMode()) ? (new FileInputManager()) : new UserInputManager();
    }

    @Override
    public Climate build(boolean flag) {
        String strClimate;
        Climate climate;
        while (true) {
            try {
                this.console.println("Список видов климата - " + Climate.climates());
                this.console.println("Введите тип климата (может быть null, тогда оставьте пустой):");

                strClimate = this.input.getInputString();
                if(strClimate.equals("")) return null;
                climate = Climate.valueOf(strClimate.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                this.console.printError("Тип климата не распознан!");
            } catch (IllegalArgumentException exception) {
                this.console.printError("Такого вида климата нет в списке!");
            } catch (IllegalStateException exception) {
                this.console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return climate;
    }
}
