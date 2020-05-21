package FootballLeagueScoringSystem.Module;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @param : FootballPlayers,FootballTeams,Battles,...
 * @author QuanHao
 * 联赛模型，用于主视图，球队排名界面
 */
public class League {

    Player[] players;
    Team[] teams;
    Battle[] battles;
    Battle[] todayBattles;
    Battle[] oneDayBattles;
    private String userStatus;

    public League() {
        players = new Player[800];
        teams = new Team[80];
        battles = new Battle[800];
        todayBattles = new Battle[50];
    }

    public void playerSort() {
        /**
         * @param :
         * @author :long
         * 将player根据score进行排序，并写入playerrank
         */
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String sql = "select * from footballplayer";
            ResultSet rs = statement.executeQuery(sql);
            String playerName = null;
            String teamName = null;
            int i = 0;
            while (rs.next()) {
                playerName = rs.getString("playerName");
                teamName = rs.getString("playerteamName");
                this.players[i] = new Player(teamName, playerName);
                i++;
            }
            Player[] truePlayers = new Player[i];        //获取当前player数，防止sort数组值为null
            for (i = 0; players[i] != null; i++) truePlayers[i] = players[i];
            Arrays.sort(truePlayers);
            for (i = 0; i < truePlayers.length; i++) {
                sql = "update footballplayer set playerRank=" + (i + 1) + " where " + "playerName='" + truePlayers[i].getName() + "'";
                statement.executeUpdate(sql);
            }
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Player[] getPlayers() {

        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String sql = "select * from footballplayer ORDER BY playerscore DESC";
            ResultSet rs = statement.executeQuery(sql);
            String playerName = null;
            String teamName = null;
            int i = 0;
            while (rs.next()) {
                playerName = rs.getString("playerName");
                teamName = rs.getString("playerteamName");
                this.players[i] = new Player(teamName, playerName);
                i++;
            }
            for (; i < this.players.length; i++) {
                this.players[i] = null;
            }
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return this.players;
    }

    public void teamSort(String groupName) {
        /**
         * @param :
         * @author : long
         *对teamscore进行排序，将排序好的teamrank写入数据库
         */
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String sql = "select teamName,teamScore from footballteam where teamgroup='" + groupName + "'";
            ResultSet rs = statement.executeQuery(sql);
            String teamName = null;
            int i = 0;
            while (rs.next()) {
                teamName = rs.getString("teamName");
                this.teams[i] = new Team(teamName);
                i++;
            }
            Team[] trueTeams = new Team[i];       //防止出现空team导致排序失败
            for (i = 0; teams[i] != null; i++) trueTeams[i] = teams[i];
            Arrays.sort(trueTeams);
            for (i = 0; i < trueTeams.length; i++) {
                sql = "update footballteam set teamRank=" + (i + 1) + " where " + "teamName='" + trueTeams[i].getTeamName() + "'";
                statement.executeUpdate(sql);
            }
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTeams() {
        /**
         * 获取全部队伍的队名列表
         * */
        List<String> teamNames = new ArrayList<>();
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String sql = "select teamname from footballteam ";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                teamNames.add(rs.getString("teamName"));
            }
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return teamNames;
    }

    public List<String> getPlayersName(String teamName) {
        /**
         * 获取一只队伍的所有球员名字列表
         * */
        List<String> playerNames = new ArrayList<>();
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String sql = "select playername from footballplayer where playerteamname = '" + teamName + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                playerNames.add(rs.getString("playername"));
            }
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return playerNames;
    }

