package client.forms;



import client.utility.UserInputManager;
import common.models.*;
import common.exceptions.*;
import common.utility.Console;
import common.utility.FileInputManager;
import common.utility.Input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;


public class HumanForm implements Form<Human> {

    private final Console console;
    private final Input input;

    public HumanForm(Console console) {
        this.console = console;
        this.input = (UserInputManager.fileMode()) ? (new FileInputManager()) : new UserInputManager();
    }


    @Override
    public Human build(boolean flag) throws InvalidFormException, InvalidInputException {
        Date birthday;
        while (true) {
            this.console.println("Введите дату рождения в формате yyyy-mm-dd (может быть null, тогда оставьте ввод пустым)::");
            String inputString = this.input.getInputString();
            try {
              SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
              birthday = parser.parse(inputString);
                if (inputString.equals("")) return null;
                return new Human(birthday);
            } catch (DateTimeParseException | ParseException e) {
                System.out.println("Дата должна быть в формате yyyy-mm-dd");
            }
        }
    }
}
