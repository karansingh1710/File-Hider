package dao;

import db.GetConnection;
import model.Data;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataDAO {
    public static List<Data> getFiles(String email) throws SQLException {
        Connection connection= GetConnection.getConnection();
        PreparedStatement pre=connection.prepareStatement("select * from Data where email=?");
        pre.setString(1,email);
        ResultSet res=pre.executeQuery();
        // Files
        List<Data> files=new ArrayList<>();
        while (res.next()){
            int id=res.getInt(1);
            String fileName=res.getString(2);
            String path=res.getString(3);
            files.add(new Data(id,fileName,path));
        }

        return files;
    }


    public static int hideFile(Data file) throws SQLException, IOException {
        Connection con=GetConnection.getConnection();
        PreparedStatement pre=con.prepareStatement("INSERT INTO Data values (default,?,?,?,?)");
        pre.setString(1,file.getFileName());
        pre.setString(2,file.getPath());
        pre.setString(3,file.getEmail());
        // Now we have to read the file's data and add it into bin_data
        File f=new File(file.getPath());
        FileReader fr=new FileReader(f);
        pre.setCharacterStream(4,fr,f.length());
        // After reading the whole files data close the reader and delete the file
        int ans=  pre.executeUpdate();
        fr.close();
        f.delete();
        return ans;
    }

    // unhide method
    public static void unHide(int id) throws SQLException, IOException {
        Connection con=GetConnection.getConnection();
        PreparedStatement pre=con.prepareStatement("SELECT path,binData from data where id=?");
        pre.setInt(1,id);

        ResultSet res=pre.executeQuery();
        res.next();
        String path=res.getString("path");
        Clob clob=res.getClob("binData");

        Reader read=clob.getCharacterStream();
        FileWriter fw=new FileWriter(path);
        int i;
        while ((i=read.read())!=-1){
            fw.write((char)i);
        }
        fw.close();

        // now we have to delete the file from database
        pre=con.prepareStatement("delete from Data where id=?");
        pre.setInt(1,id);
        pre.executeUpdate();
        System.out.println("File UnHidden Successfully");
    }
}
