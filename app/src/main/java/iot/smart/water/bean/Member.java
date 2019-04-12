package iot.smart.water.bean;

import java.io.Serializable;

public class Member implements Serializable {
    private String _id;
    private String room;
    private String membername;
    private String password;
    private String email;
    private String tel;
    private long createTime;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        return "Member{" +
                "_id='" + _id + '\'' +
                ", room='" + room + '\'' +
                ", membername='" + membername + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", createTime=" + createTime +
                ", token='" + token + '\'' +
                ", credit=" + credit +
                ", waterFlow=" + waterFlow +
                '}';
    }
}
