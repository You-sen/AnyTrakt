package com.example.anytrakt;

public class Watch_List {

    String Title;
    String Release_Year;
    String Genre;
    String IMDb_Rating;

    public Watch_List(String title, String release_Year, String genre, String IMDb_Rating) {
        Title = title;
        Release_Year = release_Year;
        Genre = genre;
        this.IMDb_Rating = IMDb_Rating;
    }

    public Watch_List() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRelease_Year() {
        return Release_Year;
    }

    public void setRelease_Year(String release_Year) {
        Release_Year = release_Year;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getIMDb_Rating() {
        return IMDb_Rating;
    }

    public void setIMDb_Rating(String IMDb_Rating) {
        this.IMDb_Rating = IMDb_Rating;
    }

    @Override
    public String toString() {
        return "Watch_List{" +
                "Title='" + Title + '\'' +
                ", Release_Year='" + Release_Year + '\'' +
                ", Genre='" + Genre + '\'' +
                ", IMDb_Rating='" + IMDb_Rating + '\'' +
                '}';
    }






}