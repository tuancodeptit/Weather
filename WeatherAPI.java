import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherAPI {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter city name: ");
            String cityName = scanner.nextLine();
            scanner.close();

            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=44d944f4fa48537c8e6328e57a9a8bd0&units=metric&lang=vi");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //kiem tra ket noi
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner2 = new Scanner(url.openStream());

                while (scanner2.hasNext()) {
                    informationString.append(scanner2.nextLine());
                }
                //Close the scanner
                scanner2.close();

                System.out.println(informationString);


                //Thư viện JSON đơn giản Thiết lập với Maven được sử dụng để chuyển đổi chuỗi thành JSON
                JSONParser parse = new JSONParser();
                JSONObject dataObject = (JSONObject) parse.parse(String.valueOf(informationString));

                //Lấy thông tin thời tiết từ đối tượng JSON
                JSONObject mainObject = (JSONObject) dataObject.get("main");
                Double temperature = (Double) mainObject.get("temp");
                Double precipitation = (Double) mainObject.get("rain");
                Double humidity = (Double) mainObject.get("humidity");
                JSONObject windObject = (JSONObject) dataObject.get("wind");
                Double windSpeed = (Double) windObject.get("speed");

                System.out.println("Humidity: " + humidity + " %");
                System.out.println("Wind speed: " + windSpeed + " km/h");
                System.out.println("Temperature in " + cityName + " is " + temperature + "°C");
                System.out.println("Precipitation: " + precipitation + " mm");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
