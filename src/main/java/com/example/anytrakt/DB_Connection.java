package com.example.anytrakt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DB_Connection {
    static String super_User_ID;
    String Super_Fiction_ID;
    String Super_Nonfiction_ID;
    String Super_Manga_ID;

    String Super_TS_ID;
    String Super_A_ID ;
    String Super_D_ID;
    String Super_M_ID;
    private Connection connection;

    Connection getConnection() throws SQLException {
       // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://LAPTOP-R38U5TTN\\SQLEXPRESS;user=sa;password=amitumir69;" + "databaseName=AnyTrakt_DB;encrypt=true;trustServerCertificate=true;");
        java.sql.Statement Statement = conn.createStatement();
        return conn;
    }

    public boolean insertDataToDB(String query)
    {
        try
        {
            java.sql.Statement Statement=connection.createStatement();
            Statement.execute(query);
            return true;
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Unable to Insert Data");
            Logger.getLogger(DB_Connection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }



    ObservableList<User> getAllUser() throws SQLException{
        ObservableList<User> userList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "select * from Users";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            String username = rs.getString("User_Name");
            String password = rs.getString("Pass_Word");
            String accountType = rs.getString("AccountType");
            User user = new User(username, password, accountType);
            userList.add(user);

        }

        String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            super_User_ID  = rs1.getString("U_ID");
        }
        return userList;

    }


    void insertUser(User user) throws SQLException {
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();
        String n="INSERT INTO Users (User_Name, Pass_Word, AccountType) VALUES ('"+user.getUsername()+"','"+user.getPassword()+"','"+user.getAccountType()+"')";
        statement.execute(n);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ObservableList<Watch_List> getAllMovie() throws SQLException {

        ObservableList<Watch_List> Movielist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating FROM Movies LEFT JOIN UserMovie\n" +
                "ON Movies.M_ID = UserMovie.M_ID  WHERE OnStatus IS NULL";
        ResultSet rs = statement.executeQuery(query);

        while(rs.next())
        {
            String Title = rs.getString("Title");
            String Release_Year = rs.getString("Release_Year");
            String Genre = rs.getString("Genre");
            String IMDb_Rating = rs.getString("IMDb_Rating");

            Watch_List movies = new Watch_List(Title,Release_Year, Genre,IMDb_Rating);

            Movielist.add(movies);

        }

        return Movielist;

    }


    void insertMovie(Watch_List movie) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n="INSERT INTO Movies (Title,Release_Year,Genre,IMDb_Rating) VALUES ('"+movie.getTitle()+"','"+movie.getRelease_Year()+"','"+movie.getGenre()+"','"+movie.getIMDb_Rating()+"')";
        statement.execute(n);
    }


    void UpdateMovie(Watch_List movie) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n= "UPDATE Movies SET Release_Year= '"+movie.getRelease_Year()+"', Genre= '"+movie.getGenre()+"', IMDb_Rating = '"+movie.getIMDb_Rating()+"' WHERE Title='"+movie.getTitle()+"'";
        statement.execute(n);
    }








    void deleteMovie(ObservableList<Watch_List> selectedMovies) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List movie : selectedMovies) {
            String query = "DELETE FROM Movies WHERE Title='" +movie.getTitle() + "'";
            statement.executeUpdate(query);
        }

    }

    ObservableList<Watch_List> searchMovie(String type, String value) throws SQLException {
        ObservableList<Watch_List> MovieSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();
        String n1 = "SELECT * FROM Movies  WHERE " + type + "='" + value + "'";
        ResultSet rs1 = statement.executeQuery(n1);
        while (rs1.next()) {
            String Title = rs1.getString("Title");
            String Release_Year = rs1.getString("Release_Year");
            String Genre = rs1.getString("Genre");
            String IMDb_Rating = rs1.getString("IMDb_Rating");

            Watch_List movies = new Watch_List(Title,Release_Year, Genre,IMDb_Rating);

            MovieSearchlist.add(movies);

        }

        return MovieSearchlist;
    }
////////////////////////////////////////////////////////////////////////////////////

    ObservableList<Watch_List> getAllTv_Serieslist() throws SQLException {

        ObservableList<Watch_List> Tv_Serieslist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "select * from Tv_Series";
        ResultSet rs3 = statement.executeQuery(query);

        while(rs3.next())
        {
            String Title = rs3.getString("Title");
            String Release_Year = rs3.getString("Release_Year");
            String Genre = rs3.getString("Genre");
            String IMDb_Rating = rs3.getString("IMDb_Rating");

            Watch_List Tv_Series = new Watch_List(Title,Release_Year, Genre,IMDb_Rating);

            Tv_Serieslist.add(Tv_Series);

        }

        return Tv_Serieslist;

    }


    void insertTv_Series(Watch_List Tv_Series) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n="INSERT INTO Tv_Series (Title,Release_Year,Genre,IMDb_Rating) VALUES ('"+Tv_Series.getTitle()+"','"+Tv_Series.getRelease_Year()+"','"+Tv_Series.getGenre()+"','"+Tv_Series.getIMDb_Rating()+"')";
        statement.execute(n);
    }

    void UpdateTv_Series(Watch_List Tv_Series) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n= "UPDATE Tv_Series SET Release_Year= '"+Tv_Series.getRelease_Year()+"', Genre= '"+Tv_Series.getGenre()+"', IMDb_Rating = '"+Tv_Series.getIMDb_Rating()+"' WHERE Title='"+Tv_Series.getTitle()+"'";
        statement.execute(n);
    }


    void deleteTv_Series(ObservableList<Watch_List> selectedTv_Series) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Tv_Series : selectedTv_Series) {
            String query = "DELETE FROM Tv_Series WHERE Title='" +Tv_Series.getTitle() + "'";
            statement.executeUpdate(query);
        }

    }



    ObservableList<Watch_List> searchTv_Series(String type, String value) throws SQLException {
        ObservableList<Watch_List> Tv_SeriesSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();
        String n1 = "SELECT * FROM Tv_Series  WHERE " + type + "='" + value + "'";
        ResultSet rs4 = statement.executeQuery(n1);
        while (rs4.next()) {
            String Title = rs4.getString("Title");
            String Release_Year = rs4.getString("Release_Year");
            String Genre = rs4.getString("Genre");
            String IMDb_Rating = rs4.getString("IMDb_Rating");

            Watch_List Tv_Series = new Watch_List(Title,Release_Year, Genre,IMDb_Rating);

            Tv_SeriesSearchlist.add(Tv_Series);

        }

        return Tv_SeriesSearchlist;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ObservableList<Watch_List> getAllAnimelist() throws SQLException {

        ObservableList<Watch_List> Animelist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "select * from Anime";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String IMDb_Rating = rs5.getString("IMDb_Rating");

            Watch_List Anime = new Watch_List(Title,Release_Year, Genre,IMDb_Rating);

            Animelist.add(Anime);

        }

        return Animelist;

    }


    void insertAnime(Watch_List Anime) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n="INSERT INTO Anime (Title,Release_Year,Genre,IMDb_Rating) VALUES ('"+Anime.getTitle()+"','"+Anime.getRelease_Year()+"','"+Anime.getGenre()+"','"+Anime.getIMDb_Rating()+"')";
        statement.execute(n);
    }
    void UpdateAnime(Watch_List Anime) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n= "UPDATE Anime SET Release_Year= '"+Anime.getRelease_Year()+"', Genre= '"+Anime.getGenre()+"', IMDb_Rating = '"+Anime.getIMDb_Rating()+"' WHERE Title='"+Anime.getTitle()+"'";
        statement.execute(n);
    }

    void deleteAnime(ObservableList<Watch_List> selectedAnime) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Anime : selectedAnime) {
            String query = "DELETE FROM Anime WHERE Title='" +Anime.getTitle() + "'";
            statement.executeUpdate(query);
        }

    }



    ObservableList<Watch_List> searchAnime(String type, String value) throws SQLException {
        ObservableList<Watch_List> AnimeSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();
        String n1 = "SELECT * FROM Anime  WHERE " + type + "='" + value + "'";
        ResultSet rs6 = statement.executeQuery(n1);
        while (rs6.next()) {
            String Title = rs6.getString("Title");
            String Release_Year = rs6.getString("Release_Year");
            String Genre = rs6.getString("Genre");
            String IMDb_Rating = rs6.getString("IMDb_Rating");

            Watch_List Anime = new Watch_List(Title,Release_Year, Genre,IMDb_Rating);

            AnimeSearchlist.add(Anime);

        }

        return AnimeSearchlist;
    }





    ///////////////////////////////////////////////////////////////////////////////////////////////////////////



    ObservableList<Watch_List> getAllDramalist() throws SQLException {

        ObservableList<Watch_List> Dramalist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "select * from Drama";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String IMDb_Rating = rs5.getString("IMDb_Rating");

            Watch_List Drama = new Watch_List(Title,Release_Year, Genre,IMDb_Rating);

            Dramalist.add(Drama);

        }

        return Dramalist;

    }


    void insertDrama(Watch_List Drama) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n="INSERT INTO Drama (Title,Release_Year,Genre,IMDb_Rating) VALUES ('"+Drama.getTitle()+"','"+Drama.getRelease_Year()+"','"+Drama.getGenre()+"','"+Drama.getIMDb_Rating()+"')";
        statement.execute(n);
    }

    void UpdateDrama(Watch_List Drama) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n= "UPDATE Drama SET Release_Year= '"+Drama.getRelease_Year()+"', Genre= '"+Drama.getGenre()+"', IMDb_Rating = '"+Drama.getIMDb_Rating()+"' WHERE Title='"+Drama.getTitle()+"'";
        statement.execute(n);
    }

    void deleteDrama(ObservableList<Watch_List> selectedDrama) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Drama : selectedDrama) {
            String query = "DELETE FROM Drama WHERE Title='" +Drama.getTitle() + "'";
            statement.executeUpdate(query);
        }

    }



    ObservableList<Watch_List> searchDrama(String type, String value) throws SQLException {
        ObservableList<Watch_List> DramaSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();
        String n1 = "SELECT * FROM Drama  WHERE " + type + "='" + value + "'";
        ResultSet rs6 = statement.executeQuery(n1);
        while (rs6.next()) {
            String Title = rs6.getString("Title");
            String Release_Year = rs6.getString("Release_Year");
            String Genre = rs6.getString("Genre");
            String IMDb_Rating = rs6.getString("IMDb_Rating");

            Watch_List Drama = new Watch_List(Title,Release_Year, Genre,IMDb_Rating);

            DramaSearchlist.add(Drama);

        }

        return DramaSearchlist;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ObservableList<Read_List> getAllManga() throws SQLException {

        ObservableList<Read_List> Mangalist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "select * from Manga";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Author = rs5.getString("Author");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");

            Read_List Manga = new Read_List(Title,Author,Publish_Year, Genre,Rating);

            Mangalist.add(Manga);

        }

        return Mangalist;

    }


    void insertManga(Read_List Manga) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n="INSERT INTO Manga (Title,Author,Publish_Year,Genre,Rating) VALUES  ('"+Manga.getTitle()+"','"+Manga.getAuthor()+"','"+Manga.getGenre()+"','"+Manga.getPublish_Year()+"','"+Manga.getRating()+"')";
        statement.execute(n);
    }


    void deleteManga(ObservableList<Read_List> selectedManga) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Manga : selectedManga) {
            String query = "DELETE FROM Manga WHERE Title='" +Manga.getTitle() + "'";
            statement.executeUpdate(query);
        }

    }

    void UpdateManga(Read_List Manga) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n= "UPDATE Manga SET Publish_Year= '"+Manga.getPublish_Year()+"',Author= '"+Manga.getAuthor()+"', Genre= '"+Manga.getGenre()+"',Rating = '"+Manga.getRating()+"' WHERE Title='"+Manga.getTitle()+"'";
        statement.execute(n);
    }



    ObservableList<Read_List> searchManga(String type, String value) throws SQLException {
        ObservableList<Read_List> MangaSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();
        String n1 = "SELECT * FROM Manga WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Author = rs5.getString("Author");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");

            Read_List Manga = new Read_List(Title,Author,Publish_Year, Genre,Rating);

            MangaSearchlist.add(Manga);

        }

        return MangaSearchlist;
    }




    ////////////////////////////////////////////////////////////////////////////////////////

    ObservableList<Read_List> getAllNonfiction() throws SQLException {

        ObservableList<Read_List> Nonfictionlist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "select * from Nonfiction";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Author = rs5.getString("Author");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");

            Read_List Nonfiction = new Read_List(Title,Author,Publish_Year, Genre,Rating);

            Nonfictionlist.add(Nonfiction);

        }

        return Nonfictionlist;

    }


    void insertNonfiction(Read_List Nonfiction) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n="INSERT INTO Nonfiction (Title,Author,Publish_Year,Genre,Rating) VALUES  ('"+Nonfiction.getTitle()+"','"+Nonfiction.getAuthor()+"','"+Nonfiction.getGenre()+"','"+Nonfiction.getPublish_Year()+"','"+Nonfiction.getRating()+"')";
        statement.execute(n);
    }


    void deleteNonfiction(ObservableList<Read_List> selectedNonfiction) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Nonfiction : selectedNonfiction) {
            String query = "DELETE FROM Nonfiction WHERE Title='" +Nonfiction.getTitle() + "'";
            statement.executeUpdate(query);
        }

    }

    void UpdateNonfiction(Read_List Nonfiction) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n= "UPDATE Nonfiction SET Publish_Year= '"+Nonfiction.getPublish_Year()+"',Author= '"+Nonfiction.getAuthor()+"', Genre= '"+Nonfiction.getGenre()+"',Rating = '"+Nonfiction.getRating()+"' WHERE Title='"+Nonfiction.getTitle()+"'";
        statement.execute(n);
    }

    ObservableList<Read_List> searchNonfiction(String type, String value) throws SQLException {
        ObservableList<Read_List> NonfictionSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();
        String n1 = "SELECT * FROM Nonfiction WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Author = rs5.getString("Author");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");

            Read_List Nonfiction = new Read_List(Title,Author,Publish_Year, Genre,Rating);

            NonfictionSearchlist.add(Nonfiction);

        }

        return NonfictionSearchlist;
    }


