package client.forms;


import client.utility.UserInputManager;
import common.models.*;
import common.exceptions.*;
import common.utility.Console;
import common.utility.FileInputManager;
import common.utility.Input;

public class CityForm implements Form<City> {
    private final Console console;
    private final Input input;

    public CityForm(Console console) {
        this.console = console;
        this.input = (UserInputManager.fileMode()) ? (new FileInputManager()) :  new UserInputManager();
    }

    @Override
    public City build(boolean flag) throws InvalidInputException, InvalidFormException {
        City city = new City(askName(), askCoordinates(), askArea(), askPopulation(), askMetersAboveSeaLevel(), askClimate(), askGovernment(), askStandardOfLiving(), askForGovernor());
        System.out.println(city.getId());
        if (!city.validate()) return null;
        if(flag){this.console.println("Город успешно создан");}
        return city;
    }

    private String askName() {
        while (true) {
            try {
                this.console.println("Введите название города:");
                String inputString = this.input.getInputString().trim();
                if (inputString.equals("")) throw new InvalidInputException();
                return inputString;
            } catch (InvalidInputException e) {
                System.out.println("Неправильный формат ввода!");
            }
        }
    }


    private Coordinates askCoordinates() throws InvalidInputException, InvalidFormException {
        return new CoordinatesForm(this.console).build(true);
    }

    private float askArea() {
        float area;
        while (true) {
            try {
                this.console.println("Введите площадь города:");
                String inputString = this.input.getInputString().trim();
                if (inputString.equals("")) throw new InvalidInputException();
                area = Float.parseFloat(inputString);
                if (area <= 0) throw new NegativeAreaException();
                return area;
            } catch (InvalidInputException e) {
                System.out.println("Неправильный формат ввода!");
            } catch (NegativeAreaException e) {
                System.out.println("Площадь должна быть положительной");
            }

        }
    }

    private int askPopulation() {
        int population;
        while (true) {
            try {
                this.console.println("Введите население города:");
                String inputString = this.input.getInputString().trim();
                try {
                    population = Integer.parseInt(inputString);
                } catch (NumberFormatException e) {
                    throw new InvalidInputException();
                }
                if (inputString.equals("")) throw new InvalidInputException();
                if (population <= 0) throw new NegativePopulationException();
                return population;
            } catch (InvalidInputException e) {
                System.out.println("Неправильный формат ввода!");
            } catch (NegativePopulationException e) {
                System.out.println("Население должно быть положительным");
            }

        }
    }


    private double askMetersAboveSeaLevel() {
        double metersAboveSeaLevel;
        while (true) {
            try {
                this.console.println("Введите количество метров над уровнем моря города:");
                String inputString = this.input.getInputString().trim();
                if (inputString.equals("")) throw new InvalidInputException();
                metersAboveSeaLevel = Double.parseDouble(inputString);
                return metersAboveSeaLevel;
            } catch (InvalidInputException | NumberFormatException e) {
                System.out.println("Неправильный формат ввода!");
            }

        }
    }

    private Climate askClimate() throws InvalidInputException, InvalidFormException {
        return new ClimateForm(this.console).build(true);
    }

    private Government askGovernment() throws InvalidInputException, InvalidFormException {
        return new GovernmentForm(this.console).build(true);
    }

    private StandardOfLiving askStandardOfLiving() throws InvalidInputException, InvalidFormException {
        return new StandardOfLivingForm(this.console).build(true);
    }

    private Human askForGovernor() throws InvalidInputException, InvalidFormException {
        return new HumanForm(this.console).build(true);
    }
}
