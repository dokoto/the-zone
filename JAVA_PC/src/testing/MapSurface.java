package testing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JPanel;

import engine.logic.detection.Scan;
import engine.logic.model.TerritoryMap;
import engine.logic.model.User;
import Utils.Logg;

@SuppressWarnings("serial")
public class MapSurface extends JPanel implements MouseListener, MouseMotionListener
{
	private int columns, rows, screenWidth, screenHeight, mapWidth;
	private int spaceBetweenCells;
	private final int margin = 10;
	private final int STROKE = 10, STROKE_DIAGONAL = STROKE / 2;
	private ArrayList<Line2D> lines;
	private ArrayList<Edge> linesSelect;
	private final int HIT_BOX_SIZE = 8;
	private Line2D lastLineCatch;
	private boolean interActive = false;
	private TerritoryMap map;
	private ControlPanel controlPanel;
	private User[] users;
	private static final int MAX_NUM_USERS = 8;
	private Color[] colors = new Color[] { Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE };

	public MapSurface(int rows, int columns, int screenWidth, int screenHeight, boolean interActive, int numOfUsers)
	{
		this.columns = columns;
		this.rows = rows;
		this.mapWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.spaceBetweenCells = this.mapWidth / this.columns;
		this.linesSelect = new ArrayList<Edge>();
		this.lines = new ArrayList<Line2D>();
		this.interActive = interActive;
		this.map = TerritoryMap.createNodes(rows, columns);
		this.users = createUser(numOfUsers);
		if (interActive)
		{
			this.addMouseListener(this);
			// this.addMouseMotionListener(this);
		}

	}

	private User[] createUser(int numOfUsers)
	{
		assert (numOfUsers < MAX_NUM_USERS);
		User tmpUsers[] = new User[numOfUsers];
		for (int i = 0; i < numOfUsers; i++)
		{
			tmpUsers[i] = map.createNewUser("Player" + String.valueOf(i), colors[i]);
		}

		return tmpUsers;
	}

	public User[] getUsers()
	{
		return this.users;
	}

	public void setControlPanel(ControlPanel cotrolPanel)
	{
		this.controlPanel = cotrolPanel;
	}

	public TerritoryMap getMap()
	{
		return this.map;
	}

	private void drawMapInSections(Graphics graphics)
	{

		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setStroke(new BasicStroke(STROKE));

		// HORIZONTALES
		int x1 = margin, y1 = margin, x2 = this.spaceBetweenCells + margin, y2 = margin;
		int nodes = 0;
		for (int row = 0; row <= rows; row++)
		{
			for (int col = 0; col < columns; col++)
			{
				graphics2D.drawString(String.valueOf(nodes++), x1 + 25, y1 + 17);
				lines.add(new Line2D.Double(x1, y1, x2, y2));
				graphics2D.drawLine(x1, y1, x2, y2);
				x1 += this.spaceBetweenCells;
				x2 += this.spaceBetweenCells;
			}
			graphics2D.drawString(String.valueOf(nodes++), x1 + 10, y1 + 20);
			x1 = this.margin;
			x2 = this.spaceBetweenCells + margin;
			y1 += spaceBetweenCells;
			y2 += spaceBetweenCells;
		}

		// VERTICALES
		x1 = margin;
		y1 = margin;
		x2 = margin;
		y2 = this.spaceBetweenCells + margin;
		for (int col = 0; col <= columns; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				lines.add(new Line2D.Double(x1, y1, x2, y2));
				graphics2D.drawLine(x1, y1, x2, y2);
				y1 += this.spaceBetweenCells;
				y2 += this.spaceBetweenCells;
			}
			y1 = this.margin;
			y2 = this.spaceBetweenCells + margin;
			x1 += spaceBetweenCells;
			x2 += spaceBetweenCells;
		}

		// DIAGONALES -  NORTE:OESTE->SUR:ESTE
		graphics2D.setStroke(new BasicStroke(STROKE_DIAGONAL));
		
		x1 = margin; y1 = margin; x2 = this.spaceBetweenCells + margin; y2 = this.spaceBetweenCells + margin;
		nodes = 0;
		for (int row = 0; row < rows; row++)
		{
			for (int col = 0; col < columns; col++)
			{
				lines.add(new Line2D.Double(x1, y1, x2, y2));
				graphics2D.drawLine(x1, y1, x2, y2);
				x1 += this.spaceBetweenCells;
				x2 += this.spaceBetweenCells;
			}
			//graphics2D.drawString(String.valueOf(nodes++), x1 + 10, y1 + 20);
			x1 = this.margin;			
			y1 += spaceBetweenCells;
			x2 = this.spaceBetweenCells + margin;
			y2 += spaceBetweenCells;
		}
		
