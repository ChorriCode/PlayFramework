package controllers;

import models.BBDD_Connection;
import models.PersonJPA;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by root on 10/07/17.
 */
public class PersonController extends Controller {

    Set<PersonJPA> PersonsJPAList;

    @Inject
    FormFactory formFactory;

    @Inject
    JPAApi jpaApi;


    public Result index(){
        System.out.println("************************************************************************");
        jpaApi.em().createQuery( "FROM PersonJPA" ).getResultList();
        System.out.println(Json.toJson( PersonsJPAList ));
        return ok(Json.toJson( PersonsJPAList ));
        //return ok( views.html.PersonJPAs.index.render(PersonsJPAList));
    }

    public Result create(){
        Form<PersonJPA> PersonJPAForm = formFactory.form(PersonJPA.class);
        System.out.println("Estoy en CREATE");
        //return ok(create.render(PersonJPAForm));
        return TODO;
    }

/*    public Result save(){
        System.out.println("Estoy en SAVE");

        Form<PersonJPA> PersonJPAForm = formFactory.form(PersonJPA.class).bindFromRequest();
        System.out.print("PersonJPAForm.get(): ");
        System.out.println(PersonJPAForm.get().getFirstName());
        PersonJPA PersonJPA = PersonJPAForm.get();
        System.out.print("PersonJPAa: ");
        System.out.println(PersonJPA.getFirstName());
        PersonJPA.addPersonJPA(PersonJPA);
        for (PersonJPA PersonJPA1 : PersonJPA.AllPersonJPAs()){
            System.out.println("bucle for: " + PersonJPA1.getId() + " " + PersonJPA1.getFirstName() + " " + PersonJPA1.getLastName());
        }
        //return redirect(routes.PersonJPAController.index());
        return TODO;
    }*/

    public Result edit(Integer id){
        return TODO;
    }



    public Result delete(Integer id){
        return TODO;
    }

    public Result show(Integer id){
        BBDD_Connection conectame = new BBDD_Connection();

        return TODO;}



}

