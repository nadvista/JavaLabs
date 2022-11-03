import java.util.Scanner;

public class Lab1 
{
    public static void main(String[] args)
    {
        var points = new Point3d[3];
        var reader = new Scanner(System.in);

        for(int i = 0; i < 3; i++)
        {
            var x = reader.nextDouble();
            var y = reader.nextDouble();
            var z = reader.nextDouble();
            var point = new Point3d(x, y, z);
            points[i] = point;
        }
        var area = ComputeArea(points[0], points[1], points[2]);
        if(points[0].IsEquals(points[1]) || 
           points[0].IsEquals(points[2]) || 
           points[1].IsEquals(points[2]))
            System.out.println("Error: Some points match");
        else
            System.out.println(String.format("The area of the triangle formed: %s", area));

        reader.close();
    }

    private static double ComputeArea(Point3d p1, Point3d p2, Point3d p3)
    {
        var side1 = p1.DistanceTo(p2);
        var side2 = p1.DistanceTo(p3);
        var side3 = p2.DistanceTo(p3);

        var p = (side1 + side2 + side3) / 2;
        var area = Math.sqrt(
            p * (p - side1) * 
            (p - side2) * 
            (p - side3)
        );
        return area;
    }
}