		// DIAGONALES -  NORTE:ESTE->SUR:OESTE
		x1 = this.spaceBetweenCells + margin; y1 = margin; x2 = margin; y2 = this.spaceBetweenCells + margin;
		nodes = 0;
		for (int row = 0; row < rows; row++)
		{
			for (int col = 0; col < columns; col++)
			{
				lines.add(new Line2D.Double(x1, y1, x2, y2));
				graphics2D.drawLine(x1, y1, x2, y2);
				x1 += this.spaceBetweenCells;
				x2 += this.spaceBetweenCells;
			}
			//graphics2D.drawString(String.valueOf(nodes++), x1 + 10, y1 + 20);
			x1 = this.spaceBetweenCells + this.margin;			
			y1 += spaceBetweenCells;
			x2 = margin;
			y2 += spaceBetweenCells;
		}		

	}

	public Line2D thisLineExist(int x, int y)
	{
		int boxX = x - HIT_BOX_SIZE / 2;
		int boxY = y - HIT_BOX_SIZE / 2;

		int width = HIT_BOX_SIZE;
		int height = HIT_BOX_SIZE;

		for (Line2D line : lines)
		{
			if (line.intersects(boxX, boxY, width, height))
			{
				return line;
			}
		}
		return null;
	}

	public Point2D converNodeToPoint(int node)
	{
		int y = node / columns;
		int x = (node < columns) ? node : node - (y * columns);

		x *= spaceBetweenCells;
		y *= spaceBetweenCells;

		return new Point2D.Double(x + margin, y + margin);
	}

	public int convertLineToNode(int x, int y)
	{

		int x1 = (x - this.margin) / this.spaceBetweenCells;
		int y1 = (y - this.margin) / this.spaceBetweenCells;

		return (y1 * this.columns) + x1;
	}

	public void drawEdge(int source, int destination, Color color)
	{
		this.linesSelect.add(new Edge(new Line2D.Double(converNodeToPoint(source), converNodeToPoint(destination)), color));
		repaint();
	}

	public void paint(Graphics g)
	{
		update(g);
	}

	public void paintLine(Graphics graphics)
	{
		if (this.linesSelect.size() > 0)
		{
			Graphics2D graphics2D = (Graphics2D) graphics;
			graphics2D.setStroke(new BasicStroke(STROKE));

			for (Edge edge : this.linesSelect)
			{
				graphics.setColor(edge.getColor());
				graphics2D.drawLine((int) edge.getEdge().getX1(), (int) edge.getEdge().getY1(), (int) edge.getEdge().getX2(), (int) edge.getEdge().getY2());
			}
		}
	}

	@Override
	public void update(Graphics graphics)
	{
		drawMapInSections(graphics);
		paintLine(graphics);

	}

	public void drawEdgeIf(int x, int y, Color color)
	{
		Line2D ll = thisLineExist(x, y);
		if (ll == null)
			Logg.INFO("NULL");
		else
		{
			Logg.INFO("FIND: [" + ll.getX1() + "," + ll.getY1() + "," + ll.getX2() + "," + ll.getY2() + "]");
			int source = convertLineToNode((int) ll.getX1(), (int) ll.getY1());
			int destination = convertLineToNode((int) ll.getX2(), (int) ll.getY2());
			Logg.INFO("POINT[" + source + "," + destination + "]");
			drawEdge(source, destination, color);
		}
	}

	@Override
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		// drawMap(graphics);
		paintLine(graphics);
	}

	/*
	 * ON MOUSE LISTENER
	 */

	@Override
	public void mouseClicked(MouseEvent ev)
	{
		if (interActive)
		{
			Line2D posibleLine = thisLineExist(ev.getX(), ev.getY());
			if (posibleLine != null)
			{
				int source = convertLineToNode((int) posibleLine.getX1(), (int) posibleLine.getY1());
				int destination = convertLineToNode((int) posibleLine.getX2(), (int) posibleLine.getY2());
				User activeUser = controlPanel.getActiveUser();
				if (map.add(source, destination, activeUser))
				{
					drawEdge(source, destination, activeUser.getColor());
					Scan.run(map, activeUser);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent ev)
	{
	}

	@Override
	public void mouseExited(MouseEvent ev)
	{
	}

	@Override
	public void mousePressed(MouseEvent ev)
	{
	}

	@Override
	public void mouseReleased(MouseEvent ev)
	{
	}

	/*
	 * ON MOUSE MOTION
	 */

	@Override
	public void mouseDragged(MouseEvent ev)
	{
	}

	@Override
	public void mouseMoved(MouseEvent ev)
	{
		Line2D lineCatch = thisLineExist(ev.getX(), ev.getY());

		if (lineCatch != null)
		{
			// Logg.INFO("FIND ONMOUSE-OVER: [" + lineCatch.getX1() + "," +
			// lineCatch.getY1() + "," + lineCatch.getX2() + "," +
			// lineCatch.getY2() + "]");
			int source = convertLineToNode((int) lineCatch.getX1(), (int) lineCatch.getY1());
			int destination = convertLineToNode((int) lineCatch.getX2(), (int) lineCatch.getY2());
			// Logg.INFO("POINT[" + source + "," + destination + "]");
			drawEdge(source, destination, controlPanel.getActiveUser().getColor());
			lastLineCatch = (Line2D) lineCatch.clone();
		} else
		{
			if (lastLineCatch != null)
			{
				int source = convertLineToNode((int) lastLineCatch.getX1(), (int) lastLineCatch.getY1());
				int destination = convertLineToNode((int) lastLineCatch.getX2(), (int) lastLineCatch.getY2());
				drawEdge(source, destination, Color.black);
			}
		}
	}
}
