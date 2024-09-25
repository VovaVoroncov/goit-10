package ua.goit.hw10;

import lombok.Data;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.goit.hw10.models.Client;
import ua.goit.hw10.models.Planet;

@Data
public class HibernateUtil {
    private static final HibernateUtil INSTANCE;

    static {
        INSTANCE = new HibernateUtil();
    }
    private final SessionFactory sessionFactory;

    public HibernateUtil() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Planet.class)
                .buildSessionFactory();
    }

    public static HibernateUtil getInstance() {
        return INSTANCE;
    }

    public void close() {
        sessionFactory.close();
    }
}
