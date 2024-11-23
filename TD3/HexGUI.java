import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// Based on
// https://stackoverflow.com/questions/24034747/how-to-implement-mouselistener-on-a-particular-shape


class HexGUI extends JPanel implements MouseListener, KeyListener {
  static final double UNIT = 36.0;
  static final double V = 36.0;
  static final double TILESCALE = 0.93;
  static final double BORDERWIDTH = 0.4;
  static final double H = V * Math.sqrt(3.0) / 2;

  static final Color BLUE = new Color(0x177e89);
  static final Color FADED_BLUE = new Color(0xa2cbd0);
  // static final Color FADED_BLUE = new Color(0x9cb7c0);
  // static final Color BLUE = new Color(0x084c61);
  static final Color RED = new Color(0xdb3a34);
  static final Color FADED_RED = new Color(0xf1b0ae);
  static final Color GRAY = new Color(0xcccccc);

  static final int UP = 0;
  static final int UPLEFT = 1;
  static final int DOWNLEFT = 2;
  static final int DOWN = 3;
  static final int DOWNRIGHT = 4;
  static final int UPRIGHT = 5;

  Vector<Integer> randomMoves;
  int randomMoveIt = 0;

  static double centerX(int i, int j) {
    return (2 * i + j) * H;
  }

  static double centerY(int i, int j) {
    return V * (j + 1) * 3.0 / 2;
  }

  static double hexagonVertexX(int pt, double scale) {
    switch (pt % 6) {
      case UP:
      case DOWN:
        return 0.0;
      case UPLEFT:
      case DOWNLEFT:
        return -H * scale;
      case UPRIGHT:
      case DOWNRIGHT:
        return H * scale;
    }
    throw new IllegalArgumentException("pt should be nonnegative");
  }

  static double hexagonVertexY(int pt, double scale) {
    switch (pt % 6) {
      case UP:
        return -V * scale;
      case UPLEFT:
      case UPRIGHT:
        return -V * scale / 2;
      case DOWNLEFT:
      case DOWNRIGHT:
        return V * scale / 2;
      case DOWN:
        return V * scale;
    }
    throw new IllegalArgumentException("pt should be 0 <= pt < 6");
  }

  boolean randomMove() {
    if (randomMoveIt >= n * n)
      return false;

    int k = randomMoves.get(randomMoveIt++);
    int i = k / n + 1;
    int j = k % n + 1;
    if (hex.get(i, j) == Hex.Player.NOONE) {
      System.out.printf("autoplay (%d, %d)...\n", i, j);
      if (hex.click(i, j)) {
        System.out.println("   ... OK");
      } else {
        System.out.println("   ... not a move");
      }
    }

    return true;
  }

  Hex hex;

  protected boolean printLabels = false;
  protected boolean printColors = false;
  protected boolean printCoords = false;

  private final List<Shape> tiles;

  private final Path2D redBorder, blueBorder;

  final int n;

