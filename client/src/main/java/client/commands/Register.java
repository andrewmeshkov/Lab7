package client.commands;



import client.Client;
import client.forms.UserForm;
import client.network.UDPClient;
import common.exceptions.*;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import common.network.User;
import common.utility.Commands;
import common.utility.Console;

import java.io.IOException;

/**
 * Регистрирует пользователя.
 */
public class Register extends Command {
  private final Console console;
  private final UDPClient client;

  public Register(Console console, UDPClient client) {
    super("register");
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
      //if (SessionHandler.getCurrentUser() != null) {console.println("Вы уже вошли в аккаунт!"); ;return true;}
      if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
      console.println("Создание пользователя:");

      User user = new UserForm(console).build(true);

      Response response = client.sendAndReceiveCommand(new Request(Commands.REGISTER, user));
      if (response.getStatus() == Status.ERROR) {
        throw new APIException(response.getString());
      }

      Client.setCurrentUser(response.getUser());
      console.println("Пользователь " + response.getUser().getName() +
        " с id=" + response.getUser().getId() + " успешно создан!");
      return true;

    } catch (WrongAmountOfElementsException exception) {
      console.printError("Неправильное количество аргументов!");
      console.println("Nспользование: '" + getName() + "'");
    } catch (InvalidFormException exception) {
      console.printError("Введенные данные не валидны! Пользователь на зарегистрирован");
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
