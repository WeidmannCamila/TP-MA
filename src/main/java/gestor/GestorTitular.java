package gestor;

import dto.DTOAltaTitular;
import dto.DTOEmitirLicencia;
import enumeration.*;
import hibernate.DAO;
import model.Licencia;
import model.Titular;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorTitular {
    private static GestorTitular instanciaGestor = null;

    private GestorTitular() {}

    public static GestorTitular get() {
        if (instanciaGestor == null){
            instanciaGestor = new GestorTitular();
        }
        return instanciaGestor;
    }

    public boolean verificarTitular (DTOAltaTitular dto){
        //TODO buscar en base de datos los titulares para comparar DNI

        return true;

    }

    public void registrarTitular(DTOAltaTitular dto){
        Titular titular = new Titular(dto.getTipoDNI(),dto.getDNI(), dto.getApellido(), dto.getNombre(),
                dto.getFechaNacimiento(),dto.getGrupoSanguineo(), dto.getFactorRH(), dto.getDonanteOrganos(),dto.getSexo());
        DAO.get().save(titular);
    }

    public List<DTOEmitirLicencia> buscarTitulares() {
          /*
            TODO cambiar al implementar buscar/alta titular
         */
        String consulta = "SELECT new dto.DTOEmitirLicencia(t.id, t.fechaNacimiento, t.nombre, t.apellido, t.tipoDNI, t.DNI) "
                + "FROM Titular t ORDER BY t.id";
        return (List<DTOEmitirLicencia>) DAO.get().getResultList(consulta, DTOEmitirLicencia.class);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Licencia> getHistorialLicencias(Integer idTitular){
        return (ArrayList<Licencia>) DAO.get().getResultList("select l from Licencia l where l.titular="+idTitular, Licencia.class);
    }

    public static Integer getEdad(LocalDate fechaNacimiento){
        LocalDate today = LocalDate.now();
        return Period.between(fechaNacimiento, today).getYears();
    }

    public Titular getTitular(Integer idTitular) {
        return (Titular) DAO.get().get(Titular.class, idTitular);
    }
}
