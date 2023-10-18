interface myInterface{
    public String drivingSpeed();
}

enum VehicleSpeed implements myInterface{
    SLOW(20)
    {
        public String drivingSpeed(){return "Slow";}
    },

    MEDUIM(60){public String drivingSpeed(){return "Medium";}},

    FAST(120){public String drivingSpeed(){return "Fast";}};

    private int speed;

    private VehicleSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed(){
        return speed;
    }
}

public class TestClass {
    public static void main(String[] args)
    {
        VehicleSpeed v1 = VehicleSpeed.SLOW;

    }
}