////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////
ObservableList<Read_List> getAllFiction() throws SQLException {

    ObservableList<Read_List> Fictionlist = FXCollections.observableArrayList();

    Connection conn = getConnection();

    Statement statement = conn.createStatement();
    String query = "select * from Fiction";
    ResultSet rs5 = statement.executeQuery(query);

    while(rs5.next())
    {
        String Title = rs5.getString("Title");
        String Author = rs5.getString("Author");
        String Publish_Year = rs5.getString("Publish_Year");
        String Genre = rs5.getString("Genre");
        String Rating = rs5.getString("Rating");

        Read_List Fiction = new Read_List(Title,Author,Publish_Year, Genre,Rating);

        Fictionlist.add(Fiction);

    }

    return Fictionlist;

}


    void insertFiction(Read_List Fiction) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n="INSERT INTO Fiction (Title,Author,Publish_Year,Genre,Rating) VALUES  ('"+Fiction.getTitle()+"','"+Fiction.getAuthor()+"','"+Fiction.getGenre()+"','"+Fiction.getPublish_Year()+"','"+Fiction.getRating()+"')";
        statement.execute(n);
    }


    void deleteFiction(ObservableList<Read_List> selectedFiction) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Fiction : selectedFiction) {
            String query = "DELETE FROM Fiction WHERE Title='" +Fiction.getTitle() + "'";
            statement.executeUpdate(query);
        }

    }

    void UpdateFiction(Read_List Fiction) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n= "UPDATE Fiction SET Publish_Year= '"+Fiction.getPublish_Year()+"', Author= '"+Fiction.getAuthor()+"',Genre= '"+Fiction.getGenre()+"',Rating = '"+Fiction.getRating()+"' WHERE Title='"+Fiction.getTitle()+"'";
        statement.execute(n);
    }

    ObservableList<Read_List> searchFiction(String type, String value) throws SQLException {
        ObservableList<Read_List> FictionSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();
        String n1 = "SELECT * FROM Fiction WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Author = rs5.getString("Author");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");

            Read_List Fiction = new Read_List(Title,Author,Publish_Year, Genre,Rating);

            FictionSearchlist.add(Fiction);

        }

        return FictionSearchlist;
    }
////////////////////////////////////////////////////////////////////////////////////////
ObservableList<Read_List> getAllComic() throws SQLException {

    ObservableList<Read_List> Comiclist = FXCollections.observableArrayList();

    Connection conn = getConnection();

    Statement statement = conn.createStatement();
    String query = "select * from Comic";
    ResultSet rs5 = statement.executeQuery(query);

    while(rs5.next())
    {
        String Title = rs5.getString("Title");
        String Author = rs5.getString("Author");
        String Publish_Year = rs5.getString("Publish_Year");
        String Genre = rs5.getString("Genre");
        String Rating = rs5.getString("Rating");

        Read_List Comic = new Read_List(Title,Author,Publish_Year, Genre,Rating);

        Comiclist.add(Comic);

    }

    return Comiclist;

}


    void insertComic(Read_List Comic) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n="INSERT INTO Comic (Title,Author,Publish_Year,Genre,Rating) VALUES  ('"+Comic.getTitle()+"','"+Comic.getAuthor()+"','"+Comic.getGenre()+"','"+Comic.getPublish_Year()+"','"+Comic.getRating()+"')";
        statement.execute(n);
    }


    void deleteComic(ObservableList<Read_List> selectedComic) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Comic : selectedComic) {
            String query = "DELETE FROM Comic WHERE Title='" +Comic.getTitle() + "'";
            statement.executeUpdate(query);
        }

    }

    void UpdateComic(Read_List Comic) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String n= "UPDATE Manga SET Publish_Year= '"+Comic.getPublish_Year()+"',Author= '"+Comic.getAuthor()+"',Genre= '"+Comic.getGenre()+"',Rating = '"+Comic.getRating()+"' WHERE Title='"+Comic.getTitle()+"'";
        statement.execute(n);
    }


    ObservableList<Read_List> searchComic(String type, String value) throws SQLException {
        ObservableList<Read_List> ComicSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();
        String n1 = "SELECT * FROM Comic WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Author = rs5.getString("Author");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");

            Read_List Comic = new Read_List(Title,Author,Publish_Year, Genre,Rating);

            ComicSearchlist.add(Comic);

        }

        return ComicSearchlist;
    }