    public Team[] getTeams(String groupName) {
        /**
         * 获取特定组别的排名信息
         * */
        Team[] teams = new Team[20];
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

            Statement statement = conn.createStatement();
            String sql = "select * from footballteam where teamgroup='" + groupName + "' Order BY teamscore DESC ";
            ResultSet rs = statement.executeQuery(sql);
            String teamName = null;
            int i = 0;
            while (rs.next()) {
                teamName = rs.getString("teamName");
                teams[i] = new Team(teamName);
                i++;
            }
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    /**
     * @author: long
     * 查询当天赛程
     */
    public Battle[] getTodayBattle() {
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            java.util.Date todaydate = new java.util.Date();    //今天的日期
            SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd");
            String date = ft2.format(todaydate);
            String sql = "select * from battledetail where DATE_FORMAT(battleTime, '%Y-%m-%d') = DATE_FORMAT('" + date + "', '%Y-%m-%d')";
            ResultSet rs = statement.executeQuery(sql);
            Timestamp battleTime;  //对战时间
            String teamA = null;
            String teamB = null;
            String battleSide = null;      //比赛场地
            int battleResult = 0;    //比赛结果，1表示A胜，0表示平局，-1表示A负,-2表示未开始
            String battleScore = null;     //比赛比分
            int i = 0;
            while (rs.next()) {
                battleTime = rs.getTimestamp("battleTime");
                teamA = rs.getString("teamOne");
                teamB = rs.getString("teamTwo");
                battleSide = rs.getString("battleSide");
                battleResult = rs.getInt("battleResult");
                battleScore = rs.getString("battleScore");
                this.todayBattles[i] = new Battle(teamA, teamB, battleTime, battleSide, battleResult, battleScore);
                i++;
            }

            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return todayBattles;
    }

    public Battle[] getNoStartBattle() {
        /**
         * 返回未开始比赛的赛程信息
         * */
        Battle[] noStartBattles = new Battle[100];
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            java.util.Date todaydate = new java.util.Date();    //今天的日期
            SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd");
            String date = ft2.format(todaydate);
            String sql = "select * from battledetail where battleresult = -2";
            ResultSet rs = statement.executeQuery(sql);
            Timestamp battleTime;  //对战时间
            String teamA = null;
            String teamB = null;
            String battleSide = null;      //比赛场地
            int battleResult = 0;    //比赛结果，1表示A胜，0表示平局，-1表示A负,-2表示未开始
            String battleScore = null;     //比赛比分
            int i = 0;
            while (rs.next()) {
                battleTime = rs.getTimestamp("battleTime");
                teamA = rs.getString("teamOne");
                teamB = rs.getString("teamTwo");
                battleSide = rs.getString("battleSide");
                battleResult = rs.getInt("battleResult");
                battleScore = rs.getString("battleScore");
                noStartBattles[i] = new Battle(teamA, teamB, battleTime, battleSide, battleResult, battleScore);
                i++;
            }
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return noStartBattles;
    }

    /**
     * @author :long
     * 查询某天赛程
     */
    public Battle[] getOneDayBattles(String date) {
        oneDayBattles = new Battle[50];
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String sql = "select * from battledetail where DATE_FORMAT(battleTime, '%Y-%m-%d') = DATE_FORMAT('" + date + "', '%Y-%m-%d')";
            ResultSet rs = statement.executeQuery(sql);
            Timestamp battleTime;  //对战时间
            String teamA = null;
            String teamB = null;
            String battleSide = null;      //比赛场地
            int battleResult = 0;    //比赛结果，1表示A胜，0表示平局，-1表示A负,-2表示未开始
            String battleScore = null;     //比赛比分
            int i = 0;
            while (rs.next()) {
                battleTime = rs.getTimestamp("battleTime");
                teamA = rs.getString("teamOne");
                teamB = rs.getString("teamTwo");
                battleSide = rs.getString("battleSide");
                battleResult = rs.getInt("battleResult");
                battleScore = rs.getString("battleScore");
                this.oneDayBattles[i] = new Battle(teamA, teamB, battleTime, battleSide, battleResult, battleScore);
                i++;
            }

            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return oneDayBattles;
    }

    /**
     * 查询所有赛程
     */
    public Battle[] getAllBattles() {
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String sql = "select * from battledetail ORDER BY battletime";
            ResultSet rs = statement.executeQuery(sql);
            Timestamp battleTime;  //对战时间
            String teamA = null;
            String teamB = null;
            String battleSide = null;      //比赛场地
            int battleResult = 0;    //比赛结果，1表示A胜，0表示平局，-1表示A负
            String battleScore = null;     //比赛比分
            int i = 0;
            while (rs.next()) {
                battleTime = rs.getTimestamp("battleTime");
                teamA = rs.getString("teamOne");
                teamB = rs.getString("teamTwo");
                battleSide = rs.getString("battleSide");
                battleResult = rs.getInt("battleResult");
                battleScore = rs.getString("battleScore");
                this.battles[i] = new Battle(teamA, teamB, battleTime, battleSide, battleResult, battleScore);
                i++;
            }
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return this.battles;
    }

    public boolean checkeam(String teamName) {
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        boolean status = false;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String sql = "select * from footballteam where teamname='" + teamName + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                if (rs != null) status = true;
            }
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean addSystemUser(String name, String account, String passwd, String position) {
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String[] s = {"系统管理员", "其他管理员", "主裁判", "副裁判"};
            List<String> strList = new ArrayList<String>();
            strList = Arrays.asList(s);
            if (strList.contains(position)) {
                String sql = "Insert Into systemuser values ('" + name +
                        "','" + account +
                        "','" + passwd +
                        "','" + position +
                        "')";
                statement.executeUpdate(sql);
                conn.close();
                return true;//新用户插入成功
            } else return false;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getCourts() {
        List<String> courts = new ArrayList<>();
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String sql = "SELECT battleside from court ";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                courts.add(rs.getString("battleSide"));
            }
            rs.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return courts;
    }

    public String checkUser(String account, String passwd) {
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=Asia/Shanghai&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        String position = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String sql = "SELECT * from systemuser where Account='" + account +
                    "' and password='" + passwd +
                    "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                if (rs != null) {
                    position = rs.getString("position");
                    return position;
                }
            }
            rs.close();
            conn.close();
            return position;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUserStatus(String position) {
        if (position.equals("系统管理员")) {
            this.userStatus = "root";
        }
        if (position.equals("其他管理员")) {
            this.userStatus = "administrator";
        }
        if (position.equals("主裁判")) {
            this.userStatus = "mainJudge";
        }
        if (position.equals("副裁判")) {
            this.userStatus = "asJudge";
        } else
            this.userStatus = "visitor";
    }

