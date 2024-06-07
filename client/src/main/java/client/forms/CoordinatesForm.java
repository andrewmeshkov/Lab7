package client.forms;


import client.utility.UserInputManager;
import common.models.*;
import common.exceptions.*;
import common.utility.Console;
import common.utility.FileInputManager;
import common.utility.Input;

import java.util.NoSuchElementException;


public class CoordinatesForm implements Form<Coordinates>{
    private final Console console;
    private final Input input;

    public CoordinatesForm(Console console) {
        this.console = console;
        this.input = (UserInputManager.fileMode()) ? (new FileInputManager()) : new UserInputManager();
    }

    @Override
    public Coordinates build(boolean flag) throws InvalidFormException {
        Coordinates coordinates = new Coordinates(askX(), askY());
        if (!coordinates.validate()) throw new InvalidFormException();
        return coordinates;
    }



    public Integer askX() throws  InvalidFormException {
        boolean fileMode = UserInputManager.fileMode();
        int x;
        while (true) {
            try {
                this.console.println("Введите координату X(может быть null):");
                String strX = this.input.getInputString().trim();
                if (fileMode) this.console.println(strX);
                if(strX.equals("")) return null;
                x = Integer.parseInt(strX);
                break;
            } catch (NoSuchElementException exception) {
                this.console.printError("Координата X не распознана!");
                if (fileMode) throw new InvalidFormException();
            } catch (NumberFormatException exception) {
                this.console.printError("Координата X должна быть представлена числом!");
                if (fileMode) throw new InvalidFormException();
            } catch (NullPointerException | IllegalStateException exception) {
                this.console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }


    public Float askY() throws InvalidFormException {
        boolean fileMode = UserInputManager.fileMode();
        float y;
        while (true) {
            try {
                this.console.println("Введите координату Y:");
                String strY = this.input.getInputString().trim();
                if (fileMode) this.console.println(strY);

                y = Float.parseFloat(strY);
                break;
            } catch (NoSuchElementException exception) {
                this.console.printError("Координата Y не распознана!");
                if (fileMode) throw new InvalidFormException();
            } catch (NumberFormatException exception) {
                this.console.printError("Координата Y должна быть представлена числом!");
                if (fileMode) throw new InvalidFormException();
            } catch (NullPointerException | IllegalStateException exception) {
                this.console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }
}
