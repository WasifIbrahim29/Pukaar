package wasif.whatevervalue.com.pukaarapp.Models;

public class question2 {

    private String question;
    private String answer;

    public question2(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    question2(){

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
