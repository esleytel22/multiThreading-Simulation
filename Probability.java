
public class Probability {
	double sum=0;
	int n=0;
	public Probability(int number){
		n=number;
		sum++;
	}
	public Probability() {
		
	}
	public void Up() {
		sum++;
	}
	public double getSum() {
		return sum;
	}
	public double getHeight() {
		return sum/n;
	}
}
