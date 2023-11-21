package Utility.Math;

public class Vector2
{
    public float x;
    public float y;

    public Vector2()
    {
    }

    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void Set(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2 Normalized()
    {
        float sqrt = (float) Math.sqrt(x * x + y * y);
        return new Vector2(x / sqrt, y / sqrt);
    }

    public float Magnitude()
    {
        return (float) Math.sqrt(x * x + y * y);
    }
}
