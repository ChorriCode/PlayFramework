package models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PersonJPA {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(nullable = false)
    public Long id;

    @Column(nullable = false)
    public String firstName;

    @Column(nullable = false)
    public String lastName;

    @OneToMany(mappedBy = "personJPA", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PhoneNumberJPA> phoneNumberJPAList = new ArrayList<>();

    public PersonJPA(){

    }

    public PersonJPA(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString(){
     return id + " " + firstName + " " + lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lasName) {
        this.lastName = lasName;
    }

    public List<PhoneNumberJPA> getPhones(){
        return phoneNumberJPAList;
    }

    public String getPhoneNumberJPAList() {
        String showPhoneNumberJPA = null;
        for (PhoneNumberJPA phoneNumberJPA : phoneNumberJPAList) {
            showPhoneNumberJPA = phoneNumberJPA.phoneNumber;
        }
        
        return showPhoneNumberJPA;
    }

    public void addPhoneNumberJPA(PhoneNumberJPA phoneNumberJPA) {
        if (!phoneNumberJPAList.contains( phoneNumberJPA )){
            phoneNumberJPAList.add( phoneNumberJPA );
            phoneNumberJPA.setPersonJPA(this);

        }
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonJPA personJPA = (PersonJPA) o;

        if (id != null ? !id.equals( personJPA.id ) : personJPA.id != null) return false;
        if (firstName != null ? !firstName.equals( personJPA.firstName ) : personJPA.firstName != null) return false;
        if (lastName != null ? !lastName.equals( personJPA.lastName ) : personJPA.lastName != null) return false;
        return phoneNumberJPAList != null ? phoneNumberJPAList.equals( personJPA.phoneNumberJPAList ) : personJPA.phoneNumberJPAList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phoneNumberJPAList != null ? phoneNumberJPAList.hashCode() : 0);
        return result;
    }
}
