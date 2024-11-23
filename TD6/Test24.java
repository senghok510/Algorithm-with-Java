import java.util.Random;

public class Test24 extends TestBase {

	public static void main(String[] args) {

		if (!Test24.class.desiredAssertionStatus()) {
			System.err.println("Vous devez activer l'option -ea de la JVM");
			System.err.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		}

		System.out.print("Test de cumulative... ");
		testCumulative(16, 0);
		testCumulative(31, 0);
		testCumulative(32, 0);
		testCumulative(33, 0);
		testCumulative(145, 12);
		testCumulative(1000, 0);
		testCumulative(10000, 0);
		long t1 = testCumulative(100000, 0);
		long t2 = testCumulative(200000, 0);
		assert t2 < 2.3*t1: "\nL'implémentation de cumulative n'a probablement pas une complexité logarithmique.";
		System.out.println("\t\t\t [OK].");
	}

	static long testCumulative(int size, int offset) {
		Fenwick t = new Fenwick(offset, offset + size);

		testWellFormed(t);
		testWellMaintained(t);

		int[] acc = new int[size];
		Random r = new Random();
		int nb = 100000;
		for (int i = 0; i < nb; i++) {
			int s = r.nextInt(size);
			acc[s]++;
			t.inc(offset + s);
		}

		testWellFormed(t);
		testWellMaintained(t);

		assert t.cumulative(offset) == 0 : "\ncumulative(lo) doit être égal à zéro.";
		assert t.cumulative(
				offset + size + 10) == nb : "\nSi i >= hi, cumulative(i) doit renvoyer la somme de tous les éléments.";

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < size; i++) {
			assert acc[i] == t.cumulative(offset + i + 1)
					- t.cumulative(offset + i) : "\nMauvaise valeur de retour pour cumulative.";
		}

		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}

}
