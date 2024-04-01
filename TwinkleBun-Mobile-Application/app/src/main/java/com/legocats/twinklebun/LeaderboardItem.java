package com.legocats.twinklebun;

public class LeaderboardItem {
    private String name;
    private int overallScore;
    private String id;

    public LeaderboardItem(String name, int overallScore, String id) {
        this.name = name;
        this.overallScore = overallScore;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(int overallScore) {
        this.overallScore = overallScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LeaderboardItem{" +
                "name='" + name + '\'' +
                ", overallScore=" + overallScore +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeaderboardItem that = (LeaderboardItem) o;

        if (overallScore != that.overallScore) return false;
        if (!name.equals(that.name)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + overallScore;
        result = 31 * result + id.hashCode();
        return result;
    }
}
