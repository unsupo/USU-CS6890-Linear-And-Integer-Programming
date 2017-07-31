package utilities;

public class Ratio {
    int numerator, denominator;

    public Ratio(int numerator, int denominator) {
        basicInit(numerator,denominator);
    }public Ratio(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }public Ratio(double value){
        int count = 0;
        while ((int)value != value) {
            value *= 10;
            count++;
        }
        int n = (int) value, d = (int) Math.pow(10,count);
        int v = gcd(n,d);
        this.numerator = n/v;
        this.denominator = d/v;
    }public Ratio(String fraction){
        if(!fraction.contains("/")){
            Ratio r = parseDecimal(fraction);
            this.numerator = r.numerator;
            this.denominator = r.denominator;
            return;
        }
        String[] split = fraction.split("/");
        int v1 = Integer.parseInt(split[0]), v2 = Integer.parseInt(split[1]);
        basicInit(v1,v2);
    }

    private void basicInit(int numerator, int denominator) {
        if(denominator == 0)
            throw new IllegalArgumentException("Denominator must not be 0");
        int v = gcd(numerator, denominator);
        this.numerator = numerator/v;
        this.denominator = denominator/v;
    }

    private Ratio parseDecimal(String fraction) {
        double d = Double.parseDouble(fraction);
        return new Ratio(d);
    }

    public double getDoubleValue(){
        return numerator/new Double(denominator);
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ratio ratio = (Ratio) o;

        if (numerator != ratio.numerator) return false;
        return denominator == ratio.denominator;
    }

    @Override
    public int hashCode() {
        int result = numerator;
        result = 31 * result + denominator;
        return result;
    }

    @Override
    public String toString() {
        if(denominator < 0){
            denominator*=-1;
            numerator*=-1;
        }
        if(denominator == 1)
            return numerator+"";
        return numerator+"/"+denominator;
    }

    public Ratio add(Ratio r){
        int v = lcm(denominator,r.denominator);
        int v1 = v/denominator, v2 = v/r.denominator;
        return new Ratio(v1*numerator+v2*r.numerator,v);
    }public Ratio plus(Ratio r){
        return add(r);
    }public Ratio add(int r){
        return this.add(new Ratio(r));
    }public Ratio add(double r){
        return this.add(new Ratio(r));
    }public Ratio add(String r){
        return this.add(new Ratio(r));
    }

    public Ratio subtract(Ratio r){
        int v = lcm(denominator,r.denominator);
        int v1 = v/denominator, v2 = v/r.denominator;
        return new Ratio(v1*numerator-v2*r.numerator,v);
    }public Ratio minus(Ratio r){
        return subtract(r);
    }public Ratio subtract(int r){
        return this.subtract(new Ratio(r));
    }public Ratio subtract(double r){
        return this.subtract(new Ratio(r));
    }public Ratio subtract(String r){
        return this.subtract(new Ratio(r));
    }

    public Ratio multiply(Ratio r){
        return new Ratio(r.numerator*numerator,r.denominator*denominator);
    }public Ratio multiply(int r){
        return new Ratio(r*numerator,denominator);
    }public Ratio multiply(double r){
        return this.multiply(new Ratio(r));
    }public Ratio multiply(String r){
        return this.multiply(new Ratio(r));
    }

    public Ratio divide(Ratio r){
        return new Ratio(numerator*r.denominator,denominator*r.numerator);
    }public Ratio divide(int r){
        return this.divide(new Ratio(r));
    }public Ratio divide(double r){
        return this.divide(new Ratio(r));
    }public Ratio divide(String r){
        return this.divide(new Ratio(r));
    }

    private int gcd(int a, int b){
        while(a!=0 && b!=0){ // until either one of them is 0
            int c = b;
            b = a%b;
            a = c;
        }
        return a+b; // either one is 0, so return the non-zero value
    }private int lcm(int a, int b){
        return a * (b / gcd(a, b));
    }
}
