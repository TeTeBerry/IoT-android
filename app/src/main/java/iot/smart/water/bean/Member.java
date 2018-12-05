package iot.smart.water.bean;

import java.io.Serializable;

public class Member implements Serializable {
    private String _id;
    private String room;
    private String membername;
    private String password;
    private String mail;
    private String tel;
    private long creation_dt;
    private String token;
    private int credit;
    private float waterFlow;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public long getCreation_dt() {
        return creation_dt;
    }

    public void setCreation_dt(long creation_dt) {
        this.creation_dt = creation_dt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public float getWaterFlow() {
        return waterFlow;
    }

    public void setWaterFlow(float waterFlow) {
        this.waterFlow = waterFlow;
    }
}