  public HexGUI(int boardSize) {
    addMouseListener(this);

    addKeyListener(this);
    this.setFocusable(true);
    this.requestFocus();

    this.n = boardSize;

    tiles = new ArrayList<Shape>();
    for (int j = 1; j <= boardSize; j++)
      for (int i = 1; i <= boardSize; i++)
        makeTile(i, j);

    redBorder = new Path2D.Double();
    blueBorder = new Path2D.Double();
    makeBorders();

    hex = new Hex(boardSize);

    randomMoves = new Vector<Integer>();
    for (int k = 0; k < n * n; k++) {
      randomMoves.add(k);
    }
    java.util.Collections.shuffle(randomMoves);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    for (int k = 0; k < tiles.size(); k++) {
      Shape shape = tiles.get(k);
      if (shape.contains(e.getPoint())) {
        int i = k % n + 1;
        int j = k / n + 1;

        System.out.printf("click (%d, %d)... \n", i, j);
        if (hex.click(i, j)) {
          System.out.println("   ... OK");
        } else {
          System.out.println("   ... not a move");
        }

        repaint();
        break;
      }
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
    switch (e.getKeyChar()) {
      case 'l':
        printLabels = !printLabels;
        repaint();
        break;

      case 'p':
        printColors = !printColors;
        repaint();
        break;

      case 'c':
        printCoords = !printCoords;
        repaint();
        break;

      case 'r':
        System.out.println("reset");
        hex = new Hex(n);
        randomMoveIt = 0;
        java.util.Collections.shuffle(randomMoves);
        repaint();
        break;

      case ' ':
        randomMove();
        repaint();
        break;

      case 'a':
        while (randomMove())
          ;
        repaint();
        break;

    }
  }

  static void createAndShowGUI() {
    JFrame f = new JFrame();
    int size = 11;
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(new HexGUI(size));
    f.setSize((int) HexGUI.centerX(size + 1, size + 2),
        (int) HexGUI.centerY(size + 1, size + 2));
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }

  @Override
  protected void paintComponent(Graphics gr) {
    super.paintComponent(gr);
    // https://stackoverflow.com/questions/59431324/java-how-to-make-an-antialiasing-line-with-graphics2d
    Graphics2D g = (Graphics2D) gr.create();

    g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

    Hex.Player winner = hex.winner();
    System.out.println(winner + " wins");

    for (int i = 0; i < tiles.size(); i++) {
      Shape shape = tiles.get(i);
      switch (hex.get(i % n + 1, i / n + 1)) {
        case RED:
          g.setColor(winner == Hex.Player.BLUE ? FADED_RED : RED);
          break;
        case BLUE:
          g.setColor(winner == Hex.Player.RED ? FADED_BLUE : BLUE);
          break;
        default:
          g.setColor(GRAY);
      }
      g.fill(shape);
    }

    Stroke stroke = new BasicStroke((float) (V * BORDERWIDTH * 0.95), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    g.setStroke(stroke);
    g.setColor(hex.currentPlayer() == Hex.Player.RED || winner == Hex.Player.RED ? RED : FADED_RED);
    g.draw(redBorder);
    g.setColor(hex.currentPlayer() == Hex.Player.BLUE || winner == Hex.Player.BLUE ? BLUE : FADED_BLUE);
    g.draw(blueBorder);

    if (printLabels) {
      g.setColor(Color.BLACK);
      for (int i = 0; i <= n + 1; i++) {
        for (int j = 0; j <= n + 1; j++) {
          g.drawString(String.valueOf(hex.label(i, j)), (int) centerX(i, j), (int) centerY(i, j));
        }
      }
    }

    if (printColors) {
      g.setColor(Color.BLACK);
      for (int i = 0; i <= n + 1; i++) {
        for (int j = 0; j <= n + 1; j++) {
          g.drawString(String.valueOf(hex.get(i, j).name()), (int) centerX(i, j) - 10, (int) centerY(i, j) + 12);
        }
      }
    }

    if (printCoords) {
      g.setColor(Color.BLACK);
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
          g.drawString(String.format("%d, %d", i, j), (int) centerX(i, j) - 10, (int) centerY(i, j) + 12);
        }
      }
    }

    g.dispose();
  }

  void makeTile(int i, int j) {

    Path2D path = new Path2D.Double();

    for (int pt = 0; pt < 6; pt++) {
      double x = centerX(i, j) + hexagonVertexX(pt, TILESCALE);
      double y = centerY(i, j) + hexagonVertexY(pt, TILESCALE);
      if (pt == 0) {
        path.moveTo(x, y);
      } else {
        path.lineTo(x, y);
      }
    }
    path.closePath();
    tiles.add(path);

  }

  void makeBorders() {
    double in = TILESCALE - BORDERWIDTH / 2;
    double out = 2.0 - TILESCALE + BORDERWIDTH / 2;

    for (int i = 1; i <= n; i++) {
      double x = centerX(i, 0) + hexagonVertexX(DOWN, in);
      double y = centerY(i, 0) + hexagonVertexY(DOWN, in);
      if (i == 1)
        redBorder.moveTo(x, y);
      else
        redBorder.lineTo(x, y);

      x = centerX(i, 1) + hexagonVertexX(UP, out);
      y = centerY(i, 1) + hexagonVertexY(UP, out);
      redBorder.lineTo(x, y);
    }

    for (int i = 1; i <= n; i++) {
      double x = centerX(i, n) + hexagonVertexX(DOWN, out);
      double y = centerY(i, n) + hexagonVertexY(DOWN, out);
      if (i == 1)
        redBorder.moveTo(x, y);
      else
        redBorder.lineTo(x, y);

      x = centerX(i, n + 1) + hexagonVertexX(UP, in);
      y = centerY(i, n + 1) + hexagonVertexY(UP, in);
      redBorder.lineTo(x, y);
    }

    for (int j = 1; j <= n; j++) {
      double x = centerX(0, j) + hexagonVertexX(UPRIGHT, in);
      double y = centerY(0, j) + hexagonVertexY(UPRIGHT, in);
      if (j == 1)
        blueBorder.moveTo(x, y);
      else
        blueBorder.lineTo(x, y);

      x = centerX(1, j) + hexagonVertexX(DOWNLEFT, out);
      y = centerY(1, j) + hexagonVertexY(DOWNLEFT, out);
      blueBorder.lineTo(x, y);
    }

    for (int j = 1; j <= n; j++) {
      double x = centerX(n, j) + hexagonVertexX(UPRIGHT, out);
      double y = centerY(n, j) + hexagonVertexY(UPRIGHT, out);
      if (j == 1)
        blueBorder.moveTo(x, y);
      else
        blueBorder.lineTo(x, y);

      x = centerX(n + 1, j) + hexagonVertexX(DOWNLEFT, in);
      y = centerY(n + 1, j) + hexagonVertexY(DOWNLEFT, in);
      blueBorder.lineTo(x, y);
    }
  }
}
