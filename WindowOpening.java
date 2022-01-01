class WindowOpening{
      
    public static void main(String[] args) {
System.out.println(feltTemperature(90,40));
    }

    //Functions
    static double feltTemperature(int T,int H){
        double c1 = -42;
        double   c2 =2.05;
        double  c3 = 10.14;
        double   c4 = -0.224;
        double  c5 = -6.83/1000;
        double c6 = -5.481/100;
        double c7 =1.228/1000 ;
        double c8 = 8.52/10000;
        double  c9 = -1.99/1000000;
        double feltTemperature=0;
        feltTemperature=c1+c2*T+c3*H+c4*T*H+c5*T*T+c6*H*H+c7*T*T*H+c8*T*H*H+c9*T*T*H*H;
        
        feltTemperature-=32;
        feltTemperature/=9;
        feltTemperature*=5;
        
        return feltTemperature;
    }
     static int returnTime(int feltTemp){
       int time=0;

        
        return time;
    }
  
}