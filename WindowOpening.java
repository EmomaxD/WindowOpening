import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.lang.Math;

class WindowOpening{

     static String api_URL = "http://api.weatherapi.com/v1/current.json?key=c5029df5d2c34a07b53154946220101&q=Sakarya&aqi=no";
     static float humidity;
     static float Temperature;
     static String windSpeed;
     static String windDegree;
     static String airCondition;
     static String feltTemperature;   

    public static void main(String[] args) throws IOException {  

        //setCity("Sakarya");

        //Main Function
        if(!CheckIfExists("weatherData.json")){
            WriteJSONFile(ParseHTMLBody(api_URL)); 
        }else{
            System.out.println("File already exists!\nFile Updated!");
            WriteJSONFile(ParseHTMLBody(api_URL));
        }
       
        //Set variables
        windDegree=WeatherInfo("current", "wind_degree","weatherData.json").toString();
        Temperature=Float.parseFloat(WeatherInfo("current","temp_c","weatherData.json").toString()) ;
        humidity=Float.parseFloat(WeatherInfo("current","humidity","weatherData.json").toString());
        windSpeed=WeatherInfo("current", "wind_kph","weatherData.json").toString();
        airCondition=WeatherInfo("current","condition","weatherData.json").toString();
        airCondition=secondParse("text", airCondition);
        feltTemperature=WeatherInfo("current","feelslike_c","weatherData.json").toString();
    
        //----------------------------------------------------------------------- 
        System.out.println("Air Condition : "+airCondition);
        System.out.println("Wind Degree : "+windDegree);
        System.out.println("Wind Speed : "+windSpeed);
        System.out.println("Felt Temperature : "+feltTemperature);
        System.out.println("Time : "+returnTime(Float.parseFloat(feltTemperature)));
        DeleteFile("weatherData.json");
        
    }
       
    //Functions
    static void DeleteFile(String file){
        File f = new File(file);
        f.delete();
    }
    static String secondParse(String e,String File){
        String data;
        JSONObject obj = new JSONObject(File);
        data=obj.get(e).toString();
        return data;
    }
    static void setCity(String e){
        api_URL="http://api.weatherapi.com/v1/current.json?key=c5029df5d2c34a07b53154946220101&q="+e+"&aqi=no";
        System.out.println("City changed as "+e+"");
    }
    static boolean CheckIfExists(String e){
        File tempFile = new File(e);
        boolean exists = tempFile.exists();
        
        return exists;
    }
    static void WriteJSONFile(String e)throws IOException{
        FileWriter f=new FileWriter("weatherData.json");
        f.write(e);
        f.close();
    }
    static String ParseHTMLBody(String link)throws IOException{
        Document html = Jsoup.connect(link).ignoreContentType(true).get();      
        String Body = html.body().text();    
        return Body;
    }
    static Object WeatherInfo(String object,String valueOf,String File)throws IOException{
        File myObj = new File(File);
        Object e;
        String data="";
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
    static Object WeatherInfo(String valueOf,String File)throws IOException{
        File myObj = new File(File);
        Object e;
        String data="";
        Scanner myReader = new Scanner(myObj);

        while (myReader.hasNextLine()) {
          data = myReader.nextLine();
        }

        myReader.close();

        JSONObject obj = new JSONObject(data);
         e =obj.get(valueOf);        
        return e;
    }
    static float feltTemperature(float T,float H){
        float c1 = -42;
        float   c2 =2.05f;
        float  c3 = 10.14f;
        float   c4 = -0.224f;
        float  c5 = -6.83f/1000;
        float c6 = -5.481f/100f;
        float c7 =1.228f/1000f;
        float c8 = 8.52f/10000f;
        float  c9 = -1.99f/1000000f;
        float feltTemperature=0;

        T*=9;
        T/=5;
        T+=32;
        feltTemperature=c1+c2*T+c3*H+c4*T*H+c5*T*T+c6*H*H+c7*T*T*H+c8*T*H*H+c9*T*T*H*H;
        
        feltTemperature-=32;
        feltTemperature/=9;
        feltTemperature*=5;
        
        return feltTemperature;
    }
    static float returnTime(float feltTemp){
       float time=0;
       
       if(feltTemp<0&&feltTemp>-10){time=13-Math.abs(feltTemp);}
       else if(feltTemp==0){time=2;}
       else if(feltTemp>0&&feltTemp<10){time=4*feltTemp;}
       else if(feltTemp>10&&feltTemp<20){time=14*feltTemp-100;}
       else if(feltTemp>20&&feltTemp<25){time=feltTemp*feltTemp;}

        
        return time;
    }
}
