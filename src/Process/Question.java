
package Process;
import DataBase.Connect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;



public class Question {
public Connect cn;
public static Random random = new Random();
    public Question(){
           cn = new Connect();
    }
     public ResultSet ShowAll() throws SQLException{
        
           //cn.connectSQL();
           String sql = "SELECT *FROM question";        
           return cn.LoadData(sql);
    }
    public ResultSet ShowQuestion() throws SQLException{
        cn.connectSQL();
       String sql="SELECT * FROM question where MaQT=1";
        return cn.LoadData(sql);
    }
    public ResultSet ShowAnswer() throws SQLException{
        cn.connectSQL();
        String sql="SELECT Answer FROM question";
        return cn.LoadData(sql);
    }
    public ResultSet RandomMaQuestion() throws SQLException{
      //  cn.connectSQL();
        int maqt = random.nextInt(15)+1;
        String sql="SELECT * FROM question where MaQT="+maqt;
        System.out.println("-------------------------------");
//        if(cn == null){
//            System.out.println("cn is null");
//        }
        System.out.println(cn.LoadData(sql));
        System.out.println("-------------------------------");

        return cn.LoadData(sql);
    }
    public ResultSet ShowQuestbyRandomMaQuestion(int maqt) throws SQLException{
       // cn.connectSQL();
        String sql="Select * from question where MaQT="+maqt;
        return cn.LoadData(sql);
    }
    
    public static void main(String[] args)throws SQLException{
        
      Question question = new Question();
      ResultSet resultSet =  question.ShowAll();
        while (resultSet.next()) {
           // System.out.println(resultSet.getString(0));
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
            System.out.println(resultSet.getString(4));
            System.out.println(resultSet.getString(5));
            System.out.println(resultSet.getString(6));
            System.out.println(resultSet.getString(7));
            
        }
        Connect connect = new Connect();
        String sql = "SELECT *FROM question";        
           System.out.println(connect.LoadData(sql));
    }
    
    
    /***************************************************************************************/
   /*  public void InsertData(String ques, String a, String b,String c, String d, String da) throws SQLException{   
           String sql = "INSERT INTO question values(N'"+ques+"',N'" + a +"',N'" + b +"',N'"+c+"',N'"+d+"',N'"+da+"')"; 
           cn.UpdateData(sql);
        }
        //Dieu chinh 1 dong du lieu vao table LoaiSP
        public void EditData(int ma,String ques, String a, String b,String c, String d, String da) throws SQLException{   
           String sql = "Update question set Question=N'" + ques +"',AnswerA=N'"+a+"',AnswerB=N'"+b+"'AnswerC=N'"+c+"',AnswerD=N'"+c+"',Answer=N'"+da+"' where MaQT='" + ma +"'";        
           cn.UpdateData(sql);
        }
        //Xoa 1 dong du lieu vao table LoaiSP
        public void DeleteData(int ma) throws SQLException{   
           String sql = "Delete from question where MaQT='" + ma +"'";        
           cn.UpdateData(sql);
        }
        public MediaPlayer loadMusic(String status) {
        try {
            String source = getClass().getResource("/Audio/" + status + ".mp3").toString();
            Media media = null;
            media = new Media(source);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            return mediaPlayer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/
    
}