////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////50%//////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////




void InsertIntoUserComicOnComplete(ObservableList<Read_List> selectedComic) throws SQLException {

    Connection conn = getConnection();
    Statement statement = conn.createStatement();
    for (Read_List Comic : selectedComic) {
        String n="INSERT INTO UserComic (Title,Author,Publish_Year,Genre,Rating,OnStatus) VALUES  ('"+Comic.getTitle()+"','"+Comic.getAuthor()+"','"+Comic.getGenre()+"','"+Comic.getPublish_Year()+"','"+Comic.getRating()+"','Completed')";
       // String query = "DELETE FROM Anime WHERE Title='" +Anime.getTitle() + "'";
        statement.executeUpdate(n);
    }

}

    void InsertIntoUserComicOnReading(ObservableList<Read_List> selectedComic) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Comic : selectedComic) {
            String n="INSERT INTO UserComic (Title,Author,Publish_Year,Genre,Rating,OnStatus) VALUES  ('"+Comic.getTitle()+"','"+Comic.getAuthor()+"','"+Comic.getGenre()+"','"+Comic.getPublish_Year()+"','"+Comic.getRating()+"','Reading')";
            // String query = "DELETE FROM Anime WHERE Title='" +Anime.getTitle() + "'";
            statement.executeUpdate(n);
        }

    }


    void InsertIntoUserComicOnCompletePlan_To_Read(ObservableList<Read_List> selectedComic) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Comic : selectedComic) {
            String n="INSERT INTO UserComic (Title,Author,Publish_Year,Genre,Rating,OnStatus) VALUES  ('"+Comic.getTitle()+"','"+Comic.getAuthor()+"','"+Comic.getGenre()+"','"+Comic.getPublish_Year()+"','"+Comic.getRating()+"','Plan To Read')";
            // String query = "DELETE FROM Anime WHERE Title='" +Anime.getTitle() + "'";
            statement.executeUpdate(n);
        }

    }
//////////////////////////////////        GET VALUE                //////////////////////////////////////////////////////
//////////////////////////////////        GET VALUE                //////////////////////////////////////////////////////

    ObservableList<Read_List_OnStatus> getAllComicOnStatus() throws SQLException {

        ObservableList<Read_List_OnStatus> OnStatusUserComic = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "select * from UserComic";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Author = rs5.getString("Author");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");
            String OnStatus = rs5.getString("OnStatus");

            Read_List_OnStatus UserComic = new Read_List_OnStatus(Title,Author,Publish_Year, Genre,Rating,OnStatus);

            OnStatusUserComic.add(UserComic);

        }

        return OnStatusUserComic;

    }
////////////////////////////////////////////////////////////////////////////////////////


    void deleteOnStatusComic(ObservableList<Read_List_OnStatus> selectedComic) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List_OnStatus Comic : selectedComic) {
            String query = "DELETE FROM UserComic WHERE Title='" +Comic.getTitle() + "'";
            statement.executeUpdate(query);
        }

    }



////////////////////////////////////////////////////////////////////////////////////////



    ObservableList<Read_List_OnStatus> searchOnStatusComic(String type, String value) throws SQLException {
        ObservableList<Read_List_OnStatus> ComicSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();
        String n1 = "SELECT * FROM UserComic WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Author = rs5.getString("Author");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");
            String OnStatus = rs5.getString("OnStatus");

            Read_List_OnStatus Comic = new Read_List_OnStatus(Title,Author,Publish_Year, Genre,Rating,OnStatus);

            ComicSearchlist.add(Comic);

        }
        return ComicSearchlist;
    }





////////////////////////////////////////////////////////////////////////////////////////
ObservableList<Read_List_OnStatus> getAllComicGroupByCompleted() throws SQLException {

    ObservableList<Read_List_OnStatus> Comiclist = FXCollections.observableArrayList();

    Connection conn = getConnection();

    Statement statement = conn.createStatement();
    String query = "SELECT Title,Author,Publish_Year,Genre,Rating,OnStatus FROM UserComic GROUP BY Title,Author,Publish_Year,Genre,Rating,OnStatus HAVING OnStatus = 'Completed'";
    ResultSet rs5 = statement.executeQuery(query);

    while(rs5.next())
    {
        String Title = rs5.getString("Title");
        String Author = rs5.getString("Author");
        String Publish_Year = rs5.getString("Publish_Year");
        String Genre = rs5.getString("Genre");
        String Rating = rs5.getString("Rating");
        String OnStatus = rs5.getString("OnStatus");

        Read_List_OnStatus Comic = new Read_List_OnStatus(Title,Author,Publish_Year, Genre,Rating,OnStatus);

        Comiclist.add(Comic);

    }

    return Comiclist;

}



    ObservableList<Read_List_OnStatus> getAllComicGroupByReading() throws SQLException {

        ObservableList<Read_List_OnStatus> Comiclist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Author,Publish_Year,Genre,Rating,OnStatus FROM UserComic GROUP BY Title,Author,Publish_Year,Genre,Rating,OnStatus HAVING OnStatus = 'Reading'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Author = rs5.getString("Author");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");
            String OnStatus = rs5.getString("OnStatus");

            Read_List_OnStatus Comic = new Read_List_OnStatus(Title,Author,Publish_Year, Genre,Rating,OnStatus);

            Comiclist.add(Comic);

        }

        return Comiclist;

    }




    ObservableList<Read_List_OnStatus> getAllComicGroupByPlan_To_Read() throws SQLException {

        ObservableList<Read_List_OnStatus> Comiclist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Author,Publish_Year,Genre,Rating,OnStatus FROM UserComic GROUP BY Title,Author,Publish_Year,Genre,Rating,OnStatus HAVING OnStatus = 'Plan To Read'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Author = rs5.getString("Author");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");
            String OnStatus = rs5.getString("OnStatus");

            Read_List_OnStatus Comic = new Read_List_OnStatus(Title,Author,Publish_Year, Genre,Rating,OnStatus);

            Comiclist.add(Comic);

        }

        return Comiclist;

    }
    ///////////////////////////////////////////////////////////////////////////////////
    void InsertIntoUserMangaOnComplete(ObservableList<Read_List> selectedManga) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Manga : selectedManga) {
            String query = "SELECT Manga_ID FROM Manga WHERE Title='" +Manga.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_Manga_ID  = rs.getString("Manga_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserManga (Manga_ID,U_ID,OnStatus) VALUES  ('" +Super_Manga_ID+ "','" +DB_Connection.super_User_ID + "','Completed')";
            statement.executeUpdate(n);
        }

    }




    void InsertIntoUserMangaOnRead(ObservableList<Read_List> selectedManga) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Manga : selectedManga) {
            String query = "SELECT Manga_ID FROM  Manga WHERE Title='" +Manga.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_Manga_ID  = rs.getString("Manga_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserManga (Manga_ID,U_ID,OnStatus) VALUES  ('" +Super_Manga_ID+ "','" +DB_Connection.super_User_ID + "','Watching')";
            statement.executeUpdate(n);
        }

    }



    void InsertIntoUserMangaOnPlan_to_Read(ObservableList<Read_List> selectedManga) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Manga : selectedManga) {
            String query = "SELECT Manga_ID FROM Manga WHERE Title='" +Manga.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_Manga_ID  = rs.getString("Manga_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserManga (Manga_ID,U_ID,OnStatus) VALUES  ('" +Super_Manga_ID+ "','" +DB_Connection.super_User_ID + "','Plan To Watch')";
            statement.executeUpdate(n);
        }

    }



    ObservableList<Read_List_OnStatus> getAllMangaOnStatus() throws SQLException {

        ObservableList<Read_List_OnStatus> OnStatusUserMoive = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Publish_Year,Rating,Author,OnStatus FROM Manga INNER JOIN UserManga ON Manga.Manga_ID = UserManga.Manga_ID  WHERE U_ID IN (SELECT U_ID FROM UserManga WHERE U_ID = '" +DB_Connection.super_User_ID + "' )";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String Author = rs5.getString("Author");
            String OnStatus = rs5.getString("OnStatus");

            Read_List_OnStatus UserManga = new Read_List_OnStatus(Title,Publish_Year, Genre,Rating,Author,OnStatus);

            OnStatusUserMoive.add(UserManga);

        }

        return OnStatusUserMoive;

    }



    void deleteOnStatusUserManga(ObservableList<Read_List_OnStatus> selectedManga) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List_OnStatus Manga : selectedManga) {
            String query = "SELECT Manga_ID FROM Manga WHERE Title='" +Manga.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_Manga_ID  = rs.getString("Manga_ID");


            }
            String query1 = "DELETE FROM UserManga WHERE Manga_ID='" +Super_Manga_ID + "'";
            statement.executeUpdate(query1);

        }

    }




    ObservableList<Read_List_OnStatus> searchOnStatusManga(String type, String value) throws SQLException {
        ObservableList<Read_List_OnStatus> MangaSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();

        String n1 = "SELECT  Title,Genre,Publish_Year,Rating,Author,OnStatus  FROM Manga INNER JOIN UserManga ON Manga.Manga_ID = UserManga.Manga_ID WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");
            String Author = rs5.getString("Author");
            String OnStatus = rs5.getString("OnStatus");

            Read_List_OnStatus Manga = new Read_List_OnStatus(Title,Publish_Year, Genre,Rating,Author,OnStatus);

            MangaSearchlist.add(Manga);

        }
        return MangaSearchlist;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
