package ua.goit.hw10;

import ua.goit.hw10.services.ClientCrudService;
import ua.goit.hw10.services.PlanetCrudService;
import ua.goit.hw10.models.Client;
import ua.goit.hw10.models.Planet;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        new DatabaseInitService().initDB();
        HibernateUtil hibernateUtil = HibernateUtil.getInstance();

        // Client
        ClientCrudService clientCrudService = new ClientCrudService(hibernateUtil);

        Client client1 = new Client();
        client1.setName("Volodymyr Voroncov");
        clientCrudService.addClient(client1);
        System.out.println("New client: " + client1);

        System.out.println("Get client by id: " + clientCrudService.getById(11));

        Client updateClientById = clientCrudService.getById(11);
        updateClientById.setName("Steve Jobs");
        clientCrudService.updateClient(updateClientById, 11);
        System.out.println("Change client name by id: " + clientCrudService.getById(11));

        clientCrudService.deleteById(7);

        List<Client> allClients = clientCrudService.getAllClients();
        System.out.println("All Clients:");
        for (Client client : allClients) {
            System.out.println(client);
        }
        System.out.println("\n");

        //Planet
        PlanetCrudService planetCrudService = new PlanetCrudService(hibernateUtil);

        Planet planet1 = new Planet();
        planet1.setId("EARTH");
        planet1.setName("Earth");
        planetCrudService.addPlanet(planet1);
        System.out.println("New planet: " + planet1);

        System.out.println("Get planet by id: " + planetCrudService.getById("EARTH"));

        Planet updatePlanetById = planetCrudService.getById("EARTH");
        updatePlanetById.setName("Nibiru");
        planetCrudService.updatePlanet(updatePlanetById, "EARTH");
        System.out.println("Change planet name by id: " + planetCrudService.getById("EARTH"));

        planetCrudService.deleteById("EARTH");

        List<Planet> allPlanets = planetCrudService.getAllPlanets();
        System.out.println("All Planets");
        for (Planet planet : allPlanets) {
            System.out.println(planet);
        }
    }
}