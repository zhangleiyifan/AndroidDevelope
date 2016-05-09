package com.gyz.androiddevelope.view.nearby;

/**
 * @author: guoyazhou
 * @date: 2016-05-09 10:40
 */
public class Info {
    private static final String TAG = "Info";

    private int portraitId;//头像id
    private String name;//名字
    private String age;//年龄
    private boolean sex;//false为男，true为女
    private float distance;//距离

    public int getPortraitId() {
        return portraitId;
    }

    public void setPortraitId(int portraitId) {
        this.portraitId = portraitId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
