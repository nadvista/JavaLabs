public class Point3d
{
    private double _x;
    private double _y;
    private double _z;

    public Point3d(double x, double y, double z)
    {
        _x = x;
        _y = y;
        _z = z;
    }
    
    public double GetX() 
    {
        return _x;
    }

    public void SetX(double _x) 
    {
        this._x = _x;
    }
    public double GetY() 
    {
        return _y;
    }

    public void SetY(double _y) 
    {
        this._y = _y;
    }

    public double GetZ() 
    {
        return _z;
    }

    public void SetZ(double _z) 
    {
        this._z = _z;
    }

    public boolean IsEquals(Point3d other){
        return (GetX() == other.GetX()) && (GetY() == other.GetY()) && (GetZ() == other.GetZ());
    }
    
    public static boolean IsEquals(Point3d a, Point3d b) {
        return (a.GetX() == b.GetX()) && (a.GetY() == b.GetY()) && (a.GetZ() == b.GetZ());
    }
    public double DistanceTo(Point3d other)
    {
        double distance = Math.sqrt(
            Math.pow(other.GetX() - GetX(),2 ) + 
            Math.pow(other.GetY() - GetY(),2 ) + 
            Math.pow(other.GetZ() - GetZ(),2 )
        );
        var places = 2;
        //округление до 2-х знаков
        var factor = (long) Math.pow(10, places);
        distance = distance * factor;
        var tmp = Math.round(distance);
        return (double) tmp / factor;
    }
}