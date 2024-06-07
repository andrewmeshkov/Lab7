package client.commands;


import client.Client;
import client.forms.CityForm;
import client.network.UDPClient;
import common.models.City;
import common.exceptions.*;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import common.utility.Commands;
import common.utility.Console;

import java.io.IOException;

/**
 * Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
  private final Console console;
  private final UDPClient client;

  public Add(Console console, UDPClient client) {
    super("add {element}");
    this.console = console;
    this.client = client;
  }

  /**
   * Выполняет команду
   * @return Успешность выполнения команды.
   */
  @Override
  public boolean apply(String[] arguments) {
    try {
      if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
      console.println("* Создание нового города:");

      City city = (new CityForm(console)).build(true);
      Response response =  client.sendAndReceiveCommand(
        new Request(Commands.ADD, Client.getCurrentUser(), city)
      );
      if (response.getStatus() == Status.ERROR) {
        throw new APIException(response.getString());
      }

      console.println("Новый продукт с id=" + response.getString() + " успешно добавлен!");
      return true;

    } catch (WrongAmountOfElementsException exception) {
      console.printError("Неправильное количество аргументов!");
      console.println("Nспользование: '" + getName() + "'");
    } catch (InvalidFormException exception) {
      console.printError("Поля продукта не валидны! Продукт не создан!");
    } catch(IOException e) {
      console.printError("Ошибка взаимодействия с сервером");
    } catch (APIException | ErrorResponseException e) {
      console.printError(e.getMessage());
    } catch (InvalidInputException e) {
      e.printStackTrace();
    }
    return false;
  }
}
