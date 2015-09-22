package entities;

public class Pair<A, B> {
	public final A a;
	public final B b;
	
	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	public Boolean equal(Pair<A, B> o) {
		return this.a == o.a && this.b == o.b;
	}
	
	public Boolean equal(A a, B b) {
		return this.a == a && this.b == b;
	}
}
