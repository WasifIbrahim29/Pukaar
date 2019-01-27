package wasif.whatevervalue.com.pukaarapp.Models;

public class user_questions {

    private question1 question1;
    private question2 question2;
    private question3 question3;
    private question4 question4;
    private question5 question5;
    private question6 question6;
    private String userID;

    user_questions(){

    }

    public user_questions(wasif.whatevervalue.com.pukaarapp.Models.question1 question1, wasif.whatevervalue.com.pukaarapp.Models.question2 question2, wasif.whatevervalue.com.pukaarapp.Models.question3 question3, wasif.whatevervalue.com.pukaarapp.Models.question4 question4, wasif.whatevervalue.com.pukaarapp.Models.question5 question5, wasif.whatevervalue.com.pukaarapp.Models.question6 question6, String userID) {
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
        this.question6 = question6;
        this.userID = userID;
    }

    public wasif.whatevervalue.com.pukaarapp.Models.question1 getQuestion1() {
        return question1;
    }

    public void setQuestion1(wasif.whatevervalue.com.pukaarapp.Models.question1 question1) {
        this.question1 = question1;
    }

    public wasif.whatevervalue.com.pukaarapp.Models.question2 getQuestion2() {
        return question2;
    }

    public void setQuestion2(wasif.whatevervalue.com.pukaarapp.Models.question2 question2) {
        this.question2 = question2;
    }

    public wasif.whatevervalue.com.pukaarapp.Models.question3 getQuestion3() {
        return question3;
    }

    public void setQuestion3(wasif.whatevervalue.com.pukaarapp.Models.question3 question3) {
        this.question3 = question3;
    }

    public wasif.whatevervalue.com.pukaarapp.Models.question4 getQuestion4() {
        return question4;
    }

    public void setQuestion4(wasif.whatevervalue.com.pukaarapp.Models.question4 question4) {
        this.question4 = question4;
    }

    public wasif.whatevervalue.com.pukaarapp.Models.question5 getQuestion5() {
        return question5;
    }

    public void setQuestion5(wasif.whatevervalue.com.pukaarapp.Models.question5 question5) {
        this.question5 = question5;
    }

    public wasif.whatevervalue.com.pukaarapp.Models.question6 getQuestion6() {
        return question6;
    }

    public void setQuestion6(wasif.whatevervalue.com.pukaarapp.Models.question6 question6) {
        this.question6 = question6;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
