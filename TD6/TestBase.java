public class TestBase {

	static int nbLeafs(Fenwick t) {
		if (t.left == null) {
			return 1;
		} else {
			return nbLeafs(t.left) + nbLeafs(t.right);
		}
	}

	static int depth(Fenwick t) {
		if (t.left == null) {
			return 0;
		} else {
			return 1 + Math.max(depth(t.left), depth(t.right));
		}
	}

	static void testWellFormed(Fenwick t) {
		if (t.left == null && t.right == null && t.hi == t.lo + 1)
			return;

		assert t.left != null && t.right != null : String.format("Le nœud interne [%d, %d[ n'a qu'un seul enfant.", t.lo, t.hi);
		assert t.left.lo == t.lo && t.right.hi == t.hi && t.left.hi == t.right.lo : String.format(
				"Le nœud [%d, %d[ est mal recouvert par ses enfants [%d, %d[ et [%d, %d[.", t.lo, t.hi, t.left.lo,
				t.right.hi, t.right.lo, t.right.hi);

		testWellFormed(t.left);
		testWellFormed(t.right);
	}

	static void testWellMaintained(Fenwick t) {
		if (t.left != null) {
			assert t.acc == t.left.acc + t.right.acc : String.format(
					"L'accumulateur de l'intervalle [%d,%d[ n'est pas égal\n"
							+ "à la sommes des accumulateurs des enfants : %d != %d + %d",
					t.lo, t.hi, t.acc, t.left.acc, t.right.acc);
			testWellMaintained(t.left);
			testWellMaintained(t.right);
		}
	}

	static Fenwick tGet(Fenwick t,int i) {
		return t.lo <= i && i < t.hi ? (t.left == null ? t : (tGet(t.left,i) == null) ? tGet(t.right,i) : tGet(t.left,i)) : null;
	}

}

