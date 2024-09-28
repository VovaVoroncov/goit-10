import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ua.goit.hw10.HibernateUtil;
import ua.goit.hw10.models.Client;
import ua.goit.hw10.services.ClientCrudService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientCrudServiceTest {

    private ClientCrudService clientCrudService;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);
        when(sessionFactory.openSession()).thenReturn(session);
        clientCrudService = new ClientCrudService(new HibernateUtil(sessionFactory));
    }

    @Test
    void testAddClient() {
        Client client = new Client();
        client.setId(1);

        when(session.beginTransaction()).thenReturn(transaction);

        clientCrudService.addClient(client);

        verify(session).beginTransaction();
        verify(session).persist(client);
        verify(transaction).commit();
    }

    @Test
    void testGetById() {
        Client client = new Client();
        client.setId(1);

        when(session.get(Client.class, 1)).thenReturn(client);

        Client result = clientCrudService.getById(1);

        assertNotNull(result);
        assertEquals(client.getId(), result.getId());
    }

    @Test
    void testUpdateClient() {
        Client client = new Client();
        client.setId(1);

        when(session.beginTransaction()).thenReturn(transaction);

        clientCrudService.updateClient(client, 1);

        verify(session).beginTransaction();
        verify(session).merge(client);
        verify(transaction).commit();
    }

    @Test
    void testDeleteById() {
        Client client = new Client();
        client.setId(1);

        when(session.get(Client.class, 1)).thenReturn(client);
        when(session.beginTransaction()).thenReturn(transaction);

        clientCrudService.deleteById(1);

        verify(session).beginTransaction();
        verify(session).remove(client);
        verify(transaction).commit();
    }

    @Test
    void testGetAllClients() {
        Client client = new Client();
        client.setId(1);

        when(session.createQuery("from Client", Client.class)).thenReturn(mock(org.hibernate.query.Query.class));
        when(session.createQuery("from Client", Client.class).list()).thenReturn(Collections.singletonList(client));

        List<Client> clients = clientCrudService.getAllClients();

        assertNotNull(clients);
        assertEquals(1, clients.size());
        assertEquals(client.getId(), clients.get(0).getId());
    }
}
