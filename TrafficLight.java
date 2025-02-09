public class TrafficLight {
    private int x;
    private int y;
    public String facingDirection;
    public LightColor color;

    public TrafficLight(int x, int y, String facingDirection){
        this.x = x;
        this.y = y; 
        this.facingDirection = facingDirection;
        this.color = LightColor.RED;
    }

    public void changeLight(LightColor c){
        this.color = c;
    }

    public LightColor getLightColor() {
        return this.color;
    }

    public String getFacingDirection(){
        return this.facingDirection;
    }
    
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public LightColor getColor(){
        return this.color;
    }
}
