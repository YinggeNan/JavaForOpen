package com.cdb.hamcrestTest;

import java.util.Objects;

/**
 * @Author yingge
 * @Date 2022/12/2 17:07
 */
public class Biscuit {
    public Biscuit(){

    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer number;

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public Biscuit(String name) {
        this.name = name;
    }

    public Biscuit(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biscuit biscuit = (Biscuit) o;
        return name.equals(biscuit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Integer getChocolateChipCount() {
        return 10;
    }

    public Integer getHazelnutCount() {
        return 3;
    }

    @Override
    public String toString() {
        return "Biscuit{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
