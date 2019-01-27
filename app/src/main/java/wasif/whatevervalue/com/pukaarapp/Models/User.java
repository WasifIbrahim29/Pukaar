package wasif.whatevervalue.com.pukaarapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 6/26/2017.
 */

public class User implements Parcelable{

    private String user_id;
    private String phone_number;
    private String email;
    private String nickname;
    private String profile_photo;
    private String assignedToTherapist;
    private String status;
    private int userNumber;

    public User(String user_id, String email, String nickname,String profile_photo, String phone_number,String assignedToTherapist,String status,int userNumber) {
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.email = email;
        this.nickname = nickname;
        this.profile_photo=profile_photo;
        this.assignedToTherapist=assignedToTherapist;
        this.status=status;
        this.userNumber=userNumber;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public User() {


    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAssignedToTherapist() {
        return assignedToTherapist;
    }

    public void setAssignedToTherapist(String assignedToTherapist) {
        this.assignedToTherapist = assignedToTherapist;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    protected User(Parcel in) {
        user_id = in.readString();
        phone_number = in.readString();
        email = in.readString();
        nickname = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getUsername() {
        return nickname;
    }

    public void setUsername(String username) {
        this.nickname = nickname;
    }


    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", username='" + nickname + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(phone_number);
        dest.writeString(email);
        dest.writeString(nickname);
    }
}
