package FootballLeagueScoringSystem.Module;

import java.security.PublicKey;
import java.sql.*;

/**
 * @param :name,photo_address,teamName,score,foul,rank
 * @author QuanHao
 * @author Long
 */
public class Player implements Comparable<Player> {
    private String name;//姓名
    private String teamName;//所属球队名
    private String foul;    //违规信息
    private int score;//进球数/积分数
    private int rank;//排名
    private String photo_address;//图片地址

    public Player(String name, String teamName, String foul, int score, int rank, String photo_address) {
        /**
         * 全参数传入，生成一个新的player对象
         * */
        this.name = name;
        this.teamName = teamName;
        this.foul = foul;
        this.score = score;
        this.rank = rank;
        this.photo_address = photo_address;
    }






    public String getName() {
        return name;
    }

    public String getPhoto_address() {
        return photo_address;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getScore() {
        return score;
    }

    public int getRank() {
        return rank;
    }

    public String getFoul() {
        return foul;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setFoul(String foul) {
        this.foul = foul;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setPhoto_address(String photo_address) {
        this.photo_address = photo_address;
    }

    @Override
    public int compareTo(Player o) {
        return o.score - this.score;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", photo_address='" + photo_address + '\'' +
                ", teamName='" + teamName + '\'' +
                ", foul='" + foul + '\'' +
                ", score=" + score +
                ", rank=" + rank +
                '}';
    }
}
