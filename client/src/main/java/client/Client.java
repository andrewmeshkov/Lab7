package client;

import common.network.User;
import common.utility.Console;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import client.utility.Runner;
import client.network.UDPClient;

import java.io.IOException;
import java.net.InetAddress;


public class Client {
  private static final int PORT = 5432;
  public static final Logger logger = LogManager.getLogger("ClientLogger");

  public static User currentUser = null;

  public static User getCurrentUser() {
    return currentUser;
  }

  public static void setCurrentUser(User currentUser) {
    Client.currentUser = currentUser;
  }

  public static void main(String[] args) {
    Console console = new Console();
    try {
      UDPClient client = new UDPClient(InetAddress.getLocalHost(), PORT);
      Runner cli = new Runner(client, console);

      cli.interactiveMode();
    } catch (IOException e) {
      logger.info("Невозможно подключиться к серверу.", e);
      console.println("Невозможно подключиться к серверу!");
    }
  }
}
