package MODEL;

public class Podcast {
    String podId;
    String celebrity;
    String genre;
    String dateOfPodcast;

    public Podcast(String podId, String celebrity, String genre, String dateOfPodcast) {
        this.podId = podId;
        this.celebrity = celebrity;
        this.genre = genre;
        this.dateOfPodcast = dateOfPodcast;
    }

    public Podcast() {

    }


    public String getPodId() {
        return podId;
    }

    public void setPodID(String podID) {
        this.podId = podId;
    }

    public String getCelebrity() {
        return celebrity;
    }

    public void setCelebrity(String celebrity) {
        this.celebrity = celebrity;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDateOfPodcast() {
        return dateOfPodcast;
    }

    public void setDateOfPodcast(String dateOfPodcast) {
        this.dateOfPodcast = dateOfPodcast;
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "podID='" + podId + '\'' +
                ", celebrity='" + celebrity + '\'' +
                ", genre='" + genre + '\'' +
                ", dateOfPodcast='" + dateOfPodcast + '\'' +
                '}';
    }
}
