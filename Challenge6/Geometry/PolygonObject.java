package Geometry;
/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */

 import java.util.ArrayList;
 import Math.Matrix4x4;
 import math.Vector4;

 public class PolygonObject{

    ArrayList<Edge> edges;

    public PolygonObject(){
        edges = new ArrayList<>();
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    public void drawObject(DrawBoat db){
        for(Edge edge: edges){
            drawOneLine(db,edge);
        }
    }

    public void drawOneLine(DrawBoat db, Edge edge){
        int x1 = (int) edge.start.getX();
        int x2 = (int) edge.end.getX();
        int y1 = (int) edge.start.gety();
        int y2 = (int) edge.end.getX();

        db.drawOneLine(x1,y1,x2,y2);
    }

    public void transformObject(Matrix4x4 transformation){
        for(Edge e:edges){
            e.start = Matrix4x4.times1(transformation, e.start);
            e.end   = Matrix4x4.times1(transformation, e.end);
        }
    }

    public static PolygonObject transformObject(PolygonObject po, Matrix4x4 transformation){
        PolygonObject newObject = new PolygonObject();
        for(Edge e: po.edges){
            Vector4 newStart = Matrix4x4.times(transformation,e.start);
            Vector4 newEnd = Matrix4x4.times(transformation,e.end);
            Edge newEdge = new Edge(newStart, newEnd);
            newObject.addEdge(newEdge);
        }
    }

 }