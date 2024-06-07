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


public class Update extends Command {
  private final Console console;
  private final UDPClient client;

  public Update(Console console, UDPClient client) {
    super("update ID {element}");
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
      if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();

      var id = Integer.parseInt(arguments[1]);

      Response response = client.sendAndReceiveCommand(new Request(Commands.UPDATE, Client.getCurrentUser(), null, new String[]{id+""}));

      if(Status.OWNER_ERROR == response.getStatus()) {console.println("Объект не принадлежит пользователю");return true;};
      if(Status.ERROR == response.getStatus()) {console.println("Отсутствует объект с таким id!"); return true;}
      console.println("* Введите данные обновленного продукта:");
      City city = (new CityForm(console)).build(false);

      response = client.sendAndReceiveCommand(new Request(Commands.UPDATE, Client.getCurrentUser(), city, new String[]{id+""}));
      if (response.getStatus() == Status.ERROR) {
        throw new APIException(response.getString());
      }

      console.println("Продукт успешно обновлен.");
      return true;

    } catch (WrongAmountOfElementsException exception) {
      console.printError("Неправильное количество аргументов!");
      console.println("Nспользование: '" + getName() + "'");
    } catch (InvalidFormException exception) {
      console.printError("Поля продукта не валидны! Продукт не создан!");
    } catch (NumberFormatException exception) {
      console.printError("ID должен быть представлен числом!");
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
