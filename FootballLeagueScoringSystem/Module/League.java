package FootballLeagueScoringSystem.Module;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @param : FootballPlayers,FootballTeams,Battles,...
 * @author QuanHao
 * 联赛模型，用于主视图，球队排名界面
 * 与数据库读写数据的方法全部在这里，提升效率
 */
public class League {

    private List<Player> players;
    private List<Team> teams;
    private List<Battle> battles;
    /**
     * 连接数据库的相关信息
     *
     * @param:driver:驱动;url:数据库名;user:用户名;password:密码;
     */
    private Connection conn;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/football?serverTimezone=UTC&characterEncoding=utf-8";
    private String user = "root";
    private String password = "123456";
    private Statement statement;
    private String userStatus;

    public League() {
        players = new ArrayList<>();
        teams = new ArrayList<>();
        battles = new ArrayList<>();
        try {
            /**
             * 连接数据库，在程序运行期间连接不关闭
             * */
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            statement = conn.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*********************************************
     *              League get                   *
     *                                           *
     ********************************************/
    /********************************************************
     *                                                      *
     * getPlayer(String playerName,String teamName)         *
     * getTeam(String teamName,String teamGroup)            *
     * getPlayers()                                         *
     * getPlayers(String teamName)                          *
     *                                                      *
     *                                                      *
     *                                                      *
     *                                                      *
     ********************************************************/
    public Player getPlayer(String playerName, String teamName) {
        /**
         * @author:QuanHao
         * @param:String playerName, String teamName
         * 返回一个球员对象
         * */
        try {
            String sql = "select * from footballplayer where playerName='" + playerName + "' and playerteamName='" + teamName + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String foul = rs.getString("playerFoul");
                int score = rs.getInt("playerScore");
                int rank = rs.getInt("playerRank");
                String photo_address = rs.getString("playerPhoto");
                return new Player(playerName, teamName, foul, score, rank, photo_address);
            }
            return null;//没有找到对应的球员
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Team getTeam(String teamName) {
        /**
         * @author:QuanHao
         * @param:String teamName, String teamGroup
         * 返回一个球队对象
         * */
        try {
            String sql = "select * from footballteam where teamName='" + teamName + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int teamRank = rs.getInt("teamRank");
                int winNum = rs.getInt("winNum");
                int loseNum = rs.getInt("loseNum");
                int drawNum = rs.getInt("drawNum");
                int goalNum = rs.getInt("goalNum");
                int goalLostNum = rs.getInt("goalLostNum");
                int teamScore = rs.getInt("teamScore");
                String teamGroup = rs.getString ("teamGroup");
                return new Team(teamName, teamRank, winNum, loseNum, drawNum, goalNum, goalLostNum, teamGroup, teamScore);
            }
            return null;//没有找到对应的球队
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Player> getPlayers() {
        /**
         * @author:QuanHao
         * 此函数会返回所有一个含有所有球员的列表，列表将会按照球员进球积分排序
         * 返回根据进球积分排序好的球员列表，用于生成射手榜
         * */
        if (!players.isEmpty()) {
            players.clear();
        }//清空列表
        try {
            String sql = "select * from footballplayer ORDER BY playerscore DESC";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                players.add(new Player(
                        rs.getString("playerName"), rs.getString("playerteamName"),
                        rs.getString("playerfoul"), rs.getInt("playerscore"),
                        rs.getInt("playerrank"), rs.getString("playerPhoto")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public List<Player> getPlayers(String teamName) {
        /**
         * @author:QuanHao
         * 此函数会返回一个所有从属于此球队的所有球员的列表
         * */
        try {
            String sql = "select * from footballplayer where playerteamName='" + teamName +
                    "'";
            ResultSet rs = statement.executeQuery(sql);
            if (!players.isEmpty()) {
                players.clear();
            }//清空列表
            while (rs.next()) {
                players.add(new Player(
                        rs.getString("playerName"), rs.getString("playerteamName"),
                        rs.getString("playerfoul"), rs.getInt("playerscore"),
                        rs.getInt("playerrank"), rs.getString("playerPhoto")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public List<Team> getTeams(String groupName) {
        /**
         * @author:QuanHao
         * 此函数会返回一个所有从属于此组别的所有球队的列表，列表将会按进球积分排序
         * */
        if (!teams.isEmpty()) {
            teams.clear();
        }
        try {
            String sql = "select * from footballteam where teamgroup='" + groupName + "' Order BY teamscore DESC ";
            ResultSet rs = statement.executeQuery(sql);
            String teamName = null;
            int i = 0;
            while (rs.next()) {
                teams.add(new Team(rs.getString("teamName"), rs.getInt("teamRank"),
                        rs.getInt("winNum"), rs.getInt("loseNum"),
                        rs.getInt("drawNum"), rs.getInt("goalNum"),
                        rs.getInt("goalLostNum"), rs.getString("teamGroup"),
                        rs.getInt("teamScore")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    public List<Battle> getTodayBattle() {
        /**
         * @author: long
         * 查询当天赛程
         */
        if (!battles.isEmpty()) {
            battles.clear();
        }
        try {
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
            while (rs.next()) {
                battleTime = rs.getTimestamp("battleTime");
                teamA = rs.getString("teamOne");
                teamB = rs.getString("teamTwo");
                battleSide = rs.getString("battleSide");
                battleResult = rs.getInt("battleResult");
                battleScore = rs.getString("battleScore");
                battles.add(new Battle(teamA, teamB, battleTime, battleSide, battleResult, battleScore));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return battles;
    }

    public List<Battle> getNoStartBattle() {
        /**@author: long
         * 返回未开始比赛的赛程信息
         * */
        if (!battles.isEmpty()) {
            battles.clear();
        }
        try {
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
            while (rs.next()) {
                battleTime = rs.getTimestamp("battleTime");
                teamA = rs.getString("teamOne");
                teamB = rs.getString("teamTwo");
                battleSide = rs.getString("battleSide");
                battleResult = rs.getInt("battleResult");
                battleScore = rs.getString("battleScore");
                battles.add(new Battle(teamA, teamB, battleTime, battleSide, battleResult, battleScore));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return battles;
    }


    public List<Battle> getOneDayBattles(String date) {
        /**
         * @author :long
         * 查询某天赛程
         */
        if (!battles.isEmpty()) {
            battles.clear();
        }
        try {
            String sql = "select * from battledetail where DATE_FORMAT(battleTime, '%Y-%m-%d') = DATE_FORMAT('" + date + "', '%Y-%m-%d')";
            ResultSet rs = statement.executeQuery(sql);
            Timestamp battleTime;  //对战时间
            String teamA = null;
            String teamB = null;
            String battleSide = null;      //比赛场地
            int battleResult = 0;    //比赛结果，1表示A胜，0表示平局，-1表示A负,-2表示未开始
            String battleScore = null;     //比赛比分
            while (rs.next()) {
                battleTime = rs.getTimestamp("battleTime");
                teamA = rs.getString("teamOne");
                teamB = rs.getString("teamTwo");
                battleSide = rs.getString("battleSide");
                battleResult = rs.getInt("battleResult");
                battleScore = rs.getString("battleScore");
                battles.add(new Battle(teamA, teamB, battleTime, battleSide, battleResult, battleScore));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return battles;
    }


    public List<Battle> getAllBattles() {
        /**
         * @author :long
         * 查询所有赛程
         */
        if (!battles.isEmpty()) {
            battles.clear();
        }
        try {
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
                battles.add(new Battle(teamA, teamB, battleTime, battleSide, battleResult, battleScore));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return battles;
    }

    public String[] getGameInfo(Team team) {
        /**
         * @author:quanhao
         * 获取这支队伍的比赛信息
         * */
        String[] result = new String[50];
        try {
            String sql = "select * from battledetail where teamone='" + team.getTeamName() + "'" + "or teamtwo='" + team.getTeamName() + "'";
            ResultSet rs = statement.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                result[i] = "";
                if (rs.getString("teamOne") == team.getTeamName()) {
                    result[i] += rs.getString("teamOne") + "VS";
                    result[i] += rs.getString("teamTwo") + "\t\t";
                } else {
                    result[i] += rs.getString("teamTwo") + "VS";
                    result[i] += rs.getString("teamOne") + "\t\t";
                }
                int status = rs.getInt("battleResult");
                switch (status) {
                    case 1:
                        result[i] += rs.getString("teamOne") + "获胜" + "\n地点：";
                        break;
                    case 0:
                        result[i] += "平局" + "\n地点：";
                        break;
                    case -1:
                        result[i] += rs.getString("teamTwo") + "获胜" + "\n地点：";
                        break;
                    case -2:
                        result[i] += "比赛未开始" + "\n地点：";
                        break;
                }
                result[i] += rs.getString("battleSide") + "\t时间：";
                result[i] += rs.getString("battleTime");
                i++;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String[] getGoalInfo(Player player) {
        /**
         * @author :QUANHAO
         * 从数据库中读取该球员的进球信息
         * */
        String[] result = new String[30];
        try {
            String sql = "select * from goaldetail where playerName = '" + player.getName() + "'";
            ResultSet rs = statement.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                result[i] = "";
                result[i] += rs.getString("time").split(" ")[0] + "\t";
                result[i] += rs.getString("teamA") + "VS";
                result[i] += rs.getString("teamB") + "\n进球时间：";
                result[i] += rs.getString("time").split(" ")[1];
                i++;
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    /*********************************************
     *              League get                   *
     ********************************************/

    /*********************************************
     *              League set                   *
     *                                           *
     ********************************************/
    public boolean setPlayerRank() {
        /**
         * @param :None
         * @author :long
         * 将player根据score进行排序，并写入playerrank
         * Rewrite:QuanHao
         * 用List替代数组
         */
        try {
            String sql = "select * from footballplayer";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                players.add(new Player(
                        rs.getString("playerName"), rs.getString("playerteamName"),
                        rs.getString("playerfoul"), rs.getInt("playerscore"),
                        rs.getInt("playerrank"), rs.getString("playerPhoto")));
            }
            players.sort((o1, o2) -> o1.getScore() - o2.getScore());
            Collections.sort(players);
            int counter = 0;
            for (Player player : players) {
                sql = "update footballplayer set playerRank=" + (counter + 1)
                        + " where " + "playerName='" + player.getName()
                        + "' and playerteamname='" + player.getTeamName()
                        + "'";
                statement.executeUpdate(sql);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setTeamRank(String groupName) {
        /**
         * @param :
         * @author : long
         *对teamscore进行排序，将排序好的teamrank写入数据库
         * Rewrite:QuanHao
         */
        try {
            String sql = "select * from footballteam where teamgroup='" + groupName + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                teams.add(new Team(rs.getString("teamName"), rs.getInt("teamRank"),
                        rs.getInt("winNum"), rs.getInt("loseNum"),
                        rs.getInt("drawNum"), rs.getInt("goalNum"),
                        rs.getInt("goalLostNum"), rs.getString("teamGroup"),
                        rs.getInt("teamScore")));
            }
            teams.sort((o1, o2) -> o1.getTeamScore() - o2.getTeamScore());
            Collections.sort(teams);
            int counter = 0;
            for (Team team : teams) {
                sql = "update footballteam set teamRank=" + (counter + 1)
                        + " where " + "teamName='" + team.getTeamName()
                        + "'and teamgroup='" + team.getTeamGroup() +
                        "'";
                statement.executeUpdate(sql);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*********************************************
     *              League set                   *
     ********************************************/

    /*********************************************
     *              League update                *
     *                                           *
     ********************************************/
    public boolean updateTeam(Team team) {
        /**@author:QUANHAO
         * 将这个对象的更新数据写入数据库
         * */
        try {
            String sql = "update footballteam set teamname = '" + team.getTeamName()
                    + "',teamrank=" + team.getTeamRank()
                    + ",winNum=" + team.getWinNum()
                    + ",loseNum=" + team.getLoseNum()
                    + ",drawNum=" + team.getDrawNum()
                    + ",goalNum=" + team.getGoalNum()
                    + ",goalLostNum=" + team.getGoalLostNum()
                    + ",teamscore=" + team.getTeamScore()
                    + ",teamgroup='" + team.getTeamGroup()
                    + "',where teamName='" + team.getTeamName() + "'";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePlayer(Player player) {
        /**
         * @author :QUANHAO
         * 将这个对象的更新数据写入数据库
         * */
        try {
            String sql = "update footballplayer set playername ='" + player.getName()
                    + "',playerphoto='" + player.getPhoto_address()
                    + "',playerteamName='" + player.getTeamName()
                    + "',playerscore=" + player.getScore()
                    + ",playerfoul='" + player.getFoul()
                    + "',playerrank=" + player.getRank()
                    + " where playerName='" + player.getName() + "' and playerTeam='" + player.getTeamName() + "'";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*********************************************
     *              League update                *
     ********************************************/

    /*********************************************
     *              League add                   *
     *                                           *
     ********************************************/
    public boolean addTeam(Team team) {
        /**
         * @author :QUANHAO
         * 插入一支新的队伍
         * */
        try {
            statement.execute("SET FOREIGN_KEY_CHECKS=0");//关闭外键约束检查
            String sql = "INSERT INTO footballteam (teamname,teamrank,winNum,loseNum,drawNum,goalNum,goalLostNum,teamscore,teamgroup) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, team.getTeamName());
            ps.setInt(2, team.getTeamRank());
            ps.setInt(3, team.getWinNum());
            ps.setInt(4, team.getLoseNum());
            ps.setInt(5, team.getDrawNum());
            ps.setInt(6, team.getGoalNum());
            ps.setInt(7, team.getGoalLostNum());
            ps.setInt(8, team.getTeamScore());
            ps.setString(9, team.getTeamGroup());
            ps.executeUpdate();
            statement.execute("SET FOREIGN_KEY_CHECKS=1");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addPlayer(Player player) {
        /**
         * @author :QUANHAO
         * 将新生成的对象的数据写入数据库
         * */
        try {
            String sql = "insert INTO footballplayer values ('"
                    + player.getName() + "',"
                    + "'" + player.getPhoto_address() + "',"
                    + "'" + player.getTeamName() + "',"
                    + player.getScore() + ","
                    + "'" + player.getFoul() + "',"
                    + player.getRank() + ")";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addGoalDetail(String playerName, String teamA, String teamB, String time, int battleResult, String battleScore) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            java.util.Date date = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp sqlTime = new Timestamp(date.getTime());//LocalDateTime转换成SQL 用的date类型
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
            while (rs.next()) {
                i = rs.getInt("playerscore");
            }
            String addGoal = "update footballplayer set playerscore = " + (i + 1);
            statement.executeUpdate(addGoal);
//            String updateBattle = "update battledetail set battleresult = "+battleResult+",battlescore='" +battleScore+
//                    "'where teamone='" +teamA+
//                    "' and teamtwo='" +teamB+
//                    "'and battletime="+sqlTime+"";
//            statement.executeUpdate(updateBattle);
            statement.execute("SET FOREIGN_KEY_CHECKS=1");//启动外键约束检查

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addFoul(String[] foulInfo) {
        try {
            String sql = "Update footballplayer set playerfoul ='" + foulInfo[2] + ":" + foulInfo[3] +
                    "' where playername='" + foulInfo[0]
                    + "'" + "and playerteamname='" + foulInfo[1] +
                    "'";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addBattle(String[] battleInfo) {
        try {
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addSystemUser(String name, String account, String passwd, String position) {
        try {
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
                return true;//新用户插入成功
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*********************************************
     *              League add                   *
     ********************************************/

    public List<String> getTeams() {
        /**
         * 获取全部队伍的队名列表
         * */
        List<String> teamNames = new ArrayList<>();
        try {
            String sql = "select teamname from footballteam ";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                teamNames.add(rs.getString("teamName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamNames;
    }

    public List<String> getPlayersName(String teamName) {
        /**
         * 获取一只队伍的所有球员名字列表
         * */
        List<String> playerNames = new ArrayList<>();
        try {
            String sql = "select playername from footballplayer where playerteamname = '" + teamName + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                playerNames.add(rs.getString("playername"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerNames;
    }


    public boolean checkeam(String teamName) {
        try {
            String sql = "select * from footballteam where teamname='" + teamName + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                if (rs != null) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<String> getCourts() {
        List<String> courts = new ArrayList<>();
        try {
            String sql = "SELECT battleside from court ";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                courts.add(rs.getString("battleSide"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courts;
    }

    public String checkUser(String account, String passwd) {

        String position = null;
        try {
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
            return position;
        } catch (SQLException e) {
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


}
