package wallapop;

import org.graphstream.algorithm.BellmanFord;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.LayerRenderer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CinemaSchedule {

    private static final String styleSheet = "url('file:///C:/Users/drexa/git/snippets/src/wallapop/graph.css')";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("y-M-d H:mm");

    private static ArrayList<Show> shows = new ArrayList<Show>();
    static {
        try {
            shows.add(new Show("Show1", sdf.parse("2013-08-06 18:00"), sdf.parse("2013-08-06 20:00")));
            shows.add(new Show("ShowMorning", sdf.parse("2013-08-06 12:00"), sdf.parse("2013-08-06 15:00")));
            shows.add(new Show("Show2a", sdf.parse("2013-08-06 19:00"), sdf.parse("2013-08-06 21:00")));
            shows.add(new Show("ShowNextday2", sdf.parse("2013-08-07 00:30"), sdf.parse("2013-08-07 02:00")));
            shows.add(new Show("Show2b", sdf.parse("2013-08-06 19:00"), sdf.parse("2013-08-06 21:00")));
            shows.add(new Show("Show3", sdf.parse("2013-08-06 20:00"), sdf.parse("2013-08-06 22:00")));
            shows.add(new Show("Show4", sdf.parse("2013-08-06 22:30"), sdf.parse("2013-08-06 23:45")));
            shows.add(new Show("ShowLong", sdf.parse("2013-08-06 19:30"), sdf.parse("2013-08-06 23:00")));
            shows.add(new Show("ShowNextday1", sdf.parse("2013-08-06 23:30"), sdf.parse("2013-08-07 03:00")));
            shows.add(new Show("ShowNextday3", sdf.parse("2013-08-07 01:30"), sdf.parse("2013-08-07 03:30")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        createGraph();
    }

    private static void createGraph() {

        Graph graph = new SingleGraph("Schedule");
        initGraphUI(graph);

        // add nodes
        shows.stream().forEach(current -> {
            Node node = graph.addNode(current.getName());
            node.addAttribute("ui.label", current.toString());
        });
        // add edges
        shows.stream().forEach(current -> {
            for(Show showAfter : getShowsAfter(current)) {
                graph.addEdge("edge"+(graph.getEdgeCount()+1), current.getName(), showAfter.getName(), true);
            }
        });

    }

    private static List<Show> getShowsAfter(Show show) {
        return shows.stream().filter(remaining -> remaining.getStartTime().after(show.getEndTime())).collect(Collectors.toList());
    }

    private static void initGraphUI(Graph graph) {
        // Bypass the lightweight viewer
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        Viewer viewer = graph.display();
        DefaultView view = (DefaultView) viewer.getDefaultView();
        view.setBackLayerRenderer(new LayerRenderer() {
            @Override
            public void render(Graphics2D graphics2D, GraphicGraph graphicGraph, double v, int i, int i1, double v1, double v2, double v3, double v4) {

                graphics2D.drawString("hello", 10, 30);

                graphics2D.getDeviceConfiguration().getDevice().setFullScreenWindow(null);
                Rectangle bounds = graphics2D.getDeviceConfiguration().getBounds();
                int w =  (int) bounds.getWidth();
                int h = (int) bounds.getHeight()/2;
                Rectangle stripe = new Rectangle(0, 0, w, h);
                graphics2D.setColor(Color.red);
                graphics2D.fill(stripe);

            }
        });
    }

}

