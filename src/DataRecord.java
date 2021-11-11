public class DataRecord {
    public int a,b,c,d,e,f,g,h;

    public DataRecord(int a, int b, int c, int d, int e, int f, int g, int h) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
    }

    @Override
    public String toString() {
        return "DataRecord{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", e=" + e +
                ", f=" + f +
                ", g=" + g +
                ", h=" + h +
                '}';
    }
}
