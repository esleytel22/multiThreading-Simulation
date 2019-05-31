import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class SimThread extends Thread {
//instant variables
	private int N = 0, K, U, D;
	private ThreadLocalRandom R = ThreadLocalRandom.current();//Random R = new Random();
	private int Narray[];
	private int numWalks;
	int max = 0;

//Constructor
	public SimThread(int k, int u, int d, int nw) {
		K = k;
		U = u;
		D = d;
		numWalks = nw;
		Narray = new int[numWalks];
	}

//Methods
	public void run() {
		int X = K;
		int i = 0;
		while (i < numWalks) {
			while (X > 0) {
				if (R.nextInt(2) == 1) {
					X = X + U;
				} else {
					X = X - D;
				}

				N++;
			}

			Narray[i] = N;
			if (max < N)
				max = N;
			N = 0;
			X = K;
			i++;
		}
	}

	public int[] getNarray() {
		return this.Narray;
	}

	public int getMax() {
		return max;
	}
}
