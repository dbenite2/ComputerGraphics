/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene;

import Math.Point;
import Math.Vector4;
import Math.Ray;
import java.lang.Math;

/**
 *
 * @author htrefftz
 */
public class Shader {
    /**
     * Computes the color of a point "point", on a surface with normal "normal",
     * given the material properties of the object "material"
     * @param point 3D coordinates of the point
     * @param normal normal of the surface at point "point"
     * @param material material  of the object 
     * @return 
     */
    public static Colour computeColor(Point point, Vector4 normal, Material material) {
        normal.normalize();
        // We will add all the colors in acum
        Colour acum = new Colour(0, 0, 0);
        // Compute the Ambient Reflection
        Colour AmbientReflection = Colour.multiply(Colour.multiply(Scene.ambientLight.color, material.color), 
                material.Ka);
        acum = Colour.add(acum, AmbientReflection);
        // Compute the Diffuse Reflection, respect to all point lights
        for(PointLight pl: Scene.pointLights) {
            Vector4 light = new Vector4(point, pl.point);
            Ray shadowRay = new Ray(point, light);
            // Check if the object is in the shadow with respect to this source
            // of life. If it is, do not add diffuse reflection
            if(!Scene.intersectRayForShadow(shadowRay)) {
                light.normalize();
                // Acá se debe agregar el producto entre normal y light (***)
                double scalar = material.Kd;
                double result = Vector4.dotProduct(normal, light);
                // If dot product is < 0, the point is not receiving light from
                // this source.
                if(result < 0) result = 0;
                if(scalar < 0) scalar = 0;
                Colour DiffuseReflection = Colour.multiply(Colour.multiply(Colour.multiply(pl.color, material.color), 
                        scalar),result);
                acum = Colour.add(acum, DiffuseReflection);
            }
        }
        // Compute the Specular Reflection
        for(PointLight pl: Scene.pointLights){
            Vector4 light = new Vector4 (point,pl.point);
            Vector4 reflex = Vector4.reflection(light, normal);
            Vector4 observer =  new Vector4(point, new Point(0,0,0));
            reflex.normalize();
            observer.normalize();
            double scalar = material.Ks;
            double result = Math.pow(Vector4.dotProduct(reflex,observer),material.n);
            double lessLight = Vector4.dotProduct(normal, Vector4.multiply(-1, light));
            if(scalar < 0 ) scalar = 0;
            if(lessLight < 0) result = 0;
            Colour SpecularReflection = Colour.multiply(Colour.multiply(pl.color,scalar),result);
            acum = Colour.add(acum,SpecularReflection);
            
            
        }
        // removed the calculation of the Specular Reflection component
        
        return acum;
    }
}
