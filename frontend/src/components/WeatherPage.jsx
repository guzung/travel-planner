import { useCallback } from "react";
import { getWeather } from "../services/forecast";
import WeatherForm from "./WeatherForm";

const WeatherPage = () => {
  const handleSubmit = useCallback((city, date) => getWeather(city, date), []);

  return <WeatherForm onSubmit={handleSubmit} />;
};

export default WeatherPage;
