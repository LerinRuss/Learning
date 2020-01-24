package my;

import java.util.Objects;
import java.util.Random;

public class Gen {
    private int x1, x2, x3, x4;

    public Gen(int x1, int x2, int x3, int x4) {
        setX1(x1);
        setX2(x2);
        setX3(x3);
        setX4(x4);
    }

    public static Gen random(int exclusive) {
        Random r = new Random();
        return new Gen(r.nextInt(exclusive), r.nextInt(exclusive), r.nextInt(exclusive), r.nextInt(exclusive));
    }


    public void addDelta(int dx1, int dx2, int dx3, int dx4) {
        addX1Delta(dx1);
        addX2Delta(dx2);
        addX3Delta(dx3);
        addX4Delta(dx4);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gen)) return false;
        Gen gen = (Gen) o;
        return x1 == gen.x1 &&
                x2 == gen.x2 &&
                x3 == gen.x3 &&
                x4 == gen.x4;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, x2, x3, x4);
    }

    @Override
    public String toString() {
        return "Gen{" +
                "x1=" + x1 +
                ", x2=" + x2 +
                ", x3=" + x3 +
                ", x4=" + x4 +
                '}';
    }

    public void addX1Delta(int dx1) {
        setX1(getX1() + dx1);
    }

    public void addX2Delta(int dx2) {
        setX2(getX2() + dx2);
    }

    public void addX3Delta(int dx3) {
        setX3(getX3() + dx3);
    }

    public void addX4Delta(int dx4) {
        setX4(getX4() + dx4);
    }


    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getX3() {
        return x3;
    }

    public int getX4() {
        return x4;
    }


    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }

    public void setX4(int x4) {
        this.x4 = x4;
    }
}
