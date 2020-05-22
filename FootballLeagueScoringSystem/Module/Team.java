package FootballLeagueScoringSystem.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @param :teamScore,teamRank,teamName,winNum,loseNum,drawNum,goalNum,goalLostNum
 * @author:Long
 */
public class Team implements Comparable<Team> {
    private String teamName;//球队名字
    private int teamRank;//球队排名
    private int winNum;     //总赢局数
    private int loseNum;    //总输局数
    private int drawNum;    //总平局数
    private int goalNum;    //总进球数
    private int goalLostNum;//总失球数
    private int teamScore;//球队积分
    private String teamGroup;//球队所属组

    public Team(String teamName, int teamRank, int winNum, int loseNum, int drawNum, int goalNum, int goalLostNum, String teamGroup, int teamScore) {
        /**
         * 全参数传入，生成一个新的球队对象
         * */
        this.teamName = teamName;
        this.teamRank = teamRank;
        this.winNum = winNum;
        this.loseNum = loseNum;
        this.drawNum = drawNum;
        this.goalNum = goalNum;
        this.goalLostNum = goalLostNum;
        this.teamGroup = teamGroup;
        this.teamScore = teamScore;
    }





    public int getWinNum() {
        return winNum;
    }

    public int getLoseNum() {
        return loseNum;
    }

    public int getDrawNum() {
        return drawNum;
    }

    public int getGoalNum() {
        return goalNum;
    }

    public int getGoalLostNum() {
        return goalLostNum;
    }

    public int getTeamScore() {
        return teamScore;
    }

    public int getTeamRank() {
        return teamRank;
    }
    public String getTeamGroup(){return teamGroup;}
    public String getTeamName() {
        return teamName;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamScore=" + teamScore +
                ", teamRank=" + teamRank +
                ", teamName='" + teamName + '\'' +
                ", winNum=" + winNum +
                ", loseNum=" + loseNum +
                ", drawNum=" + drawNum +
                ", goalNum=" + goalNum +
                ", goalLostNum=" + goalLostNum +
                ", teamGroup=" + teamGroup +
                '}';
    }

    @Override
    public int compareTo(Team o) {
        return o.getTeamScore() - this.getTeamScore();
    }
}