void InsertIntoUserNonfictionOnComplete(ObservableList<Read_List> selectedNonfiction) throws SQLException {

    Connection conn = getConnection();
    Statement statement = conn.createStatement();
    for (Read_List Nonfiction : selectedNonfiction) {
        String query = "SELECT Nonfiction_ID FROM Nonfiction WHERE Title='" +Nonfiction.getTitle() + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Super_Nonfiction_ID  = rs.getString("Nonfiction_ID");
        }
        String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            super_User_ID  = rs1.getString("U_ID");
        }

        String n="INSERT INTO UserNonfiction (Nonfiction_ID,U_ID,OnStatus) VALUES  ('" +Super_Nonfiction_ID+ "','" +DB_Connection.super_User_ID + "','Completed')";
        statement.executeUpdate(n);
    }

}




    void InsertIntoUserNonfictionOnRead(ObservableList<Read_List> selectedNonfiction) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Nonfiction : selectedNonfiction) {
            String query = "SELECT Nonfiction_ID FROM  Nonfiction WHERE Title='" +Nonfiction.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_Nonfiction_ID  = rs.getString("Nonfiction_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserNonfiction (Nonfiction_ID,U_ID,OnStatus) VALUES  ('" +Super_Nonfiction_ID+ "','" +DB_Connection.super_User_ID + "','Watching')";
            statement.executeUpdate(n);
        }

    }



    void InsertIntoUserNonfictionOnPlan_to_Read(ObservableList<Read_List> selectedNonfiction) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Nonfiction : selectedNonfiction) {
            String query = "SELECT Nonfiction_ID FROM Nonfiction WHERE Title='" +Nonfiction.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_Nonfiction_ID  = rs.getString("Nonfiction_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserNonfiction (Nonfiction_ID,U_ID,OnStatus) VALUES  ('" +Super_Nonfiction_ID+ "','" +DB_Connection.super_User_ID + "','Plan To Watch')";
            statement.executeUpdate(n);
        }

    }



    ObservableList<Read_List_OnStatus> getAllNonfictionOnStatus() throws SQLException {

        ObservableList<Read_List_OnStatus> OnStatusUserMoive = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Publish_Year,Rating,Author,OnStatus FROM Nonfiction INNER JOIN UserNonfiction ON Nonfiction.Nonfiction_ID = UserNonfiction.Nonfiction_ID  WHERE U_ID IN (SELECT U_ID FROM UserNonfiction WHERE U_ID = '" +DB_Connection.super_User_ID + "' )";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String Author = rs5.getString("Author");
            String OnStatus = rs5.getString("OnStatus");

            Read_List_OnStatus UserNonfiction = new Read_List_OnStatus(Title,Publish_Year, Genre,Rating,Author,OnStatus);

            OnStatusUserMoive.add(UserNonfiction);

        }

        return OnStatusUserMoive;

    }



    void deleteOnStatusUserNonfiction(ObservableList<Read_List_OnStatus> selectedNonfiction) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List_OnStatus Nonfiction : selectedNonfiction) {
            String query = "SELECT Nonfiction_ID FROM Nonfiction WHERE Title='" +Nonfiction.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_Nonfiction_ID  = rs.getString("Nonfiction_ID");


            }
            String query1 = "DELETE FROM UserNonfiction WHERE Nonfiction_ID='" +Super_Nonfiction_ID + "'";
            statement.executeUpdate(query1);

        }

    }




    ObservableList<Read_List_OnStatus> searchOnStatusNonfiction(String type, String value) throws SQLException {
        ObservableList<Read_List_OnStatus> NonfictionSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();

        String n1 = "SELECT  Title,Genre,Publish_Year,Rating,Author,OnStatus  FROM Nonfiction INNER JOIN UserNonfiction ON Nonfiction.Nonfiction_ID = UserNonfiction.Nonfiction_ID WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");
            String Author = rs5.getString("Author");
            String OnStatus = rs5.getString("OnStatus");

            Read_List_OnStatus Nonfiction = new Read_List_OnStatus(Title,Publish_Year, Genre,Rating,Author,OnStatus);

            NonfictionSearchlist.add(Nonfiction);

        }
        return NonfictionSearchlist;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    void InsertIntoUserFictionOnComplete(ObservableList<Read_List> selectedFiction) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Fiction : selectedFiction) {
            String query = "SELECT Fiction_ID FROM Fiction WHERE Title='" +Fiction.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_Fiction_ID  = rs.getString("Fiction_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserFiction (Fiction_ID,U_ID,OnStatus) VALUES  ('" +Super_Fiction_ID+ "','" +DB_Connection.super_User_ID + "','Completed')";
            statement.executeUpdate(n);
        }

    }




    void InsertIntoUserFictionOnRead(ObservableList<Read_List> selectedFiction) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Fiction : selectedFiction) {
            String query = "SELECT Fiction_ID FROM  Fiction WHERE Title='" +Fiction.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_Fiction_ID  = rs.getString("Fiction_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserFiction (Fiction_ID,U_ID,OnStatus) VALUES  ('" +Super_Fiction_ID+ "','" +DB_Connection.super_User_ID + "','Watching')";
            statement.executeUpdate(n);
        }

    }



    void InsertIntoUserFictionOnPlan_to_Read(ObservableList<Read_List> selectedFiction) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List Fiction : selectedFiction) {
            String query = "SELECT Fiction_ID FROM Fiction WHERE Title='" +Fiction.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_Fiction_ID  = rs.getString("Fiction_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserFiction (Fiction_ID,U_ID,OnStatus) VALUES  ('" +Super_Fiction_ID+ "','" +DB_Connection.super_User_ID + "','Plan To Watch')";
            statement.executeUpdate(n);
        }

    }



    ObservableList<Read_List_OnStatus> getAllFictionOnStatus() throws SQLException {

        ObservableList<Read_List_OnStatus> OnStatusUserMoive = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Publish_Year,Rating,Author,OnStatus FROM Fiction INNER JOIN UserFiction ON Fiction.Fiction_ID = UserFiction.Fiction_ID  WHERE U_ID IN (SELECT U_ID FROM UserFiction WHERE U_ID = '" +DB_Connection.super_User_ID + "' )";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String Author = rs5.getString("Author");
            String OnStatus = rs5.getString("OnStatus");

            Read_List_OnStatus UserFiction = new Read_List_OnStatus(Title,Publish_Year, Genre,Rating,Author,OnStatus);

            OnStatusUserMoive.add(UserFiction);

        }

        return OnStatusUserMoive;

    }



    void deleteOnStatusUserFiction(ObservableList<Read_List_OnStatus> selectedFiction) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Read_List_OnStatus Fiction : selectedFiction) {
            String query = "SELECT Fiction_ID FROM Fiction WHERE Title='" +Fiction.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_Fiction_ID  = rs.getString("Fiction_ID");


            }
            String query1 = "DELETE FROM UserFiction WHERE Fiction_ID='" +Super_Fiction_ID + "'";
            statement.executeUpdate(query1);

        }

    }




    ObservableList<Read_List_OnStatus> searchOnStatusFiction(String type, String value) throws SQLException {
        ObservableList<Read_List_OnStatus> FictionSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();

        String n1 = "SELECT  Title,Genre,Publish_Year,Rating,Author,OnStatus  FROM Fiction INNER JOIN UserFiction ON Fiction.Fiction_ID = UserFiction.Fiction_ID WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Publish_Year = rs5.getString("Publish_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("Rating");
            String Author = rs5.getString("Author");
            String OnStatus = rs5.getString("OnStatus");

            Read_List_OnStatus Fiction = new Read_List_OnStatus(Title,Publish_Year, Genre,Rating,Author,OnStatus);

            FictionSearchlist.add(Fiction);

        }
        return FictionSearchlist;
    }
    ////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////        GET MOVIE VALUE                //////////////////////////////////////////////////////
    //////////////////////////////////       GET VALUE                ////////////////////////////////////////////////////

    void InsertIntoUserMovieOnComplete(ObservableList<Watch_List> selectedMovie) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Movie : selectedMovie) {
            String query = "SELECT M_ID FROM Movies WHERE Title='" +Movie.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_M_ID  = rs.getString("M_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserMovie (M_ID,U_ID,OnStatus) VALUES  ('" +Super_M_ID+ "','" +DB_Connection.super_User_ID + "','Completed')";
            statement.executeUpdate(n);
        }

    }




    void InsertIntoUserMovieOnWatch(ObservableList<Watch_List> selectedMovie) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Movie : selectedMovie) {
            String query = "SELECT M_ID FROM Movies WHERE Title='" +Movie.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_M_ID  = rs.getString("M_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserMovie (M_ID,U_ID,OnStatus) VALUES  ('" +Super_M_ID+ "','" +DB_Connection.super_User_ID + "','Watching')";
            statement.executeUpdate(n);
        }

    }



    void InsertIntoUserMovieOnPlan_to_Watch(ObservableList<Watch_List> selectedMovie) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Movie : selectedMovie) {
            String query = "SELECT M_ID FROM Movies WHERE Title='" +Movie.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_M_ID  = rs.getString("M_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserMovie (M_ID,U_ID,OnStatus) VALUES  ('" +Super_M_ID+ "','" +DB_Connection.super_User_ID + "','Plan To Watch')";
            statement.executeUpdate(n);
        }

    }



    ObservableList<Watch_List_OnStatus> getAllMovieOnStatus() throws SQLException {

        ObservableList<Watch_List_OnStatus> OnStatusUserMoive = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Movies INNER JOIN UserMovie ON Movies.M_ID = UserMovie.M_ID  WHERE U_ID IN (SELECT U_ID FROM UserMovie WHERE U_ID = '" +DB_Connection.super_User_ID + "' )";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus UserMovie = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            OnStatusUserMoive.add(UserMovie);

        }

        return OnStatusUserMoive;

    }



    void deleteOnStatusUserMovie(ObservableList<Watch_List_OnStatus> selectedMovie) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List_OnStatus Movie : selectedMovie) {
            String query = "SELECT M_ID FROM Movies WHERE Title='" +Movie.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_M_ID  = rs.getString("M_ID");


            }
            String query1 = "DELETE FROM UserMovie WHERE M_ID='" +Super_M_ID + "'";
            statement.executeUpdate(query1);

        }

    }



