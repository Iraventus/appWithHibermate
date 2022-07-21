package org.example.oneToOne.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Passport")
public class Passport implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private OneToOnePerson oneToOnePerson;

    @Column(name = "passport_number")
    private int passportNumber;

    public Passport(OneToOnePerson oneToOnePerson, int passportNumber) {
        this.oneToOnePerson = oneToOnePerson;
        this.passportNumber = passportNumber;
    }

    public Passport() {
    }

    public OneToOnePerson getOneToOnePerson() {
        return oneToOnePerson;
    }

    public void setOneToOnePerson(OneToOnePerson oneToOnePerson) {
        this.oneToOnePerson = oneToOnePerson;
    }

    public int getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "oneToOnePerson=" + oneToOnePerson +
                ", passportNumber=" + passportNumber +
                '}';
    }
}
