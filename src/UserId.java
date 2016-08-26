
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gdotta
 */
public class UserId {
    
    private String docNumber;
    private String firstName;
    private String lastName1;
    private String lastName2;
    
    private Date birthDate;
    private Date dateOdIssuance;
    private Date dateOfExpiry;
    
    private String nationality;
    private String placeOfBirth;
    private String pictureB64;
    private String observations;
    
    private String mrz;

    public UserId(String docNumber, String firstName, String lastName1, String lastName2, Date birthDate, Date dateOdIssuance, Date dateOfExpiry, String nationality, String placeOfBirth, String pictureB64, String observations, String mrz) {
        this.docNumber = docNumber;
        this.firstName = firstName;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.birthDate = birthDate;
        this.dateOdIssuance = dateOdIssuance;
        this.dateOfExpiry = dateOfExpiry;
        this.nationality = nationality;
        this.placeOfBirth = placeOfBirth;
        this.pictureB64 = pictureB64;
        this.observations = observations;
        this.mrz = mrz;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDateOdIssuance() {
        return dateOdIssuance;
    }

    public void setDateOdIssuance(Date dateOdIssuance) {
        this.dateOdIssuance = dateOdIssuance;
    }

    public Date getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(Date dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPictureB64() {
        return pictureB64;
    }

    public void setPictureB64(String pictureB64) {
        this.pictureB64 = pictureB64;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getMrz() {
        return mrz;
    }

    public void setMrz(String mrz) {
        this.mrz = mrz;
    }
    
    public UserId(byte[] buffer, int offset, int length) {
        //FABRIZIO: Esta Firma es la semantica que generalmente usan las tarjetas
        //para estas operaciones, aunque no tiene por que ser la que usemos nosotros...


        //TODO build a neat TLV parser
    }
    
}