////////////////////////////////////////////////////////////////////////////////////////
///copy Start/


    ObservableList<Watch_List_OnStatus> searchOnStatusMovie(String type, String value) throws SQLException {
        ObservableList<Watch_List_OnStatus> MovieSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();

        String n1 = "SELECT  Title,Genre,Release_Year,IMDb_Rating,OnStatus  FROM Movies INNER JOIN UserMovie ON Movies.M_ID = UserMovie.M_ID WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Movie = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            MovieSearchlist.add(Movie);

        }
        return MovieSearchlist;
    }





    ////////////////////////////////////////////////////////////////////////////////////////
    ObservableList<Watch_List_OnStatus> getAllMovieGroupByCompleted() throws SQLException {

        ObservableList<Watch_List_OnStatus> Movielist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Movies INNER JOIN UserMovie ON Movies.M_ID = UserMovie.M_ID Where OnStatus = 'Completed'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Movie = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);
            Movielist.add(Movie);

        }

        return Movielist;

    }



    ObservableList<Watch_List_OnStatus> getAllMovieGroupByWatching() throws SQLException {

        ObservableList<Watch_List_OnStatus> Movielist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Movies INNER JOIN UserMovie ON Movies.M_ID = UserMovie.M_ID Where OnStatus = 'Watching'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            // Author
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Movie = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            Movielist.add(Movie);

        }

        return Movielist;

    }




    ObservableList<Watch_List_OnStatus> getAllMovieGroupByPlan_To_Watch() throws SQLException {

        ObservableList<Watch_List_OnStatus> Movielist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Movies INNER JOIN UserMovie ON Movies.M_ID = UserMovie.M_ID Where OnStatus = 'Plan To Watch'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Movie = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            Movielist.add(Movie);

        }

        return Movielist;

    }
///copy End/
    //////////////////////////////////////////////////////////////////
void InsertIntoUserAnimeOnComplete(ObservableList<Watch_List> selectedAnime) throws SQLException {

    Connection conn = getConnection();
    Statement statement = conn.createStatement();
    for (Watch_List Anime : selectedAnime) {
        String query = "SELECT A_ID FROM Anime WHERE Title='" +Anime.getTitle() + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Super_A_ID  = rs.getString("A_ID");
        }
        String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            super_User_ID  = rs1.getString("U_ID");
        }

        String n="INSERT INTO UserAnime (A_ID,U_ID,OnStatus) VALUES  ('" +Super_A_ID+ "','" +DB_Connection.super_User_ID + "','Completed')";
        statement.executeUpdate(n);
    }

}




    void InsertIntoUserAnimeOnWatch(ObservableList<Watch_List> selectedAnime) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Anime : selectedAnime) {
            String query = "SELECT A_ID FROM Anime WHERE Title='" +Anime.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_A_ID  = rs.getString("A_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserAnime (A_ID,U_ID,OnStatus) VALUES  ('" +Super_A_ID+ "','" +DB_Connection.super_User_ID + "','Watching')";
            statement.executeUpdate(n);
        }

    }



    void InsertIntoUserAnimeOnPlan_to_Watch(ObservableList<Watch_List> selectedAnime) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Anime : selectedAnime) {
            String query = "SELECT A_ID FROM Anime WHERE Title='" +Anime.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_A_ID  = rs.getString("A_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserAnime (A_ID,U_ID,OnStatus) VALUES  ('" +Super_A_ID+ "','" +DB_Connection.super_User_ID + "','Plan To Watch')";
            statement.executeUpdate(n);
        }

    }



    ObservableList<Watch_List_OnStatus> getAllAnimeOnStatus() throws SQLException {

        ObservableList<Watch_List_OnStatus> OnStatusUserMoive = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Anime INNER JOIN UserAnime ON Anime.A_ID = UserAnime.A_ID  WHERE U_ID IN (SELECT U_ID FROM UserAnime WHERE U_ID = '" +DB_Connection.super_User_ID + "' )";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus UserAnime = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            OnStatusUserMoive.add(UserAnime);

        }

        return OnStatusUserMoive;

    }



    void deleteOnStatusUserAnime(ObservableList<Watch_List_OnStatus> selectedAnime) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List_OnStatus Anime : selectedAnime) {
            String query = "SELECT A_ID FROM Anime WHERE Title='" +Anime.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_A_ID  = rs.getString("A_ID");


            }
            String query1 = "DELETE FROM UserAnime WHERE A_ID='" +Super_A_ID + "'";
            statement.executeUpdate(query1);

        }

    }




    ObservableList<Watch_List_OnStatus> searchOnStatusAnime(String type, String value) throws SQLException {
        ObservableList<Watch_List_OnStatus> AnimeSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();

        String n1 = "SELECT  Title,Genre,Release_Year,IMDb_Rating,OnStatus  FROM Anime INNER JOIN UserAnime ON Anime.A_ID = UserAnime.A_ID WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Anime = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            AnimeSearchlist.add(Anime);

        }
        return AnimeSearchlist;
    }

    ObservableList<Watch_List_OnStatus> getAllAnimeGroupByCompleted() throws SQLException {

        ObservableList<Watch_List_OnStatus> Animelist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Anime INNER JOIN UserAnime ON Anime.A_ID = UserAnime.A_ID Where OnStatus = 'Completed'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Anime = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);
            Animelist.add(Anime);

        }

        return Animelist;

    }



    ObservableList<Watch_List_OnStatus> getAllAnimeGroupByWatching() throws SQLException {

        ObservableList<Watch_List_OnStatus> Animelist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Anime INNER JOIN UserAnime ON Anime.A_ID = UserAnime.A_ID Where OnStatus = 'Watching'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            // Author
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Anime = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            Animelist.add(Anime);

        }

        return Animelist;

    }




    ObservableList<Watch_List_OnStatus> getAllAnimeGroupByPlan_To_Watch() throws SQLException {

        ObservableList<Watch_List_OnStatus> Animelist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Anime INNER JOIN UserAnime ON Anime.A_ID = UserAnime.A_ID Where OnStatus = 'Plan To Watch'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Anime = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            Animelist.add(Anime);

        }

        return Animelist;

    }
