import java.util.Random;

public class Test23 extends TestBase {

	public static void main(String[] args) {

		if (!Test23.class.desiredAssertionStatus()) {
			System.err.println("Vous devez activer l'option -ea de la JVM");
			System.err.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		}
		System.out.print("Test de inc... \n");
		testInc(16, 0);
		testInc(31, 0);
		testInc(32, 0);
		testInc(33, 0);
		testInc(145, 12);
		long t1 = testInc(1000, 0);
		long t2 = testInc(10000, 0);
		long t3 = testInc(100000, 0);
		assert t3 - t2 < 2*(t2 - t1): "L'implémentation de inc n'a probablement pas une complexité logarithmique.";
		System.out.println("\t\t\t [OK].");
	}

	static long testInc(int size, int offset) {
		Fenwick t = new Fenwick(offset, offset + size);

		testWellFormed(t);


		int[] acc = new int[size];
		Random r = new Random();
		int nb = 1000000;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < nb; i++) {
			int s = r.nextInt(size);
			acc[s]++;
			t.inc(offset + s);
		}
		long endTime = System.currentTimeMillis();

		testWellFormed(t);
		testWellMaintained(t);

		for (int i = 0; i < size; i++)
			assert acc[i] == tGet(t,offset + i).acc : "La valeur de l'accumulateur aux feuilles est incorrecte.";

		return endTime - startTime;
	}

}
