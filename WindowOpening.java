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
     static float pressure;
     static String lastUpdatedTime;
     static String windDirectory;

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
        pressure=Float.parseFloat(WeatherInfo("current", "pressure_mb", "weatherData.json").toString());
        lastUpdatedTime=(WeatherInfo("current", "last_updated", "weatherData.json").toString());
        windDirectory=(WeatherInfo("current", "wind_dir", "weatherData.json").toString());
    
        //----------------------------------------------------------------------- 
        System.out.println("Air Condition : "+airCondition);
        System.out.println("Wind Degree : "+windDegree);
        System.out.println("Wind Speed : "+windSpeed);
        System.out.println("Humidity : "+humidity);
        System.out.println("Pressure : "+pressure);
        System.out.println("Wind Directory : "+windDirectory);
        System.out.println("Felt Temperature : "+feltTemperature);
        System.out.println("Time : "+returnTime(Float.parseFloat(feltTemperature)));
        System.out.println("Last Updated : "+ lastUpdatedTime);
        DeleteFile("weatherData.json");
        
    }
       
    //Functions
    static void DeleteFile(String file){
        File f = new File(file);
        f.delete();
    }
    static String secondParse(String e,String File){
        JSONObject obj = new JSONObject(File);
        return obj.get(e).toString();
        
    }
    static void setCity(String e){
        api_URL="http://api.weatherapi.com/v1/current.json?key=c5029df5d2c34a07b53154946220101&q="+e+"&aqi=no";
        System.out.println("City changed as "+e+"");
    }
    static boolean CheckIfExists(String e){
        File tempFile = new File(e);
        return tempFile.exists();           
    }
    static void WriteJSONFile(String e)throws IOException{
        FileWriter f=new FileWriter("weatherData.json");
        f.write(e);
        f.close();
    }
    static String ParseHTMLBody(String link)throws IOException{
        Document html = Jsoup.connect(link).ignoreContentType(true).get();      
       return  html.body().text();    
        
    }
    static Object WeatherInfo(String object,String valueOf,String File)throws IOException{
        File myObj = new File(File);      
        Scanner myReader = new Scanner(myObj);
        
        JSONObject obj = new JSONObject(myReader.nextLine());
        myReader.close();
        return obj.getJSONObject(object).get(valueOf);
        
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