//////////////////////////////////////////////////////////////////////////////
void InsertIntoUserDramaOnComplete(ObservableList<Watch_List> selectedDrama) throws SQLException {

    Connection conn = getConnection();
    Statement statement = conn.createStatement();
    for (Watch_List Drama : selectedDrama) {
        String query = "SELECT D_ID FROM Drama WHERE Title='" +Drama.getTitle() + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Super_D_ID  = rs.getString("D_ID");
        }
        String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            super_User_ID  = rs1.getString("U_ID");
        }

        String n="INSERT INTO UserDrama (D_ID,U_ID,OnStatus) VALUES  ('" +Super_D_ID+ "','" +DB_Connection.super_User_ID + "','Completed')";
        statement.executeUpdate(n);
    }

}




    void InsertIntoUserDramaOnWatch(ObservableList<Watch_List> selectedDrama) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Drama : selectedDrama) {
            String query = "SELECT D_ID FROM Drama WHERE Title='" +Drama.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_D_ID  = rs.getString("D_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserDrama (D_ID,U_ID,OnStatus) VALUES  ('" +Super_D_ID+ "','" +DB_Connection.super_User_ID + "','Watching')";
            statement.executeUpdate(n);
        }

    }



    void InsertIntoUserDramaOnPlan_to_Watch(ObservableList<Watch_List> selectedDrama) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Drama : selectedDrama) {
            String query = "SELECT D_ID FROM Drama WHERE Title='" +Drama.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_D_ID  = rs.getString("D_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserDrama (D_ID,U_ID,OnStatus) VALUES  ('" +Super_D_ID+ "','" +DB_Connection.super_User_ID + "','Plan To Watch')";
            statement.executeUpdate(n);
        }

    }



    ObservableList<Watch_List_OnStatus> getAllDramaOnStatus() throws SQLException {

        ObservableList<Watch_List_OnStatus> OnStatusUserMoive = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Drama INNER JOIN UserDrama ON Drama.D_ID = UserDrama.D_ID  WHERE U_ID IN (SELECT U_ID FROM UserDrama WHERE U_ID = '" +DB_Connection.super_User_ID + "' )";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus UserDrama = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            OnStatusUserMoive.add(UserDrama);

        }

        return OnStatusUserMoive;

    }



    void deleteOnStatusUserDrama(ObservableList<Watch_List_OnStatus> selectedDrama) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List_OnStatus Drama : selectedDrama) {
            String query = "SELECT D_ID FROM Drama WHERE Title='" +Drama.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_D_ID  = rs.getString("D_ID");


            }
            String query1 = "DELETE FROM UserDrama WHERE D_ID='" +Super_D_ID + "'";
            statement.executeUpdate(query1);

        }

    }




    ObservableList<Watch_List_OnStatus> searchOnStatusDrama(String type, String value) throws SQLException {
        ObservableList<Watch_List_OnStatus> DramaSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();

        String n1 = "SELECT  Title,Genre,Release_Year,IMDb_Rating,OnStatus  FROM Drama INNER JOIN UserDrama ON Drama.D_ID = UserDrama.D_ID WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Drama = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            DramaSearchlist.add(Drama);

        }
        return DramaSearchlist;
    }

    ObservableList<Watch_List_OnStatus> getAllDramaGroupByCompleted() throws SQLException {

        ObservableList<Watch_List_OnStatus> Dramalist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Drama INNER JOIN UserDrama ON Drama.D_ID = UserDrama.D_ID Where OnStatus = 'Completed'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Drama = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);
            Dramalist.add(Drama);

        }

        return Dramalist;

    }



    ObservableList<Watch_List_OnStatus> getAllDramaGroupByWatching() throws SQLException {

        ObservableList<Watch_List_OnStatus> Dramalist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Drama INNER JOIN UserDrama ON Drama.D_ID = UserDrama.D_ID Where OnStatus = 'Watching'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            // Author
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Drama = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            Dramalist.add(Drama);

        }

        return Dramalist;

    }




    ObservableList<Watch_List_OnStatus> getAllDramaGroupByPlan_To_Watch() throws SQLException {

        ObservableList<Watch_List_OnStatus> Dramalist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Drama INNER JOIN UserDrama ON Drama.D_ID = UserDrama.D_ID Where OnStatus = 'Plan To Watch'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Drama = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            Dramalist.add(Drama);

        }

        return Dramalist;

    }
//////////////////////////////////////////////////////////////////////////
void InsertIntoUserTv_SeriesOnComplete(ObservableList<Watch_List> selectedTv_Series) throws SQLException {

    Connection conn = getConnection();
    Statement statement = conn.createStatement();
    for (Watch_List Tv_Series : selectedTv_Series) {
        String query = "SELECT TS_ID FROM Tv_Series WHERE Title='" +Tv_Series.getTitle() + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Super_TS_ID  = rs.getString("TS_ID");
        }
        String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            super_User_ID  = rs1.getString("U_ID");
        }

        String n="INSERT INTO UserTv_Series (TS_ID,U_ID,OnStatus) VALUES  ('" +Super_TS_ID+ "','" +DB_Connection.super_User_ID + "','Completed')";
        statement.executeUpdate(n);
    }

}




    void InsertIntoUserTv_SeriesOnWatch(ObservableList<Watch_List> selectedTv_Series) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Tv_Series : selectedTv_Series) {
            String query = "SELECT TS_ID FROM Tv_Series WHERE Title='" +Tv_Series.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_TS_ID  = rs.getString("TS_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserTv_Series (TS_ID,U_ID,OnStatus) VALUES  ('" +Super_TS_ID+ "','" +DB_Connection.super_User_ID + "','Watching')";
            statement.executeUpdate(n);
        }

    }



    void InsertIntoUserTv_SeriesOnPlan_to_Watch(ObservableList<Watch_List> selectedTv_Series) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List Tv_Series : selectedTv_Series) {
            String query = "SELECT TS_ID FROM Tv_Series WHERE Title='" +Tv_Series.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_TS_ID  = rs.getString("TS_ID");
            }
            String query1 = "SELECT U_ID FROM Users WHERE User_Name='" +LoginController.super_name + "'";
            ResultSet rs1 = statement.executeQuery(query1);
            while (rs1.next()) {
                super_User_ID  = rs1.getString("U_ID");
            }

            String n="INSERT INTO UserTv_Series (TS_ID,U_ID,OnStatus) VALUES  ('" +Super_TS_ID+ "','" +DB_Connection.super_User_ID + "','Plan To Watch')";
            statement.executeUpdate(n);
        }

    }



    ObservableList<Watch_List_OnStatus> getAllTv_SeriesOnStatus() throws SQLException {

        ObservableList<Watch_List_OnStatus> OnStatusUserMoive = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Tv_Series INNER JOIN UserTv_Series ON Tv_Series.TS_ID = UserTv_Series.TS_ID  WHERE U_ID IN (SELECT U_ID FROM UserTv_Series WHERE U_ID = '" +DB_Connection.super_User_ID + "' )";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus UserTv_Series = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            OnStatusUserMoive.add(UserTv_Series);

        }

        return OnStatusUserMoive;

    }



    void deleteOnStatusUserTv_Series(ObservableList<Watch_List_OnStatus> selectedTv_Series) throws SQLException {

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        for (Watch_List_OnStatus Tv_Series : selectedTv_Series) {
            String query = "SELECT TS_ID FROM Tv_Series WHERE Title='" +Tv_Series.getTitle() + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Super_TS_ID  = rs.getString("TS_ID");


            }
            String query1 = "DELETE FROM UserTv_Series WHERE TS_ID='" +Super_TS_ID + "'";
            statement.executeUpdate(query1);

        }

    }




    ObservableList<Watch_List_OnStatus> searchOnStatusTv_Series(String type, String value) throws SQLException {
        ObservableList<Watch_List_OnStatus> Tv_SeriesSearchlist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        Statement statement = (Statement) conn.createStatement();

        String n1 = "SELECT  Title,Genre,Release_Year,IMDb_Rating,OnStatus  FROM Tv_Series INNER JOIN UserTv_Series ON Tv_Series.TS_ID = UserTv_Series.TS_ID WHERE " + type + "='" + value + "'";
        ResultSet rs5 = statement.executeQuery(n1);
        while (rs5.next()) {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Tv_Series = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            Tv_SeriesSearchlist.add(Tv_Series);

        }
        return Tv_SeriesSearchlist;
    }

    ObservableList<Watch_List_OnStatus> getAllTv_SeriesGroupByCompleted() throws SQLException {

        ObservableList<Watch_List_OnStatus> Tv_Serieslist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Tv_Series INNER JOIN UserTv_Series ON Tv_Series.TS_ID = UserTv_Series.TS_ID Where OnStatus = 'Completed'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Tv_Series = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);
            Tv_Serieslist.add(Tv_Series);

        }

        return Tv_Serieslist;

    }



    ObservableList<Watch_List_OnStatus> getAllTv_SeriesGroupByWatching() throws SQLException {

        ObservableList<Watch_List_OnStatus> Tv_Serieslist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Tv_Series INNER JOIN UserTv_Series ON Tv_Series.TS_ID = UserTv_Series.TS_ID Where OnStatus = 'Watching'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            // Author
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Tv_Series = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            Tv_Serieslist.add(Tv_Series);

        }

        return Tv_Serieslist;

    }




    ObservableList<Watch_List_OnStatus> getAllTv_SeriesGroupByPlan_To_Watch() throws SQLException {

        ObservableList<Watch_List_OnStatus> Tv_Serieslist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating,OnStatus FROM Tv_Series INNER JOIN UserTv_Series ON Tv_Series.TS_ID = UserTv_Series.TS_ID Where OnStatus = 'Plan To Watch'";
        ResultSet rs5 = statement.executeQuery(query);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");
            String OnStatus = rs5.getString("OnStatus");

            Watch_List_OnStatus Tv_Series = new Watch_List_OnStatus(Title,Release_Year, Genre,Rating,OnStatus);

            Tv_Serieslist.add(Tv_Series);

        }

        return Tv_Serieslist;

    }
