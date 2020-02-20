package frc.robot;

public class OI {
    private static OI INSTANCE = new OI();

    private OI(){
    }

    public static OI getINSTANCE(){
        return INSTANCE;
    }
}
