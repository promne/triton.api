package triton.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import triton.domain.Fleet;
import triton.domain.FleetOrder;
import triton.domain.PointInSpace;
import triton.domain.Star;
import triton.mechanic.Distance;
import triton.mechanic.data.DistantPointInSpace;

/**
 * Component displaying galaxy map
 * 
 * @author georgeh
 *
 */
public class UniverseMapComponent extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int planetSize = 10;
	
	private static final Color RESOURCES_COLOR = new Color(255, 255, 255, 30);
	
	private DoubleSummaryStatistics widthStat;

	private DoubleSummaryStatistics heigthStat;

	private Collection<Star> stars;

	private Collection<Fleet> fleets;
	
	public UniverseMapComponent() {
		super();
		this.addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				UniverseMapComponent.this.setToolTipToPlanet(e);
			}
			
		});
	}
	
	public void setUniverseMapData(Collection<Star> stars, Collection<Fleet> fleets) {
		this.stars = stars;
		this.fleets = fleets;
		widthStat = stars.stream().mapToDouble(star -> star.getPosX()).summaryStatistics();
		heigthStat = stars.stream().mapToDouble(star -> star.getPosY()).summaryStatistics();
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (stars==null || fleets==null) {
			return;
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);

		// planetSize acts as a padding
		Dimension canvasSize = new Dimension(this.getSize().width - 2*planetSize, this.getSize().height - 2*planetSize);
		CoordinateConverter converter = new CoordinateConverter(canvasSize, widthStat, heigthStat);
		
		int emptyPlanetSize = planetSize - 2;
		
		for (Star star : stars) {
			int x = converter.getCanvasX(star.getPosX()) + planetSize;
			int y = converter.getCanvasY(star.getPosY()) + planetSize;
					
			if (star.getNaturalResources()!=null) {
				Integer naturalResources = star.getNaturalResources()/2 + planetSize;
				
				g.setColor(RESOURCES_COLOR);
				g.fillOval(x - naturalResources/2, y-naturalResources/2, naturalResources, naturalResources);
			}
			
			if (star.getPlayerId()>=0) {
				g.setColor(PlayerColors.getColor(star.getPlayerId()));
				g.fillOval(x - planetSize/2, y - planetSize/2, planetSize, planetSize);				
			} else {
				g.setColor(Color.WHITE);
				g.drawOval(x - emptyPlanetSize/2, y - emptyPlanetSize/2, emptyPlanetSize, emptyPlanetSize);								
			}
			
			
		}
		
		int fleetSize = 4;
		for (Fleet fleet : fleets) {			
			int x = converter.getCanvasX(fleet.getPosX()) + planetSize;
			int y = converter.getCanvasY(fleet.getPosY()) + planetSize;
			g.setColor(PlayerColors.getColor(fleet.getPlayerId()));
			g.drawOval(x - fleetSize/2, y - fleetSize/2, fleetSize, fleetSize);
			
			if (!fleet.getOrders().isEmpty()) {
				FleetOrder fleetOrder = fleet.getOrders().get(0);
				
				Optional<Star> targetStarOptional = stars.stream().filter(a -> a.getId() == fleetOrder.getStarId()).findFirst();
				
				if (targetStarOptional.isPresent()) {
					Star targetStar = targetStarOptional.get();
					g.drawLine(x, y, converter.getCanvasX(targetStar.getPosX())+planetSize, converter.getCanvasY(targetStar.getPosY())+planetSize);
				}
			}
		}
		
	}

    public void setToolTipToPlanet(MouseEvent event) {
    	if (stars==null) {
    		return;
    	}
    	
		Dimension canvasSize = this.getSize();
		double factorX = (canvasSize.getWidth()-planetSize) / (widthStat.getMax() - widthStat.getMin());
		double factorY = (canvasSize.getHeight()-planetSize) / (heigthStat.getMax() - heigthStat.getMin());
        
		final double gamePosX = (event.getX()/factorX) + widthStat.getMin();
		final double gamePosY = (event.getY()/factorY) + heigthStat.getMin();

		PointInSpace point = new PointInSpace() {
			@Override
			public Double getPosY() {
				return gamePosY;
			}
			
			@Override
			public Double getPosX() {
				return gamePosX;
			}
			
			@Override
			public Integer getId() {
				return null;
			}
		};

		List<DistantPointInSpace<Star>> sortFromClosest = Distance.sortFromClosest(stars, point);
		
		Star closestStar = sortFromClosest.get(0).getPointInSpace();
		
        this.setToolTipText(closestStar.getName());
    }

	public DoubleSummaryStatistics getWidthStat() {
		return widthStat;
	}

	public DoubleSummaryStatistics getHeigthStat() {
		return heigthStat;
	}
	
}

class CoordinateConverter {
	
	private final DoubleSummaryStatistics widthStat;
	private final DoubleSummaryStatistics heigthStat;
	private final double factorX;
	private final double factorY;
	
	public CoordinateConverter(Dimension canvasSize, DoubleSummaryStatistics widthStat, DoubleSummaryStatistics heigthStat) {
		super();
		this.widthStat = widthStat;
		this.heigthStat = heigthStat;
		
		factorX = canvasSize.getWidth() / (widthStat.getMax() - widthStat.getMin());
		factorY = canvasSize.getHeight() / (heigthStat.getMax() - heigthStat.getMin());		
	}
	
	public int getCanvasX(double x) {
		return (int) Math.round((x - widthStat.getMin()) * factorX);
	}
	
	public int getCanvasY(double y) {
		return (int) Math.round((y - heigthStat.getMin()) * factorY);		
	}
	
	public double getOriginalX(int x) {
		return (x/factorX) + widthStat.getMin();
	}

	public double getOriginalY(int y) {
		return (y/factorY) + heigthStat.getMin();		
	}
	
}