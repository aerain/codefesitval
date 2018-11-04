package io.gitlab.sslab.codefestival.service;

public class User {
    private String id;
    private String password;
    private String name;
    private int scoreGet;       // 현재 스코어
    private boolean scoreBool;  // 정답을 맞추었는가 못맞추었는가

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScoreGet() {
        return scoreGet;
    }

    public void setScoreGet(int scoreGet) {
        this.scoreGet = scoreGet;
    }

    public boolean isScoreBool() {
        return scoreBool;
    }

    public void setScoreBool(boolean scoreBool) {
        this.scoreBool = scoreBool;
    }
}