////////////////////////////////////////////////////////////////////////
    ObservableList<Watch_List> getAllMoviesOnMood() throws SQLException {

        ObservableList<Watch_List> Movielist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Genre,Release_Year,IMDb_Rating FROM Movies LEFT JOIN UserMovie ON Movies.M_ID = UserMovie.M_ID  WHERE OnStatus IS NULL";
        ResultSet rs = statement.executeQuery(query);

        while(rs.next())
        {
            String Title = rs.getString("Title");
            String Release_Year = rs.getString("Release_Year");
            String Genre = rs.getString("Genre");
            String IMDb_Rating = rs.getString("IMDb_Rating");

            Watch_List movies = new Watch_List(Title,Release_Year, Genre,IMDb_Rating);

            Movielist.add(movies);

        }

        return Movielist;

    }

    ///////////////////////////////////////////////////

    ObservableList<Watch_List> getAllMovieGroupByHappy() throws SQLException {

        ObservableList<Watch_List> Movielist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String query = "SELECT Title,Movies.Genre,Release_Year,IMDb_Rating FROM Movies INNER JOIN User_Mood ON Movies.Genre = User_Mood.Genre Where Mood_Type = 'Happy'";
        ResultSet rs5Movies = statement.executeQuery(query);

        while(rs5Movies.next())
        {
            String Title = rs5Movies.getString("Title");
            String Release_Year = rs5Movies.getString("Release_Year");
            String Genre = rs5Movies.getString("Genre");
            String Rating = rs5Movies.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryAnime = "SELECT Title,Anime.Genre,Release_Year,IMDb_Rating FROM Anime INNER JOIN User_Mood ON Anime.Genre = User_Mood.Genre Where Mood_Type = 'Happy'";
        ResultSet rs51 = statement.executeQuery(queryAnime);

        while(rs51.next())
        {
            String Title = rs51.getString("Title");
            String Release_Year = rs51.getString("Release_Year");
            String Genre = rs51.getString("Genre");
            String Rating = rs51.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryDrama = "SELECT Title,Drama.Genre,Release_Year,IMDb_Rating FROM Drama INNER JOIN User_Mood ON Drama.Genre = User_Mood.Genre Where Mood_Type = 'Happy'";
        ResultSet rs51Drama = statement.executeQuery(queryDrama);

        while(rs51Drama.next())
        {
            String Title = rs51Drama.getString("Title");
            String Release_Year = rs51Drama.getString("Release_Year");
            String Genre = rs51Drama.getString("Genre");
            String Rating = rs51Drama.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryTv_Series = "SELECT Title,Tv_Series.Genre,Release_Year,IMDb_Rating FROM Tv_Series INNER JOIN User_Mood ON Tv_Series.Genre = User_Mood.Genre Where Mood_Type = 'Happy'";
        ResultSet rs51Tv_Series = statement.executeQuery(queryTv_Series);

        while(rs51Tv_Series.next())
        {
            String Title = rs51Tv_Series.getString("Title");
            String Release_Year = rs51Tv_Series.getString("Release_Year");
            String Genre = rs51Tv_Series.getString("Genre");
            String Rating = rs51Tv_Series.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryComic = "SELECT Title,Comic.Genre,Publish_Year,Rating FROM Comic INNER JOIN User_Mood ON Comic.Genre = User_Mood.Genre Where Mood_Type = 'Happy'";
        ResultSet rs51Comic = statement.executeQuery(queryComic);

        while(rs51Comic.next())
        {
            String Title = rs51Comic.getString("Title");
            String Release_Year = rs51Comic.getString("Publish_Year");
            String Genre = rs51Comic.getString("Genre");
            String Rating = rs51Comic.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryFiction = "SELECT Title,Fiction.Genre,Publish_Year,Rating FROM Fiction INNER JOIN User_Mood ON Fiction.Genre = User_Mood.Genre Where Mood_Type = 'Happy'";
        ResultSet rs51Fiction = statement.executeQuery(queryFiction);

        while(rs51Fiction.next())
        {
            String Title = rs51Fiction.getString("Title");
            String Release_Year = rs51Fiction.getString("Publish_Year");
            String Genre = rs51Fiction.getString("Genre");
            String Rating = rs51Fiction.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryManga = "SELECT Title,Manga.Genre,Publish_Year,Rating FROM Manga INNER JOIN User_Mood ON Manga.Genre = User_Mood.Genre Where Mood_Type = 'Happy'";
        ResultSet rs51Manga = statement.executeQuery(queryManga);

        while(rs51Manga.next())
        {
            String Title = rs51Manga.getString("Title");
            String Release_Year = rs51Manga.getString("Publish_Year");
            String Genre = rs51Manga.getString("Genre");
            String Rating = rs51Manga.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }


        String queryNonfiction = "SELECT Title,Nonfiction.Genre,Publish_Year,Rating FROM Nonfiction INNER JOIN User_Mood ON Nonfiction.Genre = User_Mood.Genre Where Mood_Type = 'Happy'";
        ResultSet rs51Nonfiction = statement.executeQuery(queryNonfiction);

        while(rs51Nonfiction.next())
        {
            String Title = rs51Nonfiction.getString("Title");
            String Release_Year = rs51Nonfiction.getString("Publish_Year");
            String Genre = rs51Nonfiction.getString("Genre");
            String Rating = rs51Nonfiction.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        return Movielist;

    }



    ObservableList<Watch_List> getAllMovieGroupByDepressed() throws SQLException {

        ObservableList<Watch_List> Movielist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();
        String queryMovies = "SELECT Title,Movies.Genre,Release_Year,IMDb_Rating FROM Movies INNER JOIN User_Mood ON Movies.Genre = User_Mood.Genre Where Mood_Type = 'Depressed'";
        ResultSet rs5 = statement.executeQuery(queryMovies);

        while(rs5.next())
        {
            String Title = rs5.getString("Title");
            String Release_Year = rs5.getString("Release_Year");
            String Genre = rs5.getString("Genre");
            String Rating = rs5.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryAnime = "SELECT Title,Anime.Genre,Release_Year,IMDb_Rating FROM Anime INNER JOIN User_Mood ON Anime.Genre = User_Mood.Genre Where Mood_Type = 'Depressed'";
        ResultSet rs51 = statement.executeQuery(queryAnime);

        while(rs51.next())
        {
            String Title = rs51.getString("Title");
            String Release_Year = rs51.getString("Release_Year");
            String Genre = rs51.getString("Genre");
            String Rating = rs51.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryDrama = "SELECT Title,Drama.Genre,Release_Year,IMDb_Rating FROM Drama INNER JOIN User_Mood ON Drama.Genre = User_Mood.Genre Where Mood_Type = 'Depressed'";
        ResultSet rs51Drama = statement.executeQuery(queryDrama);

        while(rs51Drama.next())
        {
            String Title = rs51Drama.getString("Title");
            String Release_Year = rs51Drama.getString("Release_Year");
            String Genre = rs51Drama.getString("Genre");
            String Rating = rs51Drama.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryTv_Series = "SELECT Title,Tv_Series.Genre,Release_Year,IMDb_Rating FROM Tv_Series INNER JOIN User_Mood ON Tv_Series.Genre = User_Mood.Genre Where Mood_Type = 'Depressed'";
        ResultSet rs51Tv_Series = statement.executeQuery(queryTv_Series);

        while(rs51Tv_Series.next())
        {
            String Title = rs51Tv_Series.getString("Title");
            String Release_Year = rs51Tv_Series.getString("Release_Year");
            String Genre = rs51Tv_Series.getString("Genre");
            String Rating = rs51Tv_Series.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryComic = "SELECT Title,Comic.Genre,Publish_Year,Rating FROM Comic INNER JOIN User_Mood ON Comic.Genre = User_Mood.Genre Where Mood_Type = 'Depressed'";
        ResultSet rs51Comic = statement.executeQuery(queryComic);

        while(rs51Comic.next())
        {
            String Title = rs51Comic.getString("Title");
            String Release_Year = rs51Comic.getString("Publish_Year");
            String Genre = rs51Comic.getString("Genre");
            String Rating = rs51Comic.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryFiction = "SELECT Title,Fiction.Genre,Publish_Year,Rating FROM Fiction INNER JOIN User_Mood ON Fiction.Genre = User_Mood.Genre Where Mood_Type = 'Depressed'";
        ResultSet rs51Fiction = statement.executeQuery(queryFiction);

        while(rs51Fiction.next())
        {
            String Title = rs51Fiction.getString("Title");
            String Release_Year = rs51Fiction.getString("Publish_Year");
            String Genre = rs51Fiction.getString("Genre");
            String Rating = rs51Fiction.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryManga = "SELECT Title,Manga.Genre,Publish_Year,Rating FROM Manga INNER JOIN User_Mood ON Manga.Genre = User_Mood.Genre Where Mood_Type = 'Depressed'";
        ResultSet rs51Manga = statement.executeQuery(queryManga);

        while(rs51Manga.next())
        {
            String Title = rs51Manga.getString("Title");
            String Release_Year = rs51Manga.getString("Publish_Year");
            String Genre = rs51Manga.getString("Genre");
            String Rating = rs51Manga.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }


        String queryNonfiction = "SELECT Title,Nonfiction.Genre,Publish_Year,Rating FROM Nonfiction INNER JOIN User_Mood ON Nonfiction.Genre = User_Mood.Genre Where Mood_Type = 'Depressed'";
        ResultSet rs51Nonfiction = statement.executeQuery(queryNonfiction);

        while(rs51Nonfiction.next())
        {
            String Title = rs51Nonfiction.getString("Title");
            String Release_Year = rs51Nonfiction.getString("Publish_Year");
            String Genre = rs51Nonfiction.getString("Genre");
            String Rating = rs51Nonfiction.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        return Movielist;

    }




    ObservableList<Watch_List> getAllMovieGroupByAngry() throws SQLException {

        ObservableList<Watch_List> Movielist = FXCollections.observableArrayList();

        Connection conn = getConnection();

        Statement statement = conn.createStatement();

        String queryMovies = "SELECT Title,Movies.Genre,Release_Year,IMDb_Rating FROM Movies INNER JOIN User_Mood ON Movies.Genre = User_Mood.Genre Where Mood_Type = 'Angry'";
        ResultSet rs5Movies = statement.executeQuery(queryMovies);

        while(rs5Movies.next())
        {
            String Title = rs5Movies.getString("Title");
            String Release_Year = rs5Movies.getString("Release_Year");
            String Genre = rs5Movies.getString("Genre");
            String Rating = rs5Movies.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryAnime = "SELECT Title,Anime.Genre,Release_Year,IMDb_Rating FROM Anime INNER JOIN User_Mood ON Anime.Genre = User_Mood.Genre Where Mood_Type = 'Angry'";
        ResultSet rs51 = statement.executeQuery(queryAnime);

        while(rs51.next())
        {
            String Title = rs51.getString("Title");
            String Release_Year = rs51.getString("Release_Year");
            String Genre = rs51.getString("Genre");
            String Rating = rs51.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryDrama = "SELECT Title,Drama.Genre,Release_Year,IMDb_Rating FROM Drama INNER JOIN User_Mood ON Drama.Genre = User_Mood.Genre Where Mood_Type = 'Angry'";
        ResultSet rs51Drama = statement.executeQuery(queryDrama);

        while(rs51Drama.next())
        {
            String Title = rs51Drama.getString("Title");
            String Release_Year = rs51Drama.getString("Release_Year");
            String Genre = rs51Drama.getString("Genre");
            String Rating = rs51Drama.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryTv_Series = "SELECT Title,Tv_Series.Genre,Release_Year,IMDb_Rating FROM Tv_Series INNER JOIN User_Mood ON Tv_Series.Genre = User_Mood.Genre Where Mood_Type = 'Angry'";
        ResultSet rs51Tv_Series = statement.executeQuery(queryTv_Series);

        while(rs51Tv_Series.next())
        {
            String Title = rs51Tv_Series.getString("Title");
            String Release_Year = rs51Tv_Series.getString("Release_Year");
            String Genre = rs51Tv_Series.getString("Genre");
            String Rating = rs51Tv_Series.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryComic = "SELECT Title,Comic.Genre,Publish_Year,Rating FROM Comic INNER JOIN User_Mood ON Comic.Genre = User_Mood.Genre Where Mood_Type = 'Angry'";
        ResultSet rs51Comic = statement.executeQuery(queryComic);

        while(rs51Comic.next())
        {
            String Title = rs51Comic.getString("Title");
            String Release_Year = rs51Comic.getString("Publish_Year");
            String Genre = rs51Comic.getString("Genre");
            String Rating = rs51Comic.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryFiction = "SELECT Title,Fiction.Genre,Publish_Year,Rating FROM Fiction INNER JOIN User_Mood ON Fiction.Genre = User_Mood.Genre Where Mood_Type = 'Angry'";
        ResultSet rs51Fiction = statement.executeQuery(queryFiction);

        while(rs51Fiction.next())
        {
            String Title = rs51Fiction.getString("Title");
            String Release_Year = rs51Fiction.getString("Publish_Year");
            String Genre = rs51Fiction.getString("Genre");
            String Rating = rs51Fiction.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryManga = "SELECT Title,Manga.Genre,Publish_Year,Rating FROM Manga INNER JOIN User_Mood ON Manga.Genre = User_Mood.Genre Where Mood_Type = 'Angry'";
        ResultSet rs51Manga = statement.executeQuery(queryManga);

        while(rs51Manga.next())
        {
            String Title = rs51Manga.getString("Title");
            String Release_Year = rs51Manga.getString("Publish_Year");
            String Genre = rs51Manga.getString("Genre");
            String Rating = rs51Manga.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }


        String queryNonfiction = "SELECT Title,Nonfiction.Genre,Publish_Year,Rating FROM Nonfiction INNER JOIN User_Mood ON Nonfiction.Genre = User_Mood.Genre Where Mood_Type = 'Angry'";
        ResultSet rs51Nonfiction = statement.executeQuery(queryNonfiction);

        while(rs51Nonfiction.next())
        {
            String Title = rs51Nonfiction.getString("Title");
            String Release_Year = rs51Nonfiction.getString("Publish_Year");
            String Genre = rs51Nonfiction.getString("Genre");
            String Rating = rs51Nonfiction.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }


        return Movielist;

    }

    ObservableList<Watch_List> getAllMovieGroupBySad() throws SQLException {

    ObservableList<Watch_List> Movielist = FXCollections.observableArrayList();

    Connection conn = getConnection();

    Statement statement = conn.createStatement();
    String query = "SELECT Title,Movies.Genre,Release_Year,IMDb_Rating FROM Movies INNER JOIN User_Mood ON Movies.Genre = User_Mood.Genre Where Mood_Type = 'Sad'";
    ResultSet rs5Movies = statement.executeQuery(query);


        while(rs5Movies.next())
        {
            String Title = rs5Movies.getString("Title");
            String Release_Year = rs5Movies.getString("Release_Year");
            String Genre = rs5Movies.getString("Genre");
            String Rating = rs5Movies.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryAnime = "SELECT Title,Anime.Genre,Release_Year,IMDb_Rating FROM Anime INNER JOIN User_Mood ON Anime.Genre = User_Mood.Genre Where Mood_Type = 'Sad'";
        ResultSet rs51 = statement.executeQuery(queryAnime);

        while(rs51.next())
        {
            String Title = rs51.getString("Title");
            String Release_Year = rs51.getString("Release_Year");
            String Genre = rs51.getString("Genre");
            String Rating = rs51.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryDrama = "SELECT Title,Drama.Genre,Release_Year,IMDb_Rating FROM Drama INNER JOIN User_Mood ON Drama.Genre = User_Mood.Genre Where Mood_Type = 'Sad'";
        ResultSet rs51Drama = statement.executeQuery(queryDrama);

        while(rs51Drama.next())
        {
            String Title = rs51Drama.getString("Title");
            String Release_Year = rs51Drama.getString("Release_Year");
            String Genre = rs51Drama.getString("Genre");
            String Rating = rs51Drama.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryTv_Series = "SELECT Title,Tv_Series.Genre,Release_Year,IMDb_Rating FROM Tv_Series INNER JOIN User_Mood ON Tv_Series.Genre = User_Mood.Genre Where Mood_Type = 'Sad'";
        ResultSet rs51Tv_Series = statement.executeQuery(queryTv_Series);

        while(rs51Tv_Series.next())
        {
            String Title = rs51Tv_Series.getString("Title");
            String Release_Year = rs51Tv_Series.getString("Release_Year");
            String Genre = rs51Tv_Series.getString("Genre");
            String Rating = rs51Tv_Series.getString("IMDb_Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryComic = "SELECT Title,Comic.Genre,Publish_Year,Rating FROM Comic INNER JOIN User_Mood ON Comic.Genre = User_Mood.Genre Where Mood_Type = 'Sad'";
        ResultSet rs51Comic = statement.executeQuery(queryComic);

        while(rs51Comic.next())
        {
            String Title = rs51Comic.getString("Title");
            String Release_Year = rs51Comic.getString("Publish_Year");
            String Genre = rs51Comic.getString("Genre");
            String Rating = rs51Comic.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryFiction = "SELECT Title,Fiction.Genre,Publish_Year,Rating FROM Fiction INNER JOIN User_Mood ON Fiction.Genre = User_Mood.Genre Where Mood_Type = 'Sad'";
        ResultSet rs51Fiction = statement.executeQuery(queryFiction);

        while(rs51Fiction.next())
        {
            String Title = rs51Fiction.getString("Title");
            String Release_Year = rs51Fiction.getString("Publish_Year");
            String Genre = rs51Fiction.getString("Genre");
            String Rating = rs51Fiction.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        String queryManga = "SELECT Title,Manga.Genre,Publish_Year,Rating FROM Manga INNER JOIN User_Mood ON Manga.Genre = User_Mood.Genre Where Mood_Type = 'Sad'";
        ResultSet rs51Manga = statement.executeQuery(queryManga);

        while(rs51Manga.next())
        {
            String Title = rs51Manga.getString("Title");
            String Release_Year = rs51Manga.getString("Publish_Year");
            String Genre = rs51Manga.getString("Genre");
            String Rating = rs51Manga.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }


        String queryNonfiction = "SELECT Title,Nonfiction.Genre,Publish_Year,Rating FROM Nonfiction INNER JOIN User_Mood ON Nonfiction.Genre = User_Mood.Genre Where Mood_Type = 'Sad'";
        ResultSet rs51Nonfiction = statement.executeQuery(queryNonfiction);

        while(rs51Nonfiction.next())
        {
            String Title = rs51Nonfiction.getString("Title");
            String Release_Year = rs51Nonfiction.getString("Publish_Year");
            String Genre = rs51Nonfiction.getString("Genre");
            String Rating = rs51Nonfiction.getString("Rating");


            Watch_List Movie = new Watch_List(Title,Release_Year, Genre,Rating);
            Movielist.add(Movie);

        }

        return Movielist;

}

}



