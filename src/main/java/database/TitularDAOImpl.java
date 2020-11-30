package database;

import dto.DTOBuscarTitular;
import model.Titular;
import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import java.util.List;

public class TitularDAOImpl extends BaseDAOImpl<Titular,Integer> implements TitularDAO{

    private Session session;

    public TitularDAOImpl() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    @Override
    public List<DTOBuscarTitular> createListDTOBuscarTitular(String argumentos) {

        session = HibernateUtil.getSessionFactory().getCurrentSession();

        String consulta = "SELECT new dto.DTOBuscarTitular(t.id, t.fechaNacimiento, t.nombre, t.apellido,t.tipoDNI, t.DNI) FROM Titular t "
                + argumentos + " ORDER BY t.nombre, t.apellido ASC ";
        System.out.println("consulta buscar" + consulta);
        try {
            if(session.getTransaction().getStatus().equals(TransactionStatus.NOT_ACTIVE))
                session.beginTransaction();
            return session.createQuery(consulta, DTOBuscarTitular.class).getResultList();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }


}
