public class Test22 extends TestBase{

	public static void main(String[] args) {

		if (!Test22.class.desiredAssertionStatus()) {
			System.err.println("Vous devez activer l'option -ea de la JVM");
			System.err.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		}
		System.out.print("Test de get... ");
		testGet(16, 0);
		testGet(31, 0);
		testGet(32, 0);
		testGet(33, 0);
		testGet(100, 12);
		System.out.println("\t\t\t\t[OK].");
	}


	static void testGet(int size, int offset) {
		Fenwick t = new Fenwick(offset, offset + size);
		testWellFormed(t);

		for (int i = -3; i < size + 5; i++) {
			assert t.get(i) == tGet(t,i) : String
					.format("Mauvaise valeur de retour de get(%d) dans un arbre Fenwick(%d, %d)", i, t.lo, t.hi);
		}
	}

}