    public boolean addGoalDetail(String playerName, String teamA, String teamB, String time,int battleResult,String battleScore) {
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            java.util.Date date = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp sqlTime = new Timestamp(date.getTime());//LocalDateTime转换成SQL 用的date类型
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            statement.execute("SET FOREIGN_KEY_CHECKS=0");//关闭外键约束检查
            String sql = "INSERT INTO goaldetail(playerName, teamA, teamB, time) values (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, playerName);
            ps.setString(2, teamA);
            ps.setString(3, teamB);
            ps.setTimestamp(4, sqlTime);
            ps.executeUpdate();
            String getGoalsql = "select playerscore from footballplayer where playername='" + playerName +
                    "'";
            ResultSet rs = statement.executeQuery(getGoalsql);
            int i = 0;
            while (rs.next()){
                i = rs.getInt("playerscore");
            }
            String addGoal = "update footballplayer set playerscore = "+(i+1);
            statement.executeUpdate(addGoal);
//            String updateBattle = "update battledetail set battleresult = "+battleResult+",battlescore='" +battleScore+
//                    "'where teamone='" +teamA+
//                    "' and teamtwo='" +teamB+
//                    "'and battletime="+sqlTime+"";
//            statement.executeUpdate(updateBattle);
            statement.execute("SET FOREIGN_KEY_CHECKS=1");//启动外键约束检查
            conn.close();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addFoul(String[] foulInfo) {
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            String sql = "Update footballplayer set playerfoul ='" + foulInfo[2] + ":" + foulInfo[3] +
                    "' where playername='" + foulInfo[0]
                    + "'" + "and playerteamname='" + foulInfo[1] +
                    "'";
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addBattle(String[] battleInfo) {
        Connection conn;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC&characterEncoding=utf-8";
        String user = "root";
        String password = "123456";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            LocalDateTime localDateTime = LocalDateTime.parse(battleInfo[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            java.util.Date date = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp sqlTime = new Timestamp(date.getTime());//LocalDateTime转换成SQL 用的date类型
            String sql = "INSERT INTO battledetail(teamone, teamtwo, battletime, battleside,battleresult,battlescore) values (?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, battleInfo[0]);
            ps.setString(2, battleInfo[1]);
            ps.setTimestamp(3, sqlTime);
            ps.setString(4, battleInfo[3]);
            ps.setInt(5, -2);
            ps.setString(6, null);
            ps.executeUpdate();
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            conn.close();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
