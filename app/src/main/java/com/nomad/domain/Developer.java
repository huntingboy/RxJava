package com.nomad.domain;

public class Developer extends Student {

    private String skill;

    public Developer(String name) {
        super(name);
    }

    public Developer(String name, String skill) {
        super(name);
        this.skill = skill;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return super.toString() + "{name='" + super.getName() + "', skill='" + skill + "'}";
    }
}
