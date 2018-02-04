package models;

import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * Created by root on 12/07/17.
 */
public class BBDD_Connection extends Controller{

    @Inject
    JPAApi jpaApi;

    @Transactional
    public Result index(){

/*        PersonJPA person1 = new PersonJPA();
        person1.setFirstName( "Cuchi" );
        person1.setLastName( "Ku" );

        PhoneNumberJPA phone1 = new PhoneNumberJPA("928224371");
        PhoneNumberJPA phone2 = new PhoneNumberJPA("928224372");
        person1.getPhones().add( phone1 );
        person1.getPhones().add( phone2 );
        //phone1.setPersonJPA( person1 );
        jpaApi.em().persist(person1);
        jpaApi.em().flush();*/

        PersonJPA person2 = new PersonJPA("Pedro","Picapiedra");
        //person2.setFirstName( "Cuchi2" );
        //person2.setLastName( "Ku2" );


        PhoneNumberJPA phone2 = new PhoneNumberJPA("928224372");
        jpaApi.em().persist(phone2);
        person2.addPhoneNumberJPA( phone2 );
        System.out.println("***********************************************");
        System.out.println(person2.getPhoneNumberJPAList());
        //jpaApi.em().getTransaction().begin();
        jpaApi.em().persist(person2);
        //jpaApi.em().getTransaction().commit();
        //jpaApi.em().close();


       List<PersonJPA> persons = jpaApi.em().createQuery( "FROM PersonJPA" ).getResultList();
        for (PersonJPA p : persons) {
            System.out.println("-----------------");
            System.out.println(p.toString());
            System.out.println(p.getPhoneNumberJPAList());
            System.out.println("-----------------");
        }

        return ok(views.html.index.render());
    }


    public static Connection goConnection() {

            Connection connection = null;
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/psqlDB","postgres", "0007");

                System.out.println("**************** Opened database successfully");


            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
            System.out.println("Operation done successfully");
            return connection;
        }

}
