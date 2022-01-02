import java.io.IOException;
import java.net.*;
import java.util.*;
import org.json.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


  


class WindowOpening{
     static String api_URL = "http://api.weatherapi.com/v1/current.json?key=c5029df5d2c34a07b53154946220101&q=Sakarya&aqi=no";
     static String weatherData;
     static URL url;
     static Scanner scanner;
     static double humidity;
     static double Temperature;
     static String windSpeed;
     static String airCondition;
     static String feltTemperature;
     
     static String data;
     static JSONObject wJ = new JSONObject();


      

    public static void main(String[] args) throws IOException {  

        //Main Function
        WriteTXT(ParseHTMLBody()); 

        //Set variables
        Temperature=Double.parseDouble(WeatherInfo("current","temp_c").toString()) ;
        humidity=Double.parseDouble(WeatherInfo("current","humidity").toString());
        windSpeed=WeatherInfo("current", "wind_kph").toString();
        //airCondition=WeatherInfo("current","condition").toString();
        feltTemperature=WeatherInfo("current","feelslike_c").toString();

        //-----------------------------------------------------------------------

        


        System.out.println(feltTemperature);                       
    }

    //Functions
    static void WriteTXT(String e)throws IOException{
        FileWriter f=new FileWriter("weatherData.json");
        f.write(e);
        f.close();
    }
    static String ParseHTMLBody()throws IOException{
        Document html = Jsoup.connect(api_URL).ignoreContentType(true).get();      
        String Body = html.body().text();    
        System.out.println(Body);
        return Body;
    }
    static String getFileData()throws IOException{
        File myObj = new File("weatherData.json");
        myObj.delete();
        PutDataToJSON(wJ);
        CreateJsonFile();
        Scanner myReader = new Scanner(myObj);

        while (myReader.hasNextLine()) {
          data = myReader.nextLine();
        }
        myReader.close();
        return data;
    }
    static Object WeatherInfo(String object,String valueOf)throws IOException{
        File myObj = new File("weatherData.json");
       Object e;
      
        Scanner myReader = new Scanner(myObj);

        while (myReader.hasNextLine()) {
          data = myReader.nextLine();
        }


        myReader.close();
        JSONObject obj = new JSONObject(data);
         e =obj.getJSONObject(object).get(valueOf);
         
         


        return e;
    }
    //Override
    static Object WeatherInfo(String valueOf)throws IOException{
        File myObj = new File("weatherData.json");
        Object e;
      
        Scanner myReader = new Scanner(myObj);

        while (myReader.hasNextLine()) {
          data = myReader.nextLine();
        }


        myReader.close();
        JSONObject obj = new JSONObject(data);
         e =obj.getString(valueOf);
         
         


        return e;
    }
    static void PutDataToJSON(JSONObject js)throws IOException{
        
        url=new URL(api_URL);
        scanner = new Scanner(url.openStream());
        while(scanner.hasNext()){
            //System.out.println(scanner.nextLine());
            
           js.put("general", scanner.nextLine());
        
        }
          

       
    }
    static void CreateJsonFile()throws IOException{
        // writing JSON to file:"JSONExample.json" in cwd
        PrintWriter pw = new PrintWriter("weatherDataAUTO.json");
        pw.write(wJ.toString());

        pw.flush();
        pw.close();

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

        T*=9;
        T/=5;
        T+=32;
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