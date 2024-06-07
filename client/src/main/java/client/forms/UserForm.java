package client.forms;

import client.utility.UserInputManager;
import common.exceptions.*;
import common.network.User;
import common.utility.Console;
import common.utility.FileInputManager;
import common.utility.Input;


import java.util.Locale;
import java.util.Objects;

public class UserForm implements Form<User> {

    private final Console console;
    private final Input input;

    public UserForm(Console console) {
        this.console = console;
        this.input = (UserInputManager.fileMode()) ? (new FileInputManager()) : new UserInputManager();
    }

    @Override
    public User build(boolean flag) throws InvalidInputException, InvalidFormException {
        return new User(0, askLogin(), askPassword());
    }

    private String askLogin(){
        String login;
        while (true){
            console.println("Введите ваш логин");
            login = this.input.getInputString().trim();
            if (login.isEmpty()){
                console.printError("Логин не может быть пустым");
                //if (Console.isFileMode()) throw new ExceptionInFileMode();
            }
            else{
                return login;
            }
        }
    }

    private String askPassword(){
        String pass;
        while (true){
            console.println("Введите пароль");
            pass = (Objects.isNull(System.console()))
                    ? this.input.getInputString().trim()
                    : new String(System.console().readPassword());
            if (pass.isEmpty()){
                console.printError("Пароль не может быть пустым");
                //if (Console.isFileMode()) throw new ExceptionInFileMode();
            }
            else{
                return pass;
            }
        }
    }
}
