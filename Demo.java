import java.util.Scanner;

public class Demo {

	public static void main(String[] args) throws InterruptedException {
		boolean closeX = false;
		boolean closeUD = false;
		int X, U, D;
		// keyboard input
		Scanner reader = new Scanner(System.in); // Reading from System.in
		while (!closeX) {
			System.out.println("Number of threads to use: (ex. 1000)");
			int numThreads = reader.nextInt();
			System.out.println("Number of walk simulations: ");
			int simNumber = reader.nextInt();
			System.out.println("Enter a X:(ex. 100) ");
			X = reader.nextInt();
			if (X <= 0 || simNumber <= 0) {
				System.out.println("Simulations & X field cant be zero or less.Try again!");
			} else if (X > 0) {
				while (!closeUD) {
					System.out.println("Enter a Up(less than down number):(ex. 9) ");
					U = reader.nextInt();
					System.out.println("Enter a Down:(ex. 10) ");
					D = reader.nextInt();
					if (U > D) {
						System.out.println("down>up therefore please try again");
					} else if (D > U) {
						SimThread threads[] = new SimThread[numThreads];

						int simsPerThread = simNumber / numThreads;
						int extraSims = Math.floorMod(simNumber, numThreads);
						System.out.println("Each thread should do " + simsPerThread
								+ " simulations with a remainder of " + extraSims);

						System.out.println("\nStarting simulations...");
						double t_start = System.currentTimeMillis();

						// START sim compute
						for (int M = 0; M < numThreads; M++) {// number of simulations
							int sims = simsPerThread;
							if (extraSims > 0) {
								sims++;
								extraSims--;
							}
							threads[M] = new SimThread(X, U, D, sims);
							threads[M].start();
						}

						for (int i = 0; i < numThreads; i++) {
							SimThread myt = threads[i];

							try {
								myt.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
								System.exit(1);
							}
						}

						// END sim compute
						System.out.println("--Walks Simulations complete!--");
						System.out.println("\nOutputting results to file...");

						// write to file
						int max = 0;
						for (SimThread t : threads) {
							if (max < t.getMax()) {
								max = t.getMax();
							}
						}

						Probability pro[] = new Probability[max + 1];

						for (SimThread t : threads) {
							int[] Narray = t.getNarray();
							for (int i = 0; i < Narray.length; i++) {
								pro[Narray[i]] = new Probability();
//								System.out.println("getNarray"+Narray[i]);
							}
						}

						for (SimThread t : threads) {
							int[] Narray = t.getNarray();

							for (int i = 0; i < Narray.length; i++) {
								if (pro[Narray[i]].getSum() == 0)
									pro[Narray[i]] = new Probability(simNumber);
								else
									pro[Narray[i]].Up();
							}
						}

						TextWriter writer = new TextWriter("histogram");

						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < pro.length; i++) {
							if (pro[i] != null) {
								sb.append("" + i + "," + pro[i].getHeight());
								sb.append(System.lineSeparator());
							//sb.append('\n');
							}
//							else
//								sb.append("N=" + i + " Height=0");
							
						}
						writer.writeLine(sb.toString());
						writer.close();

						System.out.println("--Finished writing to file!--");

						double t_end = System.currentTimeMillis();
						System.out.println("time (sec) = " + (t_end - t_start) * 0.001);

						closeUD = true; // to enable multiple simulation request comment this out
					}
				}
				closeX = true;
			}
		}
		reader.close();
	}
}
