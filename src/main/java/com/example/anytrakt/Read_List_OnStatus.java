package com.example.anytrakt;

public class Read_List_OnStatus {


    String Title;
    String Author;
    String Publish_Year;
    String Genre;
    String Rating;
    String OnStatus;

    public Read_List_OnStatus() {
    }

    public Read_List_OnStatus(String title, String author, String publish_Year, String genre, String rating, String onStatus) {
        Title = title;
        Author = author;
        Publish_Year = publish_Year;
        Genre = genre;
        Rating = rating;
        OnStatus = onStatus;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPublish_Year() {
        return Publish_Year;
    }

    public void setPublish_Year(String publish_Year) {
        Publish_Year = publish_Year;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getOnStatus() {
        return OnStatus;
    }

    public void setOnStatus(String onStatus) {
        OnStatus = onStatus;
    }

    @Override
    public String toString() {
        return "Read_List_OnStatus{" +
                "Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                ", Publish_Year='" + Publish_Year + '\'' +
                ", Genre='" + Genre + '\'' +
                ", Rating='" + Rating + '\'' +
                ", OnStatus='" + OnStatus + '\'' +
                '}';
    }
}
