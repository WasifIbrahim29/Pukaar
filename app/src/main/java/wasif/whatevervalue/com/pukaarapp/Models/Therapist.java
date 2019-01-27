package wasif.whatevervalue.com.pukaarapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.util.Strings;

/**
 * Created by User on 6/26/2017.
 */

public class Therapist{

    private String name;
    private String therapist_id;
    private String phone_number;
    private String email;
    private String description;
    private String profile_photo;
    private Speciality speciality;
    private String services;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    private String license;
    private String assignedToUser;
    private String assignedTemp;



    public Therapist(){

    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Therapist(String name, String therapist_id, String phone_number, String email, String description, String profile_photo, Speciality speciality, String services, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday, String license, String assignedToUser,String assignedTemp) {
        this.name = name;
        this.therapist_id = therapist_id;
        this.phone_number = phone_number;
        this.email = email;
        this.description = description;
        this.profile_photo = profile_photo;
        this.speciality = speciality;
        this.services = services;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.license = license;
        this.assignedToUser = assignedToUser;
        this.assignedTemp=assignedTemp;
    }

    public String getAssignedTemp() {
        return assignedTemp;
    }

    public void setAssignedTemp(String assignedTemp) {
        this.assignedTemp = assignedTemp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTherapist_id() {
        return therapist_id;
    }

    public void setTherapist_id(String therapist_id) {
        this.therapist_id = therapist_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }


    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(String assignedToUser) {
        this.assignedToUser = assignedToUser;
    }
}
