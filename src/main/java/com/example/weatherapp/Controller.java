package com.example.weatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;

public class Controller {
    @FXML
    private ImageView bgImage;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private CheckBox nextCheckBox;

    @FXML
    private RadioButton rButtonEN, rButtonRU, rButtonUA;

    @FXML
    private Text weatherText, informationText, city_error, language_error, pressure, temp_feel, temp_info, temp_max, temp_min, humidity, sky, wind_speed, part_day, sunrise, sunset;

    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            String output = getUrlContent("https://api.openweathermap.org/data/2.5/forecast?q=" + getUserCity + "&appid=abbfc7e3b5c02348decb6a4ab37ef001&units=metric&cnt=1"); //&units=metric - норм ед. измерения; c&cnt=1 - 1 экземпляр

            if (!output.isEmpty()) {
                JSONObject object = new JSONObject(output).getJSONArray("list").getJSONObject(0);
                JSONObject object1 = object.getJSONObject("main");

                String sunriseTime = Instant.ofEpochSecond((long) new JSONObject(output).getJSONObject("city").getInt("sunrise") + 10800).toString(); //+ 10800 перевод на европейское время
                String sunsetTime = Instant.ofEpochSecond((long) new JSONObject(output).getJSONObject("city").getInt("sunset") + 10800).toString();

                if (rButtonEN.isSelected()){
                    weatherText.setText("Weather");
                    city.setPromptText("Enter a city");
                    getData.setText("Get results");
                    informationText.setText("Information");
                    nextCheckBox.setText("Next");
                    city_error.setText("This city doesn't exist");

                    temp_info.setText("Temperature: " + object1.getDouble("temp") + " degrees Celsius");
                    temp_feel.setText("Feels like: " + object1.getDouble("feels_like") + " degrees Celsius");
                    temp_max.setText("Maximum: " + object1.getDouble("temp_max") + " degrees Celsius");
                    temp_min.setText("Minimum: " + object1.getDouble("temp_min") + " degrees Celsius");
                    pressure.setText("Pressure: " + object1.getDouble("pressure") + " hectopascals");
                    humidity.setText("Humidity: " + object1.getDouble("humidity") + " percent");

                    sky.setText("Sky: " + object.getJSONArray("weather").getJSONObject(0).getString("main"));
                    wind_speed.setText("Wind speed: " + String.format("%.2f", (object.getJSONObject("wind").getDouble("speed") * 3.2)) + " km/h"); //* 3.2: м/с -> км/ч

                    if(object.getJSONObject("sys").getString("pod").equals("d")){
                        part_day.setText("Part of the day: Day");
                    } else{
                        part_day.setText("Part of the day: Night");
                    }

                    sunrise.setText("Sunrise: " + sunriseTime.substring(0, 10) + " " + sunriseTime.substring(11, 19));
                    sunset.setText("Sunset: " + sunsetTime.substring(0, 10) + " " + sunsetTime.substring(11, 19));
                } else if (rButtonRU.isSelected()){
                    weatherText.setText("Погода");
                    city.setPromptText("Введите город");
                    getData.setText("Узнать статус");
                    informationText.setText("Информация");
                    nextCheckBox.setText("Дальше");
                    city_error.setText("Этого города не существует");

                    temp_info.setText("Температура: " + object1.getDouble("temp") + " градусов Цельсия");
                    temp_feel.setText("Чувствуется как: " + object1.getDouble("feels_like") + " градусов Цельсия");
                    temp_max.setText("Максимум: " + object1.getDouble("temp_max") + " градусов Цельсия");
                    temp_min.setText("Минимум: " + object1.getDouble("temp_min") + " градусов Цельсия");
                    pressure.setText("Давление: " + object1.getDouble("pressure") + " гектопаскалей");
                    humidity.setText("Влажность: " + object1.getDouble("humidity") + " процентов");

                    if (object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Clear")){
                        sky.setText("Небо: " + "Чисто");
                    } else if (object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Clouds")){
                        sky.setText("Небо: " + "Облачно");
                    } else if (object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Rain")){
                        sky.setText("Небо: " + "Дождь");
                    } else

                    wind_speed.setText("Скорость ветра: " + String.format("%.2f", (object.getJSONObject("wind").getDouble("speed") * 3.2)) + " км/ч");

                    if(object.getJSONObject("sys").getString("pod").equals("d")){
                        part_day.setText("Часть дня: День");
                    } else{
                        part_day.setText("Часть дня: Ночь");
                    }

                    sunrise.setText("Восход Солнца: " + sunriseTime.substring(0, 10) + " " + sunriseTime.substring(11, 19));
                    sunset.setText("Закат: " + sunsetTime.substring(0, 10) + " " + sunsetTime.substring(11, 19));
                } else if (rButtonUA.isSelected()){
                    weatherText.setText("Погода");
                    city.setPromptText("Введіть місто");
                    getData.setText("Результат");
                    informationText.setText("Інформація");
                    nextCheckBox.setText("Далі");
                    city_error.setText("Цього міста не існує");

                    temp_info.setText("Температура: " + object1.getDouble("temp") + " градусів Цельсія");
                    temp_feel.setText("Відчувається як: " + object1.getDouble("feels_like") + " градусів Цельсія");
                    temp_max.setText("Максимум: " + object1.getDouble("temp_max") + " градусів Цельсія");
                    temp_min.setText("Мінімум: " + object1.getDouble("temp_min") + " градусів Цельсія");
                    pressure.setText("Тиск: " + object1.getDouble("pressure") + " гектопаскалів");
                    humidity.setText("Вологість: " + object1.getDouble("humidity") + " відсотків");

                    if (object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Clear")){
                        sky.setText("Небо: " + "Чисто");
                    } else if (object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Clouds")){
                        sky.setText("Небо: " + "Хмарно");
                    } else if (object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Rain")){
                        sky.setText("Небо: " + "Дощ");
                    }

                    wind_speed.setText("Швидкість вітру: " + String.format("%.2f", (object.getJSONObject("wind").getDouble("speed") * 3.2)) + " км/год");

                    if(object.getJSONObject("sys").getString("pod").equals("d")){
                        part_day.setText("Частина дня: День");
                    } else{
                        part_day.setText("Часть дня: Ніч");
                    }

                    sunrise.setText("Схід сонця: " + sunriseTime.substring(0, 10) + " " + sunriseTime.substring(11, 19));
                    sunset.setText("Захід сонця: " + sunsetTime.substring(0, 10) + " " + sunsetTime.substring(11, 19));
                } else {
                    language_error.setOpacity(1);
                }

                if (rButtonEN.isSelected() || rButtonRU.isSelected() || rButtonUA.isSelected()){
                    if (object.getJSONObject("sys").getString("pod").equals("d") && object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Clear")) {
                        bgImage.setImage(new Image("https://w0.peakpx.com/wallpaper/312/954/HD-wallpaper-light-blue-azzurro-blue-sky-celeste-cielo-clear-color-sky.jpg"));
                    } else if (object.getJSONObject("sys").getString("pod").equals("d") && object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Clouds")) {
                        bgImage.setImage(new Image("https://s3.envato.com/files/248886220/preview.jpg"));
                    } else if (object.getJSONObject("sys").getString("pod").equals("d") && object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Rain")) {
                        bgImage.setImage(new Image("https://wallpaperaccess.com/full/822274.jpg"));
                    } else if (object.getJSONObject("sys").getString("pod").equals("n") && object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Clear")) {
                        bgImage.setImage(new Image("https://i.pinimg.com/originals/b2/70/d9/b270d96b704103fd08b19646c0e87992.jpg"));
                    } else if (object.getJSONObject("sys").getString("pod").equals("n") && object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Clouds")) {
                        bgImage.setImage(new Image("https://static.vecteezy.com/system/resources/thumbnails/001/625/795/original/lightning-storm-time-lapse-free-video.jpg"));
                    } else if (object.getJSONObject("sys").getString("pod").equals("n") && object.getJSONArray("weather").getJSONObject(0).getString("main").equals("Rain")) {
                        bgImage.setImage(new Image("https://c0.wallpaperflare.com/preview/4/348/908/black-close-up-dark-dew.jpg"));
                    }

                    language_error.setOpacity(0);
                }

                city_error.setOpacity(0);
            } else {
                city_error.setOpacity(1);
            }
        });
    }

    @SuppressWarnings("unused")
    @FXML
    public void change(ActionEvent event) {

        if(nextCheckBox.isSelected()) {
            temp_info.setOpacity(0);
            temp_feel.setOpacity(0);
            temp_max.setOpacity(0);
            temp_min.setOpacity(0);
            pressure.setOpacity(0);
            humidity.setOpacity(0);

            sky.setOpacity(1);
            wind_speed.setOpacity(1);
            part_day.setOpacity(1);
            sunrise.setOpacity(1);
            sunset.setOpacity(1);
        }
        else {
            temp_info.setOpacity(1);
            temp_feel.setOpacity(1);
            temp_max.setOpacity(1);
            temp_min.setOpacity(1);
            pressure.setOpacity(1);
            humidity.setOpacity(1);

            sky.setOpacity(0);
            wind_speed.setOpacity(0);
            part_day.setOpacity(0);
            sunrise.setOpacity(0);
            sunset.setOpacity(0);
        }
    }

    private static String getUrlContent(String urlAdress){
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e){
            System.out.println("This city doesn't exist");
        }

        return content.toString();
    }
}