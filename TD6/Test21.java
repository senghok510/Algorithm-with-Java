
public class Test21 extends TestBase {
	public static void main(String[] args) {
		if (!Test21.class.desiredAssertionStatus()) {
			System.err.println("Vous devez activer l'option -ea de la JVM");
			System.err.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		}

		System.out.print("Test du constructeur... ");
		//testConstructor(0,5);
		testConstructor(16, 0);
		testConstructor(31, 0);
		testConstructor(32, 0);
		testConstructor(33, 0);
		testConstructor(145, 12);
		System.out.println("\t\t [OK].");
	}

	static void testConstructor(int size, int offset) {
		Fenwick t = new Fenwick(offset, offset + size);

		testWellFormed(t);
		assert t.acc == 0 : "acc doit être initialisé à 0";
		assert nbLeafs(t) == size : "Le nombre de feuilles n'est pas égal à la taille demandée.";
		assert (1 << depth(t)) < 2 * size : "La profondeur de l'arbre est trop grande.";
	}

}
