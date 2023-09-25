package com.example.grifmood;
import com.google.gson.annotations.SerializedName;

public class ConditionResponse {
    private int id;
    private int assessment;
    private String description;
    private String date;
    private int Profile;
    private int work;
    private int reading;
    private int workout;
    private int gaming;
    private int family;
    private int friend;
    private int study;
    private int music;
    private int movie;
    private int shopping;
    private int travel;
    private int cleaning;
    private int sleep;
    private int party;
    private int bar;
    private int leisure;
    private int rendezvous;
    private int TV;

    @Override
    public String toString() {
        return "{" +
                "\"id=\": "+ id +
                ",  \"assessment=\": "+ assessment +
                ", \"description\": \"" + description + '\"' +
                ", \"date\": \"" + date + '\"' +
                ",  \"work=\": "+ work +
                ",  \"reading=\": "+ reading +
                ",  \"workout=\": "+ workout +
                ",  \"gaming=\": "+ gaming +
                ",  \"family=\": "+ family +
                ",  \"friend=\": "+ friend +
                ",  \"study=\": "+ study +
                ",  \"music=\": "+ music +
                ",  \"movie=\": "+ movie +
                ",  \"shopping=\": "+ shopping +
                ",  \"travel=\": "+ travel +
                ",  \"cleaning=\": "+ cleaning +
                ",  \"sleep=\": "+ sleep +
                ",  \"party=\": "+ party +
                ",  \"bar=\": "+ bar +
                ",  \"leisure=\": "+ leisure +
                ",  \"rendezvous=\": "+ rendezvous +
                ",  \"TV=\": "+ TV +
                ",  \"profile=\": "+ Profile +
                '}';
    }

    public ConditionResponse(int id, int assessment, String description, String date, int work, int reading, int workout, int gaming, int family, int friend, int study, int music, int movie, int shopping, int travel, int cleaning, int sleep, int party, int bar, int leisure, int rendezvous, int TV) {
        this.id = id;
        this.assessment = assessment;
        this.description = description;
        this.date = date;
        this.work = work;
        this.reading = reading;
        this.workout = workout;
        this.gaming = gaming;
        this.family = family;
        this.friend = friend;
        this.study = study;
        this.music = music;
        this.movie = movie;
        this.shopping = shopping;
        this.travel = travel;
        this.cleaning = cleaning;
        this.sleep = sleep;
        this.party = party;
        this.bar = bar;
        this.leisure = leisure;
        this.rendezvous = rendezvous;
        this.TV = TV;

    }

    public ConditionResponse(int id, int assessment, String description, String date, int work, int reading, int workout, int gaming, int family, int friend, int study, int music, int movie, int shopping, int travel, int cleaning, int sleep, int party, int bar, int leisure, int rendezvous, int TV,int Profile) {
        this.id = id;
        this.assessment = assessment;
        this.description = description;
        this.date = date;
        this.work = work;
        this.reading = reading;
        this.workout = workout;
        this.gaming = gaming;
        this.family = family;
        this.friend = friend;
        this.study = study;
        this.music = music;
        this.movie = movie;
        this.shopping = shopping;
        this.travel = travel;
        this.cleaning = cleaning;
        this.sleep = sleep;
        this.party = party;
        this.bar = bar;
        this.leisure = leisure;
        this.rendezvous = rendezvous;
        this.TV = TV;
        this.Profile=Profile;

    }

    public int getProfile() {
        return Profile;
    }

    public void setProfile(int Profile) {
        this.Profile = Profile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAssessment() {
        return assessment;
    }

    public void setAssessment(int assessment) {
        this.assessment = assessment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }

    public int getReading() {
        return reading;
    }

    public void setReading(int reading) {
        this.reading = reading;
    }

    public int getWorkout() {
        return workout;
    }

    public void setWorkout(int workout) {
        this.workout = workout;
    }

    public int getGaming() {
        return gaming;
    }

    public void setGaming(int gaming) {
        this.gaming = gaming;
    }

    public int getFamily() {
        return family;
    }

    public void setFamily(int family) {
        this.family = family;
    }

    public int getFriend() {
        return friend;
    }

    public void setFriend(int friend) {
        this.friend = friend;
    }

    public int getStudy() {
        return study;
    }

    public void setStudy(int study) {
        this.study = study;
    }

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getMovie() {
        return movie;
    }

    public void setMovie(int movie) {
        this.movie = movie;
    }

    public int getShopping() {
        return shopping;
    }

    public void setShopping(int shopping) {
        this.shopping = shopping;
    }

    public int getTravel() {
        return travel;
    }

    public void setTravel(int travel) {
        this.travel = travel;
    }

    public int getCleaning() {
        return cleaning;
    }

    public void setCleaning(int cleaning) {
        this.cleaning = cleaning;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public int getParty() {
        return party;
    }

    public void setParty(int party) {
        this.party = party;
    }

    public int getBar() {
        return bar;
    }

    public void setBar(int bar) {
        this.bar = bar;
    }

    public int getLeisure() {
        return leisure;
    }

    public void setLeisure(int leisure) {
        this.leisure = leisure;
    }

    public int getRendezvous() {
        return rendezvous;
    }

    public void setRendezvous(int rendezvous) {
        this.rendezvous = rendezvous;
    }

    public int getTV() {
        return TV;
    }

    public void setTV(int TV) {
        this.TV = TV;
    }

    public String getMyDate() {
        String[]words = this.date.split("-");
        String year=words[0];
        String month=words[1];
        String[]words1=words[2].split("T");
        String day=words1[0];
        String[] words2=words1[1].split(":");
        String hour=words2[0];
        String mins   =words2[1];

        Character first=day.charAt(0);
        if (first=='0') {
            day=day.substring(1);
        }

        switch (month){
            case("01"):
                month="Января";
                break;
            case("02"):
                month="Февраля";
                break;
            case("03"):
                month="Марта";
                break;
            case("04"):
                month="Апреля";
                break;
            case("05"):
                month="Мая";
                break;
            case("06"):
                month="Июня";
                break;
            case("07"):
                month="Июля";
                break;
            case("08"):
                month="Августа";
                break;
            case("09"):
                month="Сентября";
                break;
            case("10"):
                month="Октября";
                break;
            case("11"):
                month="Ноября";
                break;
            case("12"):
                month="Декабря";
                break;

        }

        String MyDate= day+" "+month+" "+ year;
        return MyDate;
    }

}
