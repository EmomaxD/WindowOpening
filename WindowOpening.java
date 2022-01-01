import java.io.IOException;
import java.net.*;
import java.util.*;

  


class WindowOpening{
     static String api_URL = "http://api.weatherapi.com/v1/current.json?key=c5029df5d2c34a07b53154946220101&q=Sakarya&aqi=no";
     static String weatherInfo = "";
     static URL url;
     static Scanner scanner;
     static String humidity;
     static String Temperature;
     static String windSpeed;
     static String airCondition;
     static String feltTemperature;
     
      

    public static void main(String[] args) throws IOException {

        System.out.println(feltTemperature(90,40));
        System.out.println(WeatherInfo());
        weatherInfo=WeatherInfo().toString();       
    }

    //Functions
    static List<String> WeatherInfo()throws IOException{
        List<String> info =new ArrayList<>();
        url=new URL(api_URL);
        scanner = new Scanner(url.openStream());
        while(scanner.hasNext()){
            //System.out.println(scanner.nextLine());
            

            info.add(scanner.nextLine());
            
        }


        return info;
    }
    static double feltTemperature(double T,double H){
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