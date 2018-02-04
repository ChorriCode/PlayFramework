package models;

import com.fasterxml.jackson.databind.JsonNode;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

import static play.mvc.Controller.request;
import static play.mvc.Results.ok;

/**
 * Created by root on 18/07/17.
 */
public class PersonCRUD {

    @Inject
    JPAApi jpaApi;

    List<PersonJPA> PersonsList;

    @Transactional
    public Result readCRUD() {

        PersonsList = jpaApi.em().createQuery( "FROM PersonJPA" ).getResultList();

        return ok(Json.toJson( PersonsList ));
    }

    @Transactional
    public Result updateCRUD(Integer id){

        JsonNode json = request().body().asJson();
        String n = "'"+(json.get( "firstName" )).asText()+"'";
        String a = "'"+(json.get( "lastName" )).asText()+"'";

        jpaApi.em().createNativeQuery( "UPDATE PersonJPA SET firstName="+n+", lastName="+a+ " WHERE id="+id).executeUpdate();
        PersonsList = jpaApi.em().createQuery( "FROM PersonJPA" ).getResultList();

        return ok(Json.toJson( PersonsList ));
    }

    @Transactional
    public Result createCRUD(){

        JsonNode json = request().body().asJson();
        String n = "'"+(json.get( "firstName" )).asText()+"'";
        String a = "'"+(json.get( "lastName" )).asText()+"'";
        jpaApi.em().createNativeQuery( "INSERT INTO PersonJPA (firstName,lastName) VALUES (" + n + "," + a + ")").executeUpdate();
        PersonsList = jpaApi.em().createQuery( "FROM PersonJPA" ).getResultList();

        return ok(Json.toJson( PersonsList ));
    }

    @Transactional
    public Result deleteCRUD(Long id){

        jpaApi.em().createNativeQuery( "DELETE FROM PersonJPA WHERE id="+id).executeUpdate();
        PersonsList = jpaApi.em().createQuery( "FROM PersonJPA" ).getResultList();

        return ok(Json.toJson( PersonsList ));
    }

    @Transactional
    public Result searchCRUD(String firstName){

        PersonsList.clear();
        String lastName = firstName;
        PersonsList = jpaApi.em().createQuery( "FROM PersonJPA WHERE firstName LIKE '%"+firstName+"%' OR lastName LIKE '%"+lastName+"%'").getResultList();

        return ok(Json.toJson( PersonsList ));
    }

}
