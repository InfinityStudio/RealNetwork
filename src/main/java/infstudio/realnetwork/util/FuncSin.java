package infstudio.realnetwork.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FuncSin {

    private static final double EPS = 1e-8;
    public Map<Double, Integer> map;
    public ArrayList<Double> A;
    public ArrayList<Double> phi;

    public FuncSin() {
        map = new HashMap<Double, Integer>();
        A = new ArrayList<>();
        phi = new ArrayList<>();
    }

    public FuncSin(double[] phi, double[] A) {
        this();
        for (int i = 0; i < phi.length; ++i) {
            this.map.put(phi[i], i);
            this.phi.add(phi[i]);
            this.A.add(A[i]);
        }
    }

    public FuncSin(double phi, double A) {
        this();
        this.map.put(phi, 0);
        this.phi.add(phi);
        this.A.add(A);
    }

    public FuncSin(FuncSin fs) {
        this();
        for (int i = 0; i < fs.phi.size(); ++i) {
            this.map.put(fs.phi.get(i), i);
            this.phi.add(fs.phi.get(i));
            this.A.add(fs.A.get(i));
        }
    }

    public void add(FuncSin fs) {
        for (int i = 0; i < fs.phi.size(); ++i) {
            if (this.map.containsKey(fs.phi.get(i))) {
                int index = this.map.get(fs.phi.get(i));
                this.A.set(index, this.A.get(index)+fs.A.get(i));
            } else {
                this.phi.add(fs.phi.get(i));
                this.A.add(fs.A.get(i));
                this.map.put(fs.phi.get(i), this.phi.size()-1);
            }
        }
    }

    public static FuncSin add(FuncSin x, FuncSin y) {
        FuncSin z = new FuncSin(x);
        z.add(y);
        return z;
    }

    public void multiply(double x) {
        for (int i = 0; i < this.A.size(); ++i) {
            this.A.set(i, x*this.A.get(i));
        }
    }

    public static FuncSin multiply(double x, FuncSin y) {
        FuncSin z = new FuncSin(y);
        z.multiply(x);
        return z;
    }

    public FuncSin getOpposite() {
        FuncSin fs = new FuncSin(this);
        for (int i = 0 ; i < fs.A.size(); ++i) {
            fs.A.set(i, -this.A.get(i));
        }
        return fs;
    }

    public boolean isZero() {
        for (int i = 0; i < this.A.size(); ++i) {
            if (this.A.get(i) > EPS) {
                return false;
            }
        }
        return true;
    }

    public double getEffective() {
        double res = 0.0D;
        for (int i = 0; i < phi.size(); ++i) {
            for (int j = 0; j < phi.size(); ++j) {
                res += 0.5D*A.get(i)*A.get(j)*Math.cos(phi.get(i)-phi.get(j));
            }
        }
        return Math.sqrt(res);
    }

    public double[] arrayA() {
        double[] array = new double[A.size()];
        for (int i = 0; i < A.size(); ++i) {
            array[i] = A.get(i);
        }
        return array;
    }

    public double[] arrayPhi() {
        double[] array = new double[phi.size()];
        for (int i = 0; i < phi.size(); ++i) {
            array[i] = phi.get(i);
        }
        return array;
    }

}
