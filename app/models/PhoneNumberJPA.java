package models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
public class PhoneNumberJPA {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(nullable = false)
    public Long id;

    @Column(nullable = false)
    public String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personajpa_id")
    public PersonJPA personJPA;


    public PhoneNumberJPA(){

    }

    public PhoneNumberJPA(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PersonJPA setPersonJPA(PersonJPA personJPA){

        return this.personJPA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumberJPA that = (PhoneNumberJPA) o;

        if (id != null ? !id.equals( that.id ) : that.id != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals( that.phoneNumber ) : that.phoneNumber != null) return false;
        return personJPA != null ? personJPA.equals( that.personJPA ) : that.personJPA == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (personJPA != null ? personJPA.hashCode() : 0);
        return result;
    }
}
